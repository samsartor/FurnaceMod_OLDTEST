package com.zapcloudstudios.furnace.api;

public interface FBlockPos extends FurnaceI
{
	public int[] pos();
	
	public FBlock block();
	
	public void set(FBlock block, int state);
	
	public void update();
}
