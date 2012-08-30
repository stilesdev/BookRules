package com.mstiles92.bookrules;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

public class Books {

	private final BookRulesPlugin plugin;
	private YamlConfiguration booksConfig;
	private File configFile;
	private boolean loaded;
	
	public Books(BookRulesPlugin plugin) {
		this.plugin = plugin;
		loaded = false;
	}
	
	public void load(String filename) {
		configFile = new File(plugin.getDataFolder(), filename);
		
		if (configFile.exists()) {
			booksConfig = new YamlConfiguration();
			
			try {
				booksConfig.load(configFile);
			}
			catch (FileNotFoundException e) {
				// TODO: Handle exception
			}
			catch (IOException e) {
				// TODO: Handle exception
			}
			catch (InvalidConfigurationException e) {
				// TODO: Handle exception
			}
			loaded = true;
		} else {
			try {
				configFile.createNewFile();
				booksConfig = new YamlConfiguration();
				booksConfig.load(configFile);
			}
			catch (IOException e) {
				// TODO: Handle exception
			}
			catch (InvalidConfigurationException e) {
				// TODO: Handle exception
			}
		}
	}
	
	public void save() {
		try {
			booksConfig.save(configFile);
		}
		catch (IOException e) {
			// TODO: Handle exception
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
