package com.zapcloudstudios.stonebrick.api;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;

import com.zapcloudstudios.furnace.api.FMinecraft;
import com.zapcloudstudios.furnace.api.FWorld;
import com.zapcloudstudios.stonebrick.StoneBrick;

public class SBMinecraft extends FMinecraft
{
	public Map<Integer, SBWorld> worlds;
	private MinecraftServer server;
	private StoneBrick sb;
	
	public SBMinecraft(StoneBrick sb)
	{
		this.sb = sb;
		this.server = this.sb.server;
		this.worlds = new HashMap<Integer, SBWorld>();
	}
	
	@Override
	public void sendChat(String msg)
	{
		this.server.addChatMessage(new ChatComponentText(msg));
	}
	
	@Override
	public FWorld getWorld(int dimention)
	{
		SBWorld w = this.worlds.get(dimention);
		if (w == null)
		{
			w = new SBWorld(this.sb, dimention);
			this.worlds.put(dimention, w);
		}
		return w;
	}
	
	@Override
	public void command(String com)
	{
		this.server.handleRConCommand(com);
	}
}
