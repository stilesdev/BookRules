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
import com.mstiles92.plugins.stileslib.menu.events.MenuClickEvent;
import com.mstiles92.plugins.stileslib.menu.items.MenuItem;
import com.mstiles92.plugins.stileslib.menu.menus.Menu;
import org.bukkit.DyeColor;
import org.bukkit.material.Wool;

//TODO: refactor messages into localization system
public abstract class ConfirmMenu extends Menu {
    public ConfirmMenu(Menu previousMenu) {
        super(BookRules.getInstance(), "Are you sure?", 1);
        setPreviousMenu(previousMenu);

        setItem(2, new MenuItem(new Wool(DyeColor.RED).toItemStack(), "No", "Cancel") {
            @Override
            public void onClick(MenuClickEvent event) {
                onCancel();
                event.setResult(MenuClickEvent.Result.PREVIOUS);
            }
        });

        setItem(6, new MenuItem(new Wool(DyeColor.GREEN).toItemStack(), "Yes", "Submit") {
            @Override
            public void onClick(MenuClickEvent event) {
                onSubmit();
                event.setResult(MenuClickEvent.Result.CLOSE);
            }
        });
    }

    public abstract void onSubmit();

    public abstract void onCancel();
}
