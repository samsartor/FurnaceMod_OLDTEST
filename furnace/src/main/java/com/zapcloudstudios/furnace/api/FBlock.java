package com.zapcloudstudios.furnace.api;

import com.zapcloudstudios.furnace.wrap.annotation.APIConst;
import com.zapcloudstudios.furnace.wrap.annotation.APIGetter;

public abstract class FBlock implements FurnaceI
{
	@APIConst("id")
	public String id;
	
	@APIGetter("item")
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
