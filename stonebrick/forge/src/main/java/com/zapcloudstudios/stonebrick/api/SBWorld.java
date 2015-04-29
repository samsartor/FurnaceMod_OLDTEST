package com.zapcloudstudios.stonebrick.api;

import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;

import com.zapcloudstudios.furnace.api.FWorld;
import com.zapcloudstudios.stonebrick.StoneBrick;

public class SBWorld extends FWorld
{
	public WorldServer world;
	private StoneBrick sb;
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
					DimensionManager.initDimension(this.dimention);
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
}
