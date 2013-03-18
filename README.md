## Features ##
Automatically give players written books containing rules, getting started guides, etc when they join your server.

## Commands ##
<table style="border:1px solid black">
<tr><td>/rulebook</td>
<td>Show current plugin information.</td></tr>
<tr><td>/rulebook info</td>
<td>Show current plugin information.</td></tr>
<tr><td>/rulebook version</td>
<td>Show current plugin information.</td></tr>
<tr><td>/rulebook commands</td>
<td>Show description of all commands available to the player.</td></tr>
<tr><td>/rulebook reload</td>
<td>Reload data from the config files.</td></tr>
<tr><td>/rulebook get [id | title]</td>
<td>Get book specified by ID or title, or all books if no ID or title is specified.</td></tr>
<tr><td>/rulebook give &lt;player&gt; [id | title]</td>
<td>Give the specified player the book specified by ID or title, or all books if no ID or title is specified.</td></tr>
<tr><td>/rulebook add</td>
<td>Add the currently held book to the plugin.</td></tr>
<tr><td>/rulebook delete &lt;id | title&gt;</td>
<td>Delete the book specified by ID or title from the plugin.</td></tr>
<tr><td>/rulebook list</td>
<td>Show all of the books currently stored by the plugin.</td></tr>
<tr><td>/rulebook setauthor &lt;author&gt;</td>
<td>Change the author of the currently held book.</td></tr>
<tr><td>/rulebook settitle &lt;title&gt;</td>
<td>Change the title of the currently held book.</td></tr>
<tr><td>/rulebook unsign</td>
<td>Unsign the currently held book, changing it back to a book and quill.</td></tr>
</table>

## Permissions ##
<table style="border:1px solid black">
<tr><td>bookrules.*</td>
<td>Allow full access to all commands in the plugin.</td></tr>
<tr><td>bookrules.info</td>
<td>Allow use of /rulebook [info | version] to see the plugin information.</td></tr>
<tr><td>bookrules.reload</td>
<td>Allow use of /rulebook reload to reload data from file.</td></tr>
<tr><td>bookrules.receivealerts</td>
<td>Receive alerts when there is an update to the plugin.</td></tr>
<tr><td>bookrules.get</td>
<td>Allow use of /rulebook get to get the books stored by the plugin.</td></tr>
<tr><td>bookrules.give</td>
<td>Allow use of /rulebook give to give books to other players.</td></tr>
<tr><td>bookrules.add</td>
<td>Allow use of /rulebook add to add a new book to be stored by the plugin.</td></tr>
<tr><td>bookrules.delete</td>
<td>Allow use of /rulebook delete to delete a book stored by the plugin.</td></tr>
<tr><td>bookrules.list</td>
<td>Allow use of /rulebook list to list all books stored by the plugin.</td></tr>
<tr><td>bookrules.setauthor</td>
<td>Allow use of /rulebook setauthor to set the author of the currently held book.</td></tr>
<tr><td>bookrules.settitle</td>
<td>Allow use of /rulebook settitle to set the title of the currently held book.</td></tr>
<tr><td>bookrules.unsign</td>
<td>Allow use of /rulebook unsign to unsign written books, changing them back to book and quills.</td></tr>
</table>

## Configuration ##
<table style="border:1px solid black">
<tr><td>Verbose</td>
<td>Boolean value that enables/disables logging to the console. (Used for debug)</td></tr>
<tr><td>Seconds-Delay</td>
<td>Integer value that controls the delay of giving books to new players after logging in. (Useful when using other plugins that modify inventories at login, such as StarterKit and AuthMe.</td></tr>
<tr><td>Check-for-Updates</td>
<td>Boolean value that determines whether the plugin will check for updates and send alerts when there is a new version.</td></tr>
<tr><td>Give-New-Books-On-Join</td>
<td>Boolean value that determines whether players will automatically be given all books they have not yet received upon logging in to the server.</td></tr>
<tr><td>Display-Messages</td>
<td>Boolean value that determines whether messages will be sent to players when they receive new books from this plugin.</td></tr>
</table>

## Planned ##
* Optional groups for books, allowing groups to only be given to players with the correct permission
* Localization options, allow plugin to be translated to different languages

## Support and Feature Requests ##
If you have found a bug with the plugin, or would like to suggest a feature to be added, please create an issue on Github to make sure I see it. You can do this by clicking either the Tickets link at the top of the BukkitDev page or the Issues link on the Github repo, then click New Issue and provide as much information as possible. The more information you provide, the better I can help you.

## Links ##
[BukkitDev Page](http://dev.bukkit.org/server-mods/bookrules/)  
[GitHub Repository](http://github.com/mstiles92/BookRules)  
[Twitter](http://twitter.com/mstiles92)  

## Donations ##
Donations are by no means required, but would be _much_ appreciated. If you feel that my plugins have been of great use to you and would like to give me a little something in return, this is the way to do it.

[Donate](https://www.paypal.com/cgi-bin/webscr?return=http%3A%2F%2Fdev.bukkit.org%2Fserver-mods%2Fbookrules%2F&cn=Add+special+instructions+to+the+addon+author%28s%29&business=mstiles92%40gmail.com&bn=PP-DonationsBF%3Abtn_donateCC_LG.gif%3ANonHosted&cancel_return=http%3A%2F%2Fdev.bukkit.org%2Fserver-mods%2Fbookrules%2F&lc=US&item_name=BookRules+%28from+Bukkit.org%29&cmd=_donations&rm=1&no_shipping=1&currency_code=USD)

## Legal ##
Licensed under the Common Development and Distribution License Version 1.0 (CDDL-1.0).
For license information, see the [LICENSE](https://github.com/mstiles92/BookRules/blob/master/LICENSE) file, or on the web at <http://opensource.org/licenses/CDDL-1.0>