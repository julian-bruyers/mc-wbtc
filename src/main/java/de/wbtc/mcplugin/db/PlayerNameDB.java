/**
 * The player name database of the plugin.
 *
 * @author Julian Bruyers
 */

package de.wbtc.mcplugin.db;

import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.HashMap;


/**
 * The PlayerNameDB class is responsible for managing the player name database.
 * It provides methods to get, set, and update player names in the database.
 */
public class PlayerNameDB {
    public static final String PLAYER_NAME_DB_FILENAME = "playerNames.wbtc";

    private HashMap<UUID, String> db;

    /**
     * Constructor for the player name database.
     * Initializes the database with an empty HashMap.
     */
    public PlayerNameDB() {
        this.db = new HashMap<>();
    }

    /**
     * Getter method for the player name database.
     * @return The player name database.
     */
    public HashMap<UUID, String> getDB() { return this.db; }

    /**
     * Setter method for the player name database.
     * @param db The new player name database.
     */
    public void setDB(HashMap<UUID, String> db) { this.db = db; }

    /**
     * Return the player name from the database.
     * @param uuid The UUID of the player.
     * @return The name of the player.
     */
    public String getName(UUID uuid) { return this.db.get(uuid); }

    /**
     * Updates the player name in the database.
     * @param player The player to update.
     */
    public void updatePlayer(Player player) {
        if (this.db.containsKey(player.getUniqueId())) {
            this.db.replace(player.getUniqueId(), player.getName());
        } else {
            this.db.put(player.getUniqueId(), player.getName());
        }
    }

    /**
     * Returns the UUID of a player from the database.
     * @param name The name of the player.
     * @return The UUID of the player.
     */
    public UUID getUUID(String name) {
        for (UUID uuid : this.db.keySet()) {
            if (this.db.get(uuid).equals(name)) {
                return uuid;
            }
        }
        return null;
    }
}
