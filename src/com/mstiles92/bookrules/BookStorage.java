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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

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
	private ArrayList<Integer> index;
	private LinkedHashMap<String, Integer> titleIndex;
	
	private final static int MAX_TITLE_LENGTH = 16;
	private final static int MAX_AUTHOR_LENGTH = 16;
	
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
		
		this.updateIndex();
	}
	
	/**
	 * Update the index of internal ids.
	 */
	private void updateIndex() {
		index = new ArrayList<Integer>();
		titleIndex = new LinkedHashMap<String, Integer>();
		for (String id : books.getConfigurationSection("Books").getKeys(false)) {
			index.add(Integer.parseInt(id));
			titleIndex.put(books.getString("Books." + id + ".Title"), Integer.parseInt(id));
		}
	}
	
	/**
	 * Convert the old format of books.yml to the currently used format.
	 * 
	 * @return true if the conversion is successful, false if it failed
	 */
	private boolean convertFromOldFormat() {
		YamlConfiguration oldConfig = new YamlConfiguration();
		ArrayList<String> pages;
		ConfigurationSection section;
		int max = 0;
		int current;
		
		try {
			oldConfig.loadFromString(books.saveToString());
		} catch (InvalidConfigurationException e) {}
		
		books.createSection("Books", oldConfig.getValues(true));
		books.createSection("Meta");
		
		for (String s : oldConfig.getKeys(false)) {
			pages = new ArrayList<String>();
			section = oldConfig.getConfigurationSection(s + ".Pages");
			for (String pageName : section.getKeys(false)) {
				pages.add(section.getString(pageName));
			}
			
			books.set("Books." + s + ".Pages", pages);
			
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
	
	/**
	 * Save the books config to file, logging any exceptions that occur.
	 */
	private void saveBooks() {
		try {
			books.save(booksFile);
		} catch (IOException e) {
			plugin.logWarning("Error saving books.yml");
		}
	}
	
	/**
	 * Save the players config to file, logging any exceptions that occur.
	 */
	private void savePlayers() {
		try {
			players.save(playersFile);
		} catch (IOException e) {
			plugin.logWarning("Error saving players.yml");
		}
	}
	
	/**
	 * Get the current internal id for the book storage, then increment the
	 * stored id.
	 * 
	 * @return the current id, or -1 if there is an error
	 */
	private int getNewID() {
		int id = books.getInt("Meta.CurrentID", -1);
		
		if (id > 0) {
			books.set("Meta.CurrentID", id + 1);
			this.saveBooks();
		}
		
		return id;
	}
	
	/**
	 * Search the books stored by this plugin by name or list id.
	 * 
	 * @param query the name or list id of the book to search for
	 * @return the internal id, or -1 if the book cannot be found
	 */
	private int getInternalID(String query) {
		if (titleIndex.containsKey(query)) {
			return titleIndex.get(query);
		} else {
			try {
				int result = Integer.parseInt(query);
				if (result < index.size() + 1) {
					return index.get(result - 1);
				}
			} catch (NumberFormatException e) {}
			return -1;
		}
	}
	
	/**
	 * Get the book specified by internal id.
	 * 
	 * @param id the internal id of the book to get
	 * @return the ItemStack of the book requested
	 */
	private ItemStack getBook(int id) {
		ItemStack book = new ItemStack(Material.WRITTEN_BOOK, 1);
		BookMeta meta = (BookMeta) book.getItemMeta();
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("BookRules id: " + id);	//TODO: change to just "BookRules" instead of showing id? (avoid confusion when using /rb get command)

		meta.setAuthor(books.getString("Books." + id + ".Author"));
		meta.setTitle(books.getString("Books." + id + ".Title"));
		meta.setPages(books.getStringList("Books." + id + ".Pages"));
		meta.setLore(lore);
		
		return (book.setItemMeta(meta)) ? book : null;
	}
	
	/**
	 * Check if a player has received the specified book before.
	 * 
	 * @param player the player to check for
	 * @param id the internal id of the book to check for
	 * @return true if the player has been given that book before, false otherwise
	 */
	private boolean checkPlayerGivenBook(String player, int id) {
		List<String> playerList = players.getStringList(String.valueOf(id));
		
		return playerList.contains(player);
	}
	
	/**
	 * Mark the specified book as given to a player.
	 * 
	 * @param player the player who was given the book
	 * @param id the internal id of the book that was given
	 */
	private void setPlayerGivenBook(String player, int id) {
		List<String> playerList = players.getStringList(String.valueOf(id));
		
		if (!playerList.contains(player)) {
			playerList.add(player);
			players.set(String.valueOf(id), playerList);
			this.savePlayers();
		}
	}
	
	/**
	 * Register a written book to be stored by the plugin.
	 * 
	 * @param book the ItemStack of the book to be added
	 */
	public void addBook(ItemStack book) {
		BookMeta meta = (BookMeta) book.getItemMeta();
		int id = getNewID();
		
		books.set("Books." + id + ".Pages", meta.getPages());
		books.set("Books." + id + ".Author", meta.getAuthor());
		books.set("Books." + id + ".Title", meta.getTitle());
		this.saveBooks();
		this.updateIndex();
	}
	
	/**
	 * Delete a book stored by this plugin.
	 * 
	 * @param query the title or list id of the book to delete
	 * @return true if the book was successfully deleted, false if it could not be found
	 */
	public boolean deleteBook(String query) {
		int id = getInternalID(query);
		if (id < 0) {
			return false;
		}
		
		books.set("Books." + id, null);
		this.saveBooks();
		players.set(String.valueOf(id), null);
		this.savePlayers();
		this.updateIndex();
		return true;
	}
	
	/**
	 * Create a detailed list of the currently registered books suitable for
	 * display to the user.
	 * 
	 * @return the list of currently registered books
	 */
	public List<String> createBookList() {
		ArrayList<String> list = new ArrayList<String>();
		
		for (int x = 0; x < index.size(); x++) {
			list.add((x + 1) + " - " + books.getString("Books." + index.get(x) + ".Title") + " by " + books.getString("Books." + index.get(x) + ".Author"));
		}
		
		return list;
	}
	
	/**
	 * Give the specified player the specified book.
	 * 
	 * @param player the player to give the book to
	 * @param id the internal id of the book to give
	 */
	private void givePlayerBook(Player player, int id) {
		ItemStack book = getBook(id);
		HashMap<Integer, ItemStack> leftovers = player.getInventory().addItem(book);
		for (ItemStack leftover : leftovers.values()) {
			player.getWorld().dropItem(player.getLocation(), leftover);
		}
		
		this.setPlayerGivenBook(player.getName(), id);
	}
	
	/**
	 * Give the specified player the specified book.
	 * 
	 * @param player the player to give the book to
	 * @param query the name or list id of the book to give
	 * @return true if the book was successfully given, false if it could not be found
	 */
	public boolean givePlayerBook(Player player, String query) {
		int id = getInternalID(query);
		if (id < 0) {
			return false;
		}
		
		this.givePlayerBook(player, id);
		
		return true;
	}
	
	/**
	 * Give the specified player all registered books.
	 * 
	 * @param player the player to give the books to
	 * @param force if books that have already been given should be reissued
	 * @return the number of books given to the player
	 */
	private int givePlayerAllBooks(Player player, boolean force) {
		int num = 0;
		
		for (int id : index) {
			if (force || !checkPlayerGivenBook(player.getName(), id)) {
				this.givePlayerBook(player, id);
				num++;
			}
		}
		
		return num;
	}
	
	/**
	 * Give the specified player any books they have not yet received.
	 * 
	 * @param player the player to give the books to
	 * @return the number of books given to the player
	 */
	public int givePlayerUngivenBooks(Player player) {
		return givePlayerAllBooks(player, false);
	}
	
	/**
	 * Give the specified player all registered books.
	 * 
	 * @param player the player to give the books to
	 * @return the number of books given to the player
	 */
	public int givePlayerAllBooks(Player player) {
		return givePlayerAllBooks(player, true);
	}
	
	/**
	 * Change the title of the given book.
	 * 
	 * @param book an ItemStack of the book to be changed
	 * @param title the title to be applied to the book, truncated if necessary
	 * @return the modified book
	 */
	public static ItemStack setTitle(ItemStack book, String title) {
		BookMeta meta = (BookMeta) book.getItemMeta();
		meta.setTitle((title.length() < MAX_TITLE_LENGTH) ? title : title.substring(0, MAX_TITLE_LENGTH));
		book.setItemMeta(meta);
		return book;
	}
	
	/**
	 * Change the author of the given book.
	 * 
	 * @param book an ItemStack of the book to be changed
	 * @param author the author to be applied to the book, truncated if necessary
	 * @return the modified book
	 */
	public static ItemStack setAuthor(ItemStack book, String author) {
		BookMeta meta = (BookMeta) book.getItemMeta();
		meta.setAuthor((author.length() < MAX_AUTHOR_LENGTH) ? author : author.substring(0, MAX_AUTHOR_LENGTH));
		book.setItemMeta(meta);
		return book;
	}
	
	/**
	 * Unsign a written book, changing it back into a book and quill with all
	 * of the pages unaltered.
	 * 
	 * @param book an ItemStack of the Written Book to unsign
	 * @return an ItemStack of the unsigned Book and Quill
	 */
	public static ItemStack unsignBook(ItemStack book) {
		BookMeta oldMeta = (BookMeta) book.getItemMeta();
		ItemStack unsignedBook = new ItemStack(Material.BOOK_AND_QUILL, 1);
		BookMeta newMeta = (BookMeta) unsignedBook.getItemMeta();
		newMeta.setPages(oldMeta.getPages());
		unsignedBook.setItemMeta(newMeta);
		return unsignedBook;
	}
}
