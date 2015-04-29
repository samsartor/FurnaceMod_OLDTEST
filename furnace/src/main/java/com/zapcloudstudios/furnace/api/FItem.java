package com.zapcloudstudios.furnace.api;

import com.zapcloudstudios.furnace.wrap.JSProp;

public abstract class FItem implements FurnaceI
{
	@JSProp(name = "id", isConst = true)
	public String id;
	
	@Override
	public String typeName()
	{
		return "Item";
	}
	
	@Override
	public boolean equals(Object o)
	{
		if (o instanceof FItem)
		{
			return this.id.equals(((FItem) o).id);
		}
		return false;
	}
	
	@Override
	public int hashCode()
	{
		return this.id.hashCode();
	}
}
