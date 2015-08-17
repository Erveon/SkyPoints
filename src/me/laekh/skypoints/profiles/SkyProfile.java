package me.laekh.skypoints.profiles;

import java.util.Date;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.laekh.skypoints.SkyPoints;
import net.minecraft.util.org.apache.commons.lang3.time.DateUtils;

public class SkyProfile {
	
	Date lastLogin;
	
	UUID uuid;
	Integer points;
	String name = null;

	//Online person
	public SkyProfile(UUID uuid, String name) {
		this.uuid = uuid;
		this.name = name;
		this.lastLogin = new Date(0);
		if(SkyPoints.getInstance().getSkyDB().hasProfile(uuid))
			SkyPoints.getInstance().getSkyDB().loadProfile(this);
		else {
			setName(name);
			setPoints(0);
			save();
		}
	}
	
	//Offline person
	public SkyProfile(UUID uuid) {
		this.uuid = uuid;
		this.lastLogin = new Date(0);
		if(SkyPoints.getInstance().getSkyDB().hasProfile(uuid))
			SkyPoints.getInstance().getSkyDB().loadProfile(this);
		else
			setPoints(0);
	}
	
	public void login() {
		Date now = new Date();
		if(!DateUtils.isSameDay(lastLogin, now)) {
			sendMessage("&aYou have received 1 SkyPoint for logging in today!");
			addPoints(1);
		}
		lastLogin = now;
	}
	
	public UUID getUUID() {
		return uuid;
	}
	
	public Player getPlayer() {
		return Bukkit.getPlayer(getUUID());
	}
	
	public String getName() {
		return name;
	}
	
	public Integer getPoints() {
		return points;
	}
	
	public SkyProfile addPoints(int points) {
		this.points += points;
		return this;
	}
	
	public SkyProfile removePoints(int points) {
		this.points -= points;
		return this;
	}
	
	public SkyProfile setPoints(int points) {
		this.points = points;
		return this;
	}
	
	public SkyProfile setName(String name) {
		this.name = name;
		return this;
	}
	
	public void setLastLogin(long epoch) {
		this.lastLogin = new Date(epoch);
	}
	
	public Date getLastLogin() {
		return lastLogin;
	}
	
	public void save() {
		SkyPoints.getInstance().getSkyDB().saveProfile(this);
	}
	
	public void sendMessage(String message) {
		Player p = getPlayer();
		if(p != null) 
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', SkyPoints.getInstance().getSettings().getPrefix() + message));
	}
	
}
