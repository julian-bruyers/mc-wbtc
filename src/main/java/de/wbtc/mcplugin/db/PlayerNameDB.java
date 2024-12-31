package de.wbtc.mcplugin.db;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class PlayerNameDB {
    public static final String PLAYER_NAME_DB_FILENAME = "playerNameDB.wbtc";

    private HashMap<UUID, String> db;

    public PlayerNameDB() {
        this.db = new HashMap<>();
    }

    public HashMap<UUID, String> getDB() {
        return this.db;
    }

    public void setDB(HashMap<UUID, String> db) {
        this.db = db;
    }

    public void updatePlayer(Player player) {
        if (this.db.containsKey(player.getUniqueId())) {
            this.db.replace(player.getUniqueId(), player.getName());
        } else {
            this.db.put(player.getUniqueId(), player.getName());
        }
    }

    //TODO: REMOVE THIS METHOD
    public void updatePlayer(UUID uuid, String name) {
        if (this.db.containsKey(uuid)) {
            this.db.replace(uuid, name);
        } else {
            this.db.put(uuid, name);
        }
    }

    public String getName(UUID uuid) {
        return this.db.get(uuid);
    }

    public UUID getUUID(String name) {
        for (UUID uuid : this.db.keySet()) {
            if (this.db.get(uuid).equals(name)) {
                return uuid;
            }
        }
        return null;
    }
}
