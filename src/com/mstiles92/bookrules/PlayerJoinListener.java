package com.mstiles92.bookrules;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
	private final BookRulesPlugin plugin;
	
	public PlayerJoinListener(BookRulesPlugin plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void OnPlayerJoin(PlayerJoinEvent e) {
		if (plugin.updateAvailable && e.getPlayer().hasPermission("bookrules.receivealerts")) {
			e.getPlayer().sendMessage(plugin.tag + "New version available! See " + ChatColor.BLUE + "http://dev.bukkit.org/server-mods/bookrules/" + ChatColor.GREEN + " for more information.");
			e.getPlayer().sendMessage(plugin.tag + "Current version: " + ChatColor.BLUE + plugin.getDescription().getVersion() + ChatColor.GREEN + ", New version: " + ChatColor.BLUE + plugin.latestKnownVersion);
			e.getPlayer().sendMessage(plugin.tag + "Changes in this version: " + ChatColor.BLUE + plugin.changes);
		}
		
		plugin.log("Give-Books-On-First-Join: " + plugin.getConfig().getBoolean("Give-Books-On-First-Join"));
		plugin.log("Player.hasPlayedBefore(): " + e.getPlayer().hasPlayedBefore());
		
		if (!plugin.getConfig().getBoolean("Give-Books-On-First-Join") || e.getPlayer().hasPlayedBefore()) {
			return;
		}
		
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new GiveBookRunnable(plugin, e.getPlayer()), (plugin.getConfig().getLong("Seconds-Delay") * 20));
	}

}
