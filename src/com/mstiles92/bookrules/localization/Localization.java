package com.mstiles92.bookrules.localization;

public class Localization {
	
	public static boolean load(Language language) {
		return LocalizationHandler.instance().loadLocalization(language);
	}
	
	public static String getString(String key) {
		return LocalizationHandler.instance().getLocalizedString(key);
	}
}
