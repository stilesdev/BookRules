package com.mstiles92.bookrules;

import org.bukkit.entity.Player;

public class GiveBookRunnable implements Runnable {
	private final BookRulesPlugin plugin;
	private final Player player;
	
	public GiveBookRunnable(BookRulesPlugin plugin, Player player) {
		this.plugin = plugin;
		this.player = player;
	}
	
	@Override
	public void run() {
		plugin.giveAllBooks(player);
		player.sendMessage(plugin.tag + plugin.getConfig().getString("Welcome-Message"));
	}

}
