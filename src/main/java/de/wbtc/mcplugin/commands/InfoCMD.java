package de.wbtc.mcplugin.commands;

import de.wbtc.mcplugin.Settings;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * The InfoCMD class is a command executor for the info command.
 *
 * @author Julian Bruyers
 */
public class InfoCMD implements CommandExecutor {

    /**
     * This method is called, when the info command is executed.
     *
     * For regular players without the "wbtc.info" permission, the plugin name and author will be displayed.
     * For players with the "wbtc.info" permission (and op's), the plugin name, author and version will be displayed.
     *
     * @param sender The sender of the command
     * @param cmd The command
     * @param label The label of the command
     * @param args The arguments of the command
     * @return true if the command was executed successfully
     */
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage("[" + Settings.PLUGIN_NAME + "]" + "Plugin by " + Settings.AUTHOR
                    + " is running on Version (" + Settings.VERSION + ")");
            return true;
        }


        Player player = (Player) sender;

        if (player.hasPermission("wbtc.info") || player.isOp()) {
            String message =    ChatColor.WHITE + "["
                                + ChatColor.GOLD + Settings.PLUGIN_NAME
                                + ChatColor.WHITE + "] "
                                + "Plugin by " + Settings.AUTHOR
                                + " running on ("
                                + ChatColor.GREEN + Settings.VERSION
                                + ChatColor.WHITE + ")";

            player.sendMessage(message);
        } else {
            String message =    ChatColor.WHITE+ "["
                                + ChatColor.GOLD + Settings.PLUGIN_NAME
                                + ChatColor.WHITE + "] "
                                + "Plugin by " + Settings.AUTHOR;

            player.sendMessage(message);
        }

        return true;
    }
}
