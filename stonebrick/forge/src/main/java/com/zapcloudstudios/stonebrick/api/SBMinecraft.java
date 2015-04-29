package com.zapcloudstudios.stonebrick.api;

import com.zapcloudstudios.furnace.api.FMinecraft;
import com.zapcloudstudios.furnace.api.FWorld;

public class SBMinecraft extends FMinecraft
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
	
	@Override
	public void command(String com)
	{
		
	}
	
	@Override
	public void command(double x, double y, double z, String com)
	{
		
	}
}
