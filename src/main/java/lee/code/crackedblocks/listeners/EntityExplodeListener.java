package lee.code.crackedblocks.listeners;

import lee.code.crackedblocks.CrackedBlocks;
import lee.code.crackedblocks.events.CustomBlockBreakEvent;
import lee.code.crackedblocks.files.defaults.Settings;
import lee.code.crackedblocks.xseries.XMaterial;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

import java.util.ArrayList;

public class EntityExplodeListener implements Listener {

    @EventHandler
    public void onExplode(EntityExplodeEvent e) {

        //dragon check
        if (e.getEntityType() == EntityType.ENDER_DRAGON) return;

        //water check from location of explosion
        if (Settings.WATER_PROTECTION.getConfigValue() && e.getLocation().getBlock().getType() == XMaterial.WATER.parseMaterial()) return;

        CrackedBlocks plugin = CrackedBlocks.getPlugin();

        for (Block block : new ArrayList<Block>(e.blockList())) {
            if (plugin.getData().getBlocks().contains(block.getType())) {
                e.blockList().remove(block);
                Bukkit.getServer().getPluginManager().callEvent(new CustomBlockBreakEvent(block));
            }
        }

        Location explodePos = e.getLocation();
        int r = 1;
        for (int x = r * -1; x <= r; x++) {
            for (int y = r * -1; y <= r; y++) {
                for (int z = r * -1; z <= r; z++) {
                    Block block = explodePos.getWorld().getBlockAt(explodePos.getBlockX() + x, explodePos.getBlockY() + y, explodePos.getBlockZ() + z);

                    //check for bedrock
                    if (block.getType() == XMaterial.BEDROCK.parseMaterial()) {
                        if (block.getLocation().getBlockY() >= 127 && plugin.getData().getDisabledBedrockRoofWorlds().contains(block.getLocation().getWorld().getName())) return;
                        if (block.getLocation().getBlockY() <= 0 && plugin.getData().getDisabledBedrockFloorWorlds().contains(block.getLocation().getWorld().getName())) return;
                    }

                    if (plugin.getData().getBlocks().contains(block.getType()) && plugin.getData().getUnbreakableBlocks().contains(block.getType())) {
                        Bukkit.getServer().getPluginManager().callEvent(new CustomBlockBreakEvent(block));
                    }
                }
            }
        }
    }
}
