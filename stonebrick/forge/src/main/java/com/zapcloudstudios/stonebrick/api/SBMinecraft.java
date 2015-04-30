package com.zapcloudstudios.stonebrick.api;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.common.DimensionManager;

import com.zapcloudstudios.furnace.api.FMinecraft;
import com.zapcloudstudios.furnace.api.FWorld;
import com.zapcloudstudios.stonebrick.ComFuncSender;
import com.zapcloudstudios.stonebrick.StoneBrick;

public class SBMinecraft extends FMinecraft
{
	public Map<Integer, SBWorld> worlds;
	private MinecraftServer server;
	private StoneBrick sb;
	
	private ComFuncSender commandSender;
	
	public SBMinecraft(StoneBrick sb)
	{
		this.sb = sb;
		this.server = this.sb.server;
		this.worlds = new HashMap<Integer, SBWorld>();
	}
	
	@Override
	public void sendChat(String msg)
	{
		this.server.getConfigurationManager().sendChatMsg(new ChatComponentText(msg));
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
		if (this.commandSender == null)
		{
			this.commandSender = new ComFuncSender(DimensionManager.getWorld(0), 0, 0, 0, "@");
		}
		this.server.getCommandManager().executeCommand(this.commandSender, com);
	}
}
