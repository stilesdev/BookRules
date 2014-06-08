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

package com.mstiles92.plugins.bookrules.localization;

import com.google.common.io.CharStreams;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * LocalizationHandler is a singleton class that handles all loading and
 * storing of localized strings used by the plugin. All accessing of these
 * strings should be done via the {@link Localization} class methods.
 *
 * @author mstiles92
 */
public class LocalizationHandler {
    private static LocalizationHandler instance = null;
    private JSONObject jsonObject = null;

    private LocalizationHandler() {

    }

    /**
     * Get the instance of the singleton class.
     *
     * @return the instance of the class
     */
    public static LocalizationHandler getInstance() {
        if (instance == null) {
            instance = new LocalizationHandler();
        }
        return instance;
    }

    /**
     * Load the selected language from the language file stored within the jar.
     *
     * @param language the language to load
     * @return true if loading succeeded, false if it failed
     */
    public boolean loadLocalization(Language language) {
        String contents;

        InputStream in = LocalizationHandler.class.getResourceAsStream(language.getPath());
        try {
            contents = CharStreams.toString(new InputStreamReader(in, "UTF-8"));
        } catch (IOException e) {
            return false;
        }

        JSONParser parser = new JSONParser();
        Object obj = null;

        try {
            obj = parser.parse(contents);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (obj == null) {
            return false;
        } else {
            this.jsonObject = (JSONObject) obj;
            return true;
        }
    }

    /**
     * Get a string in the loaded language that corresponds to key.
     *
     * @param key the key to look up the string by
     * @return the specified string in the currently loaded language, or null if
     * the string could not be found.
     */
    @SuppressWarnings("rawtypes")
    public String getLocalizedString(String key) {
        String[] keys = key.split("\\.");
        Object string = this.jsonObject;

        for (int i = 0; i < keys.length; i++) {
            string = (string == null) ? null : ((Map) string).get(keys[i]);
        }

        return (string == null) ? null : string.toString();
    }
}
