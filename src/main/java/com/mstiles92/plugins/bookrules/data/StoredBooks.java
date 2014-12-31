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

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import javax.json.*;
import javax.json.stream.JsonGenerator;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

//TODO: synchronized locks on books array when needed for background saving.
public class StoredBooks {
    private static File file;
    private static List<StoredBook> storedBooks = new ArrayList<>();
    private static List<String> storedGroups = new ArrayList<>();

    public static void init(File jsonFile) {
        file = jsonFile;
        try {
            if (file.exists()) {
                if (file.length() > 0) {
                    load();
                }
            } else {
                if (!file.createNewFile()) {
                    throw new IOException("Unable to create the new JSON file.");
                }
            }
        } catch (IOException e) {
            //TODO: Log exception correctly
            e.printStackTrace();
        }
    }

    public static void reload() {
        //TODO: handle any special cases of reloading instead of just loading from the file?
        storedBooks.clear();
        storedGroups.clear();

        load();
    }

    private static void load() {
        try {
            JsonReader reader = Json.createReader(new FileInputStream(file));
            JsonObject jsonRoot = reader.readObject();
            reader.close();

            for (JsonObject book : jsonRoot.getJsonArray("books").getValuesAs(JsonObject.class)) {
                storedBooks.add(new StoredBook(book));
            }

            PlayerData.load(jsonRoot.getJsonObject("players"));

            for (JsonString group : jsonRoot.getJsonArray("groups").getValuesAs(JsonString.class)) {
                storedGroups.add(group.getString());
            }
        } catch (IOException e) {
            //TODO: Log exception correctly
            e.printStackTrace();
        }
    }

    public static void save() {
        try {
            JsonArrayBuilder booksArrayBuilder = Json.createArrayBuilder();
            for (StoredBook book : storedBooks) {
                booksArrayBuilder.add(book.toJsonObject());
            }

            JsonArrayBuilder groupsArrayBuilder = Json.createArrayBuilder();
            for (String group : storedGroups) {
                groupsArrayBuilder.add(group);
            }

            JsonObjectBuilder rootBuilder = Json.createObjectBuilder();
            rootBuilder.add("books", booksArrayBuilder.build());
            rootBuilder.add("players", PlayerData.serialize());
            rootBuilder.add("groups", groupsArrayBuilder.build());

            Map<String, Object> config = new HashMap<>();
            config.put(JsonGenerator.PRETTY_PRINTING, true);
            JsonWriter writer = Json.createWriterFactory(config).createWriter(new FileOutputStream(file));
            writer.write(rootBuilder.build());
            writer.close();
        } catch (IOException e) {
            //TODO: Log exception correctly
            e.printStackTrace();
        }
    }

    /**
     * Get a List of all of the books stored by the plugin.
     *
     * @return a list of all of the books stored by the plugin
     */
    public static List<StoredBook> getStoredBooks() {
        return storedBooks;
    }

    /**
     * Get the stored book with the specified UUID
     *
     * @param bookUUID the UUID of the stored book to retrieve
     * @return the stored book with the given UUID, or null if there is no book with the given UUID
     */
    public static StoredBook get(UUID bookUUID) {
        for (StoredBook book : storedBooks) {
            if (book.getUUID().equals(bookUUID)) {
                return book;
            }
        }

        return null;
    }

    /**
     * Get a list of all titles of the books stored by the plugin.
     *
     * @return a list of all titles of the books stored by the plugin
     */
    public static List<String> getAllBookTitles() {
        List<String> titles = new ArrayList<>();
        for (StoredBook book : storedBooks) {
            titles.add(book.getTitle());
        }
        return titles;
    }

    /**
     * Add a book from the given ItemStack to the plugin's storage.
     *
     * @param bookItem the written book item to be added to the plugin
     */
    public static void add(ItemStack bookItem) {
        BookMeta meta = (BookMeta) bookItem.getItemMeta();
        StoredBook storedBook = new StoredBook(meta.getTitle(), meta.getAuthor(), meta.getPages());
        storedBooks.add(storedBook);
    }

    /**
     * Search for a StoredBook by the given title or number in list.
     *
     * @param query the user-provided entry, either a parsable Integer to look up by index, or a String to look up by title
     * @return the first matching StoredBook if a match was found, or null if no match was found
     */
    public static StoredBook findBook(String query) {
        int index;
        try {
            index = Integer.parseInt(query);
        } catch (NumberFormatException e) {
            index = -1;
        }

        if (index > 0 && index <= storedBooks.size()) {
            return storedBooks.get(index - 1);
        } else {
            for (StoredBook book : storedBooks) {
                if (book.getTitle().toLowerCase().startsWith(query.toLowerCase())) {
                    return book;
                }
            }

            return null;
        }
    }

    /**
     * Remove a StoredBook from the plugin.
     *
     * @param book the StoredBook to remove
     */
    public static void delete(StoredBook book) {
        storedBooks.remove(book);
    }

    /**
     * Update the contents of the currently stored book with the specified UUID.
     *
     * @param bookUUID the UUID of the book to update
     * @param newMeta the BookMeta to update the stored book with
     * @return true if the update was successful, false if it failed
     */
    public static boolean update(UUID bookUUID, BookMeta newMeta) {
        int index = -1;

        for (int i = 0; i < storedBooks.size(); i++) {
            if (storedBooks.get(i).getUUID().toString().equals(bookUUID.toString())) {
                index = i;
            }
        }

        if (index > 0) {
            StoredBook book = storedBooks.get(index);
            book.setTitle(newMeta.getTitle());
            book.setAuthor(newMeta.getAuthor());
            book.setPages(newMeta.getPages());

            return true;
        }

        return false;
    }

    /**
     * Remove a StoredBook from the plugin.
     *
     * @param bookUUID the UUID of the stored book to delete
     */
    public static void delete(UUID bookUUID) {
        for (StoredBook book : storedBooks) {
            if (book.getUUID().equals(bookUUID)) {
                storedBooks.remove(book);
                return;
            }
        }
    }
}
