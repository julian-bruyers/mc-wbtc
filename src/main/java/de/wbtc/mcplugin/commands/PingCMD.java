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

public class PingCMD implements CommandExecutor {
    public static final String PERMISSION = "wbtc.ping";

    private final WBTC wbtc;

    public PingCMD(WBTC plugin) {
        this.wbtc = plugin;
    }

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
                    + ChatColor.GREEN + "Your ping is: "
                    + ChatColor.WHITE + player.getPing() + "ms");
        } else {
            try {
                Object nmsPlayer = player.getClass().getMethod("getHandle").invoke(player);
                int ping = (int) nmsPlayer.getClass().getField("ping").get(nmsPlayer);

                player.sendMessage(Settings.PLUGIN_PREFIX
                        + ChatColor.GREEN + "Your ping is: "
                        + ChatColor.WHITE + ping + "ms");

            } catch (Exception e) {
                wbtc.log("Gathering ping failed: " + e.getMessage());
                player.sendMessage(Settings.PLUGIN_PREFIX
                        + ChatColor.GREEN + "This command is only available for Minecraft 1.17 and above.");
            }
        }
        return true;
    }

    private double getVersion() {
        String version = wbtc.getServer().getMinecraftVersion();
        String[] parts = version.split("\\.");
        version = parts[0] + "." + parts[1];
        return Double.parseDouble(version);
    }
}
