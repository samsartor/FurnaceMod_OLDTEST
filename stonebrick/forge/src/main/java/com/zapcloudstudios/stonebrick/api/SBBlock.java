package com.zapcloudstudios.stonebrick.api;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

import com.zapcloudstudios.furnace.api.FBlock;
import com.zapcloudstudios.furnace.api.FItem;

public class SBBlock extends FBlock
{
	private final Block block;
	private final FItem item;
	
	public SBBlock(Block block)
	{
		this.block = block;
		this.id = Block.blockRegistry.getNameForObject(this.block).toString();
		Item i = Item.getItemFromBlock(this.block);
		if (i == null)
		{
			this.item = null;
		}
		else
		{
			this.item = new SBItem(i);
		}
	}
	
	@Override
	public FItem item()
	{
		return this.item;
	}
}
