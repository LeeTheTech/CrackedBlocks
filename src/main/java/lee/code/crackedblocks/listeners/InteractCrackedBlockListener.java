package lee.code.crackedblocks.listeners;

import lee.code.crackedblocks.Core;
import lee.code.crackedblocks.CrackedBlocks;
import lee.code.crackedblocks.Data;
import lee.code.crackedblocks.files.file.FileLang;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class InteractCrackedBlockListener implements Listener {

    @EventHandler
    public void onBlockInteract(PlayerInteractEvent e) {
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            CrackedBlocks plugin = CrackedBlocks.getPlugin();
            Data data = plugin.getData();
            Player player = e.getPlayer();
            ItemStack handItem = player.getInventory().getItemInMainHand();

            if (!handItem.getType().equals(data.getCheckerMaterial())) return;
            e.setCancelled(true);
            if (Core.hasClickDelay(e.getPlayer())) return;
            Block block = e.getClickedBlock();
            if (block != null) {
                if (data.getBlocks().contains(block.getType())) {
                    int maxDurability = data.getBlockMaxDurability(e.getClickedBlock().getType());
                    if (e.getClickedBlock().hasMetadata("hits")) {
                        int currentDurability = e.getClickedBlock().getMetadata("hits").get(0).asInt();
                        player.sendMessage(FileLang.PREFIX.getString(null) + FileLang.CHECKER_INFO_MESSAGE.getString(new String[] {
                                Core.parseCapitalization(e.getClickedBlock().getType().name()),
                                String.valueOf(maxDurability - currentDurability),
                                String.valueOf(maxDurability)
                        }));
                    } else {
                        player.sendMessage(FileLang.PREFIX.getString(null) + FileLang.CHECKER_INFO_MESSAGE.getString(new String[] {
                                Core.parseCapitalization(e.getClickedBlock().getType().name()),
                                String.valueOf(maxDurability),
                                String.valueOf(maxDurability)
                        }));
                    }
                }
            }
        }
    }
}
