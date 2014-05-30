/*
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
import com.mstiles92.plugins.bookrules.localization.Localization;
import com.mstiles92.plugins.bookrules.localization.Strings;
import com.mstiles92.plugins.bookrules.util.BookStorage;
import com.mstiles92.plugins.commonutils.commands.Arguments;
import com.mstiles92.plugins.commonutils.commands.CommandHandler;
import com.mstiles92.plugins.commonutils.commands.annotations.Command;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class BookRulesCommands implements CommandHandler {

    @Command(name = "bookrules", aliases = {"rulebook", "rb", "br"}, permission = "bookrules.info",
            description = "Base command for all other BookRules commands. Type \"/bookrules commands\" for more information.",
            usage = "/<command> <subcommand> [args ...]")
    public void bookrules(Arguments args) {
        args.getSender().sendMessage(ChatColor.BLUE + String.format(Localization.getString(Strings.VERSION_MESSAGE), BookRules.getInstance().getDescription().getVersion()));
    }

    @Command(name = "bookrules.info", aliases = {"rulebook.info", "rb.info", "br.info"}, permission = "bookrules.info")
    public void info(Arguments args) {
        bookrules(args);
    }

    @Command(name = "bookrules.version", aliases = {"rulebook.version", "rb.version", "br.version"}, permission = "bookrules.info")
    public void version(Arguments args) {
        bookrules(args);
    }

    @Command(name = "bookrules.commands", aliases = {"rulebook.commands", "rb.commands", "br.commands"})
    public void commands(Arguments args) {
        args.getSender().sendMessage(Strings.PLUGIN_TAG + Localization.getString(Strings.COMMANDS_HEADER));

        if (args.getSender().hasPermission("bookrules.info")) {
            args.getSender().sendMessage(String.format(Strings.CMD_USAGE_FORMAT, Strings.CMD_INFO_USAGE, Localization.getString(Strings.CMD_INFO_DESCRIPTION)));
        }

        if (args.getSender().hasPermission("bookrules.reload")) {
            args.getSender().sendMessage(String.format(Strings.CMD_USAGE_FORMAT, Strings.CMD_RELOAD_USAGE, Localization.getString(Strings.CMD_RELOAD_DESCRIPTION)));
        }

        if (args.getSender().hasPermission("bookrules.get") && args.isPlayer()) {
            args.getSender().sendMessage(String.format(Strings.CMD_USAGE_FORMAT, Strings.CMD_GET_USAGE, Localization.getString(Strings.CMD_GET_DESCRIPTION)));
        }

        if (args.getSender().hasPermission("bookrules.give")) {
            args.getSender().sendMessage(String.format(Strings.CMD_USAGE_FORMAT, Strings.CMD_GIVE_USAGE, Localization.getString(Strings.CMD_GIVE_DESCRIPTION)));
        }

        if (args.getSender().hasPermission("bookrules.add") && args.isPlayer()) {
            args.getSender().sendMessage(String.format(Strings.CMD_USAGE_FORMAT, Strings.CMD_ADD_USAGE, Localization.getString(Strings.CMD_ADD_DESCRIPTION)));
        }

        if (args.getSender().hasPermission("bookrules.delete")) {
            args.getSender().sendMessage(String.format(Strings.CMD_USAGE_FORMAT, Strings.CMD_DELETE_USAGE, Localization.getString(Strings.CMD_DELETE_DESCRIPTION)));
        }

        if (args.getSender().hasPermission("bookrules.list")) {
            args.getSender().sendMessage(String.format(Strings.CMD_USAGE_FORMAT, Strings.CMD_LIST_USAGE, Localization.getString(Strings.CMD_LIST_DESCRIPTION)));
        }

        if (args.getSender().hasPermission("bookrules.setauthor") && args.isPlayer()) {
            args.getSender().sendMessage(String.format(Strings.CMD_USAGE_FORMAT, Strings.CMD_SETAUTHOR_USAGE, Localization.getString(Strings.CMD_SETAUTHOR_DESCRIPTION)));
        }

        if (args.getSender().hasPermission("bookrules.settitle") && args.isPlayer()) {
            args.getSender().sendMessage(String.format(Strings.CMD_USAGE_FORMAT, Strings.CMD_SETTITLE_USAGE, Localization.getString(Strings.CMD_SETTITLE_DESCRIPTION)));
        }

        if (args.getSender().hasPermission("bookrules.unsign") && args.isPlayer()) {
            args.getSender().sendMessage(String.format(Strings.CMD_USAGE_FORMAT, Strings.CMD_UNSIGN_USAGE, Localization.getString(Strings.CMD_UNSIGN_DESCRIPTION)));
        }
    }

    @Command(name = "bookrules.reload", aliases = {"rulebook.reload", "rb.reload", "br.reload"}, permission = "bookrules.reload")
    public void reload(Arguments args) {
        BookStorage.getInstance().loadFromFile();
        args.getSender().sendMessage(Strings.PLUGIN_TAG + Localization.getString(Strings.CONFIG_RELOADED));
    }

    @Command(name = "bookrules.get", aliases = {"rulebook.get", "rb.get", "br.get"}, permission = "bookrules.get")
    public void get(Arguments args) {
        if (!args.isPlayer()) {
            args.getSender().sendMessage(Strings.PLUGIN_TAG + ChatColor.RED + Localization.getString(Strings.PLAYER_ONLY_CMD));
            return;
        }

        if (args.getArgs().length == 0) {
            int booksGiven = BookStorage.getInstance().givePlayerAllBooks(args.getPlayer());
            if (booksGiven > 0) {
                args.getSender().sendMessage(Strings.PLUGIN_TAG + Localization.getString(Strings.ALL_BOOKS_RECIEVED));
            } else {
                args.getSender().sendMessage(Strings.PLUGIN_TAG + ChatColor.RED + Localization.getString(Strings.NO_BOOKS_REGISTERED));
            }
        } else {
            String query = (args.getArgs().length > 1) ? StringUtils.join(args.getArgs(), " ", 0, args.getArgs().length) : args.getArgs()[0];

            if (BookStorage.getInstance().givePlayerBook(args.getPlayer(), query)) {
                args.getSender().sendMessage(Strings.PLUGIN_TAG + Localization.getString(Strings.BOOK_RECIEVED));
            } else {
                args.getSender().sendMessage(Strings.PLUGIN_TAG + ChatColor.RED + Localization.getString(Strings.BOOK_NOT_FOUND));
            }
        }
    }

    @Command(name = "bookrules.give", aliases = {"rulebook.give", "rb.give", "br.give"}, permission = "bookrules.give")
    public void give(Arguments args) {
        if (args.getArgs().length == 0) {
            args.getSender().sendMessage(Strings.PLUGIN_TAG + ChatColor.RED + Localization.getString(Strings.NO_PLAYER_SPECIFIED));
            return;
        }

        Player player = Bukkit.getPlayer(args.getArgs()[0]);

        if (player == null || !player.isOnline()) {
            args.getSender().sendMessage(Strings.PLUGIN_TAG + ChatColor.RED + Localization.getString(Strings.PLAYER_NOT_FOUND));
            return;
        }

        if (args.getArgs().length == 1) {
            int booksGiven = BookStorage.getInstance().givePlayerAllBooks(player);

            if (booksGiven > 0) {
                args.getSender().sendMessage(String.format(Strings.PLUGIN_TAG + Localization.getString(Strings.ALL_BOOKS_GIVEN), player.getName()));
                player.sendMessage(String.format(Strings.PLUGIN_TAG + Localization.getString(Strings.GIVEN_ALL_BOOKS_MESSAGE), args.getSender().getName()));
            } else {
                args.getSender().sendMessage(Strings.PLUGIN_TAG + ChatColor.RED + Localization.getString(Strings.NO_BOOKS_REGISTERED));
            }
        } else {
            String query = (args.getArgs().length > 2) ? StringUtils.join(args.getArgs(), " ", 1, args.getArgs().length) : args.getArgs()[1];

            if (BookStorage.getInstance().givePlayerBook(player, query)) {
                args.getSender().sendMessage(String.format(Strings.PLUGIN_TAG + Localization.getString(Strings.BOOK_GIVEN), player.getName()));
                player.sendMessage(String.format(Strings.PLUGIN_TAG + Localization.getString(Strings.GIVEN_BOOK_MESSAGE), args.getSender().getName()));
            } else {
                args.getSender().sendMessage(Strings.PLUGIN_TAG + ChatColor.RED + Localization.getString(Strings.BOOK_NOT_FOUND));
            }
        }
    }

    @Command(name = "bookrules.add", aliases = {"rulebook.add", "rb.add", "br.add"}, permission = "bookrules.add")
    public void add(Arguments args) {
        if (!args.isPlayer()) {
            args.getSender().sendMessage(Strings.PLUGIN_TAG + ChatColor.RED + Localization.getString(Strings.PLAYER_ONLY_CMD));
            return;
        }

        Player player = args.getPlayer();
        ItemStack heldItem = player.getItemInHand();

        if (heldItem.getType() != Material.WRITTEN_BOOK) {
            player.sendMessage(Strings.PLUGIN_TAG + ChatColor.RED + Localization.getString(Strings.MUST_HOLD_BOOK));
        } else {
            BookStorage.getInstance().addBook(heldItem);
            player.sendMessage(Strings.PLUGIN_TAG + Localization.getString(Strings.BOOK_ADDED));
        }
    }

    @Command(name = "bookrules.delete", aliases = {"rulebook.delete", "rb.delete", "br.delete"}, permission = "bookrules.delete")
    public void delete(Arguments args) {
        if (!args.isPlayer()) {
            args.getSender().sendMessage(Strings.PLUGIN_TAG + ChatColor.RED + Localization.getString(Strings.PLAYER_ONLY_CMD));
            return;
        }

        if (args.getArgs().length == 0) {
            args.getSender().sendMessage(Strings.PLUGIN_TAG + ChatColor.RED + Localization.getString(Strings.NO_BOOK_SPECIFIED));
            return;
        }

        if (BookStorage.getInstance().deleteBook(args.getArgs()[0])) {
            args.getSender().sendMessage(Strings.PLUGIN_TAG + Localization.getString(Strings.BOOK_DELETED));
        } else {
            args.getSender().sendMessage(Strings.PLUGIN_TAG + ChatColor.RED + Localization.getString(Strings.BOOK_NOT_FOUND));
        }
    }

    @Command(name = "bookrules.list", aliases = {"rulebook.list", "rb.list", "br.list"}, permission = "bookrules.list")
    public void list(Arguments args) {
        List<String> books = BookStorage.getInstance().createBookList();

        if (books.isEmpty()) {
            args.getSender().sendMessage(Strings.PLUGIN_TAG + ChatColor.RED + Localization.getString(Strings.NO_BOOKS_REGISTERED));
        } else {
            args.getSender().sendMessage(Strings.PLUGIN_TAG + Localization.getString(Strings.LIST_CMD_HEADER));
            for (String line : books) {
                args.getSender().sendMessage(Strings.PLUGIN_TAG + line);
            }
        }
    }

    @Command(name = "bookrules.setauthor", aliases = {"rulebook.setauthor", "rb.setauthor", "br.setauthor"}, permission = "bookrules.setauthor")
    public void setAuthor(Arguments args) {
        if (!args.isPlayer()) {
            args.getSender().sendMessage(Strings.PLUGIN_TAG + ChatColor.RED + Localization.getString(Strings.PLAYER_ONLY_CMD));
            return;
        }

        if (args.getArgs().length == 0) {
            args.getSender().sendMessage(Strings.PLUGIN_TAG + ChatColor.RED + Localization.getString(Strings.NO_AUTHOR_SPECIFIED));
            return;
        }

        Player player = args.getPlayer();
        ItemStack book = player.getItemInHand();

        if (!book.getType().equals(Material.WRITTEN_BOOK)) {
            player.sendMessage(Strings.PLUGIN_TAG + ChatColor.RED + Localization.getString(Strings.MUST_HOLD_BOOK));
            return;
        }

        book = BookStorage.setAuthor(book, args.getArgs()[0]);

        player.setItemInHand(book);
        player.sendMessage(Strings.PLUGIN_TAG + Localization.getString(Strings.AUTHOR_CHANGED));
    }

    @Command(name = "bookrules.settitle", aliases = {"rulebook.settitle", "rb.settitle", "br.settitle"}, permission = "bookrules.settitle")
    public void setTitle(Arguments args) {
        if (!args.isPlayer()) {
            args.getSender().sendMessage(Strings.PLUGIN_TAG + ChatColor.RED + Localization.getString(Strings.PLAYER_ONLY_CMD));
            return;
        }

        if (args.getArgs().length == 0) {
            args.getSender().sendMessage(Strings.PLUGIN_TAG + ChatColor.RED + Localization.getString(Strings.NO_TITLE_SPECIFIED));
            return;
        }

        Player player = args.getPlayer();
        ItemStack book = player.getItemInHand();

        if (!book.getType().equals(Material.WRITTEN_BOOK)) {
            player.sendMessage(Strings.PLUGIN_TAG + ChatColor.RED + Localization.getString(Strings.MUST_HOLD_BOOK));
            return;
        }

        book = BookStorage.setTitle(book, args.getArgs()[0]);

        player.setItemInHand(book);
        player.sendMessage(Strings.PLUGIN_TAG + Localization.getString(Strings.TITLE_CHANGED));
    }

    @Command(name = "bookrules.unsign", aliases = {"rulebook.unsign", "rb.unsign", "br.unsign"}, permission = "bookrules.unsign")
    public void unsign(Arguments args) {
        if (!args.isPlayer()) {
            args.getSender().sendMessage(Strings.PLUGIN_TAG + ChatColor.RED + Localization.getString(Strings.PLAYER_ONLY_CMD));
            return;
        }

        Player player = args.getPlayer();
        ItemStack book = player.getItemInHand();

        if (!book.getType().equals(Material.WRITTEN_BOOK)) {
            player.sendMessage(Strings.PLUGIN_TAG + ChatColor.RED + Localization.getString(Strings.MUST_HOLD_BOOK));
            return;
        }

        book = BookStorage.unsignBook(book);
        player.setItemInHand(book);
        player.sendMessage(Strings.PLUGIN_TAG + Localization.getString(Strings.BOOK_UNSIGNED));
    }
}
