package com.zapcloudstudios.stonebrick.api.entity;

import net.minecraft.entity.player.EntityPlayer;

import com.zapcloudstudios.furnace.api.entity.FPlayer;

public class SBPlayer extends SBEntity implements FPlayer
{
	public EntityPlayer player;
	
	public SBPlayer(EntityPlayer player)
	{
		super(player);
		this.player = player;
	}
	
	@Override
	public void rename(String name, boolean visible)
	{
		
	}
	
	@Override
	public String getName()
	{
		return this.player.getName();
	}
}
