/**
 * This document is a part of the source code and related artifacts for
 * BookRules, an open source Bukkit plugin for automatically distributing
 * written books to new players.
 *
 * http://dev.bukkit.org/server-mods/plugins/
 * http://github.com/mstiles92/BookRules
 *
 * Copyright � 2013 Matthew Stiles (mstiles92)
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

/**
 * Language is an enum used to represent all of the available languages present
 * for the plugin.
 * 
 * @author mstiles92
 */
public enum Language {
	
	ENGLISH("en.json"),
	//GERMAN("de.json") - not yet translated
	;

	private final String path = "/com/mstiles92/plugins/bookrules/localization/";
	private final String filename;
	
	private Language(String filename) {
		this.filename = filename;
	}
	
	public String getFilename() {
		return this.filename;
	}
	
	public String getPath() {
		return this.path + this.filename;
	}
}
