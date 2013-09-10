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

/**
 * Localization is a class that provides easy access to all of the localized
 * strings for use by the plugin.
 * 
 * @author mstiles92
 */
public class Localization {
	
	/**
	 * Load the localization system with the specified language.
	 * 
	 * @param language the Language to load
	 * @return true if loading succeeded, false if it failed
	 */
	public static boolean load(Language language) {
		return LocalizationHandler.instance().loadLocalization(language);
	}
	
	/**
	 * Get a string in the loaded language that corresponds to key.
	 * 
	 * @param key the key to look up the string by
	 * @return the specified string in the currently loaded language, or null if 
	 * 		the string could not be found.
	 */
	public static String getString(String key) {
		return LocalizationHandler.instance().getLocalizedString(key);
	}
}
