/**
 * This document is a part of the source code and related artifacts for
 * BookRules, an open source Bukkit plugin for automatically distributing
 * written books to new players.
 *
 * http://dev.bukkit.org/server-mods/bookrules/
 * http://github.com/mstiles92/BookRules
 *
 * Copyright © 2013 Matthew Stiles (mstiles92)
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

package com.mstiles92.bookrules.localization;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import com.google.common.io.CharStreams;

/**
 * LocalizationHandler is a singleton class that handles all loading and
 * storing of localized strings used by the plugin. All accessing of these
 * strings should be done via the {@link Localization} class methods.
 * 
 * @author mstiles92
 */
public class LocalizationHandler {
	private static LocalizationHandler instance = null;
	private HashMap<String, String> strings;
	private static final String STRING_SEPERATOR = ": ";
	
	private LocalizationHandler() {
		
	}
	
	/**
	 * Get the instance of the singleton class.
	 * 
	 * @return the instance of the class
	 */
	public static LocalizationHandler instance() {
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
		
		strings = new HashMap<String, String>();
		for (String line : contents.split("\n")) {
			if (line.contains(STRING_SEPERATOR)) {
				String[] split = line.split(STRING_SEPERATOR, 2);
				strings.put(split[0], split[1]);
			}
		}
		
		return (strings.size() > 0);
	}
	
	/**
	 * Get a string in the loaded language that corresponds to key.
	 * 
	 * @param key the key to look up the string by
	 * @return the specified string in the currently loaded language, or null if 
	 * 		the string could not be found.
	 */
	public String getLocalizedString(String key) {
		return strings.get(key);
	}
}
