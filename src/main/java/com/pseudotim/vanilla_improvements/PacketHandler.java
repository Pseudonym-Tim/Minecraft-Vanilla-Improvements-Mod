package com.pseudotim.vanilla_improvements;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class PacketHandler
{
	private static final String PROTOCOL_VER = "1";
	
	public static SimpleChannel instance;
	
	private static SimpleChannel getChannelInstance()
	{
		ResourceLocation resLocation = new ResourceLocation(VanillaImprovements.MOD_ID, "main");
		
		return NetworkRegistry.newSimpleChannel(resLocation, () -> PROTOCOL_VER, PROTOCOL_VER::equals, PROTOCOL_VER::equals);
	}
	
	public static void init()
	{
		// Initialize the instance...
		instance = getChannelInstance();
		
		System.out.println("Init packet handler!");
		
		// Register MessageUpdateAttackYaw message...
		instance.registerMessage(0, MessageUpdateAttackYaw.class, MessageUpdateAttackYaw::encode, MessageUpdateAttackYaw::decode, MessageUpdateAttackYaw::handle);
	}
}
