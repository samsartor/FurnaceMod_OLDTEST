package com.zapcloudstudios.furnace;

import java.util.Iterator;

import org.apache.commons.lang3.ClassUtils;
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
		
		while (calls.hasNext())
		{
			call = calls.next();
			Class<?>[] pars = call.method.getParameterTypes();
			if (args.length == pars.length)
			{
				jargs = new Object[args.length];
				boolean match = true;
				try
				{
					for (int j = 0; j < args.length; j++)
					{
						if (jargs[j] != null)
						{
							jargs[j] = Context.jsToJava(args[j], pars[j]);
							Class<?> argtype = jargs[j].getClass();
							Class<?> partype = pars[j];
							match &= ClassUtils.isAssignable(argtype, partype);
						}
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
