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
import java.io.FileNotFoundException;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
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
	
	public void clear() {
		configFile = new File(plugin.getDataFolder(), filename);
		
		try {
			configFile.delete();
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
