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

public class FriendDB {
    public static final String FRIEND_DB_FILENAME = "friends.wbtc";

    private HashMap<UUID, HashSet<UUID>> db;

    public FriendDB() {
        db = new HashMap<>();
    }

    public HashMap<UUID, HashSet<UUID>> getDB() { return this.db; }

    public void setDB(HashMap<UUID, HashSet<UUID>> db) { this.db = db; }

    public void addFriend(UUID player, UUID friend, Server server) {
        if (!db.containsKey(player)) { db.put(player, new HashSet<>()); }
        if (!db.containsKey(friend)) { db.put(friend, new HashSet<>()); }
        db.get(player).add(friend);
        db.get(friend).add(player);
    }

    public void removeFriend(UUID player, UUID friend) {
        if (db.containsKey(player)) { db.get(player).remove(friend); }
        if (db.containsKey(friend)) { db.get(friend).remove(player); }
    }

    public void removeAllFriends(Player player) {
        if (db.containsKey(player.getUniqueId())) {
            for (HashSet<UUID> current : db.values()) {
                current.remove(player.getUniqueId());
            }
            db.get(player.getUniqueId()).clear();
        }
    }

    public boolean isFriend(Player player, Player friend) {
        return db.containsKey(player.getUniqueId()) && db.get(player.getUniqueId()).contains(friend.getUniqueId());
    }

    public HashSet<UUID> getFriends(Player player) {
        if (this.db.get(player.getUniqueId()) == null) {
            return new HashSet<>();
        }
        return this.db.get(player.getUniqueId());
    }
}
