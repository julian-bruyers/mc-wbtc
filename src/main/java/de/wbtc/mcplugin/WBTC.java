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
import de.wbtc.mcplugin.commands.EnderChestCMD;

//Event Listeners
import de.wbtc.mcplugin.events.PlayerJoinListener;

//Bukkit
import org.bukkit.plugin.java.JavaPlugin;
import de.wbtc.mcplugin.db.DataBaseHandler;


public final class WBTC extends JavaPlugin {
    private DataBaseHandler dbHandler;

    /**
     * Main method called, when the plugin is enabled/loaded by the server.
     */
    @Override
    public void onEnable() {
        this.dbHandler = new DataBaseHandler(this);

        registerCommands(this);
        registerEvents();

        displayLogoToConsole();
    }

    /**
     * Main method called, when the plugin is disabled/unloaded by the server.
     */
    @Override
    public void onDisable() {

        this.dbHandler.save();
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

    /**
     * Getter method for the database handler.
     * @return The database handler of the plugin.
     */
    public DataBaseHandler getDbHandler() {
        return this.dbHandler;
    }


    private void registerCommands(WBTC plugin) {
        try {
            getCommand("wbtc").setExecutor(new InfoCMD());
            getCommand("gm").setExecutor(new GamemodeCMD());
            getCommand("ping").setExecutor(new PingCMD(plugin));
            getCommand("friend").setExecutor(new FriendCMD(plugin));
            getCommand("position").setExecutor(new PositionCMD(plugin));
            getCommand("enderchest").setExecutor(new EnderChestCMD(plugin));
        } catch (Exception e) {
            log("Command registration failed!");
            log(e.getMessage());
        }
    }

    private void registerEvents() {
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
    }

    private void displayLogoToConsole() {
        log(String.format(Settings.START_LOGO, getServer().getBukkitVersion()));
    }
}
