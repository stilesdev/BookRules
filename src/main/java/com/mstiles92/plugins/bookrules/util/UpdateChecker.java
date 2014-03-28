/**
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

package com.mstiles92.plugins.bookrules.util;

import com.mstiles92.plugins.bookrules.BookRules;
import com.mstiles92.plugins.bookrules.localization.Localization;
import com.mstiles92.plugins.bookrules.localization.Strings;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * UpdateChecker is a class that checks the current version of the plugin
 * against the latest available version. If there is an update available,
 * it sets the plugin to notify staff of the update, as well as the changes
 * in the new version.
 *
 * @author mstiles92
 */
public class UpdateChecker implements Runnable { //TODO: use BukGet API instead

    private final String updateAddress = "http://updates.mstiles92.com/updates/bookrules.txt";
    private final BookRules plugin;

    /**
     * The main constructor of this class
     */
    public UpdateChecker() {
        this.plugin = BookRules.getInstance(); //TODO: Factor out stored instance
    }

    @Override
    public void run() {
        plugin.log(Localization.getString(Strings.UPDATECHECKER_STARTED));

        try {
            URL url = new URL(updateAddress);
            URLConnection connection = url.openConnection();
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(10000);
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String version = reader.readLine();
            String changes = reader.readLine();
            plugin.log(String.format(Localization.getString(Strings.UPDATECHECKER_VERSION_FOUND), version));
            plugin.log(String.format(Localization.getString(Strings.UPDATECHECKER_CHANGES_FOUND), changes));

            if (version != null && isNewerVersion(version)) {
                plugin.latestKnownVersion = version;
                plugin.changes = changes;
                plugin.updateAvailable = true;

                plugin.getLogger().info(Localization.getString(Strings.UPDATE_AVAILIBLE));
                plugin.getLogger().info(String.format(Localization.getString(Strings.UPDATE_VERSION_INFO), plugin.getDescription().getVersion(), version));
                plugin.getLogger().info(String.format(Localization.getString(Strings.UPDATE_CHANGES), changes));
            } else {
                plugin.log(Localization.getString(Strings.PLUGIN_UP_TO_DATE));
            }
        } catch (Exception e) {
            plugin.getLogger().info(Localization.getString(Strings.UPDATE_CHECK_FAILURE));
        }
    }

    /**
     * Provide simple natural order comparison for version numbers (ie. 2.9 is less than 2.10)
     *
     * @param newVersion the new found version
     * @return true if the provided version is newer, false otherwise
     */
    private boolean isNewerVersion(String newVersion) {
        String oldVersion = plugin.getDescription().getVersion();
        if (oldVersion.equals(newVersion)) {
            return false;
        }

        oldVersion = oldVersion.split("-pre")[0];

        String[] oldSplit = oldVersion.split("\\.");
        String[] newSplit = newVersion.split("\\.");
        int newInt;
        int oldInt;

        for (int i = 0; i < oldSplit.length || i < newSplit.length; i++) {
            if (i >= newSplit.length) {
                newInt = 0;
            } else {
                newInt = Integer.parseInt(newSplit[i]);
            }

            if (i >= oldSplit.length) {
                oldInt = 0;
            } else {
                oldInt = Integer.parseInt(oldSplit[i]);
            }

            if (newInt == oldInt) {
                continue;
            } else {
                return (newInt > oldInt);
            }
        }

        return false;
    }
}
