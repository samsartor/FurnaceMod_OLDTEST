package com.zapcloudstudios.stonebrick;

import java.util.ArrayList;
import java.util.Iterator;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

import com.zapcloudstudios.furnace.IFurnaceImpl;
import com.zapcloudstudios.furnace.api.FBlock;
import com.zapcloudstudios.furnace.api.FItem;
import com.zapcloudstudios.furnace.api.FMinecraft;
import com.zapcloudstudios.stonebrick.api.SBBlock;
import com.zapcloudstudios.stonebrick.api.SBItem;
import com.zapcloudstudios.stonebrick.api.SBMinecraft;

public class StoneBrick implements IFurnaceImpl
{
	public SBMinecraft mc;
	
	public StoneBrick()
	{
		this.mc = new SBMinecraft();
	}
	
	@Override
	public FBlock[] listBlocks()
	{
		ArrayList<Block> list = new ArrayList<Block>();
		list.ensureCapacity(Block.blockRegistry.getKeys().size());
		Iterator<?> it = Block.blockRegistry.iterator();
		while (it.hasNext())
		{
			Block b = (Block) it.next();
			list.add(b);
		}
		
		FBlock[] blocks = new FBlock[list.size()];
		for (int i = 0; i < blocks.length; i++)
		{
			blocks[i] = new SBBlock(list.get(i));
		}
		
		return blocks;
	}
	
	@Override
	public FItem[] listItems()
	{
		ArrayList<Item> list = new ArrayList<Item>();
		list.ensureCapacity(Item.itemRegistry.getKeys().size());
		Iterator<?> it = Item.itemRegistry.iterator();
		while (it.hasNext())
		{
			Item i = (Item) it.next();
			if (!(i instanceof ItemBlock))
			{
				list.add(i);
			}
		}
		
		FItem[] items = new FItem[list.size()];
		for (int i = 0; i < items.length; i++)
		{
			items[i] = new SBItem(list.get(i));
		}
		
		return items;
	}
	
	@Override
	public FMinecraft getMinecraft()
	{
		return this.mc;
	}
}
