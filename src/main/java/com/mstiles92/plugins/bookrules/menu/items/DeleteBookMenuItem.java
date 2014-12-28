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

package com.mstiles92.plugins.bookrules.menu.items;

import com.mstiles92.plugins.bookrules.data.StoredBook;
import com.mstiles92.plugins.bookrules.data.StoredBooks;
import com.mstiles92.plugins.bookrules.menu.menus.ConfirmMenu;
import com.mstiles92.plugins.stileslib.menu.events.MenuClickEvent;

public class DeleteBookMenuItem extends BookMenuItem {
    public DeleteBookMenuItem(StoredBook book) {
        super(book);
    }

    @Override
    public void onClick(final MenuClickEvent event) {
        new ConfirmMenu(event.getMenu()) {
            @Override
            public void onSubmit() {
                StoredBooks.delete(getBookUUID());
                event.getPlayer().sendMessage("Book deleted!"); //TODO: refactor into localization system
            }

            @Override
            public void onCancel() {
                event.setResult(MenuClickEvent.Result.REFRESH);
            }
        }.open(event.getPlayer());
    }
}
