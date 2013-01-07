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

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.BukkitMetrics;

public class BookRulesPlugin extends JavaPlugin {
	public Books books;
	public final String tag = ChatColor.BLUE + "[BookRules] " + ChatColor.GREEN;
	public boolean updateAvailable = false;
	public String latestKnownVersion, changes;
	
	public void onEnable() {
		getCommand("rulebook").setExecutor(new BookRulesCommandExecutor(this));
		getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
		loadConfig();
		latestKnownVersion = this.getDescription().getVersion();
		if (getConfig().getBoolean("Check-for-Updates")) {
			getServer().getScheduler().runTaskTimer(this, new UpdateChecker(this), 40, 216000);
		}
		try {
			BukkitMetrics metrics = new BukkitMetrics(this);
			metrics.start();
		} catch (IOException e) {
			log("Failed to start metrics!");
		}
	}
	
	public void onDisable() {
		books.save();
	}
	
	public void loadConfig() {
		getConfig().options().copyDefaults(true);
		saveConfig();
		books = new Books(this);
		books.load("books.yml");
		orderBooks();
	}
	
	public void log(String message) {
		if (getConfig().getBoolean("Verbose")) {
			getLogger().info(message);
		}
	}
	
	public int getCurrentID() {
		final Set<String> set = books.getConfig().getKeys(false);
		return (set.size() + 1);
	}
	
	public boolean giveBook(Player p, String ID) {
		if (books.getConfig().get(ID) == null) {
			return false;
		}
		
		ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
		BookMeta meta = (BookMeta) book.getItemMeta();
		final Map<String, Object> map = books.getConfig().getConfigurationSection(ID + ".Pages").getValues(false);
		ArrayList<String> list = new ArrayList<String>();
		
		meta.setTitle(books.getConfig().getString(ID + ".Title"));
		meta.setAuthor(books.getConfig().getString(ID + ".Author"));
		
		for (int i = 0; i < map.size(); i++) {
			list.add(i, (String) map.get("Page-" + String.valueOf(i)));
		}
		meta.setPages(list);
		
		book.setItemMeta(meta);
		p.getInventory().addItem(book);
		return true;
	}
	
	public boolean giveAllBooks(Player p) {
		final Set<String> set = books.getConfig().getKeys(false);
		if (set.size() == 0) {
			return false;
		}
		
		p.sendMessage(tag + getConfig().getString("Welcome-Message"));
		for (String s : set) {
			giveBook(p, s);
		}
		return true;
	}
	
	public void addBook(ItemStack book) {
		final LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		final BookMeta meta = (BookMeta) book.getItemMeta();
		final List<String> pages = meta.getPages();
		final String ID = String.valueOf(getCurrentID());
		
		for (Integer i = 0; i < pages.size(); i++) {
			map.put("Page-" + i.toString(), pages.get(i));
		}
		books.getConfig().createSection(ID + ".Pages", map);
		books.getConfig().set(ID + ".Title", meta.getTitle());
		books.getConfig().set(ID + ".Author", meta.getAuthor());
		books.save();
	}
	
	public boolean deleteBook(String ID) {
		if (books.getConfig().getConfigurationSection(ID) != null) {
			books.getConfig().set(ID, null);
			books.save();
			orderBooks();
			return true;
		} else {
			return false;
		}
	}
	
	public List<String> readAllBooks() {
		ArrayList<String> list = new ArrayList<String>();
		final Set<String> keys = books.getConfig().getKeys(false);
		
		for (String ID : keys) {
			String line = ID + " - " + books.getConfig().getString(ID + ".Title") + " by " + books.getConfig().getString(ID + ".Author");
			list.add(line);
		}
		return list;
	}
	
	public void orderBooks() {
		final Set<String> set = books.getConfig().getKeys(false);
		YamlConfiguration tempConfig = books.getConfig();
		int id = 1;
		books.clear();
		
		for(String s : set) {
			books.getConfig().set(String.valueOf(id), tempConfig.getConfigurationSection(s));
			id++;
		}
		books.save();
	}
}
