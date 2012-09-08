package com.mstiles92.bookrules;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.mstiles92.bookrules.lib.CraftWrittenBook;
import com.mstiles92.bookrules.lib.MetricsLite;
import com.mstiles92.bookrules.lib.WrittenBook;

public class BookRulesPlugin extends JavaPlugin {
	public Books books;
	public final String tag = ChatColor.BLUE + "[BookRules] " + ChatColor.GREEN;
	public boolean updateAvailable = false;
	public String latestKnownVersion;
	
	public void onEnable() {
		getCommand("rulebook").setExecutor(new BookRulesCommandExecutor(this));
		getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
		loadConfig();
		latestKnownVersion = this.getDescription().getVersion();
		if (getConfig().getBoolean("Check-for-Updates")) {
			this.getServer().getScheduler().scheduleAsyncRepeatingTask(this, new UpdateChecker(this), 40, 216000);
		}
		try {
			MetricsLite metrics = new MetricsLite(this);
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
		Set<String> set = books.getConfig().getKeys(false);
		return (set.size() + 1);
	}
	
	public boolean giveBook(Player p, String ID) {
		if (books.getConfig().get(ID) == null) {
			return false;
		}
		
		WrittenBook book = new CraftWrittenBook();
		
		book.setTitle(books.getConfig().getString(ID + ".Title"));
		book.setAuthor(books.getConfig().getString(ID + ".Author"));
		
		Map<String, Object> map = books.getConfig().getConfigurationSection(ID + ".Pages").getValues(false);
		ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < map.size(); i++) {
			list.add(i, (String) map.get("Page-" + String.valueOf(i)));
		}
		
		book.setPages(list);
		
		p.getInventory().addItem(book.getItemStack(1));
		return true;
	}
	
	public boolean giveAllBooks(Player p) {
		Set<String> set = books.getConfig().getKeys(false);
		if (set.size() == 0) {
			return false;
		}
		for (String s : set) {
			giveBook(p, s);
		}
		return true;
	}
	
	public void addBook(WrittenBook book) {
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		
		String[] pages = book.getPagesArray();
		
		for (Integer i = 0; i < pages.length; i++) {
			map.put("Page-" + i.toString(), pages[i]);
		}
		String ID = String.valueOf(getCurrentID());
		books.getConfig().createSection(ID + ".Pages", map);
		books.getConfig().set(ID + ".Title", book.getTitle());
		books.getConfig().set(ID + ".Author", book.getAuthor());
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
		Set<String> keys = books.getConfig().getKeys(false);
		for (String ID : keys) {
			String line = ID + " - " + books.getConfig().getString(ID + ".Title") + " by " + books.getConfig().getString(ID + ".Author");
			list.add(line);
		}
		
		return list;
	}
	
	public void orderBooks() {
		Set<String> set = books.getConfig().getKeys(false);
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
