package com.zapcloudstudios.furnace.api;

import com.zapcloudstudios.furnace.wrap.JSFunc;

public abstract class FMinecraft implements FurnaceI
{
	public abstract void sendChat(String msg);
	
	public abstract void command(String com);
	
	public abstract void command(double x, double y, double z, String com);
	
	@JSFunc(name = "getWorld")
	public abstract FWorld getWorld(int dimention);
	
	@Override
	public String typeName()
	{
		return "Minecraft";
	}
}
