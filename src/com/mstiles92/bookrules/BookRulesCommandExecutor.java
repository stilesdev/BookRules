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

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * BookRulesCommandExecutor is a class that handles the execution of the
 * commands registered to this plugin.
 * 
 * @author mstiles92
 */
public class BookRulesCommandExecutor implements CommandExecutor {
	private final BookRulesPlugin plugin;
	private final String tag = ChatColor.BLUE + "[BookRules] " + ChatColor.GREEN;
	private final String playerMessage = ChatColor.RED + tag + "This command may only be executed by a player.";
	private final String permMessage = ChatColor.RED + tag + "You do not have permission to use this command.";
	
	/**
	 * The main constructor of this class
	 * 
	 * @param plugin the instance of the plugin
	 */
	public BookRulesCommandExecutor(BookRulesPlugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
		if (args.length == 0 || args[0].equalsIgnoreCase("info") || args[0].equalsIgnoreCase("version")) {
			if (!cs.hasPermission("bookrules.info")) {
				cs.sendMessage(permMessage);
				return true;
			}
			
			cs.sendMessage(ChatColor.BLUE + "BookRules by " + plugin.getDescription().getAuthors().get(0));
			cs.sendMessage(ChatColor.BLUE + "Version " + plugin.getDescription().getVersion());
			return true;
		}
		
		if (args[0].equalsIgnoreCase("commands")) {
			cs.sendMessage(tag + "All BookRules commands you are able to use:");
			
			if (cs.hasPermission("bookrules.info")) {
				cs.sendMessage(ChatColor.GREEN + "/rulebook [info | version] " + ChatColor.GRAY+ "Show current plugin information.");
			}
			
			if (cs.hasPermission("bookrules.reload")) {
				cs.sendMessage(ChatColor.GREEN + "/rulebook reload " + ChatColor.GRAY + "Reload data from the config files.");
			}
			
			if (cs.hasPermission("bookrules.get") && (cs instanceof Player)) {
				cs.sendMessage(ChatColor.GREEN + "/rulebook get [id | title] " + ChatColor.GRAY + "Get book specified by id or title, or all books if no id or title is specified.");
			}
			
			if (cs.hasPermission("bookrules.give")) {
				cs.sendMessage(ChatColor.GREEN + "/rulebook give <player> [id | title] " + ChatColor.GRAY + "Give a player the book specified by id or title, or all books if no id or title is specified.");
			}
			
			if (cs.hasPermission("bookrules.add") && (cs instanceof Player)) {
				cs.sendMessage(ChatColor.GREEN + "/rulebook add " + ChatColor.GRAY + "Add the currently held book to the plugin.");
			}
			
			if (cs.hasPermission("bookrules.delete")) {
				cs.sendMessage(ChatColor.GREEN + "/rulebook delete <id | title> " + ChatColor.GRAY + "Delete the book specified by id or title from the plugin.");
			}
			
			if (cs.hasPermission("bookrules.list")) {
				cs.sendMessage(ChatColor.GREEN + "/rulebook list " + ChatColor.GRAY + "Show all of the books currently stored by the plugin.");
			}
			
			if (cs.hasPermission("bookrules.setauthor") && (cs instanceof Player)) {
				cs.sendMessage(ChatColor.GREEN + "/rulebook setauthor <author> " + ChatColor.GRAY + "Change the author of the currently held book.");
			}
			
			if (cs.hasPermission("bookrules.settitle") && (cs instanceof Player)) {
				cs.sendMessage(ChatColor.GREEN + "/rulebook settitle <title> " + ChatColor.GRAY + "Change the title of the currently held book.");
			}
			
			if (cs.hasPermission("bookrules.unsign") && (cs instanceof Player)) {
				cs.sendMessage(ChatColor.GREEN + "/rulebook unsign " + ChatColor.GRAY + "Unsign the currenly held book, changing it back to a book and quill.");
			}
			
			return true;
		}
		
		if (args[0].equalsIgnoreCase("reload")) {
			if (!cs.hasPermission("bookrules.reload")) {
				cs.sendMessage(permMessage);
				return true;
			}
			
			BookStorage.getInstance(plugin).loadFromFile();
			cs.sendMessage(tag + "Config reloaded from file!");
			return true;
		}
		
		if (args[0].equalsIgnoreCase("get")) {
			if (cs instanceof Player) {
				Player player = (Player) cs;
				
				if (!player.hasPermission("bookrules.get")) {
					player.sendMessage(permMessage);
					return true;
				}
				
				if (args.length < 2) {
					int booksGiven = BookStorage.getInstance(plugin).givePlayerAllBooks(player);
					if (booksGiven > 0) {
						player.sendMessage(tag + "You have received a copy of all registered books.");
					} else {
						player.sendMessage(tag + ChatColor.RED + "No books registered.");
					}
					return true;
				}
				
				String query = (args.length > 2) ? StringUtils.join(args, " ", 1, args.length) : args[1];
				
				if (BookStorage.getInstance(plugin).givePlayerBook(player, query)) {
					player.sendMessage(tag + "You have received a copy of the requested book.");
				} else {
					player.sendMessage(tag + ChatColor.RED + "The requested book could not be found.");
				}
				
				return true;
			} else {
				cs.sendMessage(playerMessage);
				return true;
			}
		}
		
		if (args[0].equalsIgnoreCase("give")) {
			if (!cs.hasPermission("bookrules.give")) {
				cs.sendMessage(permMessage);
				return true;
			}
			
			if (args.length < 2) {
				cs.sendMessage(tag + ChatColor.RED + "You must specify a player to give books to.");
				return true;
			}
			
			Player player = plugin.getServer().getPlayer(args[1]);
			if (player == null) {
				cs.sendMessage(tag + ChatColor.RED + "The requested player could not be found.");
				return true;
			}
			
			if (args.length < 3) {
				int booksGiven = BookStorage.getInstance(plugin).givePlayerAllBooks(player);
				if (booksGiven > 0) {
					cs.sendMessage(tag + "You have given " + player.getName() + " all registered books.");
					player.sendMessage(tag + cs.getName() + " has given you all registered books.");
				} else {
					cs.sendMessage(tag + ChatColor.RED + "No books registered.");
				}
				
				return true;
			}
			
			String query = (args.length > 3) ? StringUtils.join(args, " ", 2, args.length) : args[2];
			
			if (BookStorage.getInstance(plugin).givePlayerBook(player, query)) {
				cs.sendMessage(tag + "You have given " + player.getName() + " a copy of the requested book.");
				player.sendMessage(tag + cs.getName() + " has given you a book.");
			} else {
				cs.sendMessage(tag + ChatColor.RED + "The requested book could not be found.");
			}
			
			return true;
		}
		
		if (args[0].equalsIgnoreCase("add")) {
			if (cs instanceof Player) {
				Player player = (Player) cs;
				
				if (!player.hasPermission("bookrules.add")) {
					player.sendMessage(permMessage);
					return true;
				}
				
				if (player.getItemInHand().getType() != Material.WRITTEN_BOOK) {
					player.sendMessage(tag + ChatColor.RED + "You may only perform this command while holding a written book.");
					return true;
				}
				
				BookStorage.getInstance(plugin).addBook(player.getItemInHand());
				player.sendMessage(tag + "Your book has been added successfully!");
				return true;
			} else {
				cs.sendMessage(playerMessage);
				return true;
			}
		}
		
		if (args[0].equalsIgnoreCase("delete")) {
			if (!cs.hasPermission("bookrules.delete")) {
				cs.sendMessage(permMessage);
				return true;
			}
			
			if (args.length < 2) {
				cs.sendMessage(tag + ChatColor.RED + "You must specify a book to delete.");
				return true;
			}
			
			if (BookStorage.getInstance(plugin).deleteBook(args[1])) {
				cs.sendMessage(tag + "The requested book has been deleted.");
			} else {
				cs.sendMessage(tag + ChatColor.RED + "The requested book could not be found.");
			}
			
			return true;
		}
		
		if (args[0].equalsIgnoreCase("list")) {
			if (!cs.hasPermission("bookrules.list")) {
				cs.sendMessage(permMessage);
				return true;
			}
			
			List<String> books = BookStorage.getInstance(plugin).createBookList();
			
			if (books.isEmpty()) {
				cs.sendMessage(tag + ChatColor.RED + "No books registered.");
				return true;
			}

			cs.sendMessage(tag + "All BookRules books:");
			for (String s : books) {
				cs.sendMessage(tag + s);
			}
			
			return true;
		}
		
		if (args[0].equalsIgnoreCase("setauthor")) {
			if (cs instanceof Player) {
				Player player = (Player) cs;
				
				if (!player.hasPermission("bookrules.setauthor")) {
					player.sendMessage(permMessage);
					return true;
				}
				
				if (args.length < 2) {
					player.sendMessage(tag + ChatColor.RED + "You must specify an author when setting the author of a book.");
					return true;
				}
				
				ItemStack book = player.getItemInHand();
				
				if (book.getType() != Material.WRITTEN_BOOK) {
					player.sendMessage(tag + ChatColor.RED + "You may only perform this command while holding a written book.");
					return true;
				}
				
				book = BookStorage.setAuthor(book, args[1]);
				
				player.setItemInHand(book);
				player.sendMessage(tag + "The author has been successfully changed.");
			} else {
				cs.sendMessage(playerMessage);
			}
			
			return true;
		}
		
		if (args[0].equalsIgnoreCase("settitle")) {
			if (cs instanceof Player) {
				Player player = (Player) cs;
				
				if (!player.hasPermission("bookrules.settitle")) {
					player.sendMessage(permMessage);
					return true;
				}
				
				if (args.length < 2) {
					player.sendMessage(tag + ChatColor.RED + "You must specify a title when setting the title of a book.");
					return true;
				}
				
				ItemStack book = player.getItemInHand();
				
				if (book.getType() != Material.WRITTEN_BOOK) {
					player.sendMessage(tag + ChatColor.RED + "You may only perform this command while holding a written book.");
					return true;
				}
				
				book = BookStorage.setTitle(book, args[1]);
				
				player.setItemInHand(book);
				player.sendMessage(tag + "The title has been successfully changed.");
			} else {
				cs.sendMessage(playerMessage);
			}
			
			return true;
		}
		
		if (args[0].equalsIgnoreCase("unsign")) {
			if (cs instanceof Player) {
				Player player = (Player) cs;
				
				if (!player.hasPermission("bookrules.unsign")) {
					player.sendMessage(permMessage);
					return true;
				}
				
				ItemStack book = player.getItemInHand();
				
				if (book.getType() != Material.WRITTEN_BOOK) {
					player.sendMessage(tag + ChatColor.RED + "You may only perform this command while holding a written book.");
					return true;
				}
				
				book = BookStorage.unsignBook(book);
				
				player.setItemInHand(book);
				player.sendMessage(tag + "The book has been successfully unsigned.");
			} else {
				cs.sendMessage(playerMessage);
			}
			
			return true;
		}
		
		return false;
	}
}
