package lee.code.crackedblocks.commands.subcommands;

import lee.code.crackedblocks.CrackedBlocks;
import lee.code.crackedblocks.commands.SubCommand;
import lee.code.crackedblocks.files.defaults.Lang;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Reload extends SubCommand {

    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public String getDescription() {
        return Lang.MESSAGE_HELP_SUB_COMMAND_RELOAD.getConfigValue(null);
    }

    @Override
    public String getSyntax() {
        return "/cb reload";
    }

    @Override
    public String getPermission() {
        return "cb.command.reload";
    }

    @Override
    public void perform(Player player, String[] args) {
        CrackedBlocks plugin = CrackedBlocks.getPlugin();

        plugin.reloadFiles();
        plugin.loadDefaults();
        plugin.getData().loadData();
        player.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.MESSAGE_RELOAD.getConfigValue(null));
    }

    @Override
    public void performConsole(CommandSender console, String[] args) {
        CrackedBlocks plugin = CrackedBlocks.getPlugin();

        plugin.reloadFiles();
        plugin.loadDefaults();
        plugin.getData().loadData();
        console.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.MESSAGE_RELOAD.getConfigValue(null));
    }
}