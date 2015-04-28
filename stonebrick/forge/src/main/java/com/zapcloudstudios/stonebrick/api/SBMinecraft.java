package com.zapcloudstudios.stonebrick.api;

import com.zapcloudstudios.furnace.api.FMinecraft;
import com.zapcloudstudios.furnace.api.FWorld;

public class SBMinecraft implements FMinecraft
{
	public SBMinecraft()
	{
	}
	
	@Override
	public void sendChat(String msg)
	{
	}
	
	@Override
	public FWorld getWorld(int dimention)
	{
		return null;
	}
}
