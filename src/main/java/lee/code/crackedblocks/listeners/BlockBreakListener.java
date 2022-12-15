package lee.code.crackedblocks.listeners;

import lee.code.crackedblocks.CrackedBlocks;
import lee.code.crackedblocks.Data;
import lee.code.crackedblocks.events.CustomBlockBreakEvent;
import lee.code.crackedblocks.files.file.FileConfig;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

public class BlockBreakListener implements Listener {

    @EventHandler
    public void onCustomBlockBreak(CustomBlockBreakEvent e) {
        CrackedBlocks plugin = CrackedBlocks.getPlugin();
        Data data = plugin.getData();
        Block block = e.getBlock();

        int maxDurability = data.getBlockMaxDurability(e.getBlock().getType());
        World world = block.getLocation().getWorld();
        if (world == null) return;

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

    @EventHandler
    public void onNormalBlockBreak(BlockBreakEvent e) {
        Block block = e.getBlock();
        if (block.hasMetadata("hits")) block.removeMetadata("hits", CrackedBlocks.getPlugin());
    }
}
