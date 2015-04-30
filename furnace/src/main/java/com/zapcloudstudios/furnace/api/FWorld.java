package com.zapcloudstudios.furnace.api;

import com.zapcloudstudios.furnace.api.entity.FEntity;
import com.zapcloudstudios.furnace.wrap.JSFunc;
import com.zapcloudstudios.furnace.wrap.JSProp;

public abstract class FWorld implements FurnaceI
{
	@JSProp(name = "dim", isConst = true)
	public final int dimention;
	
	public FWorld(int dimention)
	{
		this.dimention = dimention;
	}
	
	public abstract FEntity[] getEntitiesInBox(double x, double y, double z, double distance);
	
	@JSFunc(name = "findEntitiesAt")
	public FEntity[] findEntitiesAt(double[] pos, double distance)
	{
		return this.getEntitiesInBox(pos[0], pos[1], pos[2], distance);
	}
	
	@Override
	public String typeName()
	{
		return "World";
	}
}
