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

package com.mstiles92.plugins.bookrules.listeners;

import com.mstiles92.plugins.bookrules.BookRules;
import com.mstiles92.plugins.bookrules.util.GiveBookRunnable;
import com.mstiles92.plugins.bookrules.localization.Localization;
import com.mstiles92.plugins.bookrules.localization.Strings;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

/**
 * PlayerListener is a class that is used to detect when a player joins the
 * server and handle the event appropriately.
 *
 * @author mstiles92
 */
public class PlayerListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();

        if (BookRules.getInstance().getUpdateChecker().isUpdateAvailable() && player.hasPermission("bookrules.receivealerts")) {
            player.sendMessage(Strings.PLUGIN_TAG + Localization.getString(Strings.UPDATE_AVAILIBLE));
            player.sendMessage(String.format(Strings.PLUGIN_TAG + Strings.UPDATE_VERSION_INFO, BookRules.getInstance().getDescription().getVersion(), BookRules.getInstance().getUpdateChecker().getNewVersion()));
        }

        if (BookRules.getInstance().getConfigObject().shouldGiveNewBooksOnJoin()) {
            BookRules.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(BookRules.getInstance(), new GiveBookRunnable(player), BookRules.getInstance().getConfigObject().getRunnableDelay());
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (!BookRules.getInstance().getConfigObject().shouldBlockVillagerTrading()) {
            return;
        }

        if (e.getInventory().getType() != InventoryType.MERCHANT) {
            return;
        }

        ItemStack book = (e.isShiftClick()) ? e.getCurrentItem() : e.getCursor();
        if (book == null || book.getType() != Material.WRITTEN_BOOK) {
            return;
        }

        if (book.getItemMeta().getLore() != null && book.getItemMeta().getLore().contains("BookRules")) {
            e.setCancelled(true);
            e.getWhoClicked().closeInventory();
            BookRules.getInstance().getServer().getPlayer(e.getWhoClicked().getName()).sendMessage(Strings.PLUGIN_TAG + ChatColor.RED + Localization.getString(Strings.TRADING_DENIED));
        }
    }
}
