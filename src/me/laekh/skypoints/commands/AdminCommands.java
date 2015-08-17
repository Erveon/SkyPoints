package me.laekh.skypoints.commands;

import me.laekh.skypoints.SkyPoints;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AdminCommands implements SubCommand {

	@Override
	public boolean onCommand(CommandSender sender, String[] args) {
		if(!sender.isOp() && !sender.hasPermission("skypoints.admin")) {
			sender.sendMessage(SkyPoints.getInstance().getSettings().getPrefix() + "§cYou do not have access to that command. [skypoints.admin]");
			return true;
		}
		if(args.length == 0) {
			help(sender);
			return true;
		}
		switch(args[0].toLowerCase()) {
			case "setpoints":
				if(args.length != 3) {
					sender.sendMessage(SkyPoints.getInstance().getSettings().getPrefix() +"§cUsage: §9/skypoints admin setpoints §bname §6amount");
					return true;
				}
				Player player = toPlayer(sender, args[1]);
				if(player == null) return true;
				Integer points = toInteger(sender, args[2]);
				if(points == null) return true;
				SkyPoints.getInstance().getProfile(player.getUniqueId()).setPoints(points);
				sender.sendMessage(SkyPoints.getInstance().getSettings().getPrefix() +"§aYou have set §b"+player.getDisplayName()+"§a's points to §b"+points);
				break;
			default:
				help(sender);
				break;
		}
		return true;
	}
	
    public void help(CommandSender p) {
		p.sendMessage("§8---------------(§a§lSkyPoints Admin§8)------------------");
		p.sendMessage("§9 /skypoints admin §bsetpoints §6[username]");
		p.sendMessage("§8--------------------------------------------------");
	}
    
    public Player toPlayer(CommandSender sender, String username) {
		Player player = Bukkit.getPlayer(username);
		if(player == null)
			sender.sendMessage(SkyPoints.getInstance().getSettings().getPrefix() +"§cThe user §b" + username +" §cis not online or does not exist.");
		return player;
    }
    
    public Integer toInteger(CommandSender sender, String s) {
		try {
			return Integer.parseInt(s);
		} catch(Exception ignored) {
			sender.sendMessage(SkyPoints.getInstance().getSettings().getPrefix() +"§cThe value §b" + s +" §cis not a valid number.");
			return null;
		}
	}

}
