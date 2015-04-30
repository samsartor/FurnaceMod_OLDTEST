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
	
	@JSGet(name = "pos")
	public double[] getPos()
	{
		return new double[] { this.getXPos(), this.getYPos(), this.getZPos() };
	}
	
	@JSSet(name = "pos")
	public abstract void setPos(double[] pos);
	
	@JSGet(name = "x")
	public abstract double getXPos();
	
	@JSGet(name = "y")
	public abstract double getYPos();
	
	@JSGet(name = "z")
	public abstract double getZPos();
	
	@JSSet(name = "x")
	public abstract void setXPos(double x);
	
	@JSSet(name = "y")
	public abstract void setYPos(double y);
	
	@JSSet(name = "z")
	public abstract void setZPos(double z);
	
	@JSFunc(name = "move")
	public abstract void move(double dx, double dy, double dz);
	
	@JSGet(name = "velx")
	public abstract double getXVel();
	
	@JSGet(name = "vely")
	public abstract double getYVel();
	
	@JSGet(name = "velz")
	public abstract double getZVel();
	
	@JSSet(name = "velx")
	public abstract void setXVel(double xvel);
	
	@JSSet(name = "vely")
	public abstract void setYVel(double yvel);
	
	@JSSet(name = "velz")
	public abstract void setZVel(double zvel);
	
	@JSGet(name = "world")
	public abstract FWorld getWorld();
	
	@Override
	public String typeName()
	{
		return this.entityType() + "Entity";
	}
}