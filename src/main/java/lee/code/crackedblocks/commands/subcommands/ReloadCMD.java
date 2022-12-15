package lee.code.crackedblocks.commands.subcommands;

import lee.code.crackedblocks.CrackedBlocks;
import lee.code.crackedblocks.commands.SubCommand;
import lee.code.crackedblocks.files.file.FileLang;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReloadCMD extends SubCommand {

    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public String getDescription() {
        return FileLang.COMMAND_RELOAD_DESCRIPTION.getString(null);
    }

    @Override
    public String getSyntax() {
        return "/crackedblocks reload";
    }

    @Override
    public String getPermission() {
        return "cb.command.reload";
    }

    @Override
    public void perform(Player player, String[] args) {
        CrackedBlocks.getPlugin().getData().load();
        player.sendMessage(FileLang.PREFIX.getString(null) + FileLang.COMMAND_RELOAD_SUCCESS.getString(null));
    }

    @Override
    public void performConsole(CommandSender console, String[] args) {
        CrackedBlocks.getPlugin().getData().load();
        console.sendMessage(FileLang.PREFIX.getString(null) + FileLang.COMMAND_RELOAD_SUCCESS.getString(null));
    }
}