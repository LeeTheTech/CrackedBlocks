package lee.code.crackedblocks.commands.subcommands;

import lee.code.crackedblocks.CrackedBlocks;
import lee.code.crackedblocks.commands.SubCommand;
import lee.code.crackedblocks.files.file.FileLang;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HelpCMD extends SubCommand {

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return FileLang.COMMAND_HELP_DESCRIPTION.getString(null);
    }

    @Override
    public String getSyntax() {
        return "/crackedblocks help";
    }

    @Override
    public String getPermission() {
        return "cb.command.help";
    }

    @Override
    public void perform(Player player, String[] args) {
        CrackedBlocks.getPlugin().getCommandManager().sendHelpMessage(player);
    }

    @Override
    public void performConsole(CommandSender console, String[] args) {
        console.sendMessage(FileLang.PREFIX.getString(null) + FileLang.ERROR_NOT_CONSOLE_COMMAND.getString(null));
    }
}
