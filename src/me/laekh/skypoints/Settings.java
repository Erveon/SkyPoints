package me.laekh.skypoints;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

public class Settings {

	FileConfiguration config;
	String prefix;
	
	public Settings() {
		config = SkyPoints.getInstance().getConfig();
		prefix = ChatColor.translateAlternateColorCodes('&', getConfig().getString("prefix", "&8[&aSkyPoints&8]&f "));
	}
	
	public FileConfiguration getConfig() {
		return config;
	}
	
	public String getPrefix() {
		return prefix;
	}
	
}
