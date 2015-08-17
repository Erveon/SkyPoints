package me.laekh.skypoints.listeners;

import me.laekh.skypoints.SkyPoints;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class JoinLeaveListener implements Listener {

	@EventHandler
	public void onJoin(final PlayerJoinEvent e) {
		new BukkitRunnable() {
			@Override
			public void run() {
				SkyPoints.getInstance().loadProfile(e.getPlayer().getUniqueId(), e.getPlayer().getName());
				SkyPoints.getInstance().getProfile(e.getPlayer().getUniqueId()).login();
			}
		}.runTaskAsynchronously(SkyPoints.getInstance());
	}
	
	@EventHandler
	public void onLeave(final PlayerQuitEvent e) {
		new BukkitRunnable() {
			@Override
			public void run() {
				SkyPoints.getInstance().getProfile(e.getPlayer().getUniqueId()).save();
				SkyPoints.getInstance().getProfileManage().unload(e.getPlayer().getUniqueId());
			}
		}.runTaskAsynchronously(SkyPoints.getInstance());
	}
	
}
