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

package com.mstiles92.plugins.bookrules.config;

import com.mstiles92.plugins.bookrules.BookRules;
import org.bukkit.configuration.file.FileConfiguration;

public class Config {
    private FileConfiguration config;

    public Config() {
        config = BookRules.getInstance().getConfig();
        config.options().copyDefaults(true);
        updateOldConfig();
        BookRules.getInstance().saveConfig();
    }

    private void updateOldConfig() {
        if (config.contains("Give-Books-On-First-Join")) {
            config.set("Give-New-Books-On-Join", config.get("Give-Books-On-First-Join"));
            config.set("Give-Books-On-First-Join", null);
        }
    }

    public boolean shouldCheckForUpdates() {
        return config.getBoolean("Check-for-Updates");
    }

    public boolean verboseOutputEnabled() {
        return config.getBoolean("Verbose");
    }

    public boolean shouldGiveBooksEveryJoin() {
        return config.getBoolean("Give-Books-Every-Join");
    }

    public boolean shouldGiveNewBooksOnJoin() {
        return config.getBoolean("Give-New-Books-On-Join");
    }

    public boolean shouldNotifyPlayers() {
        return config.getBoolean("Display-Messages");
    }

    public int getRunnableDelay() {
        return config.getInt("Seconds-Delay") * 20;
    }

    public boolean shouldBlockVillagerTrading() {
        return config.getBoolean("Block-Villager-Book-Trading");
    }
}
