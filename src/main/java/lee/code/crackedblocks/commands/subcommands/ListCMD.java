package lee.code.crackedblocks.commands.subcommands;

import lee.code.crackedblocks.Core;
import lee.code.crackedblocks.CrackedBlocks;
import lee.code.crackedblocks.Data;
import lee.code.crackedblocks.commands.SubCommand;
import lee.code.crackedblocks.files.file.FileLang;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class ListCMD extends SubCommand {

    @Override
    public String getName() {
        return "list";
    }

    @Override
    public String getDescription() {
        return FileLang.COMMAND_LIST_DESCRIPTION.getString(null);
    }

    @Override
    public String getSyntax() {
        return "/crackedblocks list";
    }

    @Override
    public String getPermission() {
        return "cb.command.list";
    }

    @Override
    public void perform(Player player, String[] args) {
        Data data = CrackedBlocks.getPlugin().getData();
        int index;
        int page = 0;
        int maxDisplayed = 10;

        if (args.length > 1) {
            if (Core.containOnlyIntNumbers(args[1])) {
                page = Integer.parseInt(args[1]);
            } else {
                player.sendMessage(FileLang.PREFIX.getString(null) + FileLang.ERROR_LIST_PAGE_NOT_NUMBER.getString(new String[]{ args[1]} ));
                return;
            }
        }
        if (page < 0) return;
        int position = page * maxDisplayed + 1;

        ArrayList<TextComponent> lines = new ArrayList<>();
        lines.add(FileLang.COMMAND_LIST_HEADER.getTextComponent(null));
        lines.add(new TextComponent(""));

        ArrayList<Material> blocks = data.getBlocks();
        if (blocks != null && !blocks.isEmpty()) {
            for (int i = 0; i < maxDisplayed; i++) {
                index = maxDisplayed * page + i;
                if (index >= blocks.size()) break;
                if (blocks.get(index) != null) {
                    int maxDurability = data.getBlockMaxDurability(blocks.get(index));
                    lines.add(FileLang.COMMAND_LIST_LINE.getTextComponent(new String[] { String.valueOf(position), Core.parseCapitalization(blocks.get(index).name()), String.valueOf(maxDurability) }));
                    position++;
                }
            }
        }
        if (lines.size() <= 2) return;
        lines.add(new TextComponent(""));

        TextComponent next = FileLang.COMMAND_LIST_NEXT_PAGE.getTextComponent(null);
        next.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(FileLang.COMMAND_LIST_NEXT_PAGE_HOVER.getString(null))));
        next.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/crackedblocks list " + (page + 1)));

        TextComponent split = FileLang.COMMAND_LIST_PAGE_SPACER.getTextComponent(null);

        TextComponent prev = FileLang.COMMAND_LIST_PREVIOUS_PAGE.getTextComponent(null);
        prev.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(FileLang.COMMAND_LIST_PREVIOUS_PAGE_HOVER.getString(null))));
        prev.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/crackedblocks list " + (page - 1)));

        prev.addExtra(split);
        prev.addExtra(next);
        lines.add(prev);
        for (TextComponent line : lines) player.spigot().sendMessage(line);
    }

    @Override
    public void performConsole(CommandSender console, String[] args) {
        console.sendMessage(FileLang.PREFIX.getString(null) + FileLang.ERROR_NOT_CONSOLE_COMMAND.getString(null));
    }
}
