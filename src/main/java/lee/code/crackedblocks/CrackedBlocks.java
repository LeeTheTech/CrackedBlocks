package lee.code.crackedblocks;

import lee.code.crackedblocks.commands.CommandManager;
import lee.code.crackedblocks.commands.TabCompletion;
import lee.code.crackedblocks.files.FileManager;
import lee.code.crackedblocks.listeners.BlockBreakListener;
import lee.code.crackedblocks.listeners.EntityExplodeListener;
import lee.code.crackedblocks.listeners.InteractCrackedBlockListener;
import lee.code.crackedblocks.listeners.PlayerBlockBreakListener;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public class CrackedBlocks extends JavaPlugin {
    @Getter private FileManager fileManager;
    @Getter private Data data;
    @Getter private CommandManager commandManager;

    @Override
    public void onEnable() {
        this.data = new Data();
        this.fileManager = new FileManager(this);
        this.commandManager = new CommandManager();

        registerCommands();
        registerListeners();

        data.load();
    }

    private void registerCommands() {
        getCommand("crackedblocks").setExecutor(commandManager);
        getCommand("crackedblocks").setTabCompleter(new TabCompletion());
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new InteractCrackedBlockListener(), this);
        getServer().getPluginManager().registerEvents(new EntityExplodeListener(), this);
        getServer().getPluginManager().registerEvents(new BlockBreakListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerBlockBreakListener(), this);
    }

    public static CrackedBlocks getPlugin() {
        return CrackedBlocks.getPlugin(CrackedBlocks.class);
    }
}
