package com.zapcloudstudios.furnace.wrap;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.HashMap;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.EvaluatorException;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.Wrapper;

import com.zapcloudstudios.furnace.FurnaceUtils;
import com.zapcloudstudios.furnace.api.FurnaceI;

public class NativeFurnaceObject implements Scriptable, Wrapper, Serializable
{
	private static final long serialVersionUID = 4686065981069999610L;
	
	abstract class FurnaceProp
	{
		public boolean isConst;
		
		public FurnaceProp(boolean isConst)
		{
			this.isConst = isConst;
		}
		
		public abstract Object get();
		
		public abstract void set(Object value);
	}
	
	class FurnacePropFeild extends FurnaceProp
	{
		public Field field;
		
		public FurnacePropFeild(Field field, boolean isConst)
		{
			super(isConst);
			this.field = field;
		}
		
		@Override
		public Object get()
		{
			try
			{
				return this.field.get(NativeFurnaceObject.this.object);
			}
			catch (IllegalArgumentException | IllegalAccessException e)
			{
				return null;
			}
		}
		
		@Override
		public void set(Object value)
		{
			try
			{
				this.field.set(NativeFurnaceObject.this.object, value);
			}
			catch (IllegalArgumentException | IllegalAccessException e)
			{
			}
		}
	}
	
	class FurnacePropGetSet extends FurnaceProp
	{
		public Method get;
		public Method set;
		
		public FurnacePropGetSet()
		{
			super(true);
		}
		
		@Override
		public Object get()
		{
			try
			{
				return this.get.invoke(NativeFurnaceObject.this.object);
			}
			catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
			{
				return null;
			}
		}
		
		@Override
		public void set(Object value)
		{
			try
			{
				this.set.invoke(NativeFurnaceObject.this.object, value);
			}
			catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
			{
			}
		}
	}
	
	class FurnacePropFunction extends FurnaceProp
	{
		public FunctionRef method;
		
		public FurnacePropFunction(Method method)
		{
			super(true);
			this.method = new FunctionRef(method, NativeFurnaceObject.this.object);
		}
		
		@Override
		public Object get()
		{
			return this.method;
		}
		
		@Override
		public void set(Object value)
		{
		}
	}
	
	public transient FurnaceI object;
	public transient HashMap<String, FurnaceProp> props = new HashMap<>();
	public transient Scriptable parent;
	
	public NativeFurnaceObject(FurnaceI object)
	{
		this.object = object;
		Class<?> clazz = this.object.getClass();
		ArrayDeque<Member> queue = new ArrayDeque<>();
		this.findMembers(clazz, queue);
		while (!queue.isEmpty())
		{
			Member member = queue.poll();
			if (member instanceof Field)
			{
				Field f = (Field) member;
				JSProp prop = f.getAnnotation(JSProp.class);
				this.props.put(prop.name(), new FurnacePropFeild(f, prop.isConst()));
			}
			else if (member instanceof Method)
			{
				Method m = (Method) member;
				JSFunc jsfunc = m.getAnnotation(JSFunc.class);
				JSGet jsget = m.getAnnotation(JSGet.class);
				JSSet jsset = m.getAnnotation(JSSet.class);
				if (jsfunc != null)
				{
					
				}
				else
				{
					String name = null;
					if (jsget != null)
					{
						name = jsget.name();
					}
					if (jsset != null)
					{
						name = jsset.name();
					}
					FurnaceProp prop = this.props.get(name);
					FurnacePropGetSet getset;
					if (prop instanceof FurnacePropFeild || prop == null)
					{
						getset = new FurnacePropGetSet();
						this.props.put(name, getset);
					}
					else
					{
						getset = (FurnacePropGetSet) prop;
					}
					if (jsget != null)
					{
						getset.get = m;
					}
					if (jsset != null)
					{
						getset.set = m;
						getset.isConst = false;
					}
				}
			}
		}
	}
	
	private void findMembers(Class<?> clazz, Collection<Member> members)
	{
		for (Field field : clazz.getDeclaredFields())
		{
			if (Modifier.isPublic(field.getModifiers()))
			{
				if (field.isAnnotationPresent(JSProp.class))
				{
					members.add(field);
				}
			}
		}
		for (Method method : clazz.getDeclaredMethods())
		{
			if (Modifier.isPublic(method.getModifiers()))
			{
				if (method.isAnnotationPresent(JSFunc.class))
				{
					members.add(method);
				}
				else if (method.isAnnotationPresent(JSGet.class) && method.getParameterCount() == 0)
				{
					members.add(method);
				}
				else if (method.isAnnotationPresent(JSSet.class) && method.getParameterCount() == 1)
				{
					members.add(method);
				}
			}
		}
		for (Class<?> inter : clazz.getInterfaces())
		{
			this.findMembers(inter, members);
		}
		if (clazz.getSuperclass() != null)
		{
			this.findMembers(clazz.getSuperclass(), members);
		}
	}
	
	@Override
	public FurnaceI unwrap()
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
		FurnaceProp prop = this.props.get(name);
		if (prop != null)
		{
			return FurnaceUtils.fixType(Context.javaToJS(prop.get(), this));
		}
		return Scriptable.NOT_FOUND;
	}
	
	@Override
	public Object get(int index, Scriptable start)
	{
		throw Context.reportRuntimeError(String.format("%s is not an array", this.getClassName()));
	}
	
	@Override
	public boolean has(String name, Scriptable start)
	{
		return this.props.containsKey(name);
	}
	
	@Override
	public boolean has(int index, Scriptable start)
	{
		return false;
	}
	
	@Override
	public void put(String name, Scriptable start, Object value)
	{
		FurnaceProp prop = this.props.get(name);
		if (prop != null)
		{
			if (prop.isConst)
			{
				throw this.immutableError(name);
			}
			prop.set(value);
		}
	}
	
	@Override
	public void put(int index, Scriptable start, Object value)
	{
		throw Context.reportRuntimeError(String.format("%s is not an array", this.getClassName()));
	}
	
	@Override
	public void delete(String name)
	{
		throw this.immutableError();
	}
	
	@Override
	public void delete(int index)
	{
		throw this.immutableError();
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
		return this.props.keySet().toArray();
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
		return Context.reportRuntimeError(String.format("Properties of %s cannot be added or removed", this.getClassName()));
	}
	
	EvaluatorException immutableError(String prop)
	{
		return Context.reportRuntimeError(String.format("%s.%s cannot be changed", this.getClassName(), prop));
	}
	
	EvaluatorException notAnArrayError()
	{
		return Context.reportRuntimeError(String.format("%s is not an array", this.getClassName()));
	}
	
	@Override
	public String toString()
	{
		return String.valueOf(this.object);
	}
}
