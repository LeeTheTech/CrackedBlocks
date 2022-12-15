package lee.code.crackedblocks.listeners;

import lee.code.crackedblocks.CrackedBlocks;
import lee.code.crackedblocks.Data;
import lee.code.crackedblocks.events.CustomBlockBreakEvent;
import lee.code.crackedblocks.files.file.FileConfig;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.metadata.FixedMetadataValue;

public class BlockBreakListener implements Listener {

    @EventHandler
    public void onBlockBreak(CustomBlockBreakEvent e) {
        CrackedBlocks plugin = CrackedBlocks.getPlugin();
        Data data = plugin.getData();
        Block block = e.getBlock();

        int maxDurability = data.getBlockMaxDurability(e.getBlock().getType());
        World world = block.getLocation().getWorld();

        if (world != null) {
            if (!block.hasMetadata("hits")) block.setMetadata("hits", new FixedMetadataValue(plugin, 0));
            block.setMetadata("hits", new FixedMetadataValue(plugin, block.getMetadata("hits").get(0).asInt() + 1));
            if (block.getMetadata("hits").get(0).asInt() >= maxDurability) {
                if (FileConfig.DROP_BLOCKS.getBoolean()) {
                    if (block.getState() instanceof Container) {
                        block.breakNaturally();
                    } else {
                        world.dropItemNaturally(block.getLocation(), new ItemStack(block.getType()));
                        block.setType(Material.AIR);
                    }
                }
                block.removeMetadata("hits", plugin);
                world.playEffect(block.getLocation(), data.getBreakEffect(), 1);
            }
        }
    }

    private ItemStack createSpawner(EntityType type) {
        ItemStack spawner = new ItemStack(Material.SPAWNER);
        BlockStateMeta spawnerMeta = (BlockStateMeta) spawner.getItemMeta();
        if (spawnerMeta != null) {
            CreatureSpawner spawnerCS = (CreatureSpawner) spawnerMeta.getBlockState();
            spawnerCS.setSpawnedType(type);
            spawnerMeta.setBlockState(spawnerCS);
            spawner.setItemMeta(spawnerMeta);
        }
        return spawner;
    }
}
