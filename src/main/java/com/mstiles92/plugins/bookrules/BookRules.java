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

package com.mstiles92.plugins.bookrules;

import com.mstiles92.plugins.bookrules.commands.BookRulesCommands;
import com.mstiles92.plugins.bookrules.config.Config;
import com.mstiles92.plugins.bookrules.listeners.PlayerListener;
import com.mstiles92.plugins.bookrules.localization.Language;
import com.mstiles92.plugins.bookrules.localization.Localization;
import com.mstiles92.plugins.bookrules.localization.Strings;
import com.mstiles92.plugins.bookrules.util.Log;
import com.mstiles92.plugins.commonutils.commands.CommandRegistry;
import com.mstiles92.plugins.commonutils.updates.UpdateChecker;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
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
    private UpdateChecker updateChecker;
    private CommandRegistry commandRegistry;

    @Override
    public void onEnable() {
        instance = this;

        config = new Config();

        // TODO: Create config option, load correct language
        if (!Localization.load(Language.ENGLISH)) {
            Log.warning("Error loading language file. BookRules will now be disabled.");
            getPluginLoader().disablePlugin(this);
        }

        commandRegistry = new CommandRegistry(this);
        commandRegistry.setDefaultNoPermissionMessage(Localization.getString(Strings.NO_PERMISSIONS));
        commandRegistry.setDefaultPlayerOnlyMessage(Localization.getString(Strings.PLAYER_ONLY_CMD));
        commandRegistry.registerCommands(new BookRulesCommands());
        commandRegistry.registerHelp();

        getServer().getPluginManager().registerEvents(new PlayerListener(), this);

        if (config.shouldCheckForUpdates()) {
            updateChecker = new UpdateChecker("bookrules", getLogger(), getDescription().getVersion());
            getServer().getScheduler().runTaskTimer(this, updateChecker, 40, 216000);
        }

        try {
            Metrics metrics = new Metrics(this);
            metrics.start();
        } catch (IOException e) {
            Log.warning(Localization.getString(Strings.METRICS_START_FAILURE));
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return commandRegistry.handleCommand(sender, command, label, args);
    }

    @Override
    public void onDisable() {

    }

    public Config getConfigObject() {
        return config;
    }

    public UpdateChecker getUpdateChecker() {
        return updateChecker;
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
