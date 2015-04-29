package com.zapcloudstudios.furnace.api;

import com.zapcloudstudios.furnace.wrap.JSFunc;

public abstract class FMinecraft implements FurnaceI
{
	public abstract void sendChat(String msg);
	
	@JSFunc(name = "getWorld")
	public abstract FWorld getWorld(int dimention);
	
	public abstract void command(String com);
	
	@Override
	public String typeName()
	{
		return "Minecraft";
	}
}
