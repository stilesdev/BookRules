package com.mstiles92.bookrules;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

public class BookRulesCommandExecutor implements CommandExecutor {
	private final BookRulesPlugin plugin;
	
	public BookRulesCommandExecutor(BookRulesPlugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
		if (!(cs instanceof Player)) {
			cs.sendMessage(plugin.tag + ChatColor.RED + "This command may only be executed by a player.");
			return true;
		}
		Player p = (Player)cs;
		
		if (args.length == 0) {
			if (!p.hasPermission("bookrules.info")) {
				p.sendMessage(plugin.tag + ChatColor.RED + "You do not have permission to use this command.");
				return true;
			}
			
			p.sendMessage(ChatColor.BLUE + "BookRules by " + plugin.getDescription().getAuthors().get(0));
			p.sendMessage(ChatColor.BLUE + "Version " + plugin.getDescription().getVersion());
			return true;
		}
		
		if (args[0].equalsIgnoreCase("reload")) {
			if (!p.hasPermission("bookrules.reload")) {
				p.sendMessage(plugin.tag + ChatColor.RED + "You do not have permission to use this command.");
				return true;
			}
			
			plugin.loadConfig();
			p.sendMessage(plugin.tag + "Files reloaded!");
			return true;
		}
		
		if (args[0].equalsIgnoreCase("get")) {
			if (!p.hasPermission("bookrules.get")) {
				p.sendMessage(plugin.tag + ChatColor.RED + "You do not have permission to use this command.");
				return true;
			}
			
			if (args.length < 2) {
				if (plugin.giveAllBooks(p)) {
					p.sendMessage(plugin.tag + "You have received a copy of all registered books.");
				} else {
					p.sendMessage(plugin.tag + ChatColor.RED + "No books defined.");
				}
				return true;
			}
			
			if (plugin.giveBook(p, args[1])) {
				p.sendMessage(plugin.tag + "You have received a copy of the requested book.");
			} else {
				p.sendMessage(plugin.tag + ChatColor.RED + "The specified book could not be found!");
			}
			return true;
		}
		
		if (args[0].equalsIgnoreCase("add")) {
			if (!p.hasPermission("bookrules.add")) {
				p.sendMessage(plugin.tag + ChatColor.RED + "You do not have permission to use this command.");
				return true;
			}
			
			if (p.getItemInHand().getType() != Material.WRITTEN_BOOK) {
				p.sendMessage(plugin.tag + ChatColor.RED + "This command may only be used while holding a written book.");
				return true;
			}
			
			plugin.addBook(p.getItemInHand());
			p.sendMessage(plugin.tag + "Your book has been added successfully.");
			return true;
		}
		
		if (args[0].equalsIgnoreCase("delete")) {
			if (!p.hasPermission("bookrules.delete")) {
				p.sendMessage(plugin.tag + ChatColor.RED + "You do not have permission to use this command.");
				return true;
			}
			
			if (args.length < 2) {
				p.sendMessage(plugin.tag + ChatColor.RED + "You must specify an ID of the book you would like to delete.");
				return true;
			}
			
			if (plugin.deleteBook(args[1])) {
				p.sendMessage(plugin.tag + "Book successfully deleted.");
			} else {
				p.sendMessage(plugin.tag + ChatColor.RED + "The specified book could not be found!");
			}
			return true;
		}
		
		if (args[0].equalsIgnoreCase("list")) {
			if (!p.hasPermission("bookrules.list")) {
				p.sendMessage(plugin.tag + ChatColor.RED + "You do not have permission to use this command.");
				return true;
			}
			
			List<String> list = plugin.readAllBooks();
			p.sendMessage(plugin.tag + "All registered books:");
			for (String s : list) {
				p.sendMessage(plugin.tag + s);
			}
			
			return true;
		}
		
		if (args[0].equalsIgnoreCase("setauthor")) {
			if (!p.hasPermission("bookrules.setauthor")) {
				p.sendMessage(plugin.tag + ChatColor.RED + "You do not have permission to use this command.");
				return true;
			}
			
			if (args.length < 2) {
				p.sendMessage(plugin.tag + ChatColor.RED + "You must specify an author to change to.");
				return true;
			}
			
			ItemStack book = p.getItemInHand();
			BookMeta meta = (BookMeta) book.getItemMeta();
			
			if (book.getType() != Material.WRITTEN_BOOK) {
				p.sendMessage(plugin.tag + ChatColor.RED + "This command may only be performed while holding a written book.");
				return true;
			}
			
			meta.setAuthor(args[1]);
			book.setItemMeta(meta);
			p.setItemInHand(book);
			return true;
		}
		
		if (args[0].equalsIgnoreCase("settitle")) {
			if (!p.hasPermission("bookrules.settitle")) {
				p.sendMessage(plugin.tag + ChatColor.RED + "You do not have permission to use this command.");
				return true;
			}
			
			if (args.length < 2) {
				p.sendMessage(plugin.tag + ChatColor.RED + "You must specify a title to change to.");
				return true;
			}
			
			if (p.getInventory().getItemInHand().getType() != Material.WRITTEN_BOOK) {
				p.sendMessage(plugin.tag + ChatColor.RED + "This command may only be performed while holding a written book.");
				return true;
			}
			
			ItemStack book = p.getItemInHand();
			BookMeta meta = (BookMeta) book.getItemMeta();
			
			meta.setTitle(args[1]);
			book.setItemMeta(meta);
			p.setItemInHand(book);
			return true;
		}
		return false;
	}
}
