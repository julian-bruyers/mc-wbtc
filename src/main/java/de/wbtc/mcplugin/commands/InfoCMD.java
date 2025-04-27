/**
 * The InfoCMD class is a command executor for the info command.
 *
 * @author Julian Bruyers
 */

package de.wbtc.mcplugin.commands;

import de.wbtc.mcplugin.Settings;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.ConsoleCommandSender;

import org.jetbrains.annotations.NotNull;


/** * Command to display plugin information.
 *
 * The command can be used by players with the permission "wbtc.info" to display plugin information.
 */
public class InfoCMD implements CommandExecutor {
    public static final String PERMISSION = "wbtc.info";

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

        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage("[" + Settings.PLUGIN_NAME + "]"
                    + " Plugin by " + Settings.AUTHOR
                    + " is running on Version (" + Settings.VERSION + ")");
            return true;
        }

        Player player = (Player) sender;

        if (player.hasPermission(PERMISSION)) {
            player.sendMessage(Settings.PLUGIN_PREFIX
                    + "Plugin by " + Settings.AUTHOR
                    + " running on ("
                    + ChatColor.GREEN + Settings.VERSION
                    + ChatColor.WHITE + ")");
        } else {
            player.sendMessage(Settings.PLUGIN_PREFIX
                    + "Plugin by " + Settings.AUTHOR);
        }
        return true;
    }
}
