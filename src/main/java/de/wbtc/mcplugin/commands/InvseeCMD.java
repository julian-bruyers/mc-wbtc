/**
 * Command class for the invsee command
 *
 * @author Julian Bruyers
 */

package de.wbtc.mcplugin.commands;

import de.wbtc.mcplugin.Settings;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.ConsoleCommandSender;

import org.jetbrains.annotations.NotNull;


/**
 * Command to open the inventory of the player or another player.
 */
public class InvseeCMD implements CommandExecutor {
    public static final String PERMISSION = "wbtc.invsee";

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

        if (args.length == 1) {
            Player player = (Player) sender;
            Player target = Bukkit.getPlayer(args[0]);

            if (target == null || player == target) {
                sender.sendMessage(Settings.PLUGIN_PREFIX
                        + "The player "
                        + ChatColor.AQUA
                        + args[0]
                        + ChatColor.WHITE
                        + " is not online.");
                return true;
            }

            player.openInventory(target.getInventory()).setTitle("Inventory of " + target.getName());
        }

        return true;
    }
}
