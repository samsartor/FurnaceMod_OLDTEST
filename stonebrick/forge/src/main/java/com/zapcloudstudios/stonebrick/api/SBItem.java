package com.zapcloudstudios.stonebrick.api;

import net.minecraft.item.Item;

import com.zapcloudstudios.furnace.api.FItem;

public class SBItem implements FItem
{
	private final Item item;
	private final String id;
	
	public SBItem(Item item)
	{
		this.item = item;
		this.id = Item.itemRegistry.getNameForObject(this.item).toString();
	}
	
	@Override
	public String id()
	{
		return this.id;
	}
}
