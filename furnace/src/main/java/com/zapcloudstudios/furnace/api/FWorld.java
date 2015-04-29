package com.zapcloudstudios.furnace.api;

import com.zapcloudstudios.furnace.wrap.JSProp;

public class FWorld implements FurnaceI
{
	@JSProp(name = "dim", isConst = true)
	public final int dimention;
	
	public FWorld(int dimention)
	{
		this.dimention = dimention;
	}
	
	@Override
	public String typeName()
	{
		return "World";
	}
}
