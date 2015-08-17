package me.laekh.skypoints.commands;

import org.bukkit.command.CommandSender;

public interface SubCommand
{
	
    boolean onCommand(CommandSender sender, String[] args);
    
}
