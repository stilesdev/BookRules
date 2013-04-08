package com.mstiles92.bookrules.localization;

public enum Language {
	
	ENGLISH("en.lang"),
	GERMAN("de.lang");

	private final String path = "/com/mstiles92/bookrules/localization/";
	private final String filename;
	
	private Language(String filename) {
		this.filename = filename;
	}
	
	public String getFilename() {
		return this.filename;
	}
	
	public String getPath() {
		return this.path + this.filename;
	}
}
