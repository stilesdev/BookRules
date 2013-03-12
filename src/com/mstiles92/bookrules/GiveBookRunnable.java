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
import org.bukkit.entity.Player;

/**
 * GiveBookRunnable is a simple class used to give the player a book after
 * a short delay.
 * 
 * @author mstiles92
 */
public class GiveBookRunnable implements Runnable {
	private final BookRulesPlugin plugin;
	private Player player;
	
	/**
	 * The main constructor for this class
	 * 
	 * @param plugin the instance of the plugin
	 * @param player the player to give the books to
	 */
	public GiveBookRunnable(BookRulesPlugin plugin, Player player) {
		this.plugin = plugin;
		this.player = player;
	}
	
	@Override
	public void run() {
		int num = BookStorage.getInstance(plugin).givePlayerUngivenBooks(player);
		plugin.log("Player " + player.getName() + " was given " + num + " book(s) when joining the server.");
		
		if (num > 0 && plugin.getConfig().getBoolean("Display-Messages")) {
			player.sendMessage(ChatColor.BLUE + "[BookRules] " + ChatColor.GREEN + num + " new books received!");
		}
	}

}
