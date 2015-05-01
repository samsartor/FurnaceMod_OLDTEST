package com.zapcloudstudios.stonebrick.api;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.WorldServer;

import com.zapcloudstudios.furnace.api.FBlockPos;
import com.zapcloudstudios.furnace.api.FWorld;
import com.zapcloudstudios.furnace.api.entity.FEntity;
import com.zapcloudstudios.stonebrick.StoneBrick;
import com.zapcloudstudios.stonebrick.api.entity.SBEntity;

public class SBWorld extends FWorld
{
	public WorldServer world;
	public StoneBrick sb;
	private boolean loadError = false;;
	
	public SBWorld(StoneBrick sb, int dimention)
	{
		super(dimention);
		this.sb = sb;
	}
	
	public boolean load()
	{
		if (this.world == null)
		{
			if (this.loadError)
			{
				return false;
			}
			else
			{
				try
				{
					this.world = this.sb.server.worldServerForDimension(this.dimention);
				}
				catch (RuntimeException e)
				{
					
				}
				if (this.world == null)
				{
					this.loadError = true;
					return false;
				}
			}
		}
		this.loadError = false;
		return true;
	}
	
	@Override
	public FEntity[] getEntitiesInBox(double x, double y, double z, double d)
	{
		if (this.load())
		{
			List<?> es = this.world.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.fromBounds(x - d, y - d, z - d, x + d, y + d, z + d));
			FEntity[] out = new FEntity[es.size()];
			for (int i = 0; i < out.length; i++)
			{
				out[i] = SBEntity.get((Entity) es.get(i));
			}
			return out;
		}
		return null;
	}
	
	@Override
	public FBlockPos getPos(int x, int y, int z)
	{
		return new SBBlockPos(this, x, y, z);
	}
}
