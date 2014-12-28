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

package com.mstiles92.plugins.bookrules.util;

import com.comphenix.attribute.Attributes;
import com.comphenix.attribute.Attributes.Attribute;
import com.comphenix.attribute.Attributes.AttributeType;
import com.comphenix.attribute.Attributes.Operation;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class AttributeWrapper {
    private static final UUID id = UUID.fromString("1902e1ee-3df7-484d-9a33-7117ded726de");
    private ItemStack itemStack;

    private AttributeWrapper(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public static AttributeWrapper newWrapper(ItemStack itemStack) {
        return new AttributeWrapper(itemStack);
    }

    public void setData(String data) {
        Attributes attributes = new Attributes(itemStack);
        Attribute attr = getAttribute(attributes);

        if (attr == null) {
            attributes.add(Attribute.newBuilder().name(data).amount(0).uuid(id).operation(Operation.ADD_NUMBER).type(AttributeType.GENERIC_ATTACK_DAMAGE).build());
        } else {
            attr.setName(data);
        }

        itemStack = attributes.getStack();
    }

    public String getData() {
        Attribute attr = getAttribute(new Attributes(itemStack));

        return (attr == null) ? null : attr.getName();
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    private Attribute getAttribute(Attributes attributes) {
        for (Attribute attr : attributes.values()) {
            if (attr.getUUID().equals(id)) {
                return attr;
            }
        }
        return null;
    }
}
