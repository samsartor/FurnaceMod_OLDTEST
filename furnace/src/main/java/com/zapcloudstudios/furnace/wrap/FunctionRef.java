package com.zapcloudstudios.furnace.wrap;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.mozilla.javascript.BaseFunction;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class FunctionRef extends BaseFunction
{
	private static final long serialVersionUID = 4296247151079223585L;
	
	public Method javaMethod;
	public Object object;
	
	public FunctionRef(Method javaMethod, Object object)
	{
		this.javaMethod = javaMethod;
		this.object = object;
	}
	
	@Override
	public Object call(Context cx, Scriptable scope, Scriptable thisObj, Object[] args)
	{
		try
		{
			return this.javaMethod.invoke(this.object, args);
		}
		catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
		{
			return null;
		}
	}
}
