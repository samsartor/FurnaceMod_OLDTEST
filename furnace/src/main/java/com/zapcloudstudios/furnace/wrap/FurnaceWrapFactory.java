package com.zapcloudstudios.furnace.wrap;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.WrapFactory;

import com.zapcloudstudios.furnace.Furnace;
import com.zapcloudstudios.furnace.api.FurnaceI;

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
			FurnaceI o = (FurnaceI) javaObject;
			wrapped = this.furnace.getWrap(o);
		}
		if (wrapped == null)
		{
			return super.wrapAsJavaObject(cx, scope, javaObject, staticType);
		}
		return wrapped;
	}
}
