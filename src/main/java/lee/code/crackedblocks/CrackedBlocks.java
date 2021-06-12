package lee.code.crackedblocks;

import lee.code.crackedblocks.commands.CommandManager;
import lee.code.crackedblocks.commands.TabCompletion;
import lee.code.crackedblocks.files.CustomFile;
import lee.code.crackedblocks.files.FileManager;
import lee.code.crackedblocks.files.defaults.Lang;
import lee.code.crackedblocks.files.defaults.Settings;
import lee.code.crackedblocks.listeners.BlockBreakListener;
import lee.code.crackedblocks.listeners.EntityExplodeListener;
import lee.code.crackedblocks.listeners.InteractCrackedBlockListener;
import lee.code.crackedblocks.listeners.PlayerBlockBreakListener;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public class CrackedBlocks extends JavaPlugin {

    @Getter private FileManager fileManager;
    @Getter private Data data;
    @Getter private PluginUtility pU;

    @Override
    public void onEnable() {

        this.data = new Data();
        this.fileManager = new FileManager();
        this.pU = new PluginUtility();

        //File Manager for configs
        fileManager.addConfig("config");
        fileManager.addConfig("lang");
        fileManager.addConfig("settings");

        loadDefaults();

        registerCommands();
        registerListeners();

        data.loadData();
    }

    private void registerCommands() {
        getCommand("cb").setExecutor(new CommandManager());
        getCommand("cb").setTabCompleter(new TabCompletion());
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new InteractCrackedBlockListener(), this);
        getServer().getPluginManager().registerEvents(new EntityExplodeListener(), this);
        getServer().getPluginManager().registerEvents(new BlockBreakListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerBlockBreakListener(), this);
    }

    public void loadDefaults() {
        //lang
        Lang.setFile(fileManager.getConfig("lang").getData());
        for (Lang value : Lang.values()) fileManager.getConfig("lang").getData().addDefault(value.getPath(), value.getDefault());
        fileManager.getConfig("lang").getData().options().copyDefaults(true);
        fileManager.getConfig("lang").save();

        //config
        Settings.setFile(fileManager.getConfig("settings").getData());
        for (Settings value : Settings.values()) fileManager.getConfig("settings").getData().addDefault(value.getPath(), value.getDefault());
        fileManager.getConfig("settings").getData().options().copyDefaults(true);
        fileManager.getConfig("settings").save();
    }

    //gets a file
    public CustomFile getFile(String file) {
        return fileManager.getConfig(file);
    }

    //saves a file
    public void saveFile(String file) {
        fileManager.getConfig(file).save();
    }

    //reloads all files
    public void reloadFiles() {
        fileManager.reloadAll();
    }

    public static CrackedBlocks getPlugin() {
        return CrackedBlocks.getPlugin(CrackedBlocks.class);
    }
}
