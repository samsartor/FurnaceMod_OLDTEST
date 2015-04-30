package com.zapcloudstudios.stonebrick;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;

@Mod(modid = StoneBrickMod.MODID, useMetadata = true)
public class StoneBrickMod
{
	public static final String MODID = "stonebrick";
	
	@Mod.Metadata(MODID)
	public static ModMetadata MODMETA;
	
	@Mod.Instance(MODID)
	public static StoneBrickMod instance;
	
	public StoneBrick sb;
	public EventHooks hooksEvent;
	public TickHooks hooksTick;
	
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
	}
	
	@EventHandler
	public void onServerPreStart(FMLServerAboutToStartEvent event)
	{
		this.sb = new StoneBrick(event.getServer());
		this.hooksEvent = new EventHooks(this.sb);
		this.hooksTick = new TickHooks(this.sb);
		MinecraftForge.EVENT_BUS.register(this.hooksEvent);
		MinecraftForge.EVENT_BUS.register(this.hooksTick);
	}
	
	@EventHandler
	public void onServerStart(FMLServerStartingEvent event)
	{
		this.sb.start();
		event.registerServerCommand(new CommandSmelt(this.sb.furnace.makeContext()));
	}
	
	@EventHandler
	public void onServerStopping(FMLServerStoppingEvent event)
	{
		this.sb.close();
		this.sb = null;
	}
}
