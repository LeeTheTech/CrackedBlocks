package lee.code.crackedblocks.files.defaults;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

@AllArgsConstructor
public enum Lang {
    //plugin prefix
    PREFIX("PREFIX", "&5&lCrackedBlocks &e➔ &r"),
    //Error no permission
    ERROR_NO_PERMISSION("ERROR_NO_PERMISSION", "&cYou sadly do not have permission for this."),
    //checker item message
    CHECKER_INFO_MESSAGE("CHECKER_INFO_MESSAGE", "&dThe block &e{0} &dhas &e{1}&7/&e{2} &ddurability."),
    //Message Help info divider
    MESSAGE_HELP_DIVIDER("MESSAGE_HELP_DIVIDER", "&e▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬"),
    //Message Help info title
    MESSAGE_HELP_TITLE("MESSAGE_HELP_TITLE", "             &d-== &5&l&nCrackedBlocks Help&r &d==-"),
    //Message Help info sub command
    MESSAGE_HELP_SUB_COMMAND("MESSAGE_HELP_SUB_COMMAND", "&3{0}&b. &e{1} &c| &7{2}"),
    //Message plugin has reloaded
    MESSAGE_RELOAD("MESSAGE_RELOAD", "&aThe plugin has been reloaded."),
    //Error not a console command
    ERROR_NOT_A_CONSOLE_COMMAND("ERROR_NOT_A_CONSOLE_COMMAND", "&cThis is not a console command."),
    //Error command list page number
    ERROR_COMMAND_LIST_PAGE("ERROR_COMMAND_LIST_PAGE", "&cThe input &6{0} &cis not a number. &a&lUse&7: &e(1)"),
    //Message Help reload sub command
    MESSAGE_HELP_SUB_COMMAND_RELOAD("MESSAGE_HELP_SUB_COMMAND_RELOAD", "Reloads the plugin."),
    //Message Help list sub command
    MESSAGE_HELP_SUB_COMMAND_LIST("MESSAGE_HELP_SUB_COMMAND_LIST", "List all the blocks that have custom durability."),
    //Message command list header
    MESSAGE_COMMAND_LIST_HEADER("MESSAGE_COMMAND_LIST_HEADER", "&d&l-----<< &5&lBlocks &d&l>>-----"),
    //Message command list command
    MESSAGE_COMMAND_LIST_COMMAND("MESSAGE_COMMAND_LIST_COMMAND", "&b{0}&7. &6{1} &7- &eDurability&7: &a{2}"),
    //Message command list next page
    MESSAGE_COMMAND_LIST_NEXT_PAGE("MESSAGE_COMMAND_LIST_NEXT_PAGE", "&d&lNext >>---"),
    //Message command list next page hover
    MESSAGE_COMMAND_LIST_NEXT_PAGE_HOVER("MESSAGE_COMMAND_LIST_NEXT_PAGE_HOVER", "&5&lNext Page"),
    //Message command list previous page
    MESSAGE_COMMAND_LIST_PREVIOUS_PAGE("MESSAGE_COMMAND_LIST_PREVIOUS_PAGE", "&d&l---<< Prev"),
    //Message command list previous page hover
    MESSAGE_COMMAND_LIST_PREVIOUS_PAGE_HOVER("MESSAGE_COMMAND_LIST_PREVIOUS_PAGE_HOVER", "&5&lPrevious Page"),
    ;

    @Getter private final String path;
    @Getter private final String def;
    @Setter private static FileConfiguration file;

    public String getDefault() {
        return def;
    }

    public String getConfigValue(final String[] args) {
        String fileValue = file.getString(this.path, this.def);
        if (fileValue == null) fileValue = "";

        String value = ChatColor.translateAlternateColorCodes('&', fileValue);

        if (args == null) return value;
        else if (args.length == 0) return value;

        for (int i = 0; i < args.length; i++) value = value.replace("{" + i + "}", args[i]);

        return value;
    }
}