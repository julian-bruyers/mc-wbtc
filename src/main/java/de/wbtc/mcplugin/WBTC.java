/**
 * The main class of the WBTC plugin.
 *
 * @author Julian Bruyers
 */

package de.wbtc.mcplugin;

//Commands
import de.wbtc.mcplugin.commands.InfoCMD;
import de.wbtc.mcplugin.commands.PingCMD;
import de.wbtc.mcplugin.commands.FriendCMD;
import de.wbtc.mcplugin.commands.GamemodeCMD;
import de.wbtc.mcplugin.commands.PositionCMD;
import de.wbtc.mcplugin.commands.WayPointCMD;
import de.wbtc.mcplugin.commands.EnderChestCMD;

//Event Listeners
import de.wbtc.mcplugin.events.PlayerJoinListener;
import de.wbtc.mcplugin.events.PlayerUsesBedListener;

//Bukkit
import org.bukkit.plugin.java.JavaPlugin;
import de.wbtc.mcplugin.db.DataBaseHandler;

import java.util.Objects;


/**
 * The main class of the WBTC plugin.
 * This class is responsible for loading and unloading the plugin.
 * It also registers the commands and events of the plugin.
 */
public final class WBTC extends JavaPlugin {
    private DataBaseHandler dbHandler;

    /**
     * Main method called, when the plugin is enabled/loaded by the server.
     */
    @Override
    public void onEnable() {
        this.dbHandler = new DataBaseHandler(this);

        registerCommands(this);
        registerEvents(this);

        displayLogoToConsole();
    }

    /**
     * Main method called, when the plugin is disabled/unloaded by the server.
     */
    @Override
    public void onDisable() {
        this.dbHandler.save();

        log("Plugin disabled! Goodbye!");
    }

    /**
     * Getter method for the database handler.
     * @return The database handler of the plugin.
     */
    public DataBaseHandler getDbHandler() {
        return this.dbHandler;
    }

    /**
     * The log method for the plugin.
     * Logs will be written to the console of the server and therefor in the server logfiles as well.
     *
     * @param msg The message to log.
     */
    public void log(String msg) {
        getLogger().info(msg);
    }


    private void registerCommands(WBTC plugin) {
        try {
            Objects.requireNonNull(getCommand("wbtc")).setExecutor(new InfoCMD());
            Objects.requireNonNull(getCommand("gm")).setExecutor(new GamemodeCMD());
            Objects.requireNonNull(getCommand("ping")).setExecutor(new PingCMD(plugin));
            Objects.requireNonNull(getCommand("friend")).setExecutor(new FriendCMD(plugin));
            Objects.requireNonNull(getCommand("waypoint")).setExecutor(new WayPointCMD(plugin));
            Objects.requireNonNull(getCommand("position")).setExecutor(new PositionCMD(plugin));
            Objects.requireNonNull(getCommand("enderchest")).setExecutor(new EnderChestCMD(plugin));

            log("Registered commands successfully!");
        } catch (Exception e) {
            log("Command registration failed!");
            log(e.getMessage());
        }
    }

    private void registerEvents(WBTC plugin) {
        try {
            getServer().getPluginManager().registerEvents(new PlayerJoinListener(plugin), plugin);
            getServer().getPluginManager().registerEvents(new PlayerUsesBedListener(plugin), plugin);

            log("Registered events successfully!");
        } catch (Exception e) {
            log("Event registration failed!");
            log(e.getMessage());
        }
    }

    private void displayLogoToConsole() {
        log(String.format(Settings.START_LOGO, getServer().getBukkitVersion()));
    }
}
