package de.wbtc.mcplugin.commands;

import de.wbtc.mcplugin.Settings;
import de.wbtc.mcplugin.WBTC;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.UUID;

public class PositionCMD implements CommandExecutor {
    public static final String PERMISSION = "wbtc.position";

    private final WBTC wbtc;

    public PositionCMD(WBTC plugin) {
        this.wbtc = plugin;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage(Settings.INVALID_CONSOLE_CMD);
        }

        if (!sender.hasPermission(PERMISSION)) {
            sender.sendMessage(String.format(Settings.NO_PERMISSION, PERMISSION));
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            HashSet<UUID> friends = this.wbtc.getDbHandler().getFriendDB().getFriends(player);

            for (Player current : player.getServer().getOnlinePlayers()) {
                if (friends.contains(current.getUniqueId())) {
                    sendPosition(current, player);
                }
            }
            player.sendMessage(Settings.PLUGIN_PREFIX + ChatColor.GREEN + "Sent your position to all friends.");
        } else if (args.length == 1){
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
                + ChatColor.WHITE + "]");
    }
}
