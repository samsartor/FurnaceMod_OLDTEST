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
		ArrayList<FBlock> blocks = new ArrayList<FBlock>();
		
		Iterator<?> it = Block.blockRegistry.iterator();
		while (it.hasNext())
		{
			Block b = (Block) it.next();
			blocks.add(new SBBlock(b));
		}
		
		FBlock[] list = new FBlock[blocks.size()];
		blocks.toArray(list);
		return list;
	}
	
	@Override
	public FItem[] listItems()
	{
		ArrayList<FItem> items = new ArrayList<FItem>();
		
		Iterator<?> it = Item.itemRegistry.iterator();
		while (it.hasNext())
		{
			Item i = (Item) it.next();
			if (!(i instanceof ItemBlock))
			{
				items.add(new SBItem(i));
			}
		}
		
		FItem[] list = new FItem[items.size()];
		items.toArray(list);
		return list;
	}
	
	@Override
	public FMinecraft getMinecraft()
	{
		return this.mc;
	}
}
