package com.zapcloudstudios.stonebrick.api;

import net.minecraft.item.Item;

import com.zapcloudstudios.furnace.api.FItem;

public class SBItem extends FItem
{
	private final Item item;
	
	public SBItem(Item item)
	{
		this.item = item;
		this.id = Item.itemRegistry.getNameForObject(this.item).toString();
	}
}
