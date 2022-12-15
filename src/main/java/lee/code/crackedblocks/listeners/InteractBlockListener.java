package lee.code.crackedblocks.listeners;

import lee.code.crackedblocks.Core;
import lee.code.crackedblocks.CrackedBlocks;
import lee.code.crackedblocks.Data;
import lee.code.crackedblocks.files.file.FileLang;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class InteractBlockListener implements Listener {

    @EventHandler
    public void onBlockInteract(PlayerInteractEvent e) {
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            Data data = CrackedBlocks.getPlugin().getData();
            Player player = e.getPlayer();
            Material handMaterial = player.getInventory().getItemInMainHand().getType();

            if (!handMaterial.equals(data.getCheckerMaterial())) return;
            e.setCancelled(true);
            if (Core.hasClickDelay(e.getPlayer())) return;
            Block block = e.getClickedBlock();
            if (block != null) {
                Material blockMaterial = block.getType();
                if (data.getBlocks().contains(blockMaterial)) {
                    int maxDurability = data.getBlockMaxDurability(blockMaterial);
                    int currentDurability = maxDurability;
                    if (block.hasMetadata("hits")) currentDurability = (maxDurability - block.getMetadata("hits").get(0).asInt());
                    player.sendMessage(FileLang.PREFIX.getString(null) + FileLang.CHECKER_INFO_MESSAGE.getString(new String[] {
                            Core.parseCapitalization(blockMaterial.name()),
                            String.valueOf(currentDurability),
                            String.valueOf(maxDurability)
                    }));
                } else {
                    player.sendMessage(FileLang.PREFIX.getString(null) + FileLang.ERROR_CHECKER_NOT_SUPPORTED.getString(new String[] {
                            Core.parseCapitalization(blockMaterial.name())
                    }));
                }
            }
        }
    }
}
