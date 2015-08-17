package me.laekh.skypoints.listeners;

import me.laekh.skypoints.SkyPoints;
import me.laekh.skypoints.profiles.SkyProfile;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onChat(AsyncPlayerChatEvent e) {
		String message = e.getMessage();
		
		for(Player p : Bukkit.getServer().getOnlinePlayers())
			message = message.replaceAll("(?i)" + p.getName(), ChatColor.AQUA + p.getDisplayName() + ChatColor.WHITE);
		
		//Prevent people from making that damned pigface
		if(message.contains("°") && e.getPlayer().hasPermission("skypoints.chat.admin")) {
			e.getPlayer().sendMessage("§4No.");
			e.setCancelled(true);
			return;
		}
		
		//Workaround otherwise illegal format cause % is used in Java for define chat variables
		message = message.replaceAll("%", "%%");
		
		String prefix = SkyPoints.getInstance().getChat().getPlayerPrefix(e.getPlayer());
		SkyProfile profile = SkyPoints.getInstance().getProfile(e.getPlayer().getUniqueId());
		
		if(prefix == "") {
			e.setFormat(ChatColor.translateAlternateColorCodes('&', "&f[&7"+profile.getPoints()+"&f] "+ e.getPlayer().getDisplayName() + "&f: ") + message);
		} else {
			e.setFormat(ChatColor.translateAlternateColorCodes('&', "§f["+prefix+" §f- "+prefix.substring(0, 2) + profile.getPoints() +"§f] " + prefix.substring(0, 2) + e.getPlayer().getDisplayName() + "§f: ") + message);
		}
		
		e.setMessage(message);
	}
	
}
