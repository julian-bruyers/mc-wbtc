package de.wbtc.mcplugin;

import org.bukkit.ChatColor;

/**
 * The settings class of the WBTC plugin.
 *
 * @author Julian Bruyers
 * @version 1.0.0
 */
public final class Settings {
    public static final boolean DEBUG = false;
    public static final String PLUGIN_NAME = "WBTC";
    public static final String VERSION = "1.0.0-BETA";
    public static final String AUTHOR = "Julian Bruyers";
    public static final String YEAR = "2024";
    public static final String COPYRIGHT = AUTHOR + " " + "\u00A9" + " " + YEAR;

    public static final String START_LOGO = "Plugin developed by " + COPYRIGHT + System.lineSeparator()
            + " __      _____ _____ ___ " + System.lineSeparator()
            + " \\ \\    / / _ )_   _/ __|" + "     Version " + VERSION + System.lineSeparator()
            + "  \\ \\/\\/ /| _ \\ | || (__ " + "     Running on Bukkit/Paper (v%s)" + System.lineSeparator()
            + "   \\_/\\_/ |___/ |_| \\___|" + System.lineSeparator();

    public static final String PLUGIN_PREFIX = ChatColor.WHITE + "["
            + ChatColor.GOLD + PLUGIN_NAME
            + ChatColor.WHITE + "] ";

    public static final String NO_PERMISSION = ChatColor.RED + "You require the %s permission to execute this command.";

}