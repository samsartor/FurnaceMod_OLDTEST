package com.zapcloudstudios.furnace.api;

import com.zapcloudstudios.furnace.wrap.JSFunc;
import com.zapcloudstudios.furnace.wrap.JSGet;
import com.zapcloudstudios.furnace.wrap.JSProp;

public abstract class FBlockPos implements FurnaceI
{
	@JSProp(isConst = true, name = "x")
	public final int x;
	@JSProp(isConst = true, name = "y")
	public final int y;
	@JSProp(isConst = true, name = "z")
	public final int z;
	
	public FBlockPos(int x, int y, int z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	@JSGet(name = "pos")
	public int[] pos()
	{
		return new int[] { this.x, this.y, this.z };
	}
	
	@JSFunc(name = "block")
	public abstract FBlock block();
	
	@JSFunc(name = "meta")
	public abstract int meta();
	
	@Override
	public String typeName()
	{
		return "BlockPos";
	}
}
