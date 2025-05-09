/**
 * The database handler class for the plugin.
 * This class is responsible for handling the databases of the plugin.
 * It initializes the databases and saves the current state of the databases to the file system.
 *
 * @author Julian Bruyers
 */

package de.wbtc.mcplugin.db;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import de.wbtc.mcplugin.WBTC;

import de.wbtc.mcplugin.utils.Pair;

import java.io.File;
import java.util.UUID;
import java.util.HashSet;
import java.util.HashMap;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;


/**
 * The DataBaseHandler class is responsible for managing the databases used in the plugin.
 * It initializes the databases, loads data from files, and saves data to files.
 */
public class DataBaseHandler {
    public static final String DB_PATH = "./plugins/WBTC";

    private FriendDB friendDB;
    private FriendRequestDB friendRequestDB;
    private PlayerNameDB playerNameDB;
    private WayPointDB wayPointDB;

    private final WBTC wbtc;

    /**
     * Constructor for the database handler.
     * @param plugin The plugin instance.
     */
    public DataBaseHandler(WBTC plugin) {
        this.wbtc = plugin;
        init();
    }

    /**
     * Getter method for the friend database.
     * @return The friend database.
     */
    public FriendDB getFriendDB() { return this.friendDB; }

    /**
     * Getter method for the friend request database.
     * @return The friend request database.
     */
    public FriendRequestDB getFriendRequestDB() { return this.friendRequestDB; }

    /**
     * Getter method for the player name database.
     * @return The player name database.
     */
    public PlayerNameDB getPlayerNameDB() { return this.playerNameDB; }

    /**
     * Getter method for the waypoint database.
     * @return The waypoint database
     */
    public WayPointDB getWayPointDB() { return this.wayPointDB; }

    /**
     * Saves the current state of the databases to the file system.
     */
    public void save() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(new File(DB_PATH + "/" + FriendDB.FRIEND_DB_FILENAME)
                    , this.friendDB.getDB());

            objectMapper.writeValue(new File(DB_PATH + "/" + FriendRequestDB.FRIEND_REQUEST_DB_FILENAME)
                    , this.friendRequestDB.getDB());

            objectMapper.writeValue(new File(DB_PATH + "/" + PlayerNameDB.PLAYER_NAME_DB_FILENAME)
                    , this.playerNameDB.getDB());

            objectMapper.writeValue(new File(DB_PATH + "/" + WayPointDB.WAYPOINT_DB_FILENAME)
                    , this.wayPointDB.getDB());

            wbtc.log("Database files saved.");
        } catch (Exception e) {
            wbtc.log("Database save failed!");
            wbtc.log(e.getMessage());
        }
    }

    /**
     * Initializes the databases by reading the current state from the file system.
     */
    private void init() {
        wbtc.log("Loading databases.");

        File friendDbFile = createDBFileIfAbsent(FriendDB.FRIEND_DB_FILENAME);
        File friendRqDbFile = createDBFileIfAbsent(FriendRequestDB.FRIEND_REQUEST_DB_FILENAME);
        File playerNameDbFile = createDBFileIfAbsent(PlayerNameDB.PLAYER_NAME_DB_FILENAME);
        File wayPointDbFile = createDBFileIfAbsent(WayPointDB.WAYPOINT_DB_FILENAME);

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            this.friendDB = new FriendDB();
            this.friendRequestDB = new FriendRequestDB();
            this.playerNameDB = new PlayerNameDB();
            this.wayPointDB = new WayPointDB();

            this.friendDB.setDB(objectMapper.readValue(friendDbFile,
                    new TypeReference<HashMap<UUID, HashSet<UUID>>>() {}));

            this.friendRequestDB.setDB(objectMapper.readValue(friendRqDbFile,
                    new TypeReference<HashMap<UUID, HashSet<UUID>>>() {}));

            this.playerNameDB.setDB(objectMapper.readValue(playerNameDbFile,
                    new TypeReference<HashMap<UUID, String>>() {}));

            this.wayPointDB.setDB(objectMapper.readValue(wayPointDbFile,
                    new TypeReference<HashMap<UUID, HashMap<String, Pair<int[], String>>>>() {}));
            save();
        } catch (Exception e) {
            wbtc.log("Error while loading databases!");
            wbtc.log(e.getMessage());
        }
    }

    /**
     * Creates the database file if it does not exist.
     * @param fileName The name of the file to create.
     */
    private File createDBFileIfAbsent(String fileName) {
        File directory = new File(DB_PATH);
        File file = new File(DB_PATH + "/" + fileName);

        if (!directory.exists()) {
            wbtc.log("Database " + DB_PATH + " folder does not exists. Creating it now.");

            if (!directory.mkdir()) {
                wbtc.log("Could not create " + DB_PATH + " folder.");
            }
        }

        if (!file.exists()) {
            wbtc.log(fileName + " does not exists. Creating it now.");
            try {
                if (file.createNewFile()) {
                    //Write empty JSON object to file
                    Files.write(Paths.get(DB_PATH + "/" + fileName), Collections.singletonList("{}"));
                }
            } catch (Exception e) {
                wbtc.log("Failed to create " + fileName);
                wbtc.log(e.getMessage());
            }
        }
        return file;
    }
}
