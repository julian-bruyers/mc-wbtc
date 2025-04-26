/**
 * Command class for the position command.
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

import java.util.UUID;
import java.util.HashSet;

import org.jetbrains.annotations.NotNull;

public class PositionCMD implements CommandExecutor {
    public static final String PERMISSION_SELF = "wbtc.position.self";
    public static final String PERMISSION_OTHER = "wbtc.position.other";

    private static final String OVERWORLD = "NORMAL";
    private static final String NETHER = "NETHER";
    private static final String END = "THE_END";

    private final WBTC wbtc;

    public PositionCMD(WBTC plugin) {
        this.wbtc = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender,
                             @NotNull Command cmd,
                             @NotNull String label,
                             @NotNull String[] args) {

        if (sender instanceof ConsoleCommandSender) { sender.sendMessage(Settings.INVALID_CONSOLE_CMD); return true; }

        if (!sender.hasPermission(PERMISSION_SELF)) {
            sender.sendMessage(String.format(Settings.NO_PERMISSION, PERMISSION_SELF));
            return true;
        }

        Player player = (Player) sender;

        switch (args.length) {
            case 0:
                HashSet<UUID> friends = this.wbtc.getDbHandler().getFriendDB().getFriends(player);
                for (Player current : player.getServer().getOnlinePlayers()) {
                    if (friends.contains(current.getUniqueId())) {sendPosition(current, player); }
                }
                player.sendMessage(Settings.PLUGIN_PREFIX + ChatColor.GREEN + "Sent your position to all friends.");
                break;
            case 1:
                if (!sender.hasPermission(PERMISSION_OTHER)) {
                    sender.sendMessage(String.format(Settings.NO_PERMISSION, PERMISSION_OTHER));
                    return true;
                }
                Player posPlayer = player.getServer().getPlayer(args[0]);
                if (posPlayer == null) {
                    player.sendMessage(Settings.PLUGIN_PREFIX + ChatColor.RED + "The player is not online.");
                    return true;
                }

                if (this.wbtc.getDbHandler().getFriendDB().isFriend(player, posPlayer)) {
                    sendPosition(player, posPlayer);
                } else {
                    player.sendMessage(Settings.PLUGIN_PREFIX
                            + ChatColor.GREEN + "You are not friends with "
                            + ChatColor.WHITE + posPlayer.getName());
                }
                break;
        }
        return true;
    }

    private void sendPosition(Player msgPlayer, Player posPlayer) {
        msgPlayer.sendMessage(Settings.PLUGIN_PREFIX
                + posPlayer.getName()
                + ChatColor.GREEN + " is at "
                + ChatColor.WHITE + "["
                + ChatColor.AQUA + posPlayer.getLocation().getBlockX()
                + ChatColor.WHITE + ", "
                + ChatColor.AQUA + posPlayer.getLocation().getBlockY()
                + ChatColor.WHITE + ", "
                + ChatColor.AQUA + posPlayer.getLocation().getBlockZ()
                + ChatColor.WHITE + "]"
                + ChatColor.GREEN + " in the "
                + ChatColor.AQUA + getDimension(posPlayer.getWorld().getEnvironment().toString()));
    }

    private String getDimension(String environment) {
        switch (environment) {
            case OVERWORLD:
                return "overworld";
            case NETHER:
                return "nether";
            case END:
                return "end";
        }
        return "matrix";
    }
}
