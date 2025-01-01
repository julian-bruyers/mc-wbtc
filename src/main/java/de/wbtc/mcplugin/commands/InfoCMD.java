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

public class InfoCMD implements CommandExecutor {
    public static final String PERMISSION = "wbtc.info";

    @Override
    public boolean onCommand(@NotNull CommandSender sender,
                             @NotNull Command cmd,
                             @NotNull String label,
                             @NotNull String[] args) {

        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage("[" + Settings.PLUGIN_NAME + "]" + "Plugin by " + Settings.AUTHOR
                    + " is running on Version (" + Settings.VERSION + ")");
            return true;
        }

        Player player = (Player) sender;

        if (player.hasPermission(PERMISSION)) {
            player.sendMessage(ChatColor.WHITE + "["
                    + ChatColor.GOLD + Settings.PLUGIN_NAME
                    + ChatColor.WHITE + "] "
                    + "Plugin by " + Settings.AUTHOR
                    + " running on ("
                    + ChatColor.GREEN + Settings.VERSION
                    + ChatColor.WHITE + ")");
        } else {
            player.sendMessage(ChatColor.WHITE+ "["
                    + ChatColor.GOLD + Settings.PLUGIN_NAME
                    + ChatColor.WHITE + "] "
                    + "Plugin by " + Settings.AUTHOR);
        }
        return true;
    }
}
