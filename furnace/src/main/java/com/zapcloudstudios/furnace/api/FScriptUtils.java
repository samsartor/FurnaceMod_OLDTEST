package com.zapcloudstudios.furnace.api;

import org.mozilla.javascript.Function;

import com.zapcloudstudios.furnace.Furnace;

public class FScriptUtils
{
	protected Furnace furnace;
	
	public FScriptUtils(Furnace furnace)
	{
		this.furnace = furnace;
	}
	
	public Function eval(String function)
	{
		return this.furnace.context.compileFunction(this.furnace.global, function, "", 0, null);
	}
}
