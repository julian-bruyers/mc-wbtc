/**
 * Command class for the friend command.
 *
 * @author Julian Bruyers
 */

package de.wbtc.mcplugin.commands;

import de.wbtc.mcplugin.WBTC;
import de.wbtc.mcplugin.Settings;
import de.wbtc.mcplugin.db.FriendDB;
import de.wbtc.mcplugin.db.PlayerNameDB;
import de.wbtc.mcplugin.db.FriendRequestDB;
import de.wbtc.mcplugin.db.DataBaseHandler;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.ConsoleCommandSender;

import java.util.UUID;

import org.jetbrains.annotations.NotNull;

public class FriendCMD implements CommandExecutor {
    public static final String PERMISSION = "wbtc.friend";

    private static final String ADD = "add";
    private static final String REMOVE = "remove";
    private static final String LIST = "list";
    private static final String REQUESTS = "requests";
    private static final String ACCEPT = "accept";
    private static final String DENY = "deny";
    private static final String ALL = "@a";

    private final DataBaseHandler db;
    private final FriendDB friendDB;
    private final PlayerNameDB playerNameDB;
    private final FriendRequestDB friendRequestDB;

    /**
     * The constructor of the FriendCMD class.
     * @param plugin The main class of the plugin.
     */
    public FriendCMD(WBTC plugin) {
        this.db = plugin.getDbHandler();
        this.friendDB = plugin.getDbHandler().getFriendDB();
        this.playerNameDB = plugin.getDbHandler().getPlayerNameDB();
        this.friendRequestDB = plugin.getDbHandler().getFriendRequestDB();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender,
                             @NotNull Command cmd,
                             @NotNull String label,
                             @NotNull String[] args) {

        if (sender instanceof ConsoleCommandSender) {sender.sendMessage(Settings.INVALID_CONSOLE_CMD); return true; }

        Player player = (Player) sender;

        if (!player.hasPermission(PERMISSION)) {
            player.sendMessage(String.format(Settings.NO_PERMISSION, PERMISSION));
            return true;
        }

        if (args.length == 0) {
            player.sendMessage(Settings.PLUGIN_PREFIX
                    + "Use /friend add/remove/accept/deny <player> or /friend list "
                    + "or /friend requests also (@a for <player> addresses all players)");
        }

        if (args.length == 1) {
            switch (args[0]) {
                case LIST:
                    listFriends(player);
                    break;
                case REQUESTS:
                    listRequests(player);
                    break;
            }
        }

        if (args.length == 2) {
            switch (args[0]) {
                case ADD:
                    Player target = player.getServer().getPlayer(args[1]);
                    if (target == null && !args[1].equalsIgnoreCase(ALL)) {
                        player.sendMessage(Settings.PLUGIN_PREFIX + ChatColor.RED + "The player is not online.");
                    } else addFriend(target, player, args[1].equalsIgnoreCase(ALL));
                    break;
                case REMOVE:
                    removeFriend(player, args[1], args[1].equalsIgnoreCase(ALL));
                    break;
                case ACCEPT:
                    acceptRequest(player, args[1], args[1].equalsIgnoreCase(ALL));
                    break;
                case DENY:
                    denyRequest(player, args[1], args[1].equalsIgnoreCase(ALL));
                    break;
            }
        }

        return true;
    }

    private void listFriends(Player player) {
        if (friendDB.getFriends(player).isEmpty()) {
            player.sendMessage(Settings.PLUGIN_PREFIX + ChatColor.GREEN + "You currently have no friends.");
            player.sendMessage("Use /friend add <player> to add a friend.");
            return;
        }

        player.sendMessage(Settings.PLUGIN_PREFIX + ChatColor.GREEN + "Your friends:");
        friendDB.getFriends(player).forEach(friend ->
                player.sendMessage(ChatColor.WHITE + "> " + ChatColor.AQUA + playerNameDB.getName(friend)));
    }

    private void listRequests(Player player) {
        if (friendRequestDB.getFriendRequests(player).isEmpty()) {
            player.sendMessage(Settings.PLUGIN_PREFIX
                    + ChatColor.GREEN + "You currently have no open friend requests.");
            return;
        }

        //Show open friend requests to the player.
        player.sendMessage(Settings.PLUGIN_PREFIX + ChatColor.GREEN + "Your open friend requests:");
        friendRequestDB.getFriendRequests(player).forEach(requestingPlayer ->
                player.sendMessage(ChatColor.WHITE + "> "
                        + ChatColor.AQUA + playerNameDB.getName(requestingPlayer)));
        player.sendMessage("Use /friend accept <player> to accept a request.");
    }

    private void addFriend(Player target, Player player, boolean allPlayers) {
        //Send friend request to all online players
        if (allPlayers) { addAllPlayers(player); return; }

        if (player.getUniqueId().equals(target.getUniqueId())) {
            player.sendMessage(Settings.PLUGIN_PREFIX
                    + ChatColor.GREEN + "You cannot add yourself as a friend.");
            return;
        }

        //If a friend request has already been sent by the player, add the player to the friend list.
        if (friendRequestDB.getFriendRequests(player).contains(target.getUniqueId())) {
            friendRequestDB.getFriendRequests(player).remove(target.getUniqueId());
            friendDB.addFriend(player.getUniqueId(), target.getUniqueId(), player.getServer());
            player.sendMessage(Settings.PLUGIN_PREFIX + target.getName()
                                + ChatColor.GREEN + " has already sent you a friend request. You are now friends.");
            db.save();
            return;
        }

        //Otherwise send a friend request to the player.
        friendRequestDB.addFriendRequest(target.getUniqueId(), player.getUniqueId(), player.getServer());
        player.sendMessage(Settings.PLUGIN_PREFIX
                                + ChatColor.GREEN +  "Friend request has been sent to "
                                + ChatColor.WHITE + target.getName());
        db.save();
    }

    private void removeFriend(Player player, String friendName, boolean allPlayers) {
        if (allPlayers) {
            friendDB.removeAllFriends(player);
            player.sendMessage(Settings.PLUGIN_PREFIX + ChatColor.GREEN + "All your friends have been removed.");
            db.save();
            return;
        }

        friendDB.removeFriend(player.getUniqueId(), db.getPlayerNameDB().getUUID(friendName));
        player.sendMessage(Settings.PLUGIN_PREFIX + friendName
                            + ChatColor.GREEN + " has been removed from your friends list.");
        db.save();
    }

    private void acceptRequest(Player player, String friendName, boolean allPlayers) {
        //Accept all friend requests.
        if (allPlayers) {
            for (UUID current : friendRequestDB.getFriendRequests(player)) {
                friendDB.addFriend(player.getUniqueId(), current, player.getServer());
            }
            friendRequestDB.clearFriendRequests(player);
            player.sendMessage(Settings.PLUGIN_PREFIX
                                + ChatColor.GREEN + "All your open friend requests have been accepted.");
            db.save();
            return;
        }

        UUID friend = playerNameDB.getUUID(friendName);
        player.sendMessage(friendName);

        //Checks whether the player to accept the friend request from has indeed sent a friend request.
        if (friendRequestDB.getFriendRequests(player).contains(friend)) {
            friendDB.addFriend(player.getUniqueId(), friend, player.getServer());
            friendRequestDB.removeFriendRequest(player.getUniqueId(), friend);
            player.sendMessage(Settings.PLUGIN_PREFIX + friendName
                                + ChatColor.GREEN + " has been added to your friends list.");
        } else {
            player.sendMessage(Settings.PLUGIN_PREFIX + "You have no open friend request from this player.");
        }
        db.save();
    }

    private void denyRequest(Player player, String friendName, boolean allPlayers) {
        if (allPlayers) {
            friendRequestDB.clearFriendRequests(player);
            player.sendMessage(Settings.PLUGIN_PREFIX
                            + ChatColor.GREEN + "All your open friend requests have been denied.");
            db.save();
            return;
        }

        UUID friend = playerNameDB.getUUID(friendName);

        //Checks whether the player to deny the friend request from has indeed sent a friend request.
        if (friendRequestDB.getFriendRequests(player).contains(friend)) {
            friendRequestDB.removeFriendRequest(player.getUniqueId(), friend);
            player.sendMessage(Settings.PLUGIN_PREFIX + ChatColor.GREEN + "Friend request from "
                                + ChatColor.WHITE + friendName
                                + ChatColor.GREEN + " has been denied.");
        } else {
            player.sendMessage(Settings.PLUGIN_PREFIX + "You have no open friend request from this player.");
        }
        db.save();
    }

    private void addAllPlayers(Player player) {
        for (Player current : player.getServer().getOnlinePlayers()) {
            if(current.getUniqueId().equals(player.getUniqueId())) { continue; }

            //If a friend request has already been sent by the current player, add the player to the friend list.
            if (friendRequestDB.getFriendRequests(player).contains(current.getUniqueId())) {
                friendRequestDB.removeFriendRequest(player.getUniqueId(), current.getUniqueId());
                friendDB.addFriend(player.getUniqueId(), current.getUniqueId(), player.getServer());
                player.sendMessage(Settings.PLUGIN_PREFIX + current.getName()
                        + ChatColor.GREEN + " has already sent you a friend request. You are now friends.");
            } else {
                //Otherwise send a friend request to the player.
                friendRequestDB.addFriendRequest(current.getUniqueId(), player.getUniqueId(), player.getServer());
            }
        }

        player.sendMessage(Settings.PLUGIN_PREFIX
                + ChatColor.GREEN + "Friend requests have been sent to all online players.");
        db.save();
    }
}
