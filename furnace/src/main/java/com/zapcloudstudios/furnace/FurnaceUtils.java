package com.zapcloudstudios.furnace;

import org.mozilla.javascript.ConsString;

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
}
