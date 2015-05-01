package com.zapcloudstudios.furnace.wrap.object;

public abstract class FurnacePropInfo
{
	public final FurnaceClassInfo classinfo;
	public final String name;
	
	public FurnacePropInfo(FurnaceClassInfo classinfo, String name)
	{
		this.classinfo = classinfo;
		this.name = name;
	}
	
	public abstract Object get(FurnaceObject object);
	
	public abstract void set(Object value, FurnaceObject object);
}
