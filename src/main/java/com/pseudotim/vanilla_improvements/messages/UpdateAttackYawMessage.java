package com.pseudotim.vanilla_improvements.messages;

import java.util.function.Supplier;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.LivingEntity;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

public class UpdateAttackYawMessage
{
	public float attackedAtYaw;
	
	public UpdateAttackYawMessage() { }
	
	public UpdateAttackYawMessage(float value)
	{
		attackedAtYaw = value;
	}
	
	public UpdateAttackYawMessage(LivingEntity entity)
	{
		attackedAtYaw = entity.hurtDir;
	}
	
	public static UpdateAttackYawMessage encode(UpdateAttackYawMessage message, FriendlyByteBuf packet)
	{
		packet.writeFloat(message.attackedAtYaw);
		return message;
	}
	
	public static UpdateAttackYawMessage decode(FriendlyByteBuf packet)
	{
		return new UpdateAttackYawMessage(packet.readFloat());
	}
	
	public static void handle(UpdateAttackYawMessage message, Supplier<NetworkEvent.Context> netContext)
	{
		netContext.get().setPacketHandled(true);
		
		// Fuck off if we don't play towards the client..
		if(netContext.get().getDirection() != NetworkDirection.PLAY_TO_CLIENT) { return; }

		netContext.get().enqueueWork(() -> { fromMessage(message); });
	}
	
	@SuppressWarnings("resource") // Stop complaining about a memory leak!)
	@OnlyIn(Dist.CLIENT)
	public static void fromMessage(UpdateAttackYawMessage message)
	{
		Minecraft.getInstance().player.hurtDir = message.attackedAtYaw;
	}
}
