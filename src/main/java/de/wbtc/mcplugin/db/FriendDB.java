package de.wbtc.mcplugin.db;

import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public class FriendDB {
    public static final String FRIEND_DB_FILENAME = "friendDB.wbtc";

    private HashMap<UUID, HashSet<UUID>> db;

    public FriendDB() {
        db = new HashMap<>();
    }

    public HashMap<UUID, HashSet<UUID>> getDB() {
        return this.db;
    }

    public void setDB(HashMap<UUID, HashSet<UUID>> db) {
        this.db = db;
    }

    public void addFriend(Player player, Player friend) {
        UUID player1 = player.getUniqueId();
        UUID player2 = friend.getUniqueId();

        if (!db.containsKey(player1)) {
            db.put(player1, new HashSet<>());
        }

        if (!db.containsKey(player2)) {
            db.put(player2, new HashSet<>());
        }

        db.get(player1).add(player2);
        db.get(player2).add(player1);
    }

    public void addFriend(UUID player, UUID friend, Server server) {
        if (!db.containsKey(player)) {
            db.put(player, new HashSet<>());
        }

        if (!db.containsKey(friend)) {
            db.put(friend, new HashSet<>());
        }

        db.get(player).add(friend);
        db.get(friend).add(player);
    }

    public void removeFriend(UUID player, UUID friend) {
        if (db.containsKey(player)) {
            db.get(player).remove(friend);
        }

        if (db.containsKey(friend)) {
            db.get(friend).remove(player);
        }
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
        UUID player1 = player.getUniqueId();
        UUID player2 = friend.getUniqueId();

        return db.containsKey(player1) && db.get(player1).contains(player2);
    }

    public HashSet<UUID> getFriends(Player player) {
        if (this.db.get(player.getUniqueId()) == null) {
            return new HashSet<>();
        }
        return this.db.get(player.getUniqueId());
    }
}
