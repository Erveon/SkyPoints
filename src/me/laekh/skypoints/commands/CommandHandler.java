package me.laekh.skypoints.commands;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;

import me.laekh.skypoints.SkyPoints;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class CommandHandler implements CommandExecutor
{
    private HashMap<String, SubCommand> commands;
    
    public CommandHandler(final Plugin plugin) {
        super();
        this.commands = new HashMap<String, SubCommand>();
        this.loadCommands();
    }
    
    private void loadCommands() {
        this.commands.put("admin", new AdminCommands());
    }
    
    public boolean onCommand(final CommandSender sender, final Command command, final String commandLabel, String[] args) {
        if (args == null || args.length < 1) {
        	if(sender instanceof Player) {
        		Player p = (Player) sender;
        		help(p);
        	}
            return true;
        }
        
        final String sub = args[0];
        final Vector<String> arguments = new Vector<String>();
        arguments.addAll(Arrays.asList(args));
        arguments.remove(0);
        args = arguments.toArray(new String[0]);
        if (!this.commands.containsKey(sub)) {
        	sender.sendMessage(SkyPoints.getInstance().getSettings().getPrefix() + "§cThat command does not exist.");
            return true;
        }
        try {
            this.commands.get(sub).onCommand(sender, args);
        }
        catch (Exception e) {
            e.printStackTrace();
            sender.sendMessage(SkyPoints.getInstance().getSettings().getPrefix() + "§4An error has occured.");
        }
        return true;
    }
    
    public void help(Player p) {
		p.sendMessage("§8---------------(§a§lSkyPoints Commands§8)--------------");
		p.sendMessage("§9More information and development coming soon.");
		p.sendMessage("§8--------------------------------------------------");
	}
    
}
