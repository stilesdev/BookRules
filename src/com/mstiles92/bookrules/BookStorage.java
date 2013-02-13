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

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 * BookStorage is a class used to store the content of books, as well as the
 * players who have recieved a copy of each book.
 * 
 * @author mstiles92
 */
public class BookStorage {
	
	private final BookRulesPlugin plugin;
	private File booksFile;
	private File playersFile;
	private YamlConfiguration books;
	private YamlConfiguration players;
	
	/**
	 * The main constructor of this class
	 */
	public BookStorage(BookRulesPlugin plugin) {
		this.plugin = plugin;
		loadFromFile();
	}
	
	/**
	 * Load or reload the data from the config files on disk. Useful for when
	 * the config files have been manually edited and those changes must be
	 * pulled in to the plugin.
	 */
	public void loadFromFile() {
		books = new YamlConfiguration();
		players = new YamlConfiguration();
		
		booksFile = new File(plugin.getDataFolder(), "books.yml");
		playersFile = new File(plugin.getDataFolder(), "players.yml");
		
		try {
			if (!booksFile.exists()) {
				booksFile.createNewFile();
			}
			books.load(booksFile);
		} catch(IOException e) {
			plugin.logWarning("Error opening file: plugins/BookRules/books.yml - BookRules will now be disabled!");
			plugin.getPluginLoader().disablePlugin(plugin);
		} catch (InvalidConfigurationException e) {
			plugin.logWarning("Invalid configuration file: plugins/BookRules/books.yml - BookRules will now be disabled!");
			plugin.getPluginLoader().disablePlugin(plugin);
		}
		
		try {
			if (!playersFile.exists()) {
				playersFile.createNewFile();
			}
			players.load(playersFile);
		} catch (IOException e) {
			plugin.logWarning("Error opening file: plugins/BookRules/players.yml - BookRules will now be disabled!");
			plugin.getPluginLoader().disablePlugin(plugin);
		} catch (InvalidConfigurationException e) {
			plugin.logWarning("Invalid configuration file: plugins/BookRules/players.yml - BookRules will now be disabled!");
			plugin.getPluginLoader().disablePlugin(plugin);
		}
		
		if (!books.contains("Meta")) {
			if (convertFromOldFormat()) {
				plugin.log("Old books.yml format converted successfully!");
			} else {
				plugin.logWarning("Error converting old books.yml format. BookRules will now be disabled.");
				plugin.getPluginLoader().disablePlugin(plugin);
			}
		}
	}
	
	/**
	 * Convert the old format of books.yml to the currently used format.
	 * 
	 * @return true if the conversion is successful, false if it failed
	 */
	private boolean convertFromOldFormat() {
		YamlConfiguration oldConfig = new YamlConfiguration();
		int max = 0;
		int current;
		
		try {
			oldConfig.loadFromString(books.saveToString());
		} catch (InvalidConfigurationException e) {}
		
		books.createSection("Books", oldConfig.getValues(true));
		books.createSection("Meta");
		
		for (String s : oldConfig.getKeys(false)) {
			try {
				current = Integer.parseInt(s);
			} catch (NumberFormatException e) {
				continue;
			}
			
			if (current > max) {
				max = current;
			}
			
			books.set(s, null);
		}
		
		books.set("Meta.CurrentID", max + 1);
		
		try {
			books.save(booksFile);
		} catch (IOException e) {
			return false;
		}
		
		return true;
	}
}
