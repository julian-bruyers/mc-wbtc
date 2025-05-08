/**
 * The settings class of the WBTC plugin.
 *
 * @author Julian Bruyers
 */

package de.wbtc.mcplugin;

import org.bukkit.ChatColor;


/**
 * The settings class of the WBTC plugin.
 * This class contains all the settings for the plugin.
 * It is used to store the plugin name, version, author, and other settings.
 * It also contains the start logo and the plugin prefix.
 */
public final class Settings {
    public static final String PLUGIN_NAME = "WBTC";
    public static final String VERSION = "1.0.0-BETA";
    public static final String AUTHOR = "Julian Bruyers";
    public static final String YEAR = "2025";
    public static final String COPYRIGHT = AUTHOR + " " + "\u00A9" + " " + YEAR;

    public static final String START_LOGO = "Plugin initialized"                            + System.lineSeparator()
            + " __      _____ _____ ___ "                                                   + System.lineSeparator()
            + " \\ \\    / / _ )_   _/ __|"     + "     Version " + VERSION                 + System.lineSeparator()
            + "  \\ \\/\\/ /| _ \\ | || (__ "   + "     Running on Bukkit/Paper (v%s)"      + System.lineSeparator()
            + "   \\_/\\_/ |___/ |_| \\___|"    + "     Plugin developed by " + COPYRIGHT   + System.lineSeparator();

    public static final String PLUGIN_PREFIX = ChatColor.WHITE + "" + ChatColor.BOLD + "["
            + ChatColor.GOLD + ChatColor.BOLD + PLUGIN_NAME
            + ChatColor.WHITE + ChatColor.BOLD + "] " + ChatColor.RESET;

    public static final String NO_PERMISSION = ChatColor.RED + "You require the %s permission to execute this command.";

    public static final String INVALID_CONSOLE_CMD =    "["
                                                        + PLUGIN_NAME
                                                        + "] This command can only be executed by players.";


    public Settings() {
        throw new IllegalStateException("Utility class");
    }
}