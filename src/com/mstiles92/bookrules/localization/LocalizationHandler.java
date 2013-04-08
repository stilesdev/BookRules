package com.mstiles92.bookrules.localization;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import com.google.common.io.CharStreams;

/**
 * 
 * 
 * @author mstiles92
 */
public class LocalizationHandler {
	private static LocalizationHandler instance = null;
	private HashMap<String, String> strings;
	private static final String STRING_SEPERATOR = ": ";
	
	private LocalizationHandler() {
		
	}
	
	/**
	 * Get the instance of the singleton class.
	 * 
	 * @return the instance of the class
	 */
	public static LocalizationHandler instance() {
		if (instance == null) {
			instance = new LocalizationHandler();
		}
		return instance;
	}
	
	/**
	 * Load the selected language from the language file stored within the jar.
	 * 
	 * @param language the language to load
	 * @return true if loading succeeded, false if it failed
	 */
	public boolean loadLocalization(Language language) {
		String contents;
		
		InputStream in = LocalizationHandler.class.getResourceAsStream(language.getPath());
		try {
			contents = CharStreams.toString(new InputStreamReader(in, "UTF-8"));
		} catch (IOException e) {
			return false;
		}
		
		strings = new HashMap<String, String>();
		for (String line : contents.split("\n")) {
			if (line.contains(STRING_SEPERATOR)) {
				String[] split = line.split(STRING_SEPERATOR, 2);
				strings.put(split[0], split[1]);
			}
		}
		
		return (strings.size() > 0);
	}
	
	/**
	 * Get a string in the loaded language that corresponds to key.
	 * 
	 * @param key the key to look up the string by
	 * @return the specified string in the currently loaded language, or null if 
	 * 		the string could not be found.
	 */
	public String getLocalizedString(String key) {
		return strings.get(key);
	}
}
