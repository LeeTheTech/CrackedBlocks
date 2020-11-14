package lee.code.crackedblocks.listeners;

import lee.code.crackedblocks.CrackedBlocks;
import lee.code.crackedblocks.events.CustomBlockBreakEvent;
import lee.code.crackedblocks.files.defaults.Settings;
import lee.code.crackedblocks.xseries.XMaterial;
import org.bukkit.Effect;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

public class BlockBreakListener implements Listener {

    @EventHandler
    public void onBlockBreak(CustomBlockBreakEvent e) {
        CrackedBlocks plugin = CrackedBlocks.getPlugin();
        Block block = e.getBlock();

        int durability = plugin.getData().getBlockMaxDurability(e.getBlock().getType());

        if (block.hasMetadata("hits")) {
            block.setMetadata("hits", new FixedMetadataValue(plugin, block.getMetadata("hits").get(0).asInt() + 1));
            if (block.getMetadata("hits").get(0).asInt() >= durability) {
                if (Settings.DROP_BLOCKS.getConfigValue()) {
                    if (block.getType() == XMaterial.BEDROCK.parseMaterial()) block.getLocation().getWorld().dropItemNaturally(block.getLocation(), new ItemStack(block.getType()));
                    block.breakNaturally();
                } else block.setType(XMaterial.AIR.parseMaterial());
                block.removeMetadata("hits", plugin);
                block.getLocation().getWorld().playEffect(block.getLocation(), Effect.valueOf(plugin.getData().getBreakEffect()), 1);
            }
        } else {
            block.setMetadata("hits", new FixedMetadataValue(plugin, 1));
        }
    }
}
