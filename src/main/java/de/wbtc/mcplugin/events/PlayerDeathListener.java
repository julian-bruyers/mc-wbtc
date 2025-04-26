package de.wbtc.mcplugin.events;

import de.wbtc.mcplugin.WBTC;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;


public class PlayerDeathListener implements Listener {
    private final WBTC wbtc;

    public PlayerDeathListener(WBTC plugin) {
        this.wbtc = plugin;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getPlayer();
        Block block = player.getLocation().getBlock();

        block.setType(Material.CHEST);
        Chest chest = (Chest) block.getState();

        for (ItemStack current : player.getInventory()) {
            if (current != null) {
                chest.getInventory().addItem(current);
            }
        }
    }
}
