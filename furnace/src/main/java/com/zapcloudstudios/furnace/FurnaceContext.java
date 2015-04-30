package com.zapcloudstudios.furnace;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class FurnaceContext
{
	private Context context;
	private Scriptable scope;
	
	public FurnaceContext(Context context, Scriptable scope)
	{
		this.context = context;
		this.scope = scope;
	}
	
	public Object eval(String name, String js)
	{
		return this.context.evaluateString(this.scope, js, name, 0, null);
	}
	
	public Object undefned()
	{
		return Context.getUndefinedValue();
	}
}
