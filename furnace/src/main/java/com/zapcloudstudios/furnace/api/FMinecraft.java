package com.zapcloudstudios.furnace.api;

import com.zapcloudstudios.furnace.api.entity.FPlayer;
import com.zapcloudstudios.furnace.wrap.annotation.APIFunction;
import com.zapcloudstudios.furnace.wrap.annotation.APIGetter;

public abstract class FMinecraft implements FurnaceI
{
	public abstract void sendChat(String msg);
	
	public abstract void sendChat(String from, String msg);
	
	@APIFunction("getWorld")
	public abstract FWorld getWorld(int dimention);
	
	@APIGetter("players")
	public abstract FPlayer[] getPlayers();
	
	public abstract void command(String com);
	
	@Override
	public String typeName()
	{
		return "Minecraft";
	}
}
