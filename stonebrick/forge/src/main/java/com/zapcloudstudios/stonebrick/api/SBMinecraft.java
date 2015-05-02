package com.zapcloudstudios.stonebrick.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.common.DimensionManager;

import com.zapcloudstudios.furnace.api.FMinecraft;
import com.zapcloudstudios.furnace.api.FWorld;
import com.zapcloudstudios.stonebrick.ComFuncSender;
import com.zapcloudstudios.stonebrick.StoneBrick;
import com.zapcloudstudios.stonebrick.api.entity.SBPlayer;

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
	public void sendChat(Object msg)
	{
		this.server.getConfigurationManager().sendChatMsg(new ChatComponentText(String.valueOf(msg)));
	}
	
	@Override
	public void sendChat(String from, Object msg)
	{
		this.server.getConfigurationManager().sendChatMsg(this.createChatFrom(from, String.valueOf(msg)));
	}
	
	@Override
	public FWorld getWorld(int dimention)
	{
		SBWorld w = this.worlds.get(dimention);
		if (w == null)
		{
			w = new SBWorld(this.sb, dimention);
			w.world = DimensionManager.getWorld(dimention);
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
	
	@Override
	public SBPlayer[] getPlayers()
	{
		List<?> players = this.server.getConfigurationManager().playerEntityList;
		SBPlayer[] out = new SBPlayer[players.size()];
		for (int i = 0; i < out.length; i++)
		{
			out[i] = new SBPlayer((EntityPlayer) players.get(i));
		}
		return out;
	}
	
	public IChatComponent createChatFrom(String from, String msg)
	{
		return new ChatComponentTranslation("chat.type.text", new ChatComponentText(from), new ChatComponentText(msg));
	}
}
