package com.zapcloudstudios.stonebrick;

import java.io.File;
import java.io.FilenameFilter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumChatFormatting;

import com.zapcloudstudios.furnace.Furnace;
import com.zapcloudstudios.furnace.IFurnaceImpl;
import com.zapcloudstudios.furnace.api.FBlock;
import com.zapcloudstudios.furnace.api.FChatFormatting;
import com.zapcloudstudios.furnace.api.FItem;
import com.zapcloudstudios.furnace.api.FMinecraft;
import com.zapcloudstudios.stonebrick.api.SBBlock;
import com.zapcloudstudios.stonebrick.api.SBItem;
import com.zapcloudstudios.stonebrick.api.SBMinecraft;

public class StoneBrick implements IFurnaceImpl
{
	public static StoneBrick instance;
	
	public MinecraftServer server;
	public SBMinecraft mc;
	public Furnace furnace;
	
	public File worldDir;
	public File scriptDir;
	
	public HashMap<String, FurnaceScript> scripts;
	
	public Map<Item, SBItem> items;
	public Map<Block, SBBlock> blocks;
	
	public StoneBrick(MinecraftServer minecraftServer)
	{
		instance = this;
		this.server = minecraftServer;
		this.furnace = new Furnace(this);
		this.mc = new SBMinecraft(this);
	}
	
	public void start()
	{
		this.scripts = new HashMap<String, FurnaceScript>();
		
		this.worldDir = this.server.getEntityWorld().getSaveHandler().getWorldDirectory();
		this.scriptDir = new File(this.worldDir, "scripts/");
		
		if (this.scriptDir.exists())
		{
			for (File script : this.scriptDir.listFiles(new FilenameFilter()
			{
				@Override
				public boolean accept(File dir, String name)
				{
					return name.endsWith(".js");
				}
			}))
			{
				FurnaceScript fscript = new FurnaceScript(script);
				fscript.reload();
				this.scripts.put(script.getName().substring(0, script.getName().length() - 3), fscript);
			}
		}
		
		this.items = new HashMap<Item, SBItem>();
		Iterator<?> it = Item.itemRegistry.iterator();
		while (it.hasNext())
		{
			Item i = (Item) it.next();
			if (!(i instanceof ItemBlock))
			{
				this.items.put(i, new SBItem(i));
			}
		}
		this.blocks = new HashMap<Block, SBBlock>();
		it = Block.blockRegistry.iterator();
		while (it.hasNext())
		{
			Block b = (Block) it.next();
			this.blocks.put(b, new SBBlock(b));
		}
		
		this.furnace.enter();
		this.furnace.init();
	}
	
	public void close()
	{
		this.furnace.exit();
	}
	
	@Override
	public FBlock[] listBlocks()
	{
		FBlock[] list = new FBlock[this.blocks.size()];
		this.blocks.values().toArray(list);
		return list;
	}
	
	@Override
	public FItem[] listItems()
	{
		FItem[] list = new FItem[this.items.size()];
		this.items.values().toArray(list);
		return list;
	}
	
	@Override
	public FMinecraft getMinecraft()
	{
		return this.mc;
	}
	
	@Override
	public FChatFormatting getChatFormatting()
	{
		FChatFormatting form = new FChatFormatting();
		form.AQUA = EnumChatFormatting.AQUA.toString();
		form.BLACK = EnumChatFormatting.BLACK.toString();
		form.BLUE = EnumChatFormatting.BLUE.toString();
		form.BOLD = EnumChatFormatting.BOLD.toString();
		form.DARK_AQUA = EnumChatFormatting.DARK_AQUA.toString();
		form.DARK_BLUE = EnumChatFormatting.DARK_BLUE.toString();
		form.DARK_GRAY = EnumChatFormatting.DARK_GRAY.toString();
		form.DARK_GREEN = EnumChatFormatting.DARK_GREEN.toString();
		form.DARK_PURPLE = EnumChatFormatting.DARK_PURPLE.toString();
		form.DARK_RED = EnumChatFormatting.DARK_RED.toString();
		form.GOLD = EnumChatFormatting.GOLD.toString();
		form.GRAY = EnumChatFormatting.GRAY.toString();
		form.GREEN = EnumChatFormatting.GREEN.toString();
		form.ITALIC = EnumChatFormatting.ITALIC.toString();
		form.LIGHT_PURPLE = EnumChatFormatting.LIGHT_PURPLE.toString();
		form.OBFUSCATED = EnumChatFormatting.OBFUSCATED.toString();
		form.RED = EnumChatFormatting.RED.toString();
		form.RESET = EnumChatFormatting.RESET.toString();
		form.STRIKETHROUGH = EnumChatFormatting.STRIKETHROUGH.toString();
		form.UNDERLINE = EnumChatFormatting.UNDERLINE.toString();
		form.WHITE = EnumChatFormatting.WHITE.toString();
		form.YELLOW = EnumChatFormatting.YELLOW.toString();
		form.control = "\u00a7";
		return form;
	}
}
