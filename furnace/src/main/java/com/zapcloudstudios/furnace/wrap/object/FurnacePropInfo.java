package com.zapcloudstudios.furnace.wrap.object;

import java.lang.reflect.Field;

import com.zapcloudstudios.furnace.api.FurnaceI;

public abstract class FurnacePropInfo
{
	public final FurnaceClassInfo classinfo;
	public final String name;
	
	public Field changer = null;
	
	public FurnacePropInfo(FurnaceClassInfo classinfo, String name)
	{
		this.classinfo = classinfo;
		this.name = name;
	}
	
	public abstract Object get(FurnaceI object);
	
	public abstract void set(Object value, FurnaceI object);
}
