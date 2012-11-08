package com.mstiles92.bookrules.lib;

public interface WrittenBook {

	/**
	 * Gets the author for this written book
	 * @return This book's author
	 */
	public String getAuthor();
	
	/**
	 * Gets the title for this written book
	 * @return This book's title
	 */
	public String getTitle();
	
	/**
	 * Gets a list of the pages of this written book
	 * @return The List of pages in this book
	 */
	public java.util.List<String> getPages();
	
	/**
	 * Gets an array of the pages of this written book
	 * @return The String array of pages in this book
	 */
	public String[] getPagesArray();
	
	/**
	 * Sets the author of this written book, truncated to 16 characters if necessary
	 * @param author The String to set as the author of this book
	 */
	public void setAuthor(String author);
	
	/**
	 * Sets the title of this written book, truncated to 16 characters if necessary
	 * @param title The String to set as the title of this book
	 */
	public void setTitle(String title);
	
	/**
	 * Sets the pages of this written book
	 * @param pages A List of strings to set as pages of this book
	 */
	public void setPages(java.util.List<String> pages);
	
    /**
     * Sets the pages of this written book
     * @param pages An array of strings to set as pages of this book
     */
	public void setPages(String[] pages);
	
    /**
     * Gets an ItemStack object of this book
     * @param quantity The number of books in the stack
     * @return The ItemStack object representing this book
     */
	public org.bukkit.inventory.ItemStack getItemStack(int quantity);
}
