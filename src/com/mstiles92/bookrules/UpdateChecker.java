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

package com.mstiles92.bookrules;

import java.io.BufferedReader;
import java.io.IOException;
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
public class UpdateChecker implements Runnable {

	private final String updateAddress = "http://updates.mstiles92.com/updates/bookrules.txt";
	private final BookRulesPlugin plugin;
	
	/**
	 * The main constructor of this class
	 * 
	 * @param plugin the instance of the plugin
	 */
	public UpdateChecker(BookRulesPlugin plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public void run() {
		plugin.log("Starting UpdateChecker.");
		
		try {
			URL url = new URL(updateAddress);
			URLConnection connection = url.openConnection();
			connection.setConnectTimeout(5000);
			connection.setReadTimeout(10000);
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String version = reader.readLine();
			String changes = reader.readLine();
			plugin.log("Version found: " + version);
			plugin.log("Changes: " + changes);
			
			if (version != null) {
				plugin.latestKnownVersion = version;
				plugin.changes = changes;
				
				if (!plugin.getDescription().getVersion().equalsIgnoreCase(version)) {
					plugin.updateAvailable = true;
					
					plugin.getLogger().info("Update available! New version: " + version);
					plugin.getLogger().info("Changes in this version: " + changes);
				} else {
					plugin.log("BookRules already up to date!");
				}
				return;
			}
		} catch (IOException e) {
			
		}
		plugin.getLogger().info("Error: Unable to check for updates. Will check again later.");
	}

}
