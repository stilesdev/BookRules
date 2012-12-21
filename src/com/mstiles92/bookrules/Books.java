package com.mstiles92.bookrules;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;

public class Books {

	private final BookRulesPlugin plugin;
	private YamlConfiguration booksConfig;
	private File configFile;
	private boolean loaded;
	private String filename;
	
	public Books(BookRulesPlugin plugin) {
		this.plugin = plugin;
		loaded = false;
	}
	
	public void load(String filename) {
		configFile = new File(plugin.getDataFolder(), filename);
		this.filename = filename;
		
		if (configFile.exists()) {
			booksConfig = new YamlConfiguration();
			
			try {
				booksConfig.load(configFile);
			}
			catch (Exception e) {
				throw new IllegalStateException("Could not load config!", e);
			}
			loaded = true;
		} else {
			try {
				configFile.createNewFile();
				booksConfig = new YamlConfiguration();
				booksConfig.load(configFile);
			}
			catch (Exception e) {
				throw new IllegalStateException("Could not create/load config!", e);
			}
		}
	}
	
	public void clear() {
		configFile = new File(plugin.getDataFolder(), filename);
		
		try {
			configFile.delete();
			configFile.createNewFile();
			booksConfig = new YamlConfiguration();
			booksConfig.load(configFile);
		}
		catch (Exception e) {
			throw new IllegalStateException("Could not recreate and load config!", e);
		}
		
	}
	
	public void save() {
		try {
			booksConfig.save(configFile);
		}
		catch (IOException e) {
			throw new IllegalStateException("Coudld not save config!", e);
		}
	}
	
	public File getFile() {
		return this.configFile;
	}
	
	public YamlConfiguration getConfig() {
		if (!loaded) {
			load("books.yml");
		}
		return booksConfig;
	}
}
