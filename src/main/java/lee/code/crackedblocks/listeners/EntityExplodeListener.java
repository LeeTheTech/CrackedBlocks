package lee.code.crackedblocks.listeners;

import lee.code.crackedblocks.CrackedBlocks;
import lee.code.crackedblocks.Data;
import lee.code.crackedblocks.events.CustomBlockBreakEvent;
import lee.code.crackedblocks.files.file.FileConfig;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
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
        Data data = plugin.getData();
        if (!e.isCancelled()) {
            if (e.getEntityType() == EntityType.ENDER_DRAGON) return;
            e.blockList().removeIf(block -> data.getBlocks().contains(block.getType()));

            Location location = e.getLocation();
            World world = location.getWorld();

            if (world != null) {
                int r = 1;
                for (int x = r * -1; x <= r; x++) {
                    for (int y = r * -1; y <= r; y++) {
                        for (int z = r * -1; z <= r; z++) {
                            Block block = world.getBlockAt(location.getBlockX() + x, location.getBlockY() + y, location.getBlockZ() + z);
                            if (data.getBlocks().contains(block.getType())) {
                                if (block.getType() == Material.BEDROCK) {
                                    if (block.getLocation().getBlockY() >= FileConfig.DISABLED_BEDROCK_ROOF_Y.getInt() && FileConfig.DISABLED_BEDROCK_ROOF_WORLDS.getStringList().contains(world.getName())) return;
                                    else if (block.getLocation().getBlockY() <= FileConfig.DISABLED_BEDROCK_FLOOR_Y.getInt() &&FileConfig.DISABLED_BEDROCK_FLOOR_WORLDS.getStringList().contains(world.getName())) return;
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
        if (FileConfig.WATER_PROTECTION.getBoolean()) {
            BlockFace[] faces = new BlockFace[]{BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST, BlockFace.UP, BlockFace.DOWN};
            for (BlockFace face : faces) {
                Block relativeBlock = block.getRelative(face);
                if (relativeBlock.getType().equals(Material.WATER)) return true;
            }
        }
        return false;
    }
}
