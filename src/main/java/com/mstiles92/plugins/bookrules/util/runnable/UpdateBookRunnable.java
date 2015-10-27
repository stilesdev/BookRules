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

package com.mstiles92.plugins.bookrules.util.runnable;

import com.mstiles92.plugins.bookrules.BookRules;
import com.mstiles92.plugins.bookrules.data.StoredBooks;
import com.mstiles92.plugins.bookrules.localization.Strings;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class UpdateBookRunnable extends BukkitRunnable {
    private Player player;
    private int slot;
    private UUID bookUUID;

    public UpdateBookRunnable(Player player, int slot, UUID bookUUID) {
        this.player = player;
        this.slot = slot;
        this.bookUUID = bookUUID;
    }

    @Override
    public void run() {
        ItemStack itemStack = player.getInventory().getItem(slot);

        BookMeta meta = (BookMeta) itemStack.getItemMeta();

        if (StoredBooks.update(bookUUID, meta)) {
            //TODO: refactor into localization system
            player.sendMessage(Strings.PLUGIN_TAG + "Book update successful!");
        } else {
            //TODO: refactor into localization system
            player.sendMessage(Strings.PLUGIN_TAG + ChatColor.RED + "Book update failed!");
        }

        player.getInventory().setItem(slot, null);
    }

    public void runLater() {
        runTaskLater(BookRules.getInstance(), 1);
    }
}
