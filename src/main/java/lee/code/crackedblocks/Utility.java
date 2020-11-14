package lee.code.crackedblocks;

import lee.code.crackedblocks.xseries.XMaterial;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.UUID;

public class Utility {

    //color formatting string
    public String format(String format) {
        return ChatColor.translateAlternateColorCodes('&', format);
    }

    //formats item from XMat as Item Name
    public String formatMatFriendly(ItemStack item) {
        if (XMaterial.isOneEight() && item.getType().equals(Material.POTION)) return "Potion";
        return XMaterial.matchXMaterial(item).toString();
    }

    //gets the players hand item depending on version
    public Material getHandItemType(Player player) {
        ItemStack item;
        if (XMaterial.isOneEight()) item = new ItemStack(player.getInventory().getItemInHand());
        else item = new ItemStack(player.getInventory().getItemInMainHand());
        item.setAmount(1);
        return item.getType();
    }

    //delay runnable for clicking menus
    public void addPlayerClickDelay(UUID uuid) {
        CrackedBlocks plugin = CrackedBlocks.getPlugin();

        //adds player to list for delay
        plugin.getData().addPlayerClickDelay(uuid);

        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        scheduler.runTaskLater(plugin, () ->
                plugin.getData().removePlayerClickDelay(uuid), 5);
    }
}
