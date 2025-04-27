/**
 * The class for the ping command.
 *
 * @author Julian Bruyers
 */

package de.wbtc.mcplugin.commands;

import de.wbtc.mcplugin.WBTC;
import de.wbtc.mcplugin.Settings;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.ConsoleCommandSender;

import org.jetbrains.annotations.NotNull;


/**
 * The PingCMD class implements the CommandExecutor interface to handle the ping command.
 * It checks the player's ping and sends a message back to the player.
 * The command is only available for players with the specified permission.
 */
public class PingCMD implements CommandExecutor {
    public static final String PERMISSION = "wbtc.ping";

    private final WBTC wbtc;

    /**
     * Constructor for the PingCMD class.
     *
     * @param plugin The main plugin class.
     */
    public PingCMD(WBTC plugin) {
        this.wbtc = plugin;
    }

    /**
     * Executes the command.
     * @param sender The sender of the command.
     * @param cmd The command itself.
     * @param label The label of the command
     * @param args The arguments of the command
     * @return True if the command was executed successfully, false otherwise.
     */
    @Override
    public boolean onCommand(@NotNull CommandSender sender,
                             @NotNull Command cmd,
                             @NotNull String label,
                             @NotNull String[] args) {

        if (sender instanceof ConsoleCommandSender) { sender.sendMessage(Settings.INVALID_CONSOLE_CMD); return  true; }

        if (!sender.hasPermission(PERMISSION)) {
            sender.sendMessage(String.format(Settings.NO_PERMISSION, PERMISSION));
            return true;
        }

        Player player = (Player) sender;

        if (getVersion() >= 1.17) {
            player.sendMessage(Settings.PLUGIN_PREFIX
                    + ChatColor.GREEN + "Your ping is "
                    + ChatColor.WHITE + player.getPing() + "ms");
        } else {
            try {
                Object nmsPlayer = player.getClass().getMethod("getHandle").invoke(player);
                int ping = (int) nmsPlayer.getClass().getField("ping").get(nmsPlayer);

                player.sendMessage(Settings.PLUGIN_PREFIX
                        + ChatColor.GREEN + "Your ping is "
                        + ChatColor.WHITE + ping + "ms");

            } catch (Exception e) {
                wbtc.log("Gathering ping failed: " + e.getMessage());
                player.sendMessage(Settings.PLUGIN_PREFIX
                        + ChatColor.GREEN + "This command is only available for Minecraft 1.17 and above.");
            }
        }
        return true;
    }

    /**
     * Gets the version of the server.
     * @return The version of the server.
     */
    private double getVersion() {
        String version = wbtc.getServer().getMinecraftVersion();
        String[] parts = version.split("\\.");
        version = parts[0] + "." + parts[1];
        return Double.parseDouble(version);
    }
}
