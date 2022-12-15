package lee.code.crackedblocks.listeners;

import lee.code.crackedblocks.CrackedBlocks;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class PlayerBlockBreakListener implements Listener {

    @EventHandler
    public void onPlayerBreak(BlockBreakEvent e) {
        Block block = e.getBlock();
        if (block.hasMetadata("hits")) block.removeMetadata("hits", CrackedBlocks.getPlugin());
    }
}
