package com.zapcloudstudios.furnace.api.entity;

import com.zapcloudstudios.furnace.api.FWorld;
import com.zapcloudstudios.furnace.api.FurnaceI;
import com.zapcloudstudios.furnace.wrap.annotation.APIFunction;
import com.zapcloudstudios.furnace.wrap.annotation.APIGetter;
import com.zapcloudstudios.furnace.wrap.annotation.APISetter;

public abstract class FEntity implements FurnaceI
{
	@APIGetter("type")
	public abstract String entityType();
	
	@APIFunction("rename")
	public abstract void rename(String name, boolean visible);
	
	public abstract boolean isNameVisible();
	
	@APIGetter("name")
	public abstract String getName();
	
	@APISetter("name")
	public void setName(String name)
	{
		this.rename(name, this.isNameVisible());
	}
	
	@APIGetter("silent")
	public abstract boolean getSilent();
	
	@APISetter("silent")
	public abstract void setSilent(boolean value);
	
	@APIGetter("visible")
	public abstract boolean isVisible();
	
	@APISetter("visible")
	public abstract void setVisible(boolean value);
	
	@APIGetter("dead")
	public abstract boolean isDead();
	
	@APIGetter("pos")
	public abstract double[] getPos();
	
	@APISetter("pos")
	public abstract void setPos(double[] pos);
	
	@APIFunction("move")
	public abstract void move(double dx, double dy, double dz);
	
	@APIGetter("vel")
	public abstract double[] getVel();
	
	@APISetter("vel")
	public abstract void setVel(double[] pos);
	
	@APIGetter("world")
	public abstract FWorld getWorld();
	
	@APIFunction("kill")
	public abstract void kill();
	
	@Override
	public String typeName()
	{
		return this.entityType() + "Entity";
	}
}