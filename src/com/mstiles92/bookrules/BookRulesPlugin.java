package com.mstiles92.bookrules;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

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
	
	public int getNewID() {
		currentID++;
		getConfig().set("CurrentID-DO-NOT-CHANGE", currentID);
		saveConfig();
		return currentID;
	}

}
