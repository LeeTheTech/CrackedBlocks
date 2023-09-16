package lee.code.crackedblocks.files;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import lee.code.crackedblocks.Core;
import lee.code.crackedblocks.CrackedBlocks;
import org.bukkit.configuration.file.FileConfiguration;

public class FileManager {
    protected CrackedBlocks crackedBlocks;
    protected final HashMap<String, CustomYML> ymlFiles = new HashMap<>();

    public FileManager(CrackedBlocks crackedBlocks) {
        this.crackedBlocks = crackedBlocks;
        Locale.setDefault(Locale.ENGLISH);
    }

    public void createYML(String name) {
        ymlFiles.put(name, new CustomYML(name + ".yml", "", crackedBlocks));
    }

    public CustomYML getYML(String name) {
        return ymlFiles.get(name);
    }

    public String getStringFromFile(String config, String path, String[] variables) {
        FileConfiguration fileConfig = ymlFiles.get(config).getFile();
        String value = fileConfig.getString(path);
        value = value != null ? value : "";
        if (variables == null || variables.length == 0) return Core.parseColorString(value);
        for (int i = 0; i < variables.length; i++) value = value.replace("{" + i + "}", variables[i]);
        return Core.parseColorString(value);
    }

    public boolean getBooleanFromFile(String config, String path) {
        return ymlFiles.get(config).getFile().getBoolean(path);
    }

    public int getIntFromFile(String config, String path) {
        return ymlFiles.get(config).getFile().getInt(path);
    }

    public List<String> getStringListFromFile(String config, String path) {
        return ymlFiles.get(config).getFile().getStringList(path);
    }

    public int getValueFromFile(String config, String path) {
        FileConfiguration fileConfig = ymlFiles.get(config).getFile();
        String value = fileConfig.getString(path);
        return value != null ? Integer.parseInt(value) : 0;
    }

    public void setValueInFile(String config, String path, int value) {
        CustomYML yml = ymlFiles.get(config);
        FileConfiguration fileConfig = yml.getFile();
        fileConfig.set(path, value);
        yml.saveFile();
    }

    public void setStringInFile(String config, String path, String string) {
        CustomYML yml = ymlFiles.get(config);
        FileConfiguration fileConfig = yml.getFile();
        fileConfig.set(path, string);
        yml.saveFile();
    }
}