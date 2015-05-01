package com.zapcloudstudios.furnace;

public class FurnaceException extends RuntimeException
{
	private static final long serialVersionUID = -2673830522043727293L;
	
	public FurnaceException(String msg, Object... arg)
	{
		super(String.format(msg, arg));
	}
	
	public FurnaceException(String msg, Throwable cause, Object... arg)
	{
		super(String.format(msg, arg), cause);
	}
	
	public String details()
	{
		return this.getMessage();
	}
}
