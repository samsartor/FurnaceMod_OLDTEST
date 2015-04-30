package com.zapcloudstudios.stonebrick;

import net.minecraft.command.CommandResultStats.Type;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class ComFuncSender implements ICommandSender
{
	public World world;
	public int x;
	public int y;
	public int z;
	public String name;
	
	public ComFuncSender(World world, int x, int y, int z, String name)
	{
		this.world = world;
		this.x = x;
		this.y = y;
		this.z = z;
		this.name = name;
	}
	
	@Override
	public String getName()
	{
		return "furnace";
	}
	
	@Override
	public IChatComponent getDisplayName()
	{
		return new ChatComponentText(this.name);
	}
	
	@Override
	public void addChatMessage(IChatComponent message)
	{
		
	}
	
	@Override
	public boolean canUseCommand(int permLevel, String commandName)
	{
		return true;
	}
	
	@Override
	public BlockPos getPosition()
	{
		return new BlockPos(this.x, this.y, this.z);
	}
	
	@Override
	public Vec3 getPositionVector()
	{
		return new Vec3(this.x, this.y, this.z);
	}
	
	@Override
	public World getEntityWorld()
	{
		return this.world;
	}
	
	@Override
	public Entity getCommandSenderEntity()
	{
		return null;
	}
	
	@Override
	public boolean sendCommandFeedback()
	{
		return false;
	}
	
	@Override
	public void setCommandStat(Type type, int amount)
	{
		
	}
}
