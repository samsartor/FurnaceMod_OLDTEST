package com.zapcloudstudios.furnace.wrap;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.common.base.Function;

public class MethodCall implements Function<Object[], Object>
{
	public class CallException extends RuntimeException
	{
		private static final long serialVersionUID = 7067236564221927181L;
		
		public final MethodCall call = MethodCall.this;
		
		public CallException(Exception e)
		{
			super(e);
		}
	}
	
	public final Method method;
	public final Object object;
	
	public MethodCall(Method method, Object object)
	{
		this.method = method;
		this.object = object;
	}
	
	@Override
	public Object apply(Object[] input)
	{
		try
		{
			return this.method.invoke(this.object, input);
		}
		catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
		{
			throw new CallException(e);
		}
	}
	
	public static List<MethodCall> merge(Iterable<Method> methods, Object on)
	{
		ArrayList<MethodCall> calls = new ArrayList<>();
		Iterator<Method> it = methods.iterator();
		while (it.hasNext())
		{
			calls.add(new MethodCall(it.next(), on));
		}
		return calls;
	}
	
	public static List<MethodCall> merge(Iterable<Method> methods, Iterable<Object> objects)
	{
		ArrayList<MethodCall> calls = new ArrayList<>();
		Iterator<Method> itm = methods.iterator();
		Iterator<Object> ito = objects.iterator();
		while (itm.hasNext() && ito.hasNext())
		{
			calls.add(new MethodCall(itm.next(), ito.next()));
		}
		return calls;
	}
	
	public static MethodCall find(Object in, String name, Class<?>... args)
	{
		try
		{
			return new MethodCall(in.getClass().getMethod(name, args), in);
		}
		catch (NoSuchMethodException | SecurityException e)
		{
			e.printStackTrace();
			return null;
		}
	}
}
