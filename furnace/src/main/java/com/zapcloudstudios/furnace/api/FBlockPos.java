package com.zapcloudstudios.furnace.api;

import com.zapcloudstudios.furnace.wrap.annotation.APIConst;
import com.zapcloudstudios.furnace.wrap.annotation.APIFunction;
import com.zapcloudstudios.furnace.wrap.annotation.APIGetter;

public abstract class FBlockPos implements FurnaceI
{
	@APIConst("world")
	public final FWorld world;
	@APIConst("x")
	public final int x;
	@APIConst("y")
	public final int y;
	@APIConst("z")
	public final int z;
	
	public FBlockPos(FWorld world, int x, int y, int z)
	{
		this.world = world;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	@APIGetter("pos")
	public int[] pos()
	{
		return new int[] { this.x, this.y, this.z };
	}
	
	@APIFunction("block")
	public abstract FBlock block();
	
	@APIFunction("meta")
	public abstract int meta();
	
	@Override
	public String typeName()
	{
		return "BlockPos";
	}
}
