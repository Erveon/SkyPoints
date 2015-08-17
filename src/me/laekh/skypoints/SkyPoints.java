package me.laekh.skypoints;

import java.util.UUID;

import me.laekh.skypoints.commands.CommandHandler;
import me.laekh.skypoints.database.SkyDB;
import me.laekh.skypoints.listeners.ChatListener;
import me.laekh.skypoints.listeners.JoinLeaveListener;
import me.laekh.skypoints.profiles.ProfileManager;
import me.laekh.skypoints.profiles.SkyProfile;
import net.milkbowl.vault.chat.Chat;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class SkyPoints extends JavaPlugin {
	
	static SkyPoints instance;
	Chat chat;
	
	private SkyDB skydb;
	private Settings settings;
	private ProfileManager profileManager;

	@SuppressWarnings("deprecation")
	@Override
	public void onEnable() {
		saveDefaultConfig();
		instance = this;
		
		skydb = new SkyDB();
		settings = new Settings();
		profileManager = new ProfileManager();
		
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new ChatListener(), this);
		pm.registerEvents(new JoinLeaveListener(), this);
		
		setupChat();
		setCommands();
		
		for(Player p : Bukkit.getOnlinePlayers()) {
			loadProfile(p.getUniqueId(), p.getName());
			getProfile(p.getUniqueId()).login();
		}
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void onDisable() {
		for(Player p : Bukkit.getOnlinePlayers())
			getProfile(p.getUniqueId()).save();
		getSkyDB().close();
	}
	
	public void setCommands() {
		getCommand("skypoints").setExecutor(new CommandHandler(this));
	}
	
	public static SkyPoints getInstance() {
		return instance;
	}
	
	public SkyDB getSkyDB() {
		return skydb;
	}
	
	public Settings getSettings() {
		return settings;
	}
	
	public ProfileManager getProfileManage() {
		return profileManager;
	}
	
	public SkyProfile getProfile(UUID uuid) {
		return getProfileManage().getProfile(uuid);
	}
	
	public SkyProfile loadOfflineProfile(UUID uuid) {
		return getProfileManage().getProfile(uuid);
	}
	
	public SkyProfile loadProfile(UUID uuid, String name) {
		return getProfileManage().getProfile(uuid, name);
	}
	
	public Chat getChat() {
		return chat;
	}
	
	private boolean setupChat() {
        RegisteredServiceProvider<Chat> chatProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.chat.Chat.class);
        if (chatProvider != null) {
            chat = chatProvider.getProvider();
        }
        return (chat != null);
    }
	
}
