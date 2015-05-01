package com.zapcloudstudios.stonebrick.api.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;

import com.zapcloudstudios.furnace.api.FWorld;
import com.zapcloudstudios.furnace.api.entity.FEntity;
import com.zapcloudstudios.stonebrick.StoneBrick;

public class SBEntity implements FEntity
{
	public Entity entity;
	public StoneBrick sb = StoneBrick.instance;
	
	public SBEntity(Entity entity)
	{
		this.entity = entity;
	}
	
	@Override
	public String entityType()
	{
		return (String) EntityList.classToStringMapping.get(this.entity.getClass());
	}
	
	@Override
	public void rename(String name, boolean visible)
	{
		this.entity.setCustomNameTag(name);
		this.entity.setAlwaysRenderNameTag(visible);
	}
	
	@Override
	public String getName()
	{
		return this.entity.getCustomNameTag();
	}
	
	@Override
	public boolean isNameVisible()
	{
		return this.entity.getAlwaysRenderNameTag();
	}
	
	@Override
	public boolean getSilent()
	{
		return this.entity.isSilent();
	}
	
	@Override
	public void setSilent(boolean value)
	{
		this.entity.setSilent(value);
	}
	
	@Override
	public boolean isVisible()
	{
		return !this.entity.isInvisible();
	}
	
	@Override
	public void setVisible(boolean value)
	{
		this.entity.setInvisible(!value);
	}
	
	@Override
	public boolean isDead()
	{
		return this.entity.isDead;
	}
	
	@Override
	public double[] getPos()
	{
		return new double[] { this.entity.posX, this.entity.posY, this.entity.posZ };
	}
	
	@Override
	public void setPos(double[] pos)
	{
		this.entity.setPosition(pos[0], pos[1], pos[2]);
	}
	
	@Override
	public FWorld getWorld()
	{
		return this.sb.mc.getWorld(this.entity.dimension);
	}
	
	@Override
	public double[] getVel()
	{
		return new double[] { this.entity.motionX, this.entity.motionY, this.entity.motionZ };
	}
	
	@Override
	public void setVel(double[] vel)
	{
		this.entity.setVelocity(vel[0], vel[1], vel[2]);
		this.entity.velocityChanged = true;
	}
	
	@Override
	public void move(double dx, double dy, double dz)
	{
		this.entity.moveEntity(dx, dy, dz);
	}
	
	@Override
	public void kill()
	{
		this.entity.worldObj.removeEntity(this.entity);
	}
	
	public void dataChanged()
	{
		this.entity.getDataWatcher().func_111144_e();
	}
	
	public static FEntity get(Entity entity)
	{
		if (entity instanceof EntityPlayer)
		{
			return new SBPlayer((EntityPlayer) entity);
		}
		return new SBEntity(entity);
	}
}
