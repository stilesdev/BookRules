/**
 * This document is a part of the source code and related artifacts for
 * BookRules, an open source Bukkit plugin for automatically distributing
 * written books to new players.
 *
 * http://dev.bukkit.org/server-mods/bookrules/
 * http://github.com/mstiles92/BookRules
 *
 * Copyright © 2013 Matthew Stiles (mstiles92)
 *
 * Licensed under the Common Development and Distribution License Version 1.0
 * You may not use this file except in compliance with this License.
 *
 * You may obtain a copy of the CDDL-1.0 License at
 * http://opensource.org/licenses/CDDL-1.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the license.
 */

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
			e.getPlayer().sendMessage(plugin.tag + "New version available! See http://dev.bukkit.org/server-mods/bookrules/ for more information.");
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
