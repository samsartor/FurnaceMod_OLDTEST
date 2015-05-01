package com.zapcloudstudios.furnace.wrap.object;

import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.Wrapper;

import com.zapcloudstudios.furnace.Furnace;
import com.zapcloudstudios.furnace.FurnaceException;
import com.zapcloudstudios.furnace.api.FurnaceI;

public class FurnaceObject implements Scriptable, Wrapper
{
	public FurnaceI object;
	public FurnaceClassInfo classinfo;
	
	public Scriptable parent;
	
	public FurnaceObject(FurnaceI object)
	{
		this.object = object;
		this.classinfo = FurnaceClassInfo.getInfo(object.getClass());
	}
	
	@Override
	public Object unwrap()
	{
		return this.object;
	}
	
	@Override
	public String getClassName()
	{
		return this.object.typeName();
	}
	
	@Override
	public Object get(String name, Scriptable start)
	{
		FurnacePropInfo prop = this.classinfo.props.get(name);
		if (prop == null)
		{
			return Scriptable.NOT_FOUND;
		}
		return prop.get(this);
	}
	
	@Override
	public Object get(int index, Scriptable start)
	{
		return this.get(String.valueOf(index), start);
	}
	
	@Override
	public boolean has(String name, Scriptable start)
	{
		return this.classinfo.props.containsKey(name);
	}
	
	@Override
	public boolean has(int index, Scriptable start)
	{
		return this.has(String.valueOf(index), start);
	}
	
	@Override
	public void put(String name, Scriptable start, Object value)
	{
		FurnacePropInfo prop = this.classinfo.props.get(name);
		if (prop == null)
		{
			throw new FurnaceException("Can not add property %s to a(n) %s", name, this.object.typeName());
		}
		prop.set(value, this);
	}
	
	@Override
	public void put(int index, Scriptable start, Object value)
	{
		this.put(String.valueOf(index), start, value);
	}
	
	@Override
	public void delete(String name)
	{
		throw new FurnaceException("Can not delete %s.%s", this.object.typeName(), name);
	}
	
	@Override
	public void delete(int index)
	{
		throw new FurnaceException("Can not delete %s.[%d]", this.object.typeName(), index);
	}
	
	@Override
	public Scriptable getPrototype()
	{
		return ScriptableObject.getObjectPrototype(Furnace.scope());
	}
	
	@Override
	public void setPrototype(Scriptable prototype)
	{
		
	}
	
	@Override
	public Scriptable getParentScope()
	{
		return this.parent;
	}
	
	@Override
	public void setParentScope(Scriptable parent)
	{
		this.parent = parent;
	}
	
	@Override
	public Object[] getIds()
	{
		return this.classinfo.props.keySet().toArray();
	}
	
	@Override
	public Object getDefaultValue(Class<?> hint)
	{
		return null;
	}
	
	@Override
	public boolean hasInstance(Scriptable instance)
	{
		return false;
	}
}
