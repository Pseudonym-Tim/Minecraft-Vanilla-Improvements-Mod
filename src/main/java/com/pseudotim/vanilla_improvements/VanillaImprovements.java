package com.pseudotim.vanilla_improvements;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;

@Mod(VanillaImprovements.MOD_ID)
public class VanillaImprovements 
{
	public static final String MOD_ID = "vanilla_improvements";
	
	public VanillaImprovements()
	{
		// Setup the mod...
		MinecraftForge.EVENT_BUS.register(this);
	}
}
