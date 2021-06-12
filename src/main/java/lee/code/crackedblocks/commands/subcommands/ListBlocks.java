package lee.code.crackedblocks.commands.subcommands;

import lee.code.crackedblocks.CrackedBlocks;
import lee.code.crackedblocks.commands.SubCommand;
import lee.code.crackedblocks.files.defaults.Lang;
import lee.code.crackedblocks.xseries.XMaterial;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ListBlocks extends SubCommand {

    @Override
    public String getName() {
        return "list";
    }

    @Override
    public String getDescription() {
        return Lang.MESSAGE_HELP_SUB_COMMAND_LIST.getConfigValue(null);
    }

    @Override
    public String getSyntax() {
        return "/cb list";
    }

    @Override
    public String getPermission() {
        return "cb.command.list";
    }

    @Override
    public void perform(Player player, String[] args) {
        CrackedBlocks plugin = CrackedBlocks.getPlugin();

        int page = 0;

        //page check
        if (args.length > 1) {
            Scanner sellScanner = new Scanner(args[1]);
            if (sellScanner.hasNextInt()) {
                page = Integer.parseInt(args[1]);
            } else {
                player.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.ERROR_COMMAND_LIST_PAGE.getConfigValue(new String[]{ args[1]} ));
                return;
            }
        }

        if (page < 0) return;

        int index = 0;
        int maxDisplayed = 10;

        List<String> formattedList = new ArrayList<>();
        formattedList.add(Lang.MESSAGE_COMMAND_LIST_HEADER.getConfigValue(null));


        List<Material> blocks = plugin.getData().getBlocks();

        if (blocks != null && !blocks.isEmpty()) {
            for(int i = 0; i < maxDisplayed; i++) {
                index = maxDisplayed * page + i;
                if (index >= blocks.size()) break;
                if (blocks.get(index) != null) {

                    int blockNumber = index + 1;
                    String block = plugin.getPU().formatMatFriendly(new ItemStack(blocks.get(index)));
                    int maxDurability = plugin.getData().getBlockMaxDurability(blocks.get(index));
                    formattedList.add(Lang.MESSAGE_COMMAND_LIST_COMMAND.getConfigValue(new String[] { String.valueOf(blockNumber), block, String.valueOf(maxDurability) }));
                }
            }
        }

        if (formattedList.size() < 2) return;

        for (String line : formattedList) player.sendMessage(plugin.getPU().format(line));

        //next page
        TextComponent next = new TextComponent(Lang.MESSAGE_COMMAND_LIST_NEXT_PAGE.getConfigValue(null));
        next.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/cb list " + " " + (page + 1)));
        if (XMaterial.supports(16)) next.setHoverEvent(new HoverEvent( HoverEvent.Action.SHOW_TEXT, new Text(Lang.MESSAGE_COMMAND_LIST_NEXT_PAGE_HOVER.getConfigValue(null))));
        else next.setHoverEvent(new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(Lang.MESSAGE_COMMAND_LIST_NEXT_PAGE_HOVER.getConfigValue(null)).create()));
        //previous page
        TextComponent previous = new TextComponent(Lang.MESSAGE_COMMAND_LIST_PREVIOUS_PAGE.getConfigValue(null));
        previous.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/cb list " + " " + (page - 1)));
        if (XMaterial.supports(16)) previous.setHoverEvent(new HoverEvent( HoverEvent.Action.SHOW_TEXT, new Text(Lang.MESSAGE_COMMAND_LIST_PREVIOUS_PAGE_HOVER.getConfigValue(null))));
        else previous.setHoverEvent(new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(Lang.MESSAGE_COMMAND_LIST_PREVIOUS_PAGE_HOVER.getConfigValue(null)).create()));
        //spacer
        TextComponent spacer = new TextComponent(" | ");
        spacer.setBold(true);
        spacer.setColor(ChatColor.GRAY);

        player.spigot().sendMessage(previous, spacer, next);
    }

    @Override
    public void performConsole(CommandSender console, String[] args) {

    }
}
