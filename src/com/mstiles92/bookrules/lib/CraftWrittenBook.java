package com.mstiles92.bookrules.lib;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.NBTTagList;
import net.minecraft.server.NBTTagString;

import org.bukkit.Material;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class CraftWrittenBook implements WrittenBook {
	
	private NBTTagCompound nbt;
	
	public CraftWrittenBook() {
		this.nbt = new NBTTagCompound();
	}
	
	public CraftWrittenBook(String author, String title, List<String> pages) {
		this.nbt = new NBTTagCompound();
		this.setTitle(title);
		this.setAuthor(author);
		this.setPages(pages);
	}
	
	public CraftWrittenBook(String author, String title, String[] pages) {
		this.nbt = new NBTTagCompound();
		this.setTitle(title);
		this.setAuthor(author);
		this.setPages(pages);
	}
	
	public CraftWrittenBook(ItemStack itemstack) throws Exception {
		this((CraftItemStack)itemstack);
	}
	
	public CraftWrittenBook(CraftItemStack itemstack) throws Exception {
		if (itemstack.getTypeId() != 387) {
			throw new Exception("Expected type id 387, got " + itemstack.getTypeId());
		}
		
		this.nbt = itemstack.getHandle().tag;
		if (this.nbt == null) {
			this.nbt = new NBTTagCompound();
		}
	}

	@Override
	public String getAuthor() {
		return this.nbt.getString("author");
	}

	@Override
	public String getTitle() {
		return this.nbt.getString("title");
	}

	@Override
	public List<String> getPages() {
		ArrayList<String> pages = new ArrayList<String>();
		NBTTagList list = this.nbt.getList("pages");
		
		for (int i = 0; i < list.size(); i++) {
			pages.add(((NBTTagString)list.get(i)).data);
		}
		
		return pages;
	}

	@Override
	public String[] getPagesArray() {
		NBTTagList list = this.nbt.getList("pages");
		String[] pages = new String[list.size()];
		
		for (int i = 0; i < list.size(); i++) {
			pages[i] = (((NBTTagString)list.get(i)).data);
		}
		
		return pages;
	}

	@Override
	public void setAuthor(String author) {
		if (author.length() > 16) {
			author = author.substring(0, 16);
		}
		this.nbt.setString("author", author);
	}

	@Override
	public void setTitle(String title) {
		if (title.length() > 16) {
			title = title.substring(0, 16);
		}
		this.nbt.setString("title", title);
	}

	@Override
	public void setPages(List<String> pages) {
		NBTTagList list = new NBTTagList();
		for (String page : pages) {
			if (page.length() > 256) {
				page = page.substring(0, 256);
			}
			NBTTagString tagString = new NBTTagString(page);
			tagString.data = page;
			
			list.add(tagString);
		}
		this.nbt.set("pages", list);
	}

	@Override
	public void setPages(String[] pages) {
		NBTTagList list = new NBTTagList();
		for (String page : pages) {
			if (page.length() > 256) {
				page = page.substring(0, 256);
			}
			NBTTagString tagString = new NBTTagString(page);
			tagString.data = page;
			
			list.add(tagString);
		}
		this.nbt.set("pages", list);
	}

	@Override
	public ItemStack getItemStack(int quantity) {
		CraftItemStack cis = new CraftItemStack(Material.WRITTEN_BOOK, quantity);
		
		cis.getHandle().tag = this.nbt;
		
		return cis;
	}

}
