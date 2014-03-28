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

package com.mstiles92.plugins.bookrules;

import com.mstiles92.plugins.bookrules.commands.RuleBook;
import com.mstiles92.plugins.bookrules.config.Config;
import com.mstiles92.plugins.bookrules.listeners.PlayerListener;
import com.mstiles92.plugins.bookrules.localization.Language;
import com.mstiles92.plugins.bookrules.localization.Localization;
import com.mstiles92.plugins.bookrules.localization.Strings;
import com.mstiles92.plugins.bookrules.util.UpdateChecker;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;

import java.io.IOException;

/**
 * BookRules is the main class of this Bukkit plugin. It handles enabling
 * and disabling of this plugin, loading config files, and other general
 * methods needed for this plugin's operation.
 *
 * @author mstiles92
 */
public class BookRules extends JavaPlugin {
    private static BookRules instance = null;

    private Config config;

    public boolean updateAvailable = false;
    public String latestKnownVersion, changes;

    @Override
    public void onEnable() {
        instance = this;

        config = new Config();

        // TODO: Create config option, load correct language
        if (!Localization.load(Language.ENGLISH)) {
            logWarning("Error loading language file. BookRules will now be disabled.");
            getPluginLoader().disablePlugin(this);
        }

        getCommand("rulebook").setExecutor(new RuleBook());
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);

        latestKnownVersion = this.getDescription().getVersion();
        if (config.shouldCheckForUpdates()) {
            getServer().getScheduler().runTaskTimer(this, new UpdateChecker(), 40, 216000);
        }

        try {
            Metrics metrics = new Metrics(this);
            metrics.start();
        } catch (IOException e) {
            logWarning(Localization.getString(Strings.METRICS_START_FAILURE));
        }
    }

    @Override
    public void onDisable() {

    }

    /**
     * Print a message to the server console, if verbose output is enabled
     * in the config. The message is logged at the info level.
     *
     * @param message the message to be logged
     */
    public void log(String message) {
        if (config.verboseOutputEnabled()) {
            getLogger().info(message);
        }
    }

    /**
     * Convenience method used to log warning messages to the console.
     *
     * @param message the message to be logged
     */
    public void logWarning(String message) {
        getLogger().warning(ChatColor.RED + message);
    }

    public Config getConfigObject() {
        return config;
    }

    /**
     * Get the static instance of the plugin class.
     *
     * @return the instance of this class
     */
    public static BookRules getInstance() {
        return instance;
    }
}
