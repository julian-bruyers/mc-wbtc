/**
 * The player name database of the plugin.
 *
 * @author Julian Bruyers
 */

package de.wbtc.mcplugin.db;

import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.HashMap;

public class PlayerNameDB {
    public static final String PLAYER_NAME_DB_FILENAME = "playerNames.wbtc";

    private HashMap<UUID, String> db;

    public PlayerNameDB() {
        this.db = new HashMap<>();
    }

    public HashMap<UUID, String> getDB() { return this.db; }

    public void setDB(HashMap<UUID, String> db) { this.db = db; }

    public String getName(UUID uuid) { return this.db.get(uuid); }

    public void updatePlayer(Player player) {
        if (this.db.containsKey(player.getUniqueId())) {
            this.db.replace(player.getUniqueId(), player.getName());
        } else {
            this.db.put(player.getUniqueId(), player.getName());
        }
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
