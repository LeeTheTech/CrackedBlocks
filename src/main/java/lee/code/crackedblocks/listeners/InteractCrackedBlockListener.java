package lee.code.crackedblocks.listeners;

import lee.code.crackedblocks.CrackedBlocks;
import lee.code.crackedblocks.files.defaults.Lang;
import lee.code.crackedblocks.xseries.XMaterial;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.Material;

public class InteractCrackedBlockListener implements Listener {

    @EventHandler
    public void onBlockInteract(PlayerInteractEvent e) {

        CrackedBlocks plugin = CrackedBlocks.getPlugin();

        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {

            Material handItem = plugin.getUtility().getHandItemType(e.getPlayer());
            Material checkerItem = plugin.getData().getCheckerItem();

            if (!handItem.equals(checkerItem)) return;

            //click delay
            if (plugin.getData().getPlayerClickDelay(e.getPlayer().getUniqueId())) {
                e.setCancelled(true);
                return;
            } else plugin.getUtility().addPlayerClickDelay(e.getPlayer().getUniqueId());

            if (plugin.getData().getBlocks().contains(e.getClickedBlock().getType())) {
                e.setCancelled(true);
                int maxDurability = plugin.getData().getBlockMaxDurability(e.getClickedBlock().getType());
                if (e.getClickedBlock().hasMetadata("hits")) {
                    int currentDurability = e.getClickedBlock().getMetadata("hits").get(0).asInt();
                    e.getPlayer().sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.CHECKER_INFO_MESSAGE.getConfigValue(new String[] {
                            plugin.getUtility().formatMatFriendly(XMaterial.matchXMaterial(e.getClickedBlock().getType()).parseItem()), String.valueOf(maxDurability - currentDurability), String.valueOf(maxDurability) }));
                } else {
                    e.getPlayer().sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.CHECKER_INFO_MESSAGE.getConfigValue(new String[] {
                            plugin.getUtility().formatMatFriendly(XMaterial.matchXMaterial(e.getClickedBlock().getType()).parseItem()), String.valueOf(maxDurability), String.valueOf(maxDurability) }));
                }
            }
        }
    }
}
