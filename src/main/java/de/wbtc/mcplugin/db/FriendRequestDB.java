package de.wbtc.mcplugin.db;

import de.wbtc.mcplugin.Settings;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public class FriendRequestDB {
    public static final String FRIEND_REQUEST_DB_FILENAME = "friendRequestDB.wbtc";

    private HashMap<UUID, HashSet<UUID>> db;

    public FriendRequestDB() {
        this.db = new HashMap<>();
    }

    public HashMap<UUID, HashSet<UUID>> getDB() {
        return this.db;
    }

    public void setDB(HashMap<UUID, HashSet<UUID>> db) {
        this.db = db;
    }

    public void addFriendRequest(UUID player, UUID requestingPlayer, Server server) {
        if (!this.db.containsKey(player)) {
            this.db.put(player, new HashSet<>());
        }
        this.db.get(player).add(requestingPlayer);

        /*
         * If the player receiving the friend request is online, send them a message.
         */
        for (Player current : server.getOnlinePlayers()) {
            if (current.getUniqueId().equals(player)) {
                current.sendMessage(Settings.PLUGIN_PREFIX
                        + server.getPlayer(requestingPlayer).getDisplayName()
                        + ChatColor.GREEN + " has sent you a friend request. Use "
                        + ChatColor.WHITE + "/friend accept " + server.getPlayer(requestingPlayer).getDisplayName()
                        + ChatColor.GREEN + " to accept the request.");
            }
        }
    }

    public void removeFriendRequest(UUID player, UUID requestingPlayer) {
        if (this.db.containsKey(player)) {
            this.db.get(player).remove(requestingPlayer);
        }
    }

    public void clearFriendRequests(Player player) {
        this.db.remove(player.getUniqueId());
    }

    public HashSet<UUID> getFriendRequests(Player player) {
        if (this.db.get(player.getUniqueId()) == null) {
            return new HashSet<>();
        }
        return this.db.get(player.getUniqueId());
    }
}
