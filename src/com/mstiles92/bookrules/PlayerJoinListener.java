package com.mstiles92.bookrules;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitScheduler;

public class PlayerJoinListener implements Listener {
	
	/** main plugin instance */
	private final BookRulesPlugin plugin;
	
	/** Scheduler instance from bukkit */
	private final BukkitScheduler bukkitScheduler;
	
	/** time to wait before giving the player a book. */
	private final long waitTime;
	
	/** if we give a book on join */
	private final boolean giveOnJoin;
	
	public PlayerJoinListener(BookRulesPlugin plugin) {
		this.plugin = plugin;
		bukkitScheduler = plugin.getServer().getScheduler();
		waitTime = plugin.getConfig().getLong("Seconds-Delay") * 20;
		giveOnJoin = plugin.getConfig().getBoolean("Give-Books-On-First-Join");
	}
	
	@EventHandler(ignoreCancelled = true)
	public void onPlayerJoin(PlayerJoinEvent e) {
		
		final Player player = e.getPlayer();
		
		
		if (plugin.updateAvailable && player.hasPermission("bookrules.receivealerts")) {
			e.getPlayer().sendMessage(plugin.tag + "New version available! See " + ChatColor.BLUE + "http://dev.bukkit.org/server-mods/bookrules/" + ChatColor.GREEN + " for more information.");
			e.getPlayer().sendMessage(plugin.tag + "Current version: " + ChatColor.BLUE + plugin.getDescription().getVersion() + ChatColor.GREEN + ", New version: " + ChatColor.BLUE + plugin.latestKnownVersion);
		}
		
		final boolean hasLoggedIn = player.hasPlayedBefore();
		
		plugin.log("Give-Books-On-First-Join: " + giveOnJoin);
		plugin.log("Player.hasPlayedBefore(): " + hasLoggedIn);
		
		if (!giveOnJoin || hasLoggedIn) {
			return;
		}
		
		bukkitScheduler.scheduleSyncDelayedTask(plugin, new GiveBookRunnable(plugin, player), waitTime);
	}

}
