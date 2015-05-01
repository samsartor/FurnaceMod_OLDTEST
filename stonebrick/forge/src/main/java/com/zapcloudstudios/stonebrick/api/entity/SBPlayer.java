package com.zapcloudstudios.stonebrick.api.entity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
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
	
	@Override
	public int getXP()
	{
		int xp = 0;
		for (int i = 0; i < this.player.experienceLevel; i++)
		{
			xp += this.xpBarCap(i);
		}
		xp += (int) (this.xpBarCap(this.player.experienceLevel) * this.player.experience);
		return xp;
	}
	
	@Override
	public void setXP(int xp)
	{
		if (xp <= 0)
		{
			this.player.experienceTotal = 0;
			this.player.experienceLevel = 0;
			this.player.experience = 0;
			return;
		}
		this.player.experienceTotal = xp;
		this.player.experienceLevel = 0;
		int cap;
		while ((cap = this.xpBarCap(this.player.experienceLevel)) > xp)
		{
			this.player.experienceLevel++;
			xp -= cap;
		}
		this.player.experience = xp / (float) cap;
	}
	
	@Override
	public int getHunger()
	{
		return this.player.getFoodStats().getFoodLevel();
	}
	
	@Override
	public void setHunger(int hunger)
	{
		this.player.getFoodStats().setFoodLevel(hunger);
	}
	
	@Override
	public void sendChat(String msg)
	{
		this.player.addChatComponentMessage(new ChatComponentText(msg));
	}
	
	@Override
	public void sendChat(String from, String msg)
	{
		this.player.addChatComponentMessage(this.sb.mc.createChatFrom(from, msg));
	}
	
	@Override
	public void addPrefix(String text)
	{
		this.player.getPrefixes().add(new ChatComponentText(text));
	}
	
	@Override
	public void addSuffix(String text)
	{
		this.player.getSuffixes().add(new ChatComponentText(text));
	}
	
	@Override
	public void clearPrefix()
	{
		this.player.getPrefixes().clear();
	}
	
	@Override
	public void clearSuffix()
	{
		this.player.getSuffixes().clear();
	}
	
	@Override
	public String getDisplayName()
	{
		return this.player.getDisplayName().getFormattedText();
	}
	
	public int xpBarCap(int level)
	{
		return level >= 30 ? 112 + (level - 30) * 9 : (level >= 15 ? 37 + (level - 15) * 5 : 7 + level * 2);
	}
}
