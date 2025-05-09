/**
 * The waypoint command class.
 *
 * @author Julian Bruyers
 */
package de.wbtc.mcplugin.commands;


import de.wbtc.mcplugin.WBTC;
import de.wbtc.mcplugin.Settings;
import de.wbtc.mcplugin.db.DataBaseHandler;
import de.wbtc.mcplugin.utils.Pair;
import de.wbtc.mcplugin.db.WayPointDB;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.ConsoleCommandSender;

import java.util.ArrayList;

import org.jetbrains.annotations.NotNull;


/**
 * The WayPointCMD class implements the command for managing waypoints.
 * It allows players to add, remove, show, and list their waypoints.
 */
public class WayPointCMD implements CommandExecutor {
    public static final String PERMISSION = "wbtc.waypoint";

    private final DataBaseHandler db;
    private final WayPointDB wayPointDB;

    /**
     * The constructor for the waypoint command.
     * @param plugin The WBTC plugin instance.
     */
    public WayPointCMD (WBTC plugin) {
        this.db = plugin.getDbHandler();
        this.wayPointDB = this.db.getWayPointDB();
    }

    /**
     * The method that is called when the command is executed.
     * @param sender The command sender.
     * @param cmd The command.
     * @param label The command label.
     * @param args The command arguments.
     * @return True if the command was executed successfully, false otherwise.
     */
    @Override
    public boolean onCommand(@NotNull CommandSender sender,
                             @NotNull org.bukkit.command.Command cmd,
                             @NotNull String label,
                             @NotNull String[] args) {

        if (sender instanceof ConsoleCommandSender) { sender.sendMessage(Settings.INVALID_CONSOLE_CMD); return  true; }

        if (!sender.hasPermission(PERMISSION)){
            sender.sendMessage(String.format(Settings.NO_PERMISSION, PERMISSION));
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 1 && args[0].equals("list")) {
            listAllWaypoints(player);
        } else if (args.length == 2) {
            switch (args[0]) {
                case "add":
                    addWayPoint(player, args[1]);
                    break;
                case "remove":
                    removeWayPoint(player, args[1]);
                    break;
                case "show":
                    showWayPoint(player, args[1]);
                    break;
            }
        }

        return true;
    }

    /**
     * Converts a position from an int[3] array into a string.
     * @param pos The position to convert.
     * @return The converted string.
     */
    private String getPositionString(int[] pos) {
        return ChatColor.WHITE + "["
                + ChatColor.AQUA + pos[0]
                + ChatColor.WHITE + ", "
                + ChatColor.AQUA + pos[1]
                + ChatColor.WHITE + ", "
                + ChatColor.AQUA + pos[2]
                + ChatColor.WHITE + "]";
    }

    /**
     * Lists all waypoints of the player.
     * @param player The player to list the waypoints for.
     */
    private void listAllWaypoints(Player player) {
        if (!this.wayPointDB.playerHasWayPoints(player)) {
            player.sendMessage(Settings.PLUGIN_PREFIX +
                    ChatColor.GREEN + "You have no waypoints yet.");
            return;
        }

        ArrayList<Pair<String, Pair<int[], String>>> wayPoints= this.wayPointDB.getAllWaypoints(player);

        player.sendMessage(Settings.PLUGIN_PREFIX + ChatColor.GREEN + "Your waypoints: " + ChatColor.WHITE);

        if (wayPoints.isEmpty()) {
            player.sendMessage(ChatColor.WHITE + "> You have no waypoints yet.");
            return;
        }

        for (Pair<String, Pair<int[], String>> current : wayPoints) {
            player.sendMessage(ChatColor.WHITE + "> " +
                    ChatColor.GREEN + current.getFirst() + " " +
                    getPositionString(current.getSecond().getFirst()) +
                    " in the " + current.getSecond().getSecond());
        }
    }

    /**
     * Adds a waypoint for the player.
     * @param player The player to add the waypoint for.
     * @param wpName The name of the waypoint to add.
     */
    private void addWayPoint(Player player, String wpName) {
        if (this.wayPointDB.wayPointExists(player, wpName)) {
            player.sendMessage(Settings.PLUGIN_PREFIX +
                    ChatColor.WHITE + "A waypoint with the name already exists.");
            return;
        }

        if (wpName.length() > WayPointDB.MAX_WAYPOINT_NAME_LENGTH) {
            player.sendMessage(Settings.PLUGIN_PREFIX +
                    ChatColor.WHITE + "The waypoint name must not exceed "
                    + WayPointDB.MAX_WAYPOINT_NAME_LENGTH + " characters.");
            return;
        }

        this.wayPointDB.addWayPoint(player, wpName);
        player.sendMessage(Settings.PLUGIN_PREFIX +
                ChatColor.GREEN + "Added waypoint " +
                ChatColor.WHITE + wpName + " " +
                getPositionString(new int[] {
                        player.getLocation().getBlockX(),
                        player.getLocation().getBlockY(),
                        player.getLocation().getBlockZ()
                })
                + " in the " + this.wayPointDB.getWayPoint(player, wpName).getSecond().getSecond());

        if (Settings.DB_ALWAYS_SAVE_MODE) db.save();
    }

    /**
     * Removes a waypoint for a player.
     * @param player The player to remove the waypoint for.
     * @param wpName The name of the waypoint to remove.
     */
    private void removeWayPoint(Player player, String wpName) {
        if (!this.wayPointDB.playerHasWayPoints(player)) {
            player.sendMessage(Settings.PLUGIN_PREFIX +
                    ChatColor.WHITE + "You have no waypoints yet.");
            return;
        }

        if (!this.wayPointDB.wayPointExists(player, wpName)) {
            player.sendMessage(Settings.PLUGIN_PREFIX +
                    ChatColor.WHITE + "The waypoint does not exist.");
            return;
        }

        this.wayPointDB.removeWayPoint(player, wpName);
        player.sendMessage(Settings.PLUGIN_PREFIX +
                ChatColor.GREEN + "Removed waypoint " +
                ChatColor.WHITE + wpName);

        if (Settings.DB_ALWAYS_SAVE_MODE) db.save();
    }

    /**
     * Shows a waypoint for a player.
     * @param player The player to show the waypoint for.
     * @param wpName The name of the waypoint to show.
     */
    private void showWayPoint(Player player, String wpName) {
        if (!this.wayPointDB.playerHasWayPoints(player)) {
            player.sendMessage(Settings.PLUGIN_PREFIX +
                    ChatColor.WHITE + "You have no waypoints yet.");
            return;
        }
        if (!this.wayPointDB.wayPointExists(player, wpName)) {
            player.sendMessage(Settings.PLUGIN_PREFIX +
                    ChatColor.WHITE + "The waypoint does not exist.");
            return;
        }

        int[] pos = this.wayPointDB.getWayPoint(player, wpName).getSecond().getFirst();
        String dimension = this.wayPointDB.getWayPoint(player, wpName).getSecond().getSecond();
        player.sendMessage(Settings.PLUGIN_PREFIX +
                ChatColor.GREEN + "The waypoint is at " +
                getPositionString(pos) +
                ChatColor.WHITE + " in the " + dimension);

        if (Settings.DB_ALWAYS_SAVE_MODE) db.save();
    }
}
