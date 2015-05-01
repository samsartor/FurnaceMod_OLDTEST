package com.zapcloudstudios.furnace.wrap.object;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.EvaluatorException;

import com.zapcloudstudios.furnace.Furnace;
import com.zapcloudstudios.furnace.FurnaceException;

public class FurnaceGetSetInfo extends FurnacePropInfo
{
	public Method getter;
	public Method setter;
	
	public FurnaceGetSetInfo(FurnaceClassInfo classinfo, String name)
	{
		super(classinfo, name);
	}
	
	@Override
	public Object get(FurnaceObject object)
	{
		if (this.getter == null)
		{
			
		}
		if (this.getter.getParameterCount() != 0)
		{
			throw new IllegalStateException("A getter must not require any arguments");
		}
		try
		{
			return Context.javaToJS(this.getter.invoke(object.object), Furnace.scope());
		}
		catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
		{
			throw new FurnaceException("Could not get the value of %s.%s", e, object.object.typeName(), this.name);
		}
	}
	
	@Override
	public void set(Object value, FurnaceObject object)
	{
		if (this.setter == null)
		{
			throw new FurnaceException("%s.%s is read-only", object.object.typeName(), this.name);
		}
		Class<?>[] types = this.setter.getParameterTypes();
		if (types.length != 1)
		{
			throw new IllegalStateException("A setter must take a single argument");
		}
		try
		{
			this.setter.invoke(object.object, Context.jsToJava(value, types[0]));
		}
		catch (EvaluatorException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
		{
			throw new FurnaceException("Could not set the value of %s.%s", e, object.object.typeName(), this.name);
		}
	}
}
