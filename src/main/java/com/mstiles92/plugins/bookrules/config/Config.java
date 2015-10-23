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

package com.mstiles92.plugins.bookrules.config;

import com.mstiles92.plugins.bookrules.BookRules;
import org.bukkit.configuration.file.FileConfiguration;

public class Config {
    private static boolean checkForUpdates = true;
    private static boolean verboseOutput = false;
    private static boolean giveOnEveryJoin = false;
    private static boolean giveNewBooksOnJoin = true;
    private static boolean notifyPlayers = true;
    private static int runnableDelay = 20;
    private static boolean blockTrading = true;
    private static boolean keepBooksOnDeath = false;
    private static String language = "EN";

    public static void load(FileConfiguration config) {
        updateOldConfig(config);

        checkForUpdates = config.getBoolean("Check-for-Updates", true);
        verboseOutput = config.getBoolean("Verbose", false);
        giveOnEveryJoin = config.getBoolean("Give-Books-Every-Join", false);
        giveNewBooksOnJoin = config.getBoolean("Give-New-Books-On-Join", true);
        notifyPlayers = config.getBoolean("Display-Messages", true);
        runnableDelay = config.getInt("Seconds-Delay", 1) * 20;
        blockTrading = config.getBoolean("Block-Villager-Book-Trading", true);
        keepBooksOnDeath = config.getBoolean("Keep-Books-On-Death", false);
        language = config.getString("Language", "EN");
    }

    private static void updateOldConfig(FileConfiguration config) {
        if (config.contains("Give-Books-On-First-Join")) {
            config.set("Give-New-Books-On-Join", config.get("Give-Books-On-First-Join"));
            config.set("Give-Books-On-First-Join", null);
            BookRules.getInstance().saveConfig();
        }
    }

    public static boolean shouldCheckForUpdates() {
        return checkForUpdates;
    }

    public static boolean verboseOutputEnabled() {
        return verboseOutput;
    }

    public static boolean shouldGiveBooksEveryJoin() {
        return giveOnEveryJoin;
    }

    public static boolean shouldGiveNewBooksOnJoin() {
        return giveNewBooksOnJoin;
    }

    public static boolean shouldNotifyPlayers() {
        return notifyPlayers;
    }

    public static int getRunnableDelay() {
        return runnableDelay;
    }

    public static boolean shouldBlockVillagerTrading() {
        return blockTrading;
    }

    public static boolean shouldKeepBooksOnDeath() {
        return keepBooksOnDeath;
    }

    public static String getLanguage() {
        return language;
    }
}
