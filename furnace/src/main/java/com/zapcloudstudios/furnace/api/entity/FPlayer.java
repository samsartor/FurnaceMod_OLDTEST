package com.zapcloudstudios.furnace.api.entity;

import com.zapcloudstudios.furnace.wrap.annotation.APIFunction;
import com.zapcloudstudios.furnace.wrap.annotation.APIGetter;
import com.zapcloudstudios.furnace.wrap.annotation.APISetter;

public interface FPlayer extends FEntity
{
	@APIGetter("xp")
	public int getXP();
	
	@APISetter("xp")
	public void setXP(int xp);
	
	@APIGetter("hunger")
	public int getHunger();
	
	@APISetter("hunger")
	public void setHunger(int hunger);
	
	@APIFunction("tell")
	public void sendChat(String msg);
	
	@APIFunction("tell")
	public void sendChat(String from, String msg);
	
	@APIFunction("addPrefix")
	public void addPrefix(String text);
	
	@APIFunction("addSuffix")
	public void addSuffix(String text);
	
	@APIFunction("clearPrefix")
	public void clearPrefix();
	
	@APIFunction("clarSuffix")
	public void clearSuffix();
	
	@APIGetter("displayName")
	public String getDisplayName();
}
