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

package com.mstiles92.plugins.bookrules.data;

import com.mstiles92.plugins.bookrules.util.AttributeWrapper;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import javax.json.*;
import java.util.*;

public class StoredBook {
    private UUID uuid;
    private String title;
    private String author;
    private List<String> pages;

    /**
     * Creates a new instance of a stored book, assigning it a random UUID and the default settings as defined in the
     * plugin's config.yml file.
     *
     * @param title  the title of the new StoredBook to create
     * @param author the author of the new StoredBook to create
     * @param pages  the pages of the new StoredBook to create
     */
    public StoredBook(String title, String author, List<String> pages) {
        this(UUID.randomUUID(), title, author, pages);
    }

    /**
     * Creates a new instance of a stored book, assigning it the default settings as defined in the plugin's config.yml
     * file.
     *
     * @param title  the title of the new StoredBook to create
     * @param author the author of the new StoredBook to create
     * @param pages  the pages of the new StoredBook to create
     */
    public StoredBook(UUID uuid, String title, String author, List<String> pages) {
        this.uuid = uuid;
        this.title = title;
        this.author = author;
        this.pages = pages;
    }

    /**
     * Creates a new instance of a stored book, using values loaded from the JSON storage file.
     *
     * @param json the JsonObject representing this stored book
     */
    public StoredBook(JsonObject json) {
        uuid = UUID.fromString(json.getString("uuid"));
        title = json.getString("title");
        author = json.getString("author");
        pages = new ArrayList<>();
        for (JsonString s : json.getJsonArray("pages").getValuesAs(JsonString.class)) {
            pages.add(s.getString());
        }
    }

    /**
     * Create a representation of the stored book in JSON to write to the storage file.
     *
     * @return the JsonObject representing this stored book
     */
    public JsonObject toJsonObject() {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("uuid", uuid.toString());
        builder.add("title", title);
        builder.add("author", author);
        JsonArrayBuilder pagesArrayBuilder = Json.createArrayBuilder();
        for (String page : pages) {
            pagesArrayBuilder.add(page);
        }
        builder.add("pages", pagesArrayBuilder.build());
        return builder.build();
    }

    /**
     * Check whether a player has permission to access this book.
     *
     * @param player the player whose permission should be checked
     * @return true if the player is allowed to see the book, false if they are not
     */
    public boolean checkPermission(Player player) {
        //TODO: implement, and check against group permission
        return true;
    }

    public ItemStack getItemStack() {
        ItemStack itemStack = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta meta = (BookMeta) itemStack.getItemMeta();
        meta.setTitle(title);
        meta.setAuthor(author);
        meta.setPages(pages);
        itemStack.setItemMeta(meta);
        AttributeWrapper wrapper = AttributeWrapper.newWrapper(itemStack);
        wrapper.setData(uuid.toString());
        return wrapper.getItemStack();
    }

    public ItemStack getItemStackEditable() {
        ItemStack itemStack = new ItemStack(Material.BOOK_AND_QUILL);
        BookMeta meta = (BookMeta) itemStack.getItemMeta();
        meta.setPages(pages);
        itemStack.setItemMeta(meta);
        AttributeWrapper wrapper = AttributeWrapper.newWrapper(itemStack);
        wrapper.setData(uuid.toString());
        return wrapper.getItemStack();
    }

    public void giveToPlayer(Player player, boolean editable) {
        HashMap<Integer, ItemStack> leftovers = player.getInventory().addItem(editable ? getItemStackEditable() : getItemStack());

        // Drop any books that didn't fit into the player's inventory at the player's location.
        for (Map.Entry<Integer, ItemStack> entry : leftovers.entrySet()) {
            player.getWorld().dropItem(player.getLocation(), entry.getValue());
        }

        PlayerData.get(player).markBookRecieved(uuid);
    }

    public UUID getUUID() {
        return uuid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPages(List<String> pages) {
        this.pages = pages;
    }
}
