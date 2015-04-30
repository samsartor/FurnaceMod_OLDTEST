package com.zapcloudstudios.furnace.api.entity;

import com.zapcloudstudios.furnace.api.FWorld;
import com.zapcloudstudios.furnace.api.FurnaceI;
import com.zapcloudstudios.furnace.wrap.JSFunc;
import com.zapcloudstudios.furnace.wrap.JSGet;
import com.zapcloudstudios.furnace.wrap.JSSet;

public abstract class FEntity implements FurnaceI
{
	@JSGet(name = "type")
	public abstract String entityType();
	
	@JSFunc(name = "rename")
	public abstract void rename(String name, boolean visible);
	
	@JSGet(name = "name")
	public abstract String getName();
	
	@JSGet(name = "silent")
	public abstract boolean getSilent();
	
	@JSSet(name = "silent")
	public abstract void setSilent(boolean value);
	
	@JSGet(name = "visible")
	public abstract boolean isVisible();
	
	@JSSet(name = "visible")
	public abstract void setVisible(boolean value);
	
	@JSGet(name = "dead")
	public abstract boolean isDead();
	
	@JSFunc(name = "kill")
	public abstract void kill();
	
	@JSGet(name = "pos")
	public abstract double[] getPos();
	
	@JSGet(name = "x")
	public double getXPos()
	{
		return this.getPos()[0];
	}
	
	@JSGet(name = "y")
	public double getYPos()
	{
		return this.getPos()[1];
	}
	
	@JSGet(name = "z")
	public double getZPos()
	{
		return this.getPos()[2];
	}
	
	@JSGet(name = "world")
	public abstract FWorld getWorld();
	
	@Override
	public String typeName()
	{
		return this.entityType() + "Entity";
	}
}