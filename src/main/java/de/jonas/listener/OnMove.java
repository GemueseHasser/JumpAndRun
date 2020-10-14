package de.jonas.listener;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.io.File;
import java.io.IOException;

public class OnMove implements Listener {

    private static int count = 0;

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();

        File file = new File("plugins/Jump_And_Run", "Players.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

        if(p.getLocation().getBlock().getType() == Material.IRON_PLATE) {
            if(p.getLocation().subtract(0.0D, 1.0D, 0.0).getBlock().getType() == Material.BEDROCK) {
                Location teleport = new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY() + 10, p.getLocation().getZ());
                p.teleport(teleport);
                p.getLocation().subtract(0.0D, 1.0D, 0.0).getBlock().setType(Material.DIAMOND_BLOCK);

                cfg.set("List." + p.getName(), true);
                try {
                    cfg.save(file);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }

        if(cfg.getBoolean("List." + p.getName(), true)) {
            if(p.getLocation().subtract(0.0D, 1.0D, 0.0).getBlock().getType() == Material.BEDROCK || p.getLocation().subtract(0.0D, 1.0D, 0.0).getBlock().getType() == Material.AIR || p.getLocation().subtract(0.0D, 1.0D, 0.0).getBlock().getType() == Material.DIAMOND_BLOCK || p.getLocation().subtract(0.0D, 1.0D, 0.0).getBlock().getType() == Material.GOLD_BLOCK) {
                if(p.getLocation().subtract(0.0D, 1.0D, 0.0).getBlock().getType() == Material.DIAMOND_BLOCK) {
                    p.getLocation().subtract(3.0D,-0.5D, 0.0).getBlock().setType(Material.BEDROCK);
                }
                if(p.getLocation().subtract(0.0D, 1.0D, 0.0).getBlock().getType() == Material.BEDROCK) {
                    p.getLocation().subtract(0.0D,-0.5D, 3.0).getBlock().setType(Material.GOLD_BLOCK);
                    p.getLocation().subtract(-3.0D,2.0D, 0.0).getBlock().setType(Material.AIR);
                }
                if(p.getLocation().subtract(0.0D, 1.0D, 0.0).getBlock().getType() == Material.GOLD_BLOCK) {
                    p.getLocation().subtract(3.0D,-0.5D, 0.0).getBlock().setType(Material.BEDROCK);
                    p.getLocation().subtract(0.0D,2.0D, -3.0).getBlock().setType(Material.AIR);
                }
                return;
            } else {
                cfg.set("List." + p.getName(), false);
                try {
                    cfg.save(file);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }
    }

}
