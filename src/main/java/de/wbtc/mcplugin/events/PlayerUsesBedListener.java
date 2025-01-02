/**
 * This class listens for the PlayerBedEnterEvent and checks if at least one player is sleeping.
 * If so, the time is set to day, storms and thunders are cleared and the time since rest is set to 0.
 *
 * @author Julian Bruyers
 */

package de.wbtc.mcplugin.events;

import de.wbtc.mcplugin.Settings;
import de.wbtc.mcplugin.WBTC;

import org.bukkit.ChatColor;
import org.bukkit.Statistic;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerBedEnterEvent;

public class PlayerUsesBedListener implements Listener {
    private static final int TICKS_TO_SLEEP = 101;
    private final WBTC wbtc;

    public PlayerUsesBedListener (WBTC plugin) {
        this.wbtc = plugin;
    }

    @EventHandler
    public void onPlayerBedEnter(PlayerBedEnterEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();
        long time = player.getWorld().getTime();

        if ((time <= 23457 && time >= 12542) || world.isThundering()) {
            player.getServer().getScheduler().scheduleSyncDelayedTask(wbtc, () -> {
                if(player.isSleeping() && player.getWorld().getEnvironment().toString().equals("NORMAL")) {
                    world.setTime(0);
                    world.setThundering(false);
                    world.setStorm(false);
                    player.setStatistic(Statistic.TIME_SINCE_REST, 0);
                    world.getPlayers().forEach(current -> executeSleep(current, player));

                    wbtc.log(player.getName() + " skipped the night for " + player.getWorld().getName());
                }
            }, TICKS_TO_SLEEP);
        }
    }

    private void executeSleep(Player current, Player player) {
        current.setStatistic(Statistic.TIME_SINCE_REST, 0);
        if (current.getUniqueId() != player.getUniqueId()) {
            current.sendMessage(Settings.PLUGIN_PREFIX
                    + ChatColor.WHITE + player.getName()
                    + ChatColor.GREEN + " skipped the night.");
        }
    }
}
