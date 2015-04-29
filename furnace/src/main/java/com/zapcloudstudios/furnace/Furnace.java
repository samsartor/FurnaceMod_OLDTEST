package com.zapcloudstudios.furnace;

import java.util.HashMap;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

import com.zapcloudstudios.furnace.api.FBlock;
import com.zapcloudstudios.furnace.api.FItem;
import com.zapcloudstudios.furnace.api.FMinecraft;
import com.zapcloudstudios.furnace.api.FurnaceI;
import com.zapcloudstudios.furnace.wrap.FunctionRef;
import com.zapcloudstudios.furnace.wrap.FurnaceWrapFactory;
import com.zapcloudstudios.furnace.wrap.NativeFurnaceObject;
import com.zapcloudstudios.furnace.wrap.PropMap;

public class Furnace
{
	private Context context;
	private ScriptableObject shared;
	
	public Scriptable global;
	
	public final IFurnaceImpl impl;
	
	private HashMap<FItem, Scriptable> items;
	private HashMap<FBlock, Scriptable> blocks;
	
	public Furnace(IFurnaceImpl impl)
	{
		this.impl = impl;
	}
	
	public void enter()
	{
		if (this.context == null)
		{
			this.context = Context.enter();
			this.context.setWrapFactory(new FurnaceWrapFactory(this));
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
		
		this.blocks = new HashMap<FBlock, Scriptable>();
		this.items = new HashMap<FItem, Scriptable>();
		
		this.global = null;
		this.global = this.newObject();
		
		ScriptableObject.putConstProperty(this.global, "items", this.initItems());
		ScriptableObject.putConstProperty(this.global, "blocks", this.initBlocks());
		
		FMinecraft mc = this.impl.getMinecraft();
		ScriptableObject.putConstProperty(this.global, "minecraft", Context.javaToJS(mc, this.shared));
		
		ScriptableObject.putConstProperty(this.global, "overworld", Context.javaToJS(mc.getWorld(0), this.shared));
		ScriptableObject.putConstProperty(this.global, "nether", Context.javaToJS(mc.getWorld(-1), this.shared));
		ScriptableObject.putConstProperty(this.global, "theend", Context.javaToJS(mc.getWorld(1), this.shared));
		
		this.putShortcut("chat", mc, "sendChat", String.class);
		//this.putShortcut("command", mc, "command", String.class);
		//this.putShortcut("commandAt", mc, "command", Double.class, Double.class, Double.class, String.class);
	}
	
	public void putShortcut(String name, Object object, String function, Class<?>... args)
	{
		try
		{
			ScriptableObject.putConstProperty(this.global, name, new FunctionRef(object.getClass().getMethod(function, args), object));
		}
		catch (NoSuchMethodException | SecurityException e)
		{
			e.printStackTrace();
		}
	}
	
	public Scriptable getWrap(FurnaceI furn)
	{
		if (furn instanceof FItem)
		{
			Scriptable out = this.items.get(furn);
			if (out == null)
			{
				out = new NativeFurnaceObject(furn);
				this.items.put((FItem) furn, out);
			}
			return out;
		}
		if (furn instanceof FBlock)
		{
			Scriptable out = this.blocks.get(furn);
			if (out == null)
			{
				out = new NativeFurnaceObject(furn);
				this.blocks.put((FBlock) furn, out);
			}
			return out;
		}
		return new NativeFurnaceObject(furn);
	}
	
	private Scriptable initItems()
	{
		PropMap items = new PropMap(true);
		for (FItem item : this.impl.listItems())
		{
			items.put(FurnaceUtils.resourceIdToPropertyName(item.id), (Scriptable) Context.javaToJS(item, this.shared));
		}
		return items;
	}
	
	private Scriptable initBlocks()
	{
		PropMap blocks = new PropMap(true);
		for (FBlock block : this.impl.listBlocks())
		{
			blocks.put(FurnaceUtils.resourceIdToPropertyName(block.id), (Scriptable) Context.javaToJS(block, this.shared));
		}
		return blocks;
	}
}
