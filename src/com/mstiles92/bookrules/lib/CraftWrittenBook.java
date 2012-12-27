package com.mstiles92.bookrules.lib;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

public class CraftWrittenBook implements WrittenBook {
	
	private BookMeta meta;
	
	public CraftWrittenBook() {
		this(new ItemStack(Material.WRITTEN_BOOK));
	}
	
	public CraftWrittenBook(String author, String title, List<String> pages) {
		this.meta = (BookMeta) new ItemStack(Material.WRITTEN_BOOK).getItemMeta();
		this.setTitle(title);
		this.setAuthor(author);
		this.setPages(pages);
	}
	
	public CraftWrittenBook(String author, String title, String[] pages) {
		this.meta = (BookMeta) new ItemStack(Material.WRITTEN_BOOK).getItemMeta();
		this.setTitle(title);
		this.setAuthor(author);
		this.setPages(pages);
	}
	
	public CraftWrittenBook(ItemStack itemstack) {
		this.meta = (BookMeta) itemstack.getItemMeta();
	}

	public String getAuthor() {
		return meta.getAuthor();
	}

	public String getTitle() {
		return meta.getTitle();
	}

	public List<String> getPages() {
		return meta.getPages();
	}

	public void setAuthor(String author) {
		if (author.length() > 16) {
			author = author.substring(0, 16);
		}
		meta.setAuthor(author);
	}

	public void setTitle(String title) {
		if (title.length() > 16) {
			title = title.substring(0, 16);
		}
		meta.setTitle(title);
	}

	public void setPages(List<String> pages) {
		if (pages.size() > 50) {
			pages = pages.subList(0, 50);
		}
		
		meta.setPages(pages);
	}

	public void setPages(String[] pages) {
		if (pages.length > 50) {
			//TODO: Truncate array
		}
		
		meta.setPages(pages);
	}

	public ItemStack getItemStack(int quantity) {
		ItemStack book = new ItemStack(Material.WRITTEN_BOOK, quantity);
		book.setItemMeta(meta);
		return book;
	}

}
