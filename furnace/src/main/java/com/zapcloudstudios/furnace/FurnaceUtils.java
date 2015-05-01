package com.zapcloudstudios.furnace;

import java.util.Iterator;

import org.mozilla.javascript.ConsString;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.EvaluatorException;
import org.mozilla.javascript.Scriptable;

import com.zapcloudstudios.furnace.wrap.MethodCall;

public class FurnaceUtils
{
	public static String resourceIdToPropertyName(String id)
	{
		if (id.startsWith("minecraft:"))
		{
			return id.substring(id.indexOf(':') + 1);
		}
		return id.replace(':', '$');
	}
	
	public static Object fixType(Object in)
	{
		if (in instanceof ConsString)
		{
			return in.toString();
		}
		return in;
	}
	
	public static Object invokeAMethod(Iterable<MethodCall> from, Object[] args)
	{
		MethodCall call = null;
		Object[] jargs = null;
		Iterator<MethodCall> calls = from.iterator();
		
		Class<?>[] types = new Class<?>[args.length];
		while (calls.hasNext())
		{
			call = calls.next();
			Class<?>[] pars = call.method.getParameterTypes();
			if (types.length == pars.length)
			{
				jargs = new Object[args.length];
				boolean match = true;
				try
				{
					for (int j = 0; j < types.length; j++)
					{
						jargs[j] = Context.jsToJava(args[j], pars[j]);
						match &= pars[j].isAssignableFrom(jargs[j].getClass());
					}
				}
				catch (EvaluatorException e)
				{
					match = false;
				}
				if (match)
				{
					break;
				}
			}
			call = null;
		}
		
		if (call == null)
		{
			return Scriptable.NOT_FOUND;
		}
		
		return Context.javaToJS(call.apply(jargs), Furnace.scope());
	}
}
