package com.mstiles92.bookrules;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
			
			// TODO: Show the plugin information.
			return true;
		}
		
		if (args[0].equalsIgnoreCase("reload")) {
			if (!p.hasPermission("bookrules.reload")) {
				p.sendMessage(plugin.tag + ChatColor.RED + "You do not have permission to use this command.");
				return true;
			}
			
			// TODO: Reload the config files and books.
			return true;
		}
		
		if (args[0].equalsIgnoreCase("get")) {
			if (!p.hasPermission("bookrules.get")) {
				p.sendMessage(plugin.tag + ChatColor.RED + "You do not have permission to use this command.");
				return true;
			}
			
			// TODO: Give the player the specified book(s).
			return true;
		}
		
		if (args[0].equalsIgnoreCase("add")) {
			if (!p.hasPermission("bookrules.add")) {
				p.sendMessage(plugin.tag + ChatColor.RED + "You do not have permission to use this command.");
				return true;
			}
			
			// TODO: Add the book to the plugin's list.
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
			
			// TODO: Delete the book from the plugin's list.
			return true;
		}
		
		if (args[0].equalsIgnoreCase("list")) {
			if (!p.hasPermission("bookrules.list")) {
				p.sendMessage(plugin.tag + ChatColor.RED + "You do not have permission to use this command.");
				return true;
			}
			
			// TODO: Show the player a list of all the stored books.
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
			
			// TODO: Set the author of the currently held book.
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
			
			// TODO: Set the title of the currently held book.
			return true;
		}
		
		return false;
	}

}
