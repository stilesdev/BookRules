package com.mstiles92.bookrules;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.mstiles92.bookrules.lib.CraftWrittenBook;
import com.mstiles92.bookrules.lib.WrittenBook;

public class BookRulesPlugin extends JavaPlugin {
	public Books books;
	public final String tag = ChatColor.GREEN + "[BookRules] ";
	
	private int currentID;
	
	public void onEnable() {
		getCommand("rulebook").setExecutor(new BookRulesCommandExecutor(this));
		getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
		loadConfig();
	}
	
	public void onDisable() {
		books.save();
	}
	
	public void loadConfig() {
		getConfig().options().copyDefaults(true);
		saveConfig();
		books.load("books.yml");
		currentID = getConfig().getInt("CurrentID-DO-NOT-CHANGE");
	}
	
	public void log(String message) {
		if (getConfig().getBoolean("Verbose")) {
			getLogger().info(message);
		}
	}
	
	public int getNewID() {
		currentID++;
		getConfig().set("CurrentID-DO-NOT-CHANGE", currentID);
		saveConfig();
		return currentID;
	}
	
	public void giveBook(Player p, String ID) {
		WrittenBook book = new CraftWrittenBook();
		book.setTitle(books.getConfig().getString(ID + ".Title"));
		book.setAuthor(books.getConfig().getString(ID + ".Author"));
		book.setPages(books.getConfig().getStringList(ID + ".Pages"));
		
		p.getInventory().addItem(book.getItemStack(1));
	}
	
	public void addBook(WrittenBook book) {
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		
		String[] pages = book.getPagesArray();
		
		for (Integer i = 0; i < pages.length; i++) {
			map.put("Page-" + i.toString(), pages[i]);
		}
		String ID = String.valueOf(getNewID());
		books.getConfig().createSection(ID + ".Pages", map);
		books.getConfig().set(ID + ".Title", book.getTitle());
		books.getConfig().set(ID + ".Author", book.getAuthor());
		books.save();
	}
	
	public boolean deleteBook(String ID) {
		if (books.getConfig().getConfigurationSection(ID) != null) {
			books.getConfig().set(ID, null);
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
	
	
	
	

}
