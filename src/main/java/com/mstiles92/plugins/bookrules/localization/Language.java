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

import com.mstiles92.plugins.bookrules.util.Log;

/**
 * Language is an enum used to represent all of the available languages present for the plugin.
 *
 * @author mstiles92
 */
public enum Language {

    ENGLISH("en")
    ;

    private final String path = "/com/mstiles92/plugins/bookrules/localization/";
    private final String abbreviation;

    private Language(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getFilename() {
        return abbreviation + ".json";
    }

    public String getPath() {
        return path + getFilename();
    }

    public static Language fromAbbreviation(String abbreviation) {
        for (Language l : Language.values()) {
            if (l.abbreviation.equalsIgnoreCase(abbreviation)) {
                return l;
            }
        }

        Log.warning("Language setting invalid, defaulting back to English.");
        return ENGLISH;
    }
}
