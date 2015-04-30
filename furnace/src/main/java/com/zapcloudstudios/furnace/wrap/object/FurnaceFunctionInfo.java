package com.zapcloudstudios.furnace.wrap.object;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.ScriptRuntime;
import org.mozilla.javascript.Scriptable;

import com.zapcloudstudios.furnace.FurnaceException;

public class FurnaceFunctionInfo extends FurnacePropInfo
{
	public class FurnaceFunctionObject implements Function
	{
		public FurnaceObject object;
		
		public FurnaceFunctionObject(FurnaceObject object)
		{
			this.object = object;
		}
		
		@Override
		public String getClassName()
		{
			return "Function";
		}
		
		@Override
		public Object get(String name, Scriptable start)
		{
		}
		
		@Override
		public Object get(int index, Scriptable start)
		{
		}
		
		@Override
		public boolean has(String name, Scriptable start)
		{
		}
		
		@Override
		public boolean has(int index, Scriptable start)
		{
		}
		
		@Override
		public void put(String name, Scriptable start, Object value)
		{
		}
		
		@Override
		public void put(int index, Scriptable start, Object value)
		{
		}
		
		@Override
		public void delete(String name)
		{
		}
		
		@Override
		public void delete(int index)
		{
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
		}
		
		@Override
		public void setParentScope(Scriptable parent)
		{
		}
		
		@Override
		public Object[] getIds()
		{
			return new Object[0];
		}
		
		@Override
		public Object getDefaultValue(Class<?> hint)
		{
			return this;
		}
		
		@Override
		public boolean hasInstance(Scriptable instance)
		{
			return false;
		}
		
		@Override
		public Object call(Context cx, Scriptable scope, Scriptable thisObj, Object[] args)
		{
			
		}
		
		@Override
		public Scriptable construct(Context cx, Scriptable scope, Object[] args)
		{
			throw new FurnaceException("%s.%s cannot be used as a constructor", this.object.object.typeName(), FurnaceFunctionInfo.this.name);
		}
	}
	
	public FurnaceFunctionInfo(FurnaceClassInfo classinfo, String name)
	{
		super(classinfo, name);
	}
}
