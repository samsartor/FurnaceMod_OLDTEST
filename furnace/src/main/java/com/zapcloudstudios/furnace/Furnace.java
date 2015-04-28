package com.zapcloudstudios.furnace;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

import com.zapcloudstudios.furnace.api.FBlock;
import com.zapcloudstudios.furnace.api.FItem;
import com.zapcloudstudios.furnace.api.FMinecraft;

public class Furnace
{
	private Context context;
	private ScriptableObject shared;
	
	public Scriptable global;
	
	public final IFurnaceImpl impl;
	
	public Furnace(IFurnaceImpl impl)
	{
		this.impl = impl;
	}
	
	public void enter()
	{
		if (this.context == null)
		{
			this.context = Context.enter();
			this.shared = this.context.initSafeStandardObjects();
			this.shared.sealObject();
		}
	}
	
	public void exit()
	{
		if (this.context != null)
		{
			Context.exit();
			this.context = null;
		}
	}
	
	public Scriptable newObject()
	{
		Scriptable o = this.context.newObject(this.shared);
		o.setPrototype(this.shared);
		o.setParentScope(this.global);
		return o;
	}
	
	public void init()
	{
		if (this.context == null)
		{
			return;
		}
		
		this.global = null;
		this.global = this.newObject();
		
		ScriptableObject.putConstProperty(this.global, "items", this.initItems());
		ScriptableObject.putConstProperty(this.global, "blocks", this.initBlocks());
		
		FMinecraft mc = this.impl.getMinecraft();
		ScriptableObject.putConstProperty(this.global, "minecraft", Context.javaToJS(mc, this.shared));
		
		ScriptableObject.putConstProperty(this.global, "overworld", Context.javaToJS(mc.getWorld(0), this.shared));
		ScriptableObject.putConstProperty(this.global, "nether", Context.javaToJS(mc.getWorld(-1), this.shared));
		ScriptableObject.putConstProperty(this.global, "theend", Context.javaToJS(mc.getWorld(1), this.shared));
	}
	
	private Scriptable initItems()
	{
		Scriptable items = this.newObject();
		for (FItem item : this.impl.listItems())
		{
			ScriptableObject.putConstProperty(items, FurnaceUtils.resourceIdToPropertyName(item.id()), Context.javaToJS(item, this.shared));
		}
		return items;
	}
	
	private Scriptable initBlocks()
	{
		Scriptable blocks = this.newObject();
		for (FBlock block : this.impl.listBlocks())
		{
			ScriptableObject.putConstProperty(blocks, FurnaceUtils.resourceIdToPropertyName(block.id()), Context.javaToJS(block, this.shared));
		}
		return blocks;
	}
}
