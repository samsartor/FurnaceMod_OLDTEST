package com.zapcloudstudios.furnace;

import com.zapcloudstudios.furnace.api.FBlock;
import com.zapcloudstudios.furnace.api.FChatFormatting;
import com.zapcloudstudios.furnace.api.FItem;
import com.zapcloudstudios.furnace.api.FMinecraft;

public interface IFurnaceImpl
{
	public FBlock[] listBlocks();
	
	public FItem[] listItems();
	
	public FMinecraft getMinecraft();
	
	public FChatFormatting getChatFormatting();
}
