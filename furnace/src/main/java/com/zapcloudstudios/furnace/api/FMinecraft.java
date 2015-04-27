package com.zapcloudstudios.furnace.api;

public interface FMinecraft extends FurnaceI
{
	public FBlock[] listBlocks();
	
	public FItem[] listItems();
	
	public boolean isRemote();
	
	public void sendChat(String msg);
}
