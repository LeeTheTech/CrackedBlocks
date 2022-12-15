package lee.code.crackedblocks.files.file;

import lee.code.crackedblocks.CrackedBlocks;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.md_5.bungee.api.chat.TextComponent;

@AllArgsConstructor
public enum FileLang {
    PREFIX("prefix", "&5&lCrackedBlocks &e➔ "),
    USAGE("usage", "&6&lUsage&7: &e"),
    CHECKER_INFO_MESSAGE("checker_info_message", "&dThe block &e{0} &dhas &e{1}&7/&e{2} &ddurability."),
    COMMAND_HELP_DIVIDER("command_help_divider", "&a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬"),
    COMMAND_HELP_TITLE("command_help_title", "                 &d-== &5&l&nCrackedBlocks Help&r &d==-"),
    COMMAND_HELP_SUB_COMMAND("command_help_sub_command", "&3{0}&b. &e{1}"),
    COMMAND_HELP_SUB_COMMAND_HOVER("command_help_sub_command_hover", "&6{0}"),
    COMMAND_RELOAD_DESCRIPTION("command_reload_description", "Reloads the CrackedBlocks plugin, this will read all yml files again and apply changes if any."),
    COMMAND_RELOAD_SUCCESS("command_reload_success", "&aCrackedBlocks has been reloaded, all yml file changes have been applied."),
    COMMAND_LIST_DESCRIPTION("command_list_description", "List of blocks that have custom explosion durability."),
    COMMAND_LIST_HEADER("command_list_header", "&a----------- &e[ &2&lBlock List &e] &a-----------"),
    COMMAND_LIST_FOOTER("command_list_footer", "&a-------------------------------------"),
    COMMAND_LIST_LINE("command_list_line", "&3{0}&b. &e{1} &7- &6{2}"),
    COMMAND_LIST_NEXT_PAGE("command_list_next_page", "&2&lNext &a&l>>--------"),
    COMMAND_LIST_PREVIOUS_PAGE("command_list_previous_page", "&a&l--------<< &2&lPrev"),
    COMMAND_LIST_NEXT_PAGE_HOVER("command_list_next_page_hover", "&6&lNext Page"),
    COMMAND_LIST_PREVIOUS_PAGE_HOVER("command_list_previous_page_hover", "&6&lPrevious Page"),
    COMMAND_LIST_PAGE_SPACER("command_list_page_spacer", " &e| "),
    COMMAND_HELP_DESCRIPTION("command_help_description", "A list of commands you can use with this plugin."),
    ERROR_NOT_CONSOLE_COMMAND("error_not_console_command", "&cThis command does not work in console."),
    ERROR_LIST_PAGE_NOT_NUMBER("error_list_page_not_number", "&cThe input &6{0} &cis not a number."),
    ERROR_NO_PERMISSION("error_no_permission", "&cYou sadly do not have permission for this."),
    ERROR_CHECKER_NOT_SUPPORTED("error_checker_not_supported", "&cThe block &6{0} &cdoes not have custom durability."),

    ;

    @Getter private final String path;
    @Getter private final String string;

    public String getString(String[] variables) {
        return CrackedBlocks.getPlugin().getFileManager().getStringFromFile(File.LANG.name().toLowerCase(), path, variables);
    }

    public TextComponent getTextComponent(String[] variables) {
        return new TextComponent(CrackedBlocks.getPlugin().getFileManager().getStringFromFile(File.LANG.name().toLowerCase(), path, variables));
    }
}

