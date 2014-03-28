package com.mstiles92.plugins.bookrules.config;

import com.mstiles92.plugins.bookrules.BookRules;
import org.bukkit.configuration.file.FileConfiguration;

public class Config {
    private FileConfiguration config;

    public Config() {
        config = BookRules.getInstance().getConfig();
        config.options().copyDefaults(true);
        updateOldConfig();
        BookRules.getInstance().saveConfig();
    }

    private void updateOldConfig() {
        if (config.contains("Give-Books-On-First-Join")) {
            config.set("Give-New-Books-On-Join", config.get("Give-Books-On-First-Join"));
            config.set("Give-Books-On-First-Join", null);
        }
    }

    public boolean shouldCheckForUpdates() {
        return config.getBoolean("Check-for-Updates");
    }

    public boolean verboseOutputEnabled() {
        return config.getBoolean("Verbose");
    }

    public boolean shouldGiveBooksEveryJoin() {
        return config.getBoolean("Give-Books-Every-Join");
    }

    public boolean shouldGiveNewBooksOnJoin() {
        return config.getBoolean("Give-New-Books-On-Join");
    }

    public boolean shouldNotifyPlayers() {
        return config.getBoolean("Display-Messages");
    }

    public int getRunnableDelay() {
        return config.getInt("Seconds-Delay") * 20;
    }

    public boolean shouldBlockVillagerTrading() {
        return config.getBoolean("Block-Villager-Book-Trading");
    }
}
