/*
 * This document is a part of the source code and related artifacts for
 * BookRules, an open source Bukkit plugin for automatically distributing
 * written books to new players.
 *
 * http://dev.bukkit.org/bukkit-plugins/bookrules/
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
import com.mstiles92.plugins.bookrules.config.Config;
import com.mstiles92.plugins.bookrules.data.StoredBook;
import com.mstiles92.plugins.bookrules.data.StoredBooks;
import com.mstiles92.plugins.bookrules.localization.Localization;
import com.mstiles92.plugins.bookrules.localization.Strings;
import com.mstiles92.plugins.bookrules.util.BookUtils;
import com.mstiles92.plugins.bookrules.util.Log;
import com.mstiles92.plugins.stileslib.commands.Arguments;
import com.mstiles92.plugins.stileslib.commands.CommandHandler;
import com.mstiles92.plugins.stileslib.commands.annotations.Command;
import com.mstiles92.plugins.stileslib.commands.annotations.TabCompleter;
import com.mstiles92.plugins.stileslib.updates.UpdateChecker;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BookRulesCommands implements CommandHandler {
    List<String> subcommands = Arrays.asList("info", "version", "commands", "reload", "get", "give", "add", "delete", "edit", "list", "setauthor", "settitle", "unsign");
    List<String> empty = new ArrayList<>();

    @Command(name = "bookrules", aliases = {"rulebook", "rb", "br"}, permission = "bookrules.info",
            description = "Base command for all other BookRules commands. Type \"/bookrules commands\" for more information.",
            usage = "/<command> <subcommand> [args ...]")
    public void bookrules(Arguments args) {
        String currentVersion = BookRules.getInstance().getDescription().getVersion();
        args.getSender().sendMessage(ChatColor.BLUE + String.format(Localization.getString(Strings.VERSION_MESSAGE), currentVersion));
        if (Config.shouldCheckForUpdates()) {
            UpdateChecker updateChecker = BookRules.getInstance().getUpdateChecker();
            if (updateChecker.isUpdateAvailable()) {
                args.getSender().sendMessage(ChatColor.BLUE + Localization.getString(Strings.UPDATE_AVAILIBLE));
                args.getSender().sendMessage(ChatColor.BLUE + String.format(Localization.getString(Strings.UPDATE_VERSION_INFO), currentVersion, updateChecker.getNewVersion()));
            } else {
                args.getSender().sendMessage(ChatColor.BLUE + Localization.getString(Strings.PLUGIN_UP_TO_DATE));
            }
        }
    }

    @TabCompleter(name = "bookrules", aliases = {"rulebook", "rb", "br"})
    public List<String> completeBookrules(Arguments args) {
        return autocomplete(subcommands, args.getArgs()[0]);
    }

    @Command(name = "bookrules.info", aliases = {"rulebook.info", "rb.info", "br.info"}, permission = "bookrules.info")
    public void info(Arguments args) {
        bookrules(args);
    }

    @TabCompleter(name = "bookrules.info", aliases = {"rulebook.info", "rb.info", "br.info"})
    public List<String> completeInfo(Arguments args) {
        return empty;
    }

    @Command(name = "bookrules.version", aliases = {"rulebook.version", "rb.version", "br.version"}, permission = "bookrules.info")
    public void version(Arguments args) {
        bookrules(args);
    }

    @TabCompleter(name = "bookrules.version", aliases = {"rulebook.version", "rb.version", "br.version"})
    public List<String> completeVersion(Arguments args) {
        return empty;
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

    @TabCompleter(name = "bookrules.commands", aliases = {"rulebook.commands", "rb.commands", "br.commands"})
    public List<String> completeCommands(Arguments args) {
        return empty;
    }

    @Command(name = "bookrules.reload", aliases = {"rulebook.reload", "rb.reload", "br.reload"}, permission = "bookrules.reload")
    public void reload(Arguments args) {
        Config.load(BookRules.getInstance().getConfig());
        StoredBooks.reload();
        args.getSender().sendMessage(Strings.PLUGIN_TAG + Localization.getString(Strings.CONFIG_RELOADED));
    }

    @TabCompleter(name = "bookrules.reload", aliases = {"rulebook.reload", "rb.reload", "br.reload"})
    public List<String> completeReload(Arguments args) {
        return empty;
    }

    @Command(name = "bookrules.get", aliases = {"rulebook.get", "rb.get", "br.get"}, permission = "bookrules.get", playerOnly = true)
    public void get(Arguments args) {
        if (args.getArgs().length == 0) {
            if (BookUtils.givePlayerAllBooks(args.getPlayer()) > 0) {
                args.getPlayer().sendMessage(Strings.PLUGIN_TAG + Localization.getString(Strings.ALL_BOOKS_RECIEVED));
            } else {
                args.getPlayer().sendMessage(Strings.PLUGIN_TAG + ChatColor.RED + Localization.getString(Strings.NO_BOOKS_REGISTERED));
            }
        } else {
            String query = (args.getArgs().length > 1) ? StringUtils.join(args.getArgs(), " ", 0, args.getArgs().length) : args.getArgs()[0];
            StoredBook book = StoredBooks.findBook(query);

            if (book == null) {
                args.getSender().sendMessage(Strings.PLUGIN_TAG + ChatColor.RED + Localization.getString(Strings.BOOK_NOT_FOUND));
            } else {
                book.giveToPlayer(args.getPlayer(), false);
                args.getSender().sendMessage(Strings.PLUGIN_TAG + Localization.getString(Strings.BOOK_RECIEVED));
            }
        }
    }

    @TabCompleter(name = "bookrules.get", aliases = {"rulebook.get", "rb.get", "br.get"})
    public List<String> completeGet(Arguments args) {
        return autocomplete(StoredBooks.getAllBookTitles(), (args.getArgs().length > 1) ? StringUtils.join(args.getArgs(), " ") : args.getArgs()[0]);
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
            if (BookUtils.givePlayerAllBooks(player) > 0) {
                args.getSender().sendMessage(String.format(Strings.PLUGIN_TAG + Localization.getString(Strings.ALL_BOOKS_GIVEN), player.getName()));
                player.sendMessage(String.format(Strings.PLUGIN_TAG + Localization.getString(Strings.GIVEN_ALL_BOOKS_MESSAGE), args.getSender().getName()));
            } else {
                args.getSender().sendMessage(Strings.PLUGIN_TAG + ChatColor.RED + Localization.getString(Strings.NO_BOOKS_REGISTERED));
            }
        } else {
            String query = (args.getArgs().length > 2) ? StringUtils.join(args.getArgs(), " ", 1, args.getArgs().length) : args.getArgs()[1];
            StoredBook book = StoredBooks.findBook(query);

            if (book == null) {
                args.getSender().sendMessage(Strings.PLUGIN_TAG + ChatColor.RED + Localization.getString(Strings.BOOK_NOT_FOUND));
            } else {
                book.giveToPlayer(player, false);
                args.getSender().sendMessage(String.format(Strings.PLUGIN_TAG + Localization.getString(Strings.BOOK_GIVEN), player.getName()));
                player.sendMessage(String.format(Strings.PLUGIN_TAG + Localization.getString(Strings.GIVEN_BOOK_MESSAGE), args.getSender().getName()));
            }
        }
    }

    @TabCompleter(name = "bookrules.give", aliases = {"rulebook.give", "rb.give", "br.give"})
    public List<String> completeGive(Arguments args) {
        if (args.getArgs().length > 1) {
            return autocomplete(StoredBooks.getAllBookTitles(), StringUtils.join(args.getArgs(), " ", 1, args.getArgs().length - 1));
        } else {
            return empty;
        }
    }

    @Command(name = "bookrules.add", aliases = {"rulebook.add", "rb.add", "br.add"}, permission = "bookrules.add", playerOnly = true)
    public void add(Arguments args) {
        Player player = args.getPlayer();
        ItemStack heldItem = player.getItemInHand();

        if (heldItem.getType() != Material.WRITTEN_BOOK) {
            player.sendMessage(Strings.PLUGIN_TAG + ChatColor.RED + Localization.getString(Strings.MUST_HOLD_BOOK));
        } else {
            StoredBooks.add(heldItem);
            player.sendMessage(Strings.PLUGIN_TAG + Localization.getString(Strings.BOOK_ADDED));
        }
    }

    @TabCompleter(name = "bookrules.add", aliases = {"rulebook.add", "rb.add", "br.add"})
    public List<String> completeAdd(Arguments args) {
        return empty;
    }

    @Command(name = "bookrules.delete", aliases = {"rulebook.delete", "rb.delete", "br.delete"}, permission = "bookrules.delete")
    public void delete(Arguments args) {
        if (args.getArgs().length == 0) {
            args.getSender().sendMessage(Strings.PLUGIN_TAG + ChatColor.RED + Localization.getString(Strings.NO_BOOK_SPECIFIED));
            return;
        }

        StoredBook bookToDelete = StoredBooks.findBook((args.getArgs().length > 1) ? StringUtils.join(args.getArgs(), " ", 0, args.getArgs().length) : args.getArgs()[0]);

        if (bookToDelete == null) {
            args.getSender().sendMessage(Strings.PLUGIN_TAG + ChatColor.RED + Localization.getString(Strings.BOOK_NOT_FOUND));
        } else {
            StoredBooks.delete(bookToDelete);
            args.getSender().sendMessage(Strings.PLUGIN_TAG + Localization.getString(Strings.BOOK_DELETED));
        }
    }

    @TabCompleter(name = "bookrules.delete", aliases = {"rulebook.delete", "rb.delete", "br.delete"})
    public List<String> completeDelete(Arguments args) {
        return autocomplete(StoredBooks.getAllBookTitles(), (args.getArgs().length > 1) ? StringUtils.join(args.getArgs(), " ") : args.getArgs()[0]);
    }

    @Command(name = "bookrules.edit", aliases = {"rulebook.edit", "rb.edit", "br.edit"}, permission = "bookrules.edit", playerOnly = true)
    public void edit(Arguments args) {
        if (args.getArgs().length == 0) {
            args.getSender().sendMessage(Strings.PLUGIN_TAG + ChatColor.RED + Localization.getString(Strings.NO_BOOK_SPECIFIED));
            return;
        }

        String query = (args.getArgs().length > 1) ? StringUtils.join(args.getArgs(), " ", 0, args.getArgs().length) : args.getArgs()[0];
        StoredBook book = StoredBooks.findBook(query);

        if (book == null) {
            args.getSender().sendMessage(Strings.PLUGIN_TAG + ChatColor.RED + Localization.getString(Strings.BOOK_NOT_FOUND));
        } else {
            book.giveToPlayer(args.getPlayer(), true);
            //TODO: refactor into localization system
            args.getSender().sendMessage(Strings.PLUGIN_TAG + "The stored book will be updated when the Book and Quill is signed.");
        }
    }

    @TabCompleter(name = "bookrules.edit", aliases = {"rulebook.edit", "rb.edit", "br.edit"})
    public List<String> completeEdit(Arguments args) {
        return autocomplete(StoredBooks.getAllBookTitles(), (args.getArgs().length > 1) ? StringUtils.join(args.getArgs(), " ") : args.getArgs()[0]);
    }

    @Command(name = "bookrules.list", aliases = {"rulebook.list", "rb.list", "br.list"}, permission = "bookrules.list")
    public void list(Arguments args) {
        List<StoredBook> allBooks = StoredBooks.getStoredBooks();

        if (allBooks.isEmpty()) {
            args.getSender().sendMessage(Strings.PLUGIN_TAG + ChatColor.RED + Localization.getString(Strings.NO_BOOKS_REGISTERED));
        } else {
            args.getSender().sendMessage(Strings.PLUGIN_TAG + Localization.getString(Strings.LIST_CMD_HEADER));

            //TODO: filter out books that the player does not have permission for
            for (int i = 0; i < allBooks.size(); i++) {
                args.getSender().sendMessage(Strings.PLUGIN_TAG + (i + 1) + " - " + allBooks.get(i).getTitle() + " by " + allBooks.get(i).getAuthor());
            }
        }
    }

    @TabCompleter(name = "bookrules.list", aliases = {"rulebook.list", "rb.list", "br.list"})
    public List<String> completeList(Arguments args) {
        return empty;
    }

    @Command(name = "bookrules.setauthor", aliases = {"rulebook.setauthor", "rb.setauthor", "br.setauthor"}, permission = "bookrules.setauthor", playerOnly = true)
    public void setAuthor(Arguments args) {
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

        book = BookUtils.setAuthor(book, (args.getArgs().length > 1) ? StringUtils.join(args.getArgs(), " ", 0, args.getArgs().length) : args.getArgs()[0]);

        player.setItemInHand(book);
        player.sendMessage(Strings.PLUGIN_TAG + Localization.getString(Strings.AUTHOR_CHANGED));
    }

    @TabCompleter(name = "bookrules.setauthor", aliases = {"rulebook.setauthor", "rb.setauthor", "br.setauthor"})
    public List<String> completeSetAuthor(Arguments args) {
        return empty;
    }

    @Command(name = "bookrules.settitle", aliases = {"rulebook.settitle", "rb.settitle", "br.settitle"}, permission = "bookrules.settitle", playerOnly = true)
    public void setTitle(Arguments args) {

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

        book = BookUtils.setTitle(book, (args.getArgs().length > 1) ? StringUtils.join(args.getArgs(), " ", 0, args.getArgs().length) : args.getArgs()[0]);

        player.setItemInHand(book);
        player.sendMessage(Strings.PLUGIN_TAG + Localization.getString(Strings.TITLE_CHANGED));
    }

    @TabCompleter(name = "bookrules.settitle", aliases = {"rulebook.settitle", "rb.settitle", "br.settitle"})
    public List<String> completeSetTitle(Arguments args) {
        return empty;
    }

    @Command(name = "bookrules.unsign", aliases = {"rulebook.unsign", "rb.unsign", "br.unsign"}, permission = "bookrules.unsign", playerOnly = true)
    public void unsign(Arguments args) {
        Player player = args.getPlayer();
        ItemStack book = player.getItemInHand();

        if (!book.getType().equals(Material.WRITTEN_BOOK)) {
            player.sendMessage(Strings.PLUGIN_TAG + ChatColor.RED + Localization.getString(Strings.MUST_HOLD_BOOK));
            return;
        }

        book = BookUtils.unsignBook(book);
        player.setItemInHand(book);
        player.sendMessage(Strings.PLUGIN_TAG + Localization.getString(Strings.BOOK_UNSIGNED));
    }

    @TabCompleter(name = "bookrules.unsign", aliases = {"rulebook.unsign", "rb.unsign", "br.unsign"})
    public List<String> completeUnsign(Arguments args) {
        return empty;
    }

    private List<String> autocomplete(List<String> options, String fragment) {
        if (fragment == null) {
            return options;
        }

        List<String> valid = new ArrayList<>();
        for (String s : options) {
            if (s.startsWith(fragment)) {
                valid.add(s);
            }
        }
        return valid;
    }
}
