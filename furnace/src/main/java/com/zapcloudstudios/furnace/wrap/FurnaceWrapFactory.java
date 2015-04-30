package com.zapcloudstudios.furnace.wrap;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.WrapFactory;

import com.zapcloudstudios.furnace.Furnace;
import com.zapcloudstudios.furnace.api.FurnaceI;
import com.zapcloudstudios.furnace.wrap.object.FurnaceObject;

public class FurnaceWrapFactory extends WrapFactory
{
	Furnace furnace;
	
	public FurnaceWrapFactory(Furnace furnace)
	{
		this.furnace = furnace;
	}
	
	@Override
	public Scriptable wrapAsJavaObject(Context cx, Scriptable scope, Object javaObject, Class<?> staticType)
	{
		Scriptable wrapped = null;
		if (javaObject instanceof FurnaceI)
		{
			wrapped = new FurnaceObject((FurnaceI) javaObject);
		}
		if (wrapped == null)
		{
			return super.wrapAsJavaObject(cx, scope, javaObject, staticType);
		}
		return wrapped;
	}
}
