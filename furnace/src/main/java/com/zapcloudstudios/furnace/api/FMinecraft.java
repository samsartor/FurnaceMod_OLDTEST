package com.zapcloudstudios.furnace.api;

public interface FMinecraft extends FurnaceI
{
	public void sendChat(String msg);
	
	public FWorld getWorld(int dimention);
}
