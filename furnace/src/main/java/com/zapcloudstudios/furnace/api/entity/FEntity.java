package com.zapcloudstudios.furnace.api.entity;

import com.zapcloudstudios.furnace.api.FWorld;
import com.zapcloudstudios.furnace.api.FurnaceI;
import com.zapcloudstudios.furnace.wrap.annotation.APIFunction;
import com.zapcloudstudios.furnace.wrap.annotation.APIGetter;
import com.zapcloudstudios.furnace.wrap.annotation.APISetter;

public interface FEntity extends FurnaceI
{
	@APIGetter("type")
	public String entityType();
	
	@APIFunction("rename")
	public void rename(String name, boolean visible);
	
	public boolean isNameVisible();
	
	@APIGetter("name")
	public abstract String getName();
	
	@APISetter("name")
	public default void setName(String name)
	{
		this.rename(name, this.isNameVisible());
	}
	
	@APIGetter("silent")
	public boolean getSilent();
	
	@APISetter("silent")
	public void setSilent(boolean value);
	
	@APIGetter("visible")
	public boolean isVisible();
	
	@APISetter("visible")
	public void setVisible(boolean value);
	
	@APIGetter("dead")
	public boolean isDead();
	
	@APIGetter("pos")
	public double[] getPos();
	
	@APISetter("pos")
	public void setPos(double[] pos);
	
	@APIFunction("move")
	public void move(double dx, double dy, double dz);
	
	@APIGetter("vel")
	public abstract double[] getVel();
	
	@APISetter("vel")
	public void setVel(double[] pos);
	
	@APIGetter("world")
	public FWorld getWorld();
	
	@APIFunction("kill")
	public void kill();
	
	@Override
	public default String typeName()
	{
		return this.entityType() + "Entity";
	}
}