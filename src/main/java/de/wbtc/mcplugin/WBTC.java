package de.wbtc.mcplugin;

//Commands
import de.wbtc.mcplugin.commands.InfoCMD;

//Bukkit
import org.bukkit.plugin.java.JavaPlugin;


/**
 * The main class of the WBTC plugin.
 *
 * @author Julian Bruyers
 */
public final class WBTC extends JavaPlugin {

    /**
     * Main method called, when the plugin is enabled/loaded by the server.
     */
    @Override
    public void onEnable() {
        registerCommands();
        registerEvents();

        logStart();
    }

    /**
     * Main method called, when the plugin is disabled/unloaded by the server.
     */
    @Override
    public void onDisable() {

    }

    private void registerCommands() {
        getCommand("wbtc").setExecutor(new InfoCMD());
    }

    private void registerEvents() {

    }

    private void logStart() {
        getLogger().info(String.format(Settings.START_LOGO, getServer().getBukkitVersion()));
    }
}
