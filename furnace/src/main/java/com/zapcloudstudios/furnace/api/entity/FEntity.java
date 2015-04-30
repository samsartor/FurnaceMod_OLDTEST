package com.zapcloudstudios.furnace.api.entity;

import com.zapcloudstudios.furnace.api.FurnaceI;
import com.zapcloudstudios.furnace.wrap.JSFunc;
import com.zapcloudstudios.furnace.wrap.JSGet;

public abstract class FEntity implements FurnaceI
{
	@JSGet(name = "type")
	public abstract String entityType();
	
	@JSFunc(name = "rename")
	public abstract void rename(String name, boolean visible);
	
	@JSGet(name = "name")
	public abstract String getName();
	
	@Override
	public String typeName()
	{
		return this.entityType() + "Entity";
	}
}