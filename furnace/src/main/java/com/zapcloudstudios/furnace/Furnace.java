package com.zapcloudstudios.furnace;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

import com.zapcloudstudios.furnace.api.FBlock;
import com.zapcloudstudios.furnace.api.FItem;
import com.zapcloudstudios.furnace.api.FMinecraft;
import com.zapcloudstudios.furnace.wrap.FunctionFowarder;
import com.zapcloudstudios.furnace.wrap.FurnaceWrapFactory;
import com.zapcloudstudios.furnace.wrap.PropMap;

public class Furnace
{
	private static Furnace instance;
	
	private Context context;
	private ScriptableObject shared;
	
	public Scriptable global;
	
	public final IFurnaceImpl impl;
	
	public Furnace(IFurnaceImpl impl)
	{
		instance = this;
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
	
	public FurnaceContext makeContext()
	{
		return new FurnaceContext(this.context, this.newObject());
	}
	
	public Scriptable newObject()
	{
		Scriptable o = this.context.newObject(this.shared);
		o.setParentScope(this.global);
		return o;
	}
	
	public void init()
	{
		if (this.context == null)
		{
			return;
		}
		
		this.global = this.context.newObject(this.shared);
		this.global.setParentScope(null);
		this.global.setPrototype(this.shared);
		
		ScriptableObject.putConstProperty(this.global, "items", this.initItems());
		ScriptableObject.putConstProperty(this.global, "blocks", this.initBlocks());
		
		FMinecraft mc = this.impl.getMinecraft();
		ScriptableObject.putConstProperty(this.global, "minecraft", Context.javaToJS(mc, this.shared));
		
		ScriptableObject.putConstProperty(this.global, "overworld", Context.javaToJS(mc.getWorld(0), this.shared));
		ScriptableObject.putConstProperty(this.global, "nether", Context.javaToJS(mc.getWorld(-1), this.shared));
		ScriptableObject.putConstProperty(this.global, "theend", Context.javaToJS(mc.getWorld(1), this.shared));
		
		ScriptableObject.putConstProperty(this.global, "formatting", Context.javaToJS(this.impl.getChatFormatting(), this.shared));
		
		FunctionFowarder sendChat = this.addFunction("chat").functionOnly();
		sendChat.addCall(mc, "sendChat", Object.class);
		sendChat.addCall(mc, "sendChat", String.class, Object.class);
		
		this.addFunction("command").functionOnly().addCall(mc, "command", String.class);
		
		//this.putShortcut("commandAt", mc, "command", Double.class, Double.class, Double.class, String.class);
	}
	
	public FunctionFowarder addFunction(String name)
	{
		FunctionFowarder func = new FunctionFowarder();
		ScriptableObject.putConstProperty(this.global, name, func);
		return func;
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
	
	public static Furnace instance()
	{
		return instance;
	}
	
	public static Scriptable scope()
	{
		return instance.global;
	}
}
