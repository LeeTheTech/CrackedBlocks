package lee.code.crackedblocks.listeners;

import lee.code.crackedblocks.CrackedBlocks;
import lee.code.crackedblocks.events.CustomBlockBreakEvent;
import lee.code.crackedblocks.files.defaults.Settings;
import lee.code.crackedblocks.files.defaults.Values;
import lee.code.crackedblocks.xseries.XMaterial;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public class EntityExplodeListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onExplode(EntityExplodeEvent e) {
        CrackedBlocks plugin = CrackedBlocks.getPlugin();

        if (!e.isCancelled()) {
            //dragon check
            if (e.getEntityType() == EntityType.ENDER_DRAGON) return;

            e.blockList().removeIf(block -> plugin.getData().getBlocks().contains(block.getType()));

            Location location = e.getLocation();
            World world = location.getWorld();

            if (world != null) {
                int r = 1;
                for (int x = r * -1; x <= r; x++) {
                    for (int y = r * -1; y <= r; y++) {
                        for (int z = r * -1; z <= r; z++) {
                            Block block = world.getBlockAt(location.getBlockX() + x, location.getBlockY() + y, location.getBlockZ() + z);

                            if (plugin.getData().getBlocks().contains(block.getType())) {
                                if (block.getType() == XMaterial.BEDROCK.parseMaterial()) {
                                    if (block.getLocation().getBlockY() >= Values.DISABLED_BEDROCK_ROOF.getConfigValue() && plugin.getData().getDisabledBedrockRoofWorlds().contains(world.getName())) return;
                                    else if (block.getLocation().getBlockY() <= Values.DISABLED_BEDROCK_FLOOR.getConfigValue() && plugin.getData().getDisabledBedrockFloorWorlds().contains(world.getName())) return;
                                }
                                if (!hasWaterProtection(block)) Bukkit.getServer().getPluginManager().callEvent(new CustomBlockBreakEvent(block));
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean hasWaterProtection(Block block) {
        if (Settings.WATER_PROTECTION.getConfigValue()) {
            BlockFace[] faces = new BlockFace[]{BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST};
            for (BlockFace face : faces) {
                Block relativeBlock = block.getRelative(face);
                if (relativeBlock.getType().equals(XMaterial.WATER.parseMaterial())) return true;
            }
        }
        return false;
    }
}
