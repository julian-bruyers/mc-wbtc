/**
 * The friend request database of the plugin.
 *
 * @author Julian Bruyers
 */

package de.wbtc.mcplugin.db;

import org.bukkit.Server;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import de.wbtc.mcplugin.Settings;

import java.util.UUID;
import java.util.HashSet;
import java.util.HashMap;


/**
 * The FriendRequestDB class manages friend requests between players.
 */
public class FriendRequestDB {
    public static final String FRIEND_REQUEST_DB_FILENAME = "friendRequests.wbtc";

    private HashMap<UUID, HashSet<UUID>> db;

    /**
     * Constructor for the friend request database.
     * Initializes the database with an empty HashMap.
     */
    public FriendRequestDB() {
        this.db = new HashMap<>();
    }

    /**
     * Getter method for the friend request database.
     * @return The friend request database.
     */
    public HashMap<UUID, HashSet<UUID>> getDB() { return this.db; }

    /**
     * Setter method for the friend request database.
     * @param db The new friend request database.
     */
    public void setDB(HashMap<UUID, HashSet<UUID>> db) { this.db = db; }

    /**
     * Adds a friend request to the database.
     * @param player The player who is receiving the friend request.
     * @param requestingPlayer The player who is sending the friend request.
     * @param server The server instance.
     */
    public void addFriendRequest(UUID player, UUID requestingPlayer, Server server) {
        if (!this.db.containsKey(player)) {
            this.db.put(player, new HashSet<>());
        }
        this.db.get(player).add(requestingPlayer);

        //If the player receiving the friend request is online, send them a message.
        for (Player current : server.getOnlinePlayers()) {
            if (current.getUniqueId().equals(player)) {
                current.sendMessage(Settings.PLUGIN_PREFIX
                        // getDisplayName() will not return a null pointer as it is checked beforehand
                        // that the requestingPlayer is online.
                        + server.getPlayer(requestingPlayer).getDisplayName()
                        + ChatColor.GREEN + " has sent you a friend request.");

                current.sendMessage("Use /friend accept "
                        + ChatColor.AQUA + server.getPlayer(requestingPlayer).getDisplayName()
                        + ChatColor.WHITE + " to accept the request.");
            }
        }
    }

    /**
     * Removes a friend request from the database.
     * @param player The player who is receiving the friend request.
     * @param requestingPlayer The player who is sending the friend request.
     */
    public void removeFriendRequest(UUID player, UUID requestingPlayer) {
        if (this.db.containsKey(player)) { this.db.get(player).remove(requestingPlayer); }
    }

    /**
     * Removes all friend requests of a player from the database.
     * @param player The player whose friend requests are being removed.
     */
    public void clearFriendRequests(Player player) {
        this.db.remove(player.getUniqueId());
    }

    /**
     * Retrieves the friend requests of a player.
     * @param player The player who is checking.
     * @return A HashSet of UUIDs representing the friend requests.
     */
    public HashSet<UUID> getFriendRequests(Player player) {
        if (this.db.get(player.getUniqueId()) == null) {
            return new HashSet<>();
        }
        return this.db.get(player.getUniqueId());
    }
}
