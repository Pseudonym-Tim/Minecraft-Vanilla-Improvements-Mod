package com.pseudotim.vanilla_improvements.handlers;

import com.pseudotim.vanilla_improvements.VanillaImprovements;
import com.pseudotim.vanilla_improvements.messages.UpdateAttackYawMessage;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class PacketHandler
{
	private static final String PROTOCOL_VER = "1";
	
	public static SimpleChannel instance = getChannelInstance();
	
	private static SimpleChannel getChannelInstance()
	{
		ResourceLocation resLocation = new ResourceLocation(VanillaImprovements.MOD_ID, "main");
		
		return NetworkRegistry.newSimpleChannel(resLocation, () -> PROTOCOL_VER, PROTOCOL_VER::equals, PROTOCOL_VER::equals);
	}
	
	public static void init()
	{
		// Register MessageUpdateAttackYaw message...
		instance.registerMessage(0, UpdateAttackYawMessage.class, UpdateAttackYawMessage::encode, UpdateAttackYawMessage::decode, UpdateAttackYawMessage::handle);
	}
}
