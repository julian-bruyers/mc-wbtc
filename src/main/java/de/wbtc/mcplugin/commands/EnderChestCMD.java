package de.wbtc.mcplugin.commands;

import de.wbtc.mcplugin.WBTC;
import de.wbtc.mcplugin.Settings;
import de.wbtc.mcplugin.db.DataBaseHandler;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.ConsoleCommandSender;

import org.jetbrains.annotations.NotNull;

/**
 * Command to open the ender chest of the player or another player.
 *
 * @author Julian Bruyers
 */

public class EnderChestCMD implements CommandExecutor {
    public static final String PERMISSION_SELF = "wbtc.enderchest.self";
    public static final String PERMISSION_OTHER = "wbtc.enderchest.other";

    private final DataBaseHandler db;

    /**
     * The constructor of the EnderChestCMD class.
     * @param plugin the main class of the plugin.
     */
    public EnderChestCMD(WBTC plugin) {
        this.db = plugin.getDbHandler();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender,
                             @NotNull Command cmd,
                             @NotNull String label,
                             @NotNull String[] args) {

        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage(Settings.INVALID_CONSOLE_CMD);
            return true;
        }

        Player player = (Player) sender;

        switch (args.length) {
            /*
             * Open the ender chest of the player.
             */
            case 0:
                if (player.hasPermission(PERMISSION_SELF)) {
                    player.openInventory(player.getEnderChest());
                } else {
                    player.sendMessage(String.format(Settings.NO_PERMISSION, PERMISSION_SELF));
                }
                break;

            /*
             * Open the ender chest of another player.
             */
            case 1:
                if (!player.hasPermission(PERMISSION_OTHER)) {
                    player.sendMessage(String.format(Settings.NO_PERMISSION, PERMISSION_OTHER));
                    break;
                }

                Player target = player.getServer().getPlayer(args[0]);
                if (target == null) {
                    player.sendMessage(Settings.PLUGIN_PREFIX + "The player is not online.");
                    break;
                }

                if (!db.getFriendDB().isFriend(player, target) && player.getUniqueId() != target.getUniqueId()) {
                    player.sendMessage(Settings.PLUGIN_PREFIX
                            + ChatColor.GREEN + "You can only access the ender chest of your friends. Use "
                            + ChatColor.WHITE + "/friend add " + target.getName()
                            + ChatColor.GREEN + " to add them as a friend.");
                    break;
                }
                player.openInventory(target.getEnderChest());
        }

        return true;
    }
}
