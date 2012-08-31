package com.mstiles92.bookrules;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class PlayerJoinListener implements Listener {
	private final BookRulesPlugin plugin;
	
	public PlayerJoinListener(BookRulesPlugin plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void OnPlayerJoin(PlayerLoginEvent e) {
		if (e.getPlayer().hasPlayedBefore() || !plugin.getConfig().getBoolean("Give-Books-On-First-Join")) {
			return;
		}
		
		// TODO: Give the player a copy of every stored book.
	}

}
