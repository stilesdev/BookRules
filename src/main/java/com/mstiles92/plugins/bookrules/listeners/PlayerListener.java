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

package com.mstiles92.plugins.bookrules.listeners;

import com.mstiles92.plugins.bookrules.BookRules;
import com.mstiles92.plugins.bookrules.config.Config;
import com.mstiles92.plugins.bookrules.data.PlayerData;
import com.mstiles92.plugins.bookrules.localization.Localization;
import com.mstiles92.plugins.bookrules.localization.Strings;
import com.mstiles92.plugins.bookrules.util.BookUtils;
import com.mstiles92.plugins.bookrules.util.runnable.GiveBookRunnable;
import com.mstiles92.plugins.bookrules.util.runnable.UpdateBookRunnable;
import com.mstiles92.plugins.stileslib.updates.UpdateChecker;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

/**
 * This class is used to handle general events fired off by the player.
 *
 * @author mstiles92
 */
public class PlayerListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        PlayerData playerData = PlayerData.get(player);
        UpdateChecker updateChecker = BookRules.getInstance().getUpdateChecker();

        if (updateChecker != null && updateChecker.isUpdateAvailable() && player.hasPermission("bookrules.receivealerts")) {
            player.sendMessage(Strings.PLUGIN_TAG + Localization.getString(Strings.UPDATE_AVAILIBLE));
            player.sendMessage(String.format(Strings.PLUGIN_TAG + Strings.UPDATE_VERSION_INFO, BookRules.getInstance().getDescription().getVersion(), BookRules.getInstance().getUpdateChecker().getNewVersion()));
        }

        if (Config.shouldGiveNewBooksOnJoin()) {
            new GiveBookRunnable(player).runLater();
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory().getType().equals(InventoryType.MERCHANT)) {
            ItemStack book = (event.isShiftClick()) ? event.getCurrentItem() : event.getCursor();

            if (Config.shouldBlockVillagerTrading() && BookUtils.isBookRulesBook(book)) {
                event.setCancelled(true);
                event.getWhoClicked().closeInventory();

                if (event.getWhoClicked() instanceof Player) {
                    ((Player) event.getWhoClicked()).sendMessage(Strings.PLUGIN_TAG + ChatColor.RED + Localization.getString(Strings.TRADING_DENIED));
                }
            }
        }
    }

    @EventHandler
    public void onPlayerEditBook(PlayerEditBookEvent event) {
        ItemStack itemStack = event.getPlayer().getItemInHand();
        UUID bookUUID = BookUtils.getBookUUID(itemStack);

        if (event.isSigning() && bookUUID != null) {
            new UpdateBookRunnable(event.getPlayer(), event.getSlot(), bookUUID).runLater();
        }
    }
}
