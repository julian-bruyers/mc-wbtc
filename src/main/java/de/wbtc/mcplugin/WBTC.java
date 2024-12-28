package de.wbtc.mcplugin;

//Java
import java.util.Objects;

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
        getCommand("wbtc").setExecutor(new InfoCMD());

    }

    /**
     * Main method called, when the plugin is disabled/unloaded by the server.
     */
    @Override
    public void onDisable() {

    }
}
