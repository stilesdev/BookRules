package com.mstiles92.bookrules.lib;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

public class CraftWrittenBook implements WrittenBook {
	
	private BookMeta bookMeta;
	
	
    public CraftWrittenBook() throws Exception {
    	this(new ItemStack(Material.WRITTEN_BOOK, 1));
    }
	
	public CraftWrittenBook(String author, String title, List<String> pages) {
		this.setTitle(title);
		this.setAuthor(author);
		this.setPages(pages);
	}
	
	public CraftWrittenBook(String author, String title, String[] pages) {
		this.setTitle(title);
		this.setAuthor(author);
		this.setPages(pages);
	}
	
	public CraftWrittenBook(ItemStack itemstack) throws Exception {
		if (itemstack.getTypeId() != 387) {
			throw new Exception("Expected type id 387, got " + itemstack.getTypeId());
		}
		
		this.bookMeta = (BookMeta) itemstack.getItemMeta();
	}

	public String getAuthor() {
		return this.bookMeta.getAuthor();
	}

	public String getTitle() {
		return this.bookMeta.getTitle();
	}

	public List<String> getPages() {
		List<String> list = this.bookMeta.getPages();
		return list;
	}

	public String[] getPagesArray() {
		return (String[]) getPages().toArray();
	}

	public void setAuthor(String author) {
		if (author.length() > 16) {
			author = author.substring(0, 16);
		}
		this.bookMeta.setAuthor(author);
	}

	public void setTitle(String title) {
		if (title.length() > 16) {
			title = title.substring(0, 16);
		}
		this.bookMeta.setTitle(title);
	}
	
	@Override
	public void setPages(List<String> pages) {
		if (pages.size() > 50) {
			pages = pages.subList(0, 50);
		}
		
		bookMeta.setPages(pages);
	}
	
	@Override
	public void setPages(String[] pages) {
		if (pages.length > 50) {
			//TODO: Truncate array
		}
		
		this.bookMeta.setPages(pages);
	}

	public ItemStack getItemStack(int quantity) {
		ItemStack cis = new ItemStack(Material.WRITTEN_BOOK, quantity);
		
		cis.setItemMeta(this.bookMeta);
		
		return cis;
	}
	

}
