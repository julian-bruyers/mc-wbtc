package de.wbtc.mcplugin.commands;

import de.wbtc.mcplugin.Settings;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class GamemodeCMD implements CommandExecutor {
    public static final String PERMISSION = "wbtc.gamemode";
    public static final String PERMISSION_SURVIVAL = "wbtc.gamemode.survival";
    public static final String PERMISSION_CREATIVE = "wbtc.gamemode.creative";
    public static final String PERMISSION_ADVENTURE = "wbtc.gamemode.adventure";
    public static final String PERMISSION_SPECTATOR = "wbtc.gamemode.spectator";

    private static final String SURVIVAL = "0";
    private static final String CREATIVE = "1";
    private static final String ADVENTURE = "2";
    private static final String SPECTATOR = "3";

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage(Settings.INVALID_CONSOLE_CMD);
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 1) {
            switch (args[0]) {
                case SURVIVAL:
                    if (!player.hasPermission(PERMISSION_SURVIVAL)) {
                        player.sendMessage(String.format(Settings.NO_PERMISSION, PERMISSION_SURVIVAL));
                        return true;
                    }
                    player.setGameMode(org.bukkit.GameMode.SURVIVAL);
                    player.sendMessage(Settings.PLUGIN_PREFIX
                            + ChatColor.GREEN + "Your gamemode has been set to"
                            + ChatColor.AQUA + " survival");
                    break;
                case CREATIVE:
                    if (!player.hasPermission(PERMISSION_CREATIVE)) {
                        player.sendMessage(String.format(Settings.NO_PERMISSION, PERMISSION_CREATIVE));
                        return true;
                    }
                    player.setGameMode(org.bukkit.GameMode.CREATIVE);
                    player.sendMessage(Settings.PLUGIN_PREFIX
                            + ChatColor.GREEN + "Your gamemode has been set to"
                            + ChatColor.AQUA + " creative");
                    break;
                case ADVENTURE:
                    if (!player.hasPermission(PERMISSION_ADVENTURE)) {
                        player.sendMessage(String.format(Settings.NO_PERMISSION, PERMISSION_ADVENTURE));
                        return true;
                    }
                    player.setGameMode(org.bukkit.GameMode.ADVENTURE);
                    player.sendMessage(Settings.PLUGIN_PREFIX
                            + ChatColor.GREEN + "Your gamemode has been set to"
                            + ChatColor.AQUA + " adventure");
                    break;
                case SPECTATOR:
                    if (!player.hasPermission(PERMISSION_SPECTATOR)) {
                        player.sendMessage(String.format(Settings.NO_PERMISSION, PERMISSION_SPECTATOR));
                        return true;
                    }
                    player.setGameMode(org.bukkit.GameMode.SPECTATOR);
                    player.sendMessage(Settings.PLUGIN_PREFIX
                            + ChatColor.GREEN + "Your gamemode has been set to"
                            + ChatColor.AQUA + " spectator");
                    break;
                default:
                    player.sendMessage(Settings.PLUGIN_PREFIX
                            + ChatColor.GREEN + "Invalid gamemode. Please use 0, 1, 2 or 3.");
                    break;
            }
        }

        return true;
    }
}
