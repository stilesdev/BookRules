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

package com.mstiles92.plugins.bookrules.util;

import com.mstiles92.plugins.bookrules.config.Config;
import com.mstiles92.plugins.bookrules.localization.Localization;
import com.mstiles92.plugins.bookrules.localization.Strings;
import org.bukkit.entity.Player;

/**
 * GiveBookRunnable is a simple class used to give the player a book after
 * a short delay.
 *
 * @author mstiles92
 */
public class GiveBookRunnable implements Runnable {
    private Player player;

    /**
     * The main constructor for this class
     *
     * @param player the player to give the books to
     */
    public GiveBookRunnable(Player player) {
        this.player = player;
    }

    @Override
    public void run() {
        if (Config.shouldGiveBooksEveryJoin()) {
            int num = BookStorage.getInstance().givePlayerAllBooks(player);
            player.sendMessage(String.format(Strings.PLUGIN_TAG + Localization.getString(Strings.PLAYER_JOIN_MESSAGE), String.valueOf(num)));
        } else {
            int num = BookStorage.getInstance().givePlayerUngivenBooks(player);
            Log.verbose(String.format(Localization.getString(Strings.PLAYER_GIVEN_BOOKS), player.getName(), String.valueOf(num)));

            if (num > 0 && Config.shouldNotifyPlayers()) {
                player.sendMessage(String.format(Strings.PLUGIN_TAG + Localization.getString(Strings.PLAYER_JOIN_MESSAGE), String.valueOf(num)));
            }
        }
    }
}
