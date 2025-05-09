/**
 * The waypoint database class.
 *
 * @author Julian Bruyers
 */
package de.wbtc.mcplugin.db;


import org.bukkit.entity.Player;

import de.wbtc.mcplugin.utils.Pair;

import java.util.UUID;
import java.util.HashMap;
import java.util.ArrayList;


/**
 * The waypoint database class.
 * This class is used to store waypoints for players.
 * Each player has a unique ID and a list of waypoints.
 * Each waypoint has a name and a position (x, y, z).
 */
public class WayPointDB {
    public static final String WAYPOINT_DB_FILENAME = "waypoints.wbtc";
    public static final int MAX_WAYPOINT_NAME_LENGTH = 24;

    private static final String OVERWORLD = "NORMAL";
    private static final String NETHER = "NETHER";
    private static final String END = "THE_END";

    private HashMap<UUID, HashMap<String, Pair<int[], String>>> db;


    /**
     * The constructor for the waypoint database.
     */
    public WayPointDB () {
        this.db = new HashMap<>();
    }

    /**
     * Getter method for the waypoint database.
     * @return The waypoint database.
     */
    public HashMap<UUID, HashMap<String, Pair<int[], String>>> getDB() { return this.db; }

    /**
     * Setter method for the waypoint database.
     * @param db The waypoint database.
     */
    public void setDB(HashMap<UUID, HashMap<String, Pair<int[], String>>> db) { this.db = db; }

    /**
     * Check if the player has waypoints.
     * @param player The player to check.
     * @return True if the player has waypoints, false otherwise.
     */
    public boolean playerHasWayPoints(Player player) {
        return this.db.containsKey(player.getUniqueId());
    }

    /**
     * Check if a waypoint already exists for the player.
     * @param player The player to check for.
     * @param wpName The name of the waypoint to check.
     * @return True if the player already has a waypoint with the given name, false otherwise.
     */
    public boolean wayPointExists(Player player, String wpName) {
        HashMap<String, Pair<int[], String>> wayPoints = this.db.get(player.getUniqueId());

        if (wayPoints == null) { return false; }

        return wayPoints.containsKey(wpName);
    }

    /**
     * Add a new waypoint for a given player.
     * @param player The player to add the waypoint for.
     * @param wpName The name of the waypoint to add.
     */
    public void addWayPoint(Player player, String wpName) {
        HashMap<String, Pair<int[], String>> wayPoints = this.db.get(player.getUniqueId());

        if (wayPoints == null) { wayPoints = new HashMap<>(); }

        int[] position = new int[]{ player.getLocation().getBlockX(),
                                    player.getLocation().getBlockY(),
                                    player.getLocation().getBlockZ()};

        wayPoints.put(wpName, new Pair<>(position, getDimension(player.getWorld().getEnvironment().toString())));
        this.db.put(player.getUniqueId(), wayPoints);
    }

    /**
     * Removes the waypoint with the given name for the player.
     * @param player The player to remove the waypoint for.
     * @param wpName The name of the waypoint to remove.
     */
    public void removeWayPoint(Player player, String wpName) {
        this.db.get(player.getUniqueId()).remove(wpName);
    }

    /**
     * Get the waypoint with the given name for the player.
     * @param player The player to get the waypoint for.
     * @param wpName The name of the waypoint.
     * @return A pair containing the waypoint name and its position.
     */
    public Pair<String, Pair<int[], String>> getWayPoint(Player player, String wpName) {
        HashMap<String, Pair<int[], String>> wayPoints = this.db.get(player.getUniqueId());

        return new Pair<>(wpName, wayPoints.get(wpName));
    }

    /**
     * Get all waypoint for a given player.
     * @param player The player to get the waypoints for.
     * @return An ArrayList of pairs containing the waypoint name and its position.
     */
    public ArrayList<Pair<String, Pair<int[], String>>> getAllWaypoints(Player player) {
        HashMap<String, Pair<int[], String>> wayPoints = this.db.get(player.getUniqueId());

        if (wayPoints == null) { return null; }

        ArrayList<Pair<String, Pair<int[], String>>> wayPointList = new ArrayList<>();

        for (String current : wayPoints.keySet()) {
            int[] position = wayPoints.get(current).getFirst();
            String dimension = wayPoints.get(current).getSecond();
            wayPointList.add(new Pair<>(current, new Pair<>(position, dimension)));
        }

        return wayPointList;
    }

    private String getDimension(String input) {
        switch (input) {
            case OVERWORLD:
                return "Overworld";
            case NETHER:
                return "Nether";
            case END:
                return "End";
            default:
                return input;
        }
    }
}
