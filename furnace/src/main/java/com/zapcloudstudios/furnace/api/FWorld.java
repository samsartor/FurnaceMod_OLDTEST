package com.zapcloudstudios.furnace.api;

import com.zapcloudstudios.furnace.api.entity.FEntity;
import com.zapcloudstudios.furnace.wrap.annotation.APIConst;
import com.zapcloudstudios.furnace.wrap.annotation.APIFunction;

public abstract class FWorld implements FurnaceI
{
	@APIConst("dim")
	public final int dimention;
	
	public FWorld(int dimention)
	{
		this.dimention = dimention;
	}
	
	public abstract FEntity[] getEntitiesInBox(double x, double y, double z, double distance);
	
	@APIFunction("findEntitiesAt")
	public FEntity[] findEntitiesAt(double[] pos, double distance)
	{
		return this.getEntitiesInBox(pos[0], pos[1], pos[2], distance);
	}
	
	@APIFunction("pos")
	public abstract FBlockPos getPos(int x, int y, int z);
	
	@APIFunction("getBlock")
	public FBlock getBlock(int x, int y, int z)
	{
		return this.getPos(x, y, z).block();
	}
	
	@Override
	public String typeName()
	{
		return "World";
	}
}
