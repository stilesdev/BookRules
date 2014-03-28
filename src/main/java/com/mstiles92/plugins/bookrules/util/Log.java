package com.mstiles92.plugins.bookrules.util;

import com.mstiles92.plugins.bookrules.BookRules;
import org.bukkit.ChatColor;

public class Log {
    public static void verbose(String message) {
        if (BookRules.getInstance().getConfigObject().verboseOutputEnabled()) {
            BookRules.getInstance().getLogger().info(message);
        }
    }

    public static void info(String message) {
        BookRules.getInstance().getLogger().info(message);
    }

    public static void warning(String message) {
        BookRules.getInstance().getLogger().warning(ChatColor.RED + message);
    }
}
