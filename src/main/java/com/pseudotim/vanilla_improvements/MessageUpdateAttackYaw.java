package com.pseudotim.vanilla_improvements;

import java.util.function.Supplier;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.LivingEntity;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

public class MessageUpdateAttackYaw
{
	public float attackedAtYaw;
	
	public MessageUpdateAttackYaw() { }
	
	public MessageUpdateAttackYaw(float value)
	{
		attackedAtYaw = value;
	}
	
	public MessageUpdateAttackYaw(LivingEntity entity)
	{
		attackedAtYaw = entity.hurtDir;
	}
	
	public static MessageUpdateAttackYaw encode(MessageUpdateAttackYaw message, FriendlyByteBuf packet)
	{
		packet.writeFloat(message.attackedAtYaw);
		return message;
	}
	
	public static MessageUpdateAttackYaw decode(FriendlyByteBuf packet)
	{
		return new MessageUpdateAttackYaw(packet.readFloat());
	}
	
	public static void handle(MessageUpdateAttackYaw message, Supplier<NetworkEvent.Context> ctx)
	{
		ctx.get().setPacketHandled(true);
		
		// Fuck off if we don't play towards the client..
		if(ctx.get().getDirection() != NetworkDirection.PLAY_TO_CLIENT) { return; }

		ctx.get().enqueueWork(() -> 
		{
			fromMessage(message);
		});
	}
	
	@SuppressWarnings("resource") // (Make Minecraft.getInstance() stop complaining about a memory leak)
	@OnlyIn(Dist.CLIENT)
	public static void fromMessage(MessageUpdateAttackYaw message)
	{
		Minecraft.getInstance().player.hurtDir = message.attackedAtYaw;
	}
}
