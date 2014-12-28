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

package com.mstiles92.plugins.bookrules.menu.menus;

import com.mstiles92.plugins.bookrules.BookRules;
import com.mstiles92.plugins.bookrules.data.StoredBooks;
import com.mstiles92.plugins.bookrules.util.BookUtils;
import com.mstiles92.plugins.stileslib.menu.menus.ListMenu;
import org.bukkit.entity.Player;

public class DeleteMenu extends ListMenu {
    private Player player;

    public DeleteMenu(Player player) {
        super(BookRules.getInstance(), "BookRules Delete Menu", BookUtils.filterListAsDeleteMenuItems(StoredBooks.getStoredBooks(), player));
        this.player = player;
    }

    public void open() {
        super.open(player);
    }
}
