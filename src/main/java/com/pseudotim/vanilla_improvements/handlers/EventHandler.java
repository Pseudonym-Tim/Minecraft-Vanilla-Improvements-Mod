package com.pseudotim.vanilla_improvements.handlers;

import com.pseudotim.vanilla_improvements.messages.UpdateAttackYawMessage;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.network.NetworkDirection;

public class EventHandler
{
	@SubscribeEvent
	public void onKnockback(LivingKnockBackEvent event)
	{
		// If the living entity that was knocked back was the player...
		if(event.getEntityLiving() instanceof Player)
		{
			doPlayerDamageTilt((Player)event.getEntityLiving()); // Do player camera damage tilt...
		}
	}
	
	private void doPlayerDamageTilt(Player player)
	{
		// We only want this code to run server-side!
		if(player.level.isClientSide) { return; }
		
		// Grab server player...
		if(player instanceof ServerPlayer)
		{
			ServerPlayer serverPlayer = (ServerPlayer)player;
			
			if(!canSendPacket(serverPlayer)) { return; } // Piss off if we can't send the packet...
			
			// Tell the player client to update their attack yaw...
			PacketHandler.instance.sendTo(new UpdateAttackYawMessage(player), serverPlayer.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
		}
	}
	
	private boolean canSendPacket(ServerPlayer serverPlayer)
	{
		// Make sure the player and network manager connection isn't null, just in case...
		if(serverPlayer.connection == null || serverPlayer.connection.connection == null)
		{
			return false;
		}
		
		return true;
	}
}
