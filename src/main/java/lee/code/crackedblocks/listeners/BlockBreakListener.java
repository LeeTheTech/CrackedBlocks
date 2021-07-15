package lee.code.crackedblocks.listeners;

import lee.code.crackedblocks.CrackedBlocks;
import lee.code.crackedblocks.events.CustomBlockBreakEvent;
import lee.code.crackedblocks.files.defaults.Settings;
import lee.code.crackedblocks.xseries.XMaterial;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

public class BlockBreakListener implements Listener {

    @EventHandler
    public void onBlockBreak(CustomBlockBreakEvent e) {
        CrackedBlocks plugin = CrackedBlocks.getPlugin();
        Block block = e.getBlock();

        int maxDurability = plugin.getData().getBlockMaxDurability(e.getBlock().getType());
        World world = block.getLocation().getWorld();

        if (world != null) {
            if (block.hasMetadata("hits")) {
                block.setMetadata("hits", new FixedMetadataValue(plugin, block.getMetadata("hits").get(0).asInt() + 1));
                if (block.getMetadata("hits").get(0).asInt() >= maxDurability) {
                    if (Settings.DROP_BLOCKS.getConfigValue()) {
                        if (XMaterial.isNewVersion()) {
                            if (block.getState() instanceof Container) {
                                block.breakNaturally();
                            } else {
                                world.dropItemNaturally(block.getLocation(), new ItemStack(block.getType()));
                                block.setType(Material.AIR);
                            }
                        } else {
                            Material mat = block.getType();
                            if (mat.equals(XMaterial.CHEST.parseMaterial()) || mat.equals(XMaterial.TRAPPED_CHEST.parseMaterial()) || mat.equals(XMaterial.FURNACE.parseMaterial()) || mat.equals(XMaterial.DROPPER.parseMaterial()) || mat.equals(XMaterial.SHULKER_BOX.parseMaterial()) || mat.equals(XMaterial.BREWING_STAND.parseMaterial()) || mat.equals(XMaterial.HOPPER.parseMaterial())) {
                                block.breakNaturally();
                            } else {
                                world.dropItemNaturally(block.getLocation(), new ItemStack(block.getType()));
                                block.setType(Material.AIR);
                            }
                        }
                    }
                    block.removeMetadata("hits", plugin);
                    world.playEffect(block.getLocation(), Effect.valueOf(plugin.getData().getBreakEffect()), 1);
                }
            } else block.setMetadata("hits", new FixedMetadataValue(plugin, 1));
        }
    }
}
