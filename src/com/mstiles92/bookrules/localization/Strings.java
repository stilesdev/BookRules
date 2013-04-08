package com.mstiles92.bookrules.localization;

import org.bukkit.ChatColor;

public class Strings {
	
	public static final String PLUGIN_TAG = ChatColor.BLUE + "[BookRules] " + ChatColor.GREEN;

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
	public static final String UPDATE_AVAILIBLE = "Notification.Update.Availible";
	public static final String UPDATE_VERSION_INFO = "Notification.Update.Version";
	public static final String UPDATE_CHANGES = "Notification.Update.Changes";
	public static final String PLAYER_JOIN_MESSAGE = "Notification.JoinMessage";
	public static final String GIVEN_ALL_BOOKS_MESSAGE = "Notification.GivenAllBooks";
	public static final String GIVEN_BOOK_MESSAGE = "Notification.GivenBook";
	
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
	public static final String CMD_COMMANDS_DESCRIPTION = "Commands.Info.CommandDesc";
	public static final String CMD_RELOAD_DESCRIPTION = "Commands.Info.ReloadDesc";
	public static final String CMD_GET_DESCRIPTION = "Commands.Info.GetDesc";
	public static final String CMD_GIVE_DESCRIPTION = "Commands.Info.GiveDesc";
	public static final String CMD_ADD_DESCRIPTION = "Commands.Info.AddDesc";
	public static final String CMD_DELETE_DESCRIPTION = "Commands.Info.DeleteDesc";
	public static final String CMD_LIST_DESCRIPTION = "Commands.Info.ListDesc";
	public static final String CMD_AUTHOR_DESCRIPTION = "Commands.Info.AuthorDesc";
	public static final String CMD_TITLE_DESCRIPTION = "Commands.Info.TitleDesc";
	public static final String CMD_UNSIGN_DESCRIPTION = "Commands.Info.UnsignDesc";
	public static final String LIST_CMD_HEADER = "Commands.Info.ListHeader";
	public static final String LIST_CMD_ENTRY = "Commands.Info.ListEntry";
	
	// Error messages
	public static final String TRADING_DENIED = "Commands.Error.Trading";
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
