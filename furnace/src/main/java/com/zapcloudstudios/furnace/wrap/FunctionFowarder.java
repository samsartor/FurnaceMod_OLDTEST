package com.zapcloudstudios.furnace.wrap;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

import com.zapcloudstudios.furnace.Furnace;
import com.zapcloudstudios.furnace.FurnaceException;
import com.zapcloudstudios.furnace.FurnaceUtils;

public class FunctionFowarder implements Function
{
	private Scriptable parent;
	
	private int type = 0b01;
	
	public String name = null;
	public ArrayList<MethodCall> to = new ArrayList<MethodCall>();
	
	public FunctionFowarder()
	{
		
	}
	
	public FunctionFowarder(Method method, Object in)
	{
		this.to.add(new MethodCall(method, in));
	}
	
	public FunctionFowarder(MethodCall... calls)
	{
		this.to.addAll(Arrays.asList(calls));
	}
	
	public FunctionFowarder addCall(MethodCall call)
	{
		if (call != null)
		{
			this.to.add(call);
		}
		return this;
	}
	
	public FunctionFowarder addCall(Method method, Object in)
	{
		return this.addCall(new MethodCall(method, in));
	}
	
	public FunctionFowarder addCall(Object in, String name, Class<?>... args)
	{
		return this.addCall(MethodCall.find(in, name, args));
	}
	
	public FunctionFowarder constructerOnly()
	{
		this.type = 0b10;
		return this;
	}
	
	public FunctionFowarder functionOnly()
	{
		this.type = 0b01;
		return this;
	}
	
	public FunctionFowarder both()
	{
		this.type = 0b11;
		return this;
	}
	
	@Override
	public String getClassName()
	{
		return "Function";
	}
	
	@Override
	public Object get(String name, Scriptable start)
	{
		return Scriptable.NOT_FOUND;
	}
	
	@Override
	public Object get(int index, Scriptable start)
	{
		return Scriptable.NOT_FOUND;
	}
	
	@Override
	public boolean has(String name, Scriptable start)
	{
		return false;
	}
	
	@Override
	public boolean has(int index, Scriptable start)
	{
		return false;
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
		return ScriptableObject.getFunctionPrototype(Furnace.scope());
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
		return new Object[0];
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
	
	@Override
	public Object call(Context cx, Scriptable scope, Scriptable thisObj, Object[] args)
	{
		try
		{
			if ((this.type & 0b01) > 0)
			{
				return FurnaceUtils.invokeAMethod(this.to, args);
			}
			else
			{
				throw new FurnaceException("The function %scannot be used as a constructor", this.name == null ? "" : this.name + " ");
			}
		}
		catch (MethodCall.CallException e)
		{
			throw new FurnaceException("Failed to call function%s", e, this.name == null ? "" : " " + this.name);
		}
	}
	
	@Override
	public Scriptable construct(Context cx, Scriptable scope, Object[] args)
	{
		Exception error = null;
		try
		{
			if ((this.type & 0b10) > 0)
			{
				Object result = FurnaceUtils.invokeAMethod(this.to, args);
				if (result instanceof Scriptable)
				{
					return (Scriptable) result;
				}
			}
			else
			{
				throw new FurnaceException("The constructor %scannot be used as a function", this.name == null ? "" : this.name + " ");
			}
		}
		catch (MethodCall.CallException e)
		{
			error = e;
		}
		throw new FurnaceException("Failed to call constructor%s", error, this.name == null ? "" : " " + this.name);
	}
}
