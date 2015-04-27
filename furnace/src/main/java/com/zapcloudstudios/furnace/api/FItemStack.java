package com.zapcloudstudios.furnace.api;

public interface FItemStack extends FurnaceI
{
	public FItem item();
	
	public int metadata();
	
	public int size();
	
	public void rename(String name);
}
