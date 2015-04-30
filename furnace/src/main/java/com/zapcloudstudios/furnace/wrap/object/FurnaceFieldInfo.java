package com.zapcloudstudios.furnace.wrap.object;

import java.lang.reflect.Field;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.EvaluatorException;

import com.zapcloudstudios.furnace.Furnace;
import com.zapcloudstudios.furnace.FurnaceException;
import com.zapcloudstudios.furnace.api.FurnaceI;

public class FurnaceFieldInfo extends FurnacePropInfo
{
	public Field field;
	public boolean readonly;
	
	public FurnaceFieldInfo(FurnaceClassInfo classinfo, Field field, boolean isConst, String name)
	{
		super(classinfo, name);
		this.field = field;
		this.readonly = isConst;
	}
	
	@Override
	public Object get(FurnaceI object)
	{
		Object result;
		try
		{
			result = Context.javaToJS(this.field.get(object), Furnace.scope());
		}
		catch (IllegalArgumentException | IllegalAccessException e)
		{
			throw new FurnaceException("Could not get the value of %s.%s", e, object.typeName(), this.name);
		}
		if (this.changer != null)
		{
			
		}
		return result;
	}
	
	@Override
	public void set(Object value, FurnaceI object)
	{
		if (this.readonly)
		{
			throw new FurnaceException("%s.%s is read-only", object.typeName(), this.name);
		}
		try
		{
			this.field.set(object, Context.jsToJava(value, this.field.getType()));
		}
		catch (IllegalArgumentException | IllegalAccessException e)
		{
			throw new FurnaceException("%s.%s could not be set", e, object.typeName(), this.name);
		}
		catch (EvaluatorException e)
		{
			throw new FurnaceException("%s.%s could not be set", e, object.typeName(), this.name);
		}
	}
}
