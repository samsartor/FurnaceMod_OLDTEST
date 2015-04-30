package com.zapcloudstudios.furnace.api;

import com.zapcloudstudios.furnace.wrap.JSGet;
import com.zapcloudstudios.furnace.wrap.JSProp;

public abstract class FBlock implements FurnaceI
{
	@JSProp(name = "id", isConst = true)
	public String id;
	
	@JSGet(name = "item")
	public abstract FItem item();
	
	@Override
	public String typeName()
	{
		return "Block";
	}
	
	@Override
	public boolean equals(Object o)
	{
		if (o instanceof FBlock)
		{
			return this.id.equals(((FBlock) o).id);
		}
		return false;
	}
	
	@Override
	public int hashCode()
	{
		return this.id.hashCode();
	}
	
	@Override
	public String toString()
	{
		return this.id;
	}
}
