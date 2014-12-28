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

import org.bukkit.entity.Player;

import javax.json.*;
import java.util.*;

public class PlayerData {
    private static Map<UUID, PlayerData> instances = new HashMap<>();

    private Set<UUID> receivedBooks;
    private String lastSeenName;

    private PlayerData(JsonObject json) {
        receivedBooks = new HashSet<>();
        JsonArray receivedJson = json.getJsonArray("receivedBooks");
        for (JsonString id : receivedJson.getValuesAs(JsonString.class)) {
            receivedBooks.add(UUID.fromString(id.getString()));
        }
        lastSeenName = json.getString("lastSeenName");
    }

    private PlayerData(Player player) {
        receivedBooks = new HashSet<>();
        lastSeenName = player.getName();
    }

    private JsonObject toJsonObject() {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        JsonArrayBuilder receivedJsonBuilder = Json.createArrayBuilder();
        for (UUID uuid : receivedBooks) {
            receivedJsonBuilder.add(uuid.toString());
        }
        builder.add("receivedBooks", receivedJsonBuilder.build());
        builder.add("lastSeenName", lastSeenName);
        return builder.build();
    }

    public static void load(JsonObject json) {
        instances.clear();

        for (Map.Entry<String, JsonValue> entry : json.entrySet()) {
            instances.put(UUID.fromString(entry.getKey()), new PlayerData((JsonObject) entry.getValue()));
        }
    }

    public static JsonObject serialize() {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        for (Map.Entry<UUID, PlayerData> entry : instances.entrySet()) {
            builder.add(entry.getKey().toString(), entry.getValue().toJsonObject());
        }
        return builder.build();
    }

    private static PlayerData create(Player player) {
        PlayerData data = new PlayerData(player);
        instances.put(player.getUniqueId(), data);
        return data;
    }

    public static PlayerData get(Player player) {
        return instances.containsKey(player.getUniqueId()) ? instances.get(player.getUniqueId()) : create(player);
    }

    public Set<UUID> getReceivedBooks() {
        return receivedBooks;
    }

    public void markBookRecieved(UUID bookUUID) {
        receivedBooks.add(bookUUID);
    }
}
