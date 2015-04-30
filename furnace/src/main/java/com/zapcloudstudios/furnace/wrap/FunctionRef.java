package com.zapcloudstudios.furnace.wrap;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.mozilla.javascript.BaseFunction;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import com.zapcloudstudios.furnace.FurnaceUtils;

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
		Class<?>[] argtypes = this.javaMethod.getParameterTypes();
		Object[] jargs = new Object[argtypes.length];
		if (jargs.length < args.length)
		{
			return null;
		}
		for (int i = 0; i < args.length; i++)
		{
			System.out.println(args[i].getClass());
			jargs[i] = FurnaceUtils.fixType(Context.jsToJava(args, argtypes[i]));
		}
		try
		{
			return Context.javaToJS(this.javaMethod.invoke(this.object, args), scope);
		}
		catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
		{
			return null;
		}
	}
}
