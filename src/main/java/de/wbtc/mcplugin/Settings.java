package de.wbtc.mcplugin;

/**
 * The settings class of the WBTC plugin.
 *
 * @author Julian Bruyers
 * @version 1.0.0
 */
public final class Settings {
    public static boolean DEBUG = false;
    public static String PLUGIN_NAME = "WBTC";
    public static String VERSION = "1.0.0-BETA";
    public static String AUTHOR = "Julian Bruyers";
    public static String YEAR = "2024";
    public static String COPYRIGHT = AUTHOR + " " + "\u00A9" + " " + YEAR;
    public static String START_LOGO = "Plugin developed by " + COPYRIGHT + System.lineSeparator()
            + " __      _____ _____ ___ " + System.lineSeparator()
            + " \\ \\    / / _ )_   _/ __|" + "     Version " + VERSION + System.lineSeparator()
            + "  \\ \\/\\/ /| _ \\ | || (__ " + "     Running on Bukkit/Paper (v%s)" + System.lineSeparator()
            + "   \\_/\\_/ |___/ |_| \\___|" + System.lineSeparator();
}