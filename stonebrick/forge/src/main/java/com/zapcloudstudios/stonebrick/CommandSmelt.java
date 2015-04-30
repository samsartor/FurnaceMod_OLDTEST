package com.zapcloudstudios.stonebrick;

import org.mozilla.javascript.EcmaError;
import org.mozilla.javascript.EvaluatorException;

import com.zapcloudstudios.furnace.FurnaceContext;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

public class CommandSmelt extends CommandBase
{
	public FurnaceContext context;
	
	public CommandSmelt(FurnaceContext context)
	{
		this.context = context;
	}
	
	@Override
	public String getName()
	{
		return "smelt";
	}
	
	@Override
	public int getRequiredPermissionLevel()
	{
		return 2;
	}
	
	@Override
	public String getCommandUsage(ICommandSender sender)
	{
		return "commands.smelt.usage";
	}
	
	@Override
	public void execute(ICommandSender sender, String[] args) throws CommandException
	{
		Object result;
		try
		{
			result = this.context.eval("comsmelt_" + sender.getName(), String.join(" ", args));
		}
		catch (EcmaError e)
		{
			e.printStackTrace();
			throw new CommandException("The furnace script could not be run: " + e.getErrorMessage(), e);
		}
		catch (EvaluatorException e)
		{
			e.printStackTrace();
			throw new CommandException("The furnace script could not be run: " + e.getMessage(), e);
		}
		if (result != null)
		{
			if (result != this.context.undefned())
			{
				sender.addChatMessage(new ChatComponentText(String.valueOf(result)));
			}
		}
	}
}
