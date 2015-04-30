package com.zapcloudstudios.furnace.api;

import com.zapcloudstudios.furnace.api.entity.FPlayer;
import com.zapcloudstudios.furnace.wrap.JSFunc;
import com.zapcloudstudios.furnace.wrap.JSGet;

public abstract class FMinecraft implements FurnaceI
{
	public abstract void sendChat(String msg);
	
	@JSFunc(name = "getWorld")
	public abstract FWorld getWorld(int dimention);
	
	@JSGet(name = "players")
	public abstract FPlayer[] getPlayers();
	
	public abstract void command(String com);
	
	@Override
	public String typeName()
	{
		return "Minecraft";
	}
}
