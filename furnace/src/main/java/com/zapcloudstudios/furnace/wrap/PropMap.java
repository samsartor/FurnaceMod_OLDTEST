package com.zapcloudstudios.furnace.wrap;

import java.util.HashMap;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.EvaluatorException;
import org.mozilla.javascript.Scriptable;

public class PropMap extends HashMap<String, Scriptable> implements Scriptable
{
	private static final long serialVersionUID = 954397056258707577L;
	
	public boolean isConst;
	public Scriptable parent;
	
	public PropMap(boolean isConst)
	{
		this.isConst = isConst;
	}
	
	@Override
	public String getClassName()
	{
		return "SimplePropMap";
	}
	
	@Override
	public Object get(String name, Scriptable start)
	{
		Object o = this.get(name);
		if (o == null)
		{
			return Scriptable.NOT_FOUND;
		}
		return o;
	}
	
	@Override
	public Object get(int index, Scriptable start)
	{
		return this.get(String.valueOf(index), start);
	}
	
	@Override
	public boolean has(String name, Scriptable start)
	{
		return this.containsKey(name);
	}
	
	@Override
	public boolean has(int index, Scriptable start)
	{
		return this.has(String.valueOf(index), start);
	}
	
	@Override
	public void put(String name, Scriptable start, Object value)
	{
		if (this.isConst)
		{
			throw this.immutableError();
		}
		this.put(name, (Scriptable) value);
	}
	
	@Override
	public void put(int index, Scriptable start, Object value)
	{
		this.put(String.valueOf(index), start, value);
	}
	
	@Override
	public void delete(String name)
	{
		if (this.isConst)
		{
			throw this.immutableError();
		}
		this.remove(name);
	}
	
	@Override
	public void delete(int index)
	{
		this.delete(String.valueOf(index));
	}
	
	@Override
	public Scriptable getPrototype()
	{
		return null;
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
		return this.keySet().toArray();
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
	
	EvaluatorException immutableError()
	{
		return Context.reportRuntimeError(String.format("Properties of this %s cannot be added, removed, or changed", this.getClassName()));
	}
}
