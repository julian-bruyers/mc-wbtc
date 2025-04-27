/**
 * The friend database of the plugin.
 *
 * @author Julian Bruyers
 */

package de.wbtc.mcplugin.db;

import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.HashSet;
import java.util.HashMap;


/**
 * This class represents a friend database that stores the friendships between players.
 * It uses a HashMap to store the relationships, where the key is the player's UUID and the value is a HashSet of their friends' UUIDs.
 */
public class FriendDB {
    public static final String FRIEND_DB_FILENAME = "friends.wbtc";

    private HashMap<UUID, HashSet<UUID>> db;

    /**
     * Constructor for the friend database.
     * Initializes the database with an empty HashMap.
     */
    public FriendDB() {
        db = new HashMap<>();
    }

    /**
     * Getter method for the friend database.
     * @return The friend database.
     */
    public HashMap<UUID, HashSet<UUID>> getDB() { return this.db; }

    /**
     * Setter method for the friend database.
     * @param db The new friend database.
     */
    public void setDB(HashMap<UUID, HashSet<UUID>> db) { this.db = db; }

    /**
     * Adds a friend to the database.
     * @param player The player who is adding the friend.
     * @param friend The friend being added.
     * @param server The server instance.
     */
    public void addFriend(UUID player, UUID friend, Server server) {
        if (!db.containsKey(player)) { db.put(player, new HashSet<>()); }
        if (!db.containsKey(friend)) { db.put(friend, new HashSet<>()); }
        db.get(player).add(friend);
        db.get(friend).add(player);
    }

    /**
     * Removes a friend from the database.
     * @param player The player who is removing the friend.
     * @param friend The friend being removed.
     */
    public void removeFriend(UUID player, UUID friend) {
        if (db.containsKey(player)) { db.get(player).remove(friend); }
        if (db.containsKey(friend)) { db.get(friend).remove(player); }
    }

    /**
     * Removes all friends of a player from the database.
     * @param player The player whose friends are being removed.
     */
    public void removeAllFriends(Player player) {
        if (db.containsKey(player.getUniqueId())) {
            for (HashSet<UUID> current : db.values()) {
                current.remove(player.getUniqueId());
            }
            db.get(player.getUniqueId()).clear();
        }
    }

    /**
     * Checks if a player is friends with another player.
     * @param player The player who is checking.
     * @param friend The friend being checked.
     * @return True if they are friends, false otherwise.
     */
    public boolean isFriend(Player player, Player friend) {
        return db.containsKey(player.getUniqueId()) && db.get(player.getUniqueId()).contains(friend.getUniqueId());
    }

    /**
     * Retrieves the friends of a player.
     * @param player The player who is checking.
     * @return The friends of the player.
     */
    public HashSet<UUID> getFriends(Player player) {
        if (this.db.get(player.getUniqueId()) == null) {
            return new HashSet<>();
        }
        return this.db.get(player.getUniqueId());
    }
}
