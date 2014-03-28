/**
 * This document is a part of the source code and related artifacts for
 * BookRules, an open source Bukkit plugin for automatically distributing
 * written books to new players.
 *
 * http://dev.bukkit.org/server-mods/plugins/
 * http://github.com/mstiles92/BookRules
 *
 * Copyright (c) 2014 Matthew Stiles (mstiles92)
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

package com.mstiles92.plugins.bookrules.commands;

import com.mstiles92.plugins.bookrules.BookRules;
import com.mstiles92.plugins.bookrules.util.BookStorage;
import com.mstiles92.plugins.bookrules.localization.Localization;
import com.mstiles92.plugins.bookrules.localization.Strings;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * RuleBook is a class that handles the execution of the
 * commands registered to this plugin.
 *
 * @author mstiles92
 */
public class RuleBook implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0 || args[0].equalsIgnoreCase("info") || args[0].equalsIgnoreCase("version")) {
            if (!sender.hasPermission("bookrules.info")) {
                sender.sendMessage(Strings.PLUGIN_TAG + Localization.getString(Strings.NO_PERMISSIONS));
                return true;
            }

            sender.sendMessage(ChatColor.BLUE + String.format(Localization.getString(Strings.VERSION_MESSAGE), BookRules.getInstance().getDescription().getVersion()));
            return true;
        }

        if (args[0].equalsIgnoreCase("commands")) {
            sender.sendMessage(Strings.PLUGIN_TAG + Localization.getString(Strings.COMMANDS_HEADER));


            if (sender.hasPermission("bookrules.info")) {
                sender.sendMessage(String.format(Strings.CMD_USAGE_FORMAT, Strings.CMD_INFO_USAGE, Localization.getString(Strings.CMD_INFO_DESCRIPTION)));
            }

            if (sender.hasPermission("bookrules.reload")) {
                sender.sendMessage(String.format(Strings.CMD_USAGE_FORMAT, Strings.CMD_RELOAD_USAGE, Localization.getString(Strings.CMD_RELOAD_DESCRIPTION)));
            }

            if (sender.hasPermission("bookrules.get") && (sender instanceof Player)) {
                sender.sendMessage(String.format(Strings.CMD_USAGE_FORMAT, Strings.CMD_GET_USAGE, Localization.getString(Strings.CMD_GET_DESCRIPTION)));
            }

            if (sender.hasPermission("bookrules.give")) {
                sender.sendMessage(String.format(Strings.CMD_USAGE_FORMAT, Strings.CMD_GIVE_USAGE, Localization.getString(Strings.CMD_GIVE_DESCRIPTION)));
            }

            if (sender.hasPermission("bookrules.add") && (sender instanceof Player)) {
                sender.sendMessage(String.format(Strings.CMD_USAGE_FORMAT, Strings.CMD_ADD_USAGE, Localization.getString(Strings.CMD_ADD_DESCRIPTION)));
            }

            if (sender.hasPermission("bookrules.delete")) {
                sender.sendMessage(String.format(Strings.CMD_USAGE_FORMAT, Strings.CMD_DELETE_USAGE, Localization.getString(Strings.CMD_DELETE_DESCRIPTION)));
            }

            if (sender.hasPermission("bookrules.list")) {
                sender.sendMessage(String.format(Strings.CMD_USAGE_FORMAT, Strings.CMD_LIST_USAGE, Localization.getString(Strings.CMD_LIST_DESCRIPTION)));
            }

            if (sender.hasPermission("bookrules.setauthor") && (sender instanceof Player)) {
                sender.sendMessage(String.format(Strings.CMD_USAGE_FORMAT, Strings.CMD_SETAUTHOR_USAGE, Localization.getString(Strings.CMD_SETAUTHOR_DESCRIPTION)));
            }

            if (sender.hasPermission("bookrules.settitle") && (sender instanceof Player)) {
                sender.sendMessage(String.format(Strings.CMD_USAGE_FORMAT, Strings.CMD_SETTITLE_USAGE, Localization.getString(Strings.CMD_SETTITLE_DESCRIPTION)));
            }

            if (sender.hasPermission("bookrules.unsign") && (sender instanceof Player)) {
                sender.sendMessage(String.format(Strings.CMD_USAGE_FORMAT, Strings.CMD_UNSIGN_USAGE, Localization.getString(Strings.CMD_UNSIGN_DESCRIPTION)));
            }

            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("bookrules.reload")) {
                sender.sendMessage(Strings.PLUGIN_TAG + ChatColor.RED + Localization.getString(Strings.NO_PERMISSIONS));
                return true;
            }

            BookStorage.getInstance().loadFromFile();
            sender.sendMessage(Strings.PLUGIN_TAG + Localization.getString(Strings.CONFIG_RELOADED));
            return true;
        }

        if (args[0].equalsIgnoreCase("get")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;

                if (!player.hasPermission("bookrules.get")) {
                    player.sendMessage(Strings.PLUGIN_TAG + Localization.getString(Strings.NO_PERMISSIONS));
                    return true;
                }

                if (args.length < 2) {
                    int booksGiven = BookStorage.getInstance().givePlayerAllBooks(player);
                    if (booksGiven > 0) {
                        player.sendMessage(Strings.PLUGIN_TAG + Localization.getString(Strings.ALL_BOOKS_RECIEVED));
                    } else {
                        player.sendMessage(Strings.PLUGIN_TAG + ChatColor.RED + Localization.getString(Strings.NO_BOOKS_REGISTERED));
                    }
                    return true;
                }

                String query = (args.length > 2) ? StringUtils.join(args, " ", 1, args.length) : args[1];

                if (BookStorage.getInstance().givePlayerBook(player, query)) {
                    player.sendMessage(Strings.PLUGIN_TAG + Localization.getString(Strings.BOOK_RECIEVED));
                } else {
                    player.sendMessage(Strings.PLUGIN_TAG + ChatColor.RED + Localization.getString(Strings.BOOK_NOT_FOUND));
                }

                return true;
            } else {
                sender.sendMessage(Strings.PLUGIN_TAG + ChatColor.RED + Localization.getString(Strings.PLAYER_ONLY_CMD));
                return true;
            }
        }

        if (args[0].equalsIgnoreCase("give")) {
            if (!sender.hasPermission("bookrules.give")) {
                sender.sendMessage(Strings.PLUGIN_TAG + ChatColor.RED + Localization.getString(Strings.NO_PERMISSIONS));
                return true;
            }

            if (args.length < 2) {
                sender.sendMessage(Strings.PLUGIN_TAG + ChatColor.RED + Localization.getString(Strings.NO_PLAYER_SPECIFIED));
                return true;
            }

            Player player = Bukkit.getPlayer(args[1]);
            if (player == null) {
                sender.sendMessage(Strings.PLUGIN_TAG + ChatColor.RED + Localization.getString(Strings.PLAYER_NOT_FOUND));
                return true;
            }

            if (args.length < 3) {
                int booksGiven = BookStorage.getInstance().givePlayerAllBooks(player);
                if (booksGiven > 0) {
                    sender.sendMessage(String.format(Strings.PLUGIN_TAG + Localization.getString(Strings.ALL_BOOKS_GIVEN), player.getName()));
                    player.sendMessage(String.format(Strings.PLUGIN_TAG + Localization.getString(Strings.GIVEN_ALL_BOOKS_MESSAGE), sender.getName()));
                } else {
                    sender.sendMessage(Strings.PLUGIN_TAG + ChatColor.RED + Localization.getString(Strings.NO_BOOKS_REGISTERED));
                }

                return true;
            }

            String query = (args.length > 3) ? StringUtils.join(args, " ", 2, args.length) : args[2];

            if (BookStorage.getInstance().givePlayerBook(player, query)) {
                sender.sendMessage(String.format(Strings.PLUGIN_TAG + Localization.getString(Strings.BOOK_GIVEN), player.getName()));
                player.sendMessage(String.format(Strings.PLUGIN_TAG + Localization.getString(Strings.GIVEN_BOOK_MESSAGE), sender.getName()));
            } else {
                sender.sendMessage(Strings.PLUGIN_TAG + ChatColor.RED + Localization.getString(Strings.BOOK_NOT_FOUND));
            }

            return true;
        }

        if (args[0].equalsIgnoreCase("add")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;

                if (!player.hasPermission("bookrules.add")) {
                    player.sendMessage(Strings.PLUGIN_TAG + ChatColor.RED + Localization.getString(Strings.NO_PERMISSIONS));
                    return true;
                }

                if (player.getItemInHand().getType() != Material.WRITTEN_BOOK) {
                    player.sendMessage(Strings.PLUGIN_TAG + ChatColor.RED + Localization.getString(Strings.MUST_HOLD_BOOK));
                    return true;
                }

                BookStorage.getInstance().addBook(player.getItemInHand());
                player.sendMessage(Strings.PLUGIN_TAG + Localization.getString(Strings.BOOK_ADDED));
                return true;
            } else {
                sender.sendMessage(Strings.PLUGIN_TAG + ChatColor.RED + Localization.getString(Strings.PLAYER_ONLY_CMD));
                return true;
            }
        }

        if (args[0].equalsIgnoreCase("delete")) {
            if (!sender.hasPermission("bookrules.delete")) {
                sender.sendMessage(Strings.PLUGIN_TAG + ChatColor.RED + Localization.getString(Strings.NO_PERMISSIONS));
                return true;
            }

            if (args.length < 2) {
                sender.sendMessage(Strings.PLUGIN_TAG + ChatColor.RED + Localization.getString(Strings.NO_BOOK_SPECIFIED));
                return true;
            }

            if (BookStorage.getInstance().deleteBook(args[1])) {
                sender.sendMessage(Strings.PLUGIN_TAG + Localization.getString(Strings.BOOK_DELETED));
            } else {
                sender.sendMessage(Strings.PLUGIN_TAG + ChatColor.RED + Localization.getString(Strings.BOOK_NOT_FOUND));
            }

            return true;
        }

        if (args[0].equalsIgnoreCase("list")) {
            if (!sender.hasPermission("bookrules.list")) {
                sender.sendMessage(Strings.PLUGIN_TAG + ChatColor.RED + Localization.getString(Strings.NO_PERMISSIONS));
                return true;
            }

            List<String> books = BookStorage.getInstance().createBookList();

            if (books.isEmpty()) {
                sender.sendMessage(Strings.PLUGIN_TAG + ChatColor.RED + Localization.getString(Strings.NO_BOOKS_REGISTERED));
                return true;
            }

            sender.sendMessage(Strings.PLUGIN_TAG + Localization.getString(Strings.LIST_CMD_HEADER));
            for (String s : books) {
                sender.sendMessage(Strings.PLUGIN_TAG + s);
            }

            return true;
        }

        if (args[0].equalsIgnoreCase("setauthor")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;

                if (!player.hasPermission("bookrules.setauthor")) {
                    player.sendMessage(Strings.PLUGIN_TAG + ChatColor.RED + Localization.getString(Strings.NO_PERMISSIONS));
                    return true;
                }

                if (args.length < 2) {
                    player.sendMessage(Strings.PLUGIN_TAG + ChatColor.RED + Localization.getString(Strings.NO_AUTHOR_SPECIFIED));
                    return true;
                }

                ItemStack book = player.getItemInHand();

                if (book.getType() != Material.WRITTEN_BOOK) {
                    player.sendMessage(Strings.PLUGIN_TAG + ChatColor.RED + Localization.getString(Strings.MUST_HOLD_BOOK));
                    return true;
                }

                book = BookStorage.setAuthor(book, args[1]);

                player.setItemInHand(book);
                player.sendMessage(Strings.PLUGIN_TAG + Localization.getString(Strings.AUTHOR_CHANGED));
            } else {
                sender.sendMessage(Strings.PLUGIN_TAG + ChatColor.RED + Localization.getString(Strings.PLAYER_ONLY_CMD));
            }

            return true;
        }

        if (args[0].equalsIgnoreCase("settitle")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;

                if (!player.hasPermission("bookrules.settitle")) {
                    player.sendMessage(Strings.PLUGIN_TAG + ChatColor.RED + Localization.getString(Strings.NO_PERMISSIONS));
                    return true;
                }

                if (args.length < 2) {
                    player.sendMessage(Strings.PLUGIN_TAG + ChatColor.RED + Localization.getString(Strings.NO_TITLE_SPECIFIED));
                    return true;
                }

                ItemStack book = player.getItemInHand();

                if (book.getType() != Material.WRITTEN_BOOK) {
                    player.sendMessage(Strings.PLUGIN_TAG + ChatColor.RED + Localization.getString(Strings.MUST_HOLD_BOOK));
                    return true;
                }

                book = BookStorage.setTitle(book, args[1]);

                player.setItemInHand(book);
                player.sendMessage(Strings.PLUGIN_TAG + Localization.getString(Strings.TITLE_CHANGED));
            } else {
                sender.sendMessage(Strings.PLUGIN_TAG + ChatColor.RED + Localization.getString(Strings.PLAYER_ONLY_CMD));
            }

            return true;
        }

        if (args[0].equalsIgnoreCase("unsign")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;

                if (!player.hasPermission("bookrules.unsign")) {
                    player.sendMessage(Strings.PLUGIN_TAG + ChatColor.RED + Localization.getString(Strings.NO_PERMISSIONS));
                    return true;
                }

                ItemStack book = player.getItemInHand();

                if (book.getType() != Material.WRITTEN_BOOK) {
                    player.sendMessage(Strings.PLUGIN_TAG + ChatColor.RED + Localization.getString(Strings.MUST_HOLD_BOOK));
                    return true;
                }

                book = BookStorage.unsignBook(book);

                player.setItemInHand(book);
                player.sendMessage(Strings.PLUGIN_TAG + Localization.getString(Strings.BOOK_UNSIGNED));
            } else {
                sender.sendMessage(Strings.PLUGIN_TAG + ChatColor.RED + Localization.getString(Strings.PLAYER_ONLY_CMD));
            }

            return true;
        }

        return false;
    }
}
