package lee.code.crackedblocks;

import net.md_5.bungee.api.ChatColor;
import org.apache.commons.lang3.text.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Core {
    private final static Pattern hexPattern = Pattern.compile("\\&#[a-fA-F0-9]{6}");
    private final static Pattern numberIntPattern = Pattern.compile("^[1-9]\\d*$");

    public static String parseColorString(String text) {
        if (text == null) return "";
        Matcher matcher = hexPattern.matcher(text);
        while (matcher.find()) {
            String color = text.substring(matcher.start(), matcher.end()).replaceAll("&", "");
            text = text.replace("&" + color, net.md_5.bungee.api.ChatColor.of(color) + "");
            matcher = hexPattern.matcher(text);
        }
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static String parseCapitalization(String message) {
        String format = message.toLowerCase().replaceAll("_", " ");
        return WordUtils.capitalize(format);
    }

    public static boolean hasClickDelay(Player player) {
        Data data = CrackedBlocks.getPlugin().getData();
        UUID uuid = player.getUniqueId();
        if (data.hasClickDelay(uuid)) return true;
        else scheduleClickDelay(uuid);
        return false;
    }

    private static void scheduleClickDelay(UUID uuid) {
        CrackedBlocks plugin = CrackedBlocks.getPlugin();
        Data data = plugin.getData();
        data.addClickDelay(uuid);
        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        scheduler.runTaskLater(plugin, () -> data.removeClickDelay(uuid), 5L);
    }

    public static boolean isPositiveIntNumber(String numbers) {
        final String intMax = String.valueOf(Integer.MAX_VALUE);
        if (numbers.length() > intMax.length() || (numbers.length() == intMax.length() && numbers.compareTo(intMax) > 0)) return false;
        return numberIntPattern.matcher(numbers).matches();
    }
}
