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

package com.mstiles92.plugins.bookrules.localization;

import org.bukkit.ChatColor;

/**
 * Strings is a class simply used to store all of the constant strings to be
 * used by the plugin.
 *
 * @author mstiles92
 */
public class Strings {

    public static final String PLUGIN_TAG = ChatColor.BLUE + "[BookRules] " + ChatColor.GREEN;

    // Command usage strings (not translated)
    public static final String CMD_USAGE_FORMAT = ChatColor.GREEN + "%s " + ChatColor.GRAY + "%s";
    public static final String CMD_INFO_USAGE = "/rulebook [info | version]";
    public static final String CMD_RELOAD_USAGE = "/rulebook reload";
    public static final String CMD_GET_USAGE = "/rulebook get [id | title]";
    public static final String CMD_GIVE_USAGE = "/rulebook give <player> [id | title]";
    public static final String CMD_ADD_USAGE = "/rulebook add";
    public static final String CMD_DELETE_USAGE = "/rulebook delete <id | title>";
    public static final String CMD_LIST_USAGE = "/rulebook list";
    public static final String CMD_SETAUTHOR_USAGE = "/rulebook setauthor <author>";
    public static final String CMD_SETTITLE_USAGE = "/rulebook settitle <title>";
    public static final String CMD_UNSIGN_USAGE = "/rulebook unsign";

    // Console logging
    public static final String UPDATECHECKER_STARTED = "Console.Logging.StartUpdateChecker";
    public static final String UPDATECHECKER_VERSION_FOUND = "Console.Logging.VersionFound";
    public static final String UPDATECHECKER_CHANGES_FOUND = "Console.Logging.ChangesFound";
    public static final String PLUGIN_UP_TO_DATE = "Console.Logging.UpToDate";
    public static final String OLD_CONFIG_CONVERTED = "Console.Logging.OldConfigConverted";
    public static final String PLAYER_GIVEN_BOOKS = "Console.Logging.PlayerGivenBooks";

    // Console errors
    public static final String METRICS_START_FAILURE = "Console.Error.Metrics";
    public static final String UPDATE_CHECK_FAILURE = "Console.Error.UpdateChecker";
    public static final String FILE_OPEN_FAILURE = "Console.Error.FileOpen";
    public static final String FILE_SAVE_FAILURE = "Console.Error.FileSave";
    public static final String INVALID_CONFIGURATION_ERROR = "Console.Error.InvalidConfiguration";
    public static final String OLD_FORMAT_CONVERSION_FAILURE = "Console.Error.OldFormatConversion";
    public static final String PLUGIN_DISABLED = "Console.Error.PluginDisabled";

    // Notification messages
    public static final String UPDATE_AVAILIBLE = "Notification.Update.Available";
    public static final String UPDATE_VERSION_INFO = "Notification.Update.Version";
    public static final String UPDATE_CHANGES = "Notification.Update.Changes";
    public static final String PLAYER_JOIN_MESSAGE = "Notification.JoinMessage";
    public static final String GIVEN_ALL_BOOKS_MESSAGE = "Notification.GivenAllBooks";
    public static final String GIVEN_BOOK_MESSAGE = "Notification.GivenBook";
    public static final String TRADING_DENIED = "Notification.NoTrading";

    // Success messages
    public static final String CONFIG_RELOADED = "Commands.Success.Reload";
    public static final String ALL_BOOKS_RECIEVED = "Commands.Success.GetAll";
    public static final String BOOK_RECIEVED = "Commands.Success.GetOne";
    public static final String ALL_BOOKS_GIVEN = "Commands.Success.GiveAll";
    public static final String BOOK_GIVEN = "Commands.Success.GiveOne";
    public static final String BOOK_ADDED = "Commands.Success.Add";
    public static final String BOOK_DELETED = "Commands.Success.Delete";
    public static final String AUTHOR_CHANGED = "Commands.Success.ChangedAuthor";
    public static final String TITLE_CHANGED = "Commands.Success.ChangedTitle";
    public static final String BOOK_UNSIGNED = "Commands.Success.Unsigned";

    // Info/Command descriptions
    public static final String VERSION_MESSAGE = "Commands.Info.Version";
    public static final String COMMANDS_HEADER = "Commands.Info.CommandsHeader";
    public static final String CMD_INFO_DESCRIPTION = "Commands.Info.Info";
    public static final String CMD_RELOAD_DESCRIPTION = "Commands.Info.Reload";
    public static final String CMD_GET_DESCRIPTION = "Commands.Info.Get";
    public static final String CMD_GIVE_DESCRIPTION = "Commands.Info.Give";
    public static final String CMD_ADD_DESCRIPTION = "Commands.Info.Add";
    public static final String CMD_DELETE_DESCRIPTION = "Commands.Info.Delete";
    public static final String CMD_LIST_DESCRIPTION = "Commands.Info.List";
    public static final String CMD_SETAUTHOR_DESCRIPTION = "Commands.Info.Author";
    public static final String CMD_SETTITLE_DESCRIPTION = "Commands.Info.Title";
    public static final String CMD_UNSIGN_DESCRIPTION = "Commands.Info.Unsign";
    public static final String LIST_CMD_HEADER = "Commands.Info.ListHeader";
    public static final String LIST_CMD_ENTRY = "Commands.Info.ListEntry";

    // Error messages
    public static final String PLAYER_ONLY_CMD = "Commands.Error.PlayerOnly";
    public static final String NO_PERMISSIONS = "Commands.Error.NoPerms";
    public static final String NO_BOOKS_REGISTERED = "Commands.Error.NoBooks";
    public static final String BOOK_NOT_FOUND = "Commands.Error.BookNotFound";
    public static final String NO_PLAYER_SPECIFIED = "Commands.Error.NoPlayerSpecified";
    public static final String PLAYER_NOT_FOUND = "Commands.Error.PlayerNotFound";
    public static final String MUST_HOLD_BOOK = "Commands.Error.MustHoldBook";
    public static final String NO_BOOK_SPECIFIED = "Commands.Error.NoBookSpecified";
    public static final String NO_AUTHOR_SPECIFIED = "Commands.Error.NoAuthorSpecified";
    public static final String NO_TITLE_SPECIFIED = "Commands.Error.NoTitleSpecified";
}
