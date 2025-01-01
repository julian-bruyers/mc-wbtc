/**
 * The PlayerJoinListener class listens.
 *
 * @author Julian Bruyers
 */

package de.wbtc.mcplugin.events;

import de.wbtc.mcplugin.WBTC;
import de.wbtc.mcplugin.Settings;
import de.wbtc.mcplugin.db.DataBaseHandler;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    private final WBTC wbtc;
    private final DataBaseHandler db;

    public PlayerJoinListener(WBTC plugin) {
        this.wbtc = plugin;
        this.db = plugin.getDbHandler();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        //Update the player name in the database.
        updatePlayerNameDB(event.getPlayer());

        //Show open friend requests to the player.
        showOpenRequests(event.getPlayer());
    }

    private void updatePlayerNameDB(Player player) {
        db.getPlayerNameDB().updatePlayer(player);
        wbtc.log("Updated player name in database: " + player.getName());
        db.save();
    }

    private void showOpenRequests(Player player) {
        if (this.db.getFriendRequestDB().getFriendRequests(player).isEmpty()) { return; }

        player.sendMessage(Settings.PLUGIN_PREFIX + "Your open friend requests:");
        this.db.getFriendRequestDB().getFriendRequests(player).forEach(requestingPlayer ->
                player.sendMessage(ChatColor.WHITE + "> "
                        + ChatColor.AQUA + this.db.getPlayerNameDB().getName(requestingPlayer)));

        player.sendMessage("Use /friend accept <player> to accept a request.");
    }
}

