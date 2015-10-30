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
import com.mstiles92.plugins.bookrules.data.StoredBook;
import com.mstiles92.plugins.bookrules.data.StoredBooks;
import com.mstiles92.plugins.bookrules.localization.Localization;
import com.mstiles92.plugins.bookrules.localization.Strings;
import com.mstiles92.plugins.bookrules.util.BookUtils;
import com.mstiles92.plugins.bookrules.util.runnable.GiveBookRunnable;
import com.mstiles92.plugins.bookrules.util.runnable.UpdateBookRunnable;
import com.mstiles92.plugins.stileslib.updates.UpdateChecker;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

/**
 * This class is used to handle general events fired off by the player.
 *
 * @author mstiles92
 */
public class PlayerListener implements Listener {
    private Map<UUID, List<ItemStack>> pendingRespawns = new HashMap<>();

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

        if (!event.getPlayer().hasPermission("bookrules.edit")) {
            //TODO: refactor into localization system
            event.getPlayer().sendMessage(Strings.PLUGIN_TAG + ChatColor.RED + "You do not have permission to edit this book!");;
            return;
        }

        if (event.isSigning() && BookUtils.isBookRulesBookEditable(itemStack)) {
            new UpdateBookRunnable(event.getPlayer(), event.getSlot(), bookUUID).runLater();
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            ItemStack heldItem = event.getPlayer().getItemInHand();
            if (heldItem != null && heldItem.getType().equals(Material.WRITTEN_BOOK)) {
                StoredBook book = StoredBooks.get(BookUtils.getBookUUID(heldItem));

                if (book != null) {
                    if (book.checkPermission(event.getPlayer())) {
                        event.getPlayer().setItemInHand(book.getItemStack());
                    } else {
                        //TODO: refactor into localization system
                        event.getPlayer().sendMessage(Strings.PLUGIN_TAG + ChatColor.RED + "You do not have permission to access this book.");
                        //TODO: make a config option for whether this book gets deleted or not:
                        event.getPlayer().setItemInHand(null);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (!event.getKeepInventory() && Config.shouldKeepBooksOnDeath()) {
            List<ItemStack> booksToKeep = new ArrayList<>();

            for (ItemStack item : event.getDrops()) {
                if (BookUtils.isBookRulesBook(item)) {
                    booksToKeep.add(item);
                }
            }

            event.getDrops().removeAll(booksToKeep);
            pendingRespawns.put(event.getEntity().getUniqueId(), booksToKeep);
        }
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        UUID playerUUID = event.getPlayer().getUniqueId();

        if (pendingRespawns.containsKey(playerUUID)) {
            List<ItemStack> booksToKeep = pendingRespawns.get(playerUUID);

            for (ItemStack item : booksToKeep) {
                event.getPlayer().getInventory().addItem(item);
            }

            pendingRespawns.remove(playerUUID);
        }
    }
}
