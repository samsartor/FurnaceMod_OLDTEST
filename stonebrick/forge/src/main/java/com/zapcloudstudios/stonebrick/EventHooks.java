package com.zapcloudstudios.stonebrick;

import com.zapcloudstudios.stonebrick.api.SBWorld;

import net.minecraft.world.WorldServer;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventHooks
{
	public StoneBrick sb;
	
	public EventHooks(StoneBrick sb)
	{
		this.sb = sb;
	}
	
	@SubscribeEvent
	public void onWorldLoad(WorldEvent.Load load)
	{
		if (load.world instanceof WorldServer)
		{
			int dim = load.world.provider.getDimensionId();
			SBWorld w = this.sb.mc.worlds.get(dim);
			if (w == null)
			{
				w = new SBWorld(this.sb, dim);
				this.sb.mc.worlds.put(dim, w);
			}
			w.world = (WorldServer) load.world;
		}
	}
	
	@SubscribeEvent
	public void onWorldUnload(WorldEvent.Unload unload)
	{
		if (unload.world instanceof WorldServer)
		{
			int dim = unload.world.provider.getDimensionId();
			SBWorld w = this.sb.mc.worlds.get(dim);
			if (w != null)
			{
				w.world = null;
			}
		}
	}
}
