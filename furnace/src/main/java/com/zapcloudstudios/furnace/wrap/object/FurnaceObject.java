package com.zapcloudstudios.furnace.wrap.object;

import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.Wrapper;

import com.zapcloudstudios.furnace.api.FurnaceI;

public class FurnaceObject implements Scriptable, Wrapper
{
	public FurnaceI object;
	
	public FurnaceObject(FurnaceI object)
	{
		this.object = object;
	}
	
	@Override
	public Object unwrap()
	{
		return this.object;
	}
}
