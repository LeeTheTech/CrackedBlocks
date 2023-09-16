package lee.code.crackedblocks;

import lee.code.crackedblocks.files.CustomYML;
import lee.code.crackedblocks.files.FileManager;
import lee.code.crackedblocks.files.file.ConfigType;
import lee.code.crackedblocks.files.file.File;
import lee.code.crackedblocks.files.file.FileConfig;
import lee.code.crackedblocks.files.file.FileLang;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Effect;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;

public class Data {
    @Getter private Material checkerMaterial;
    @Getter private Effect breakEffect;
    private final ArrayList<UUID> clickDelay = new ArrayList<>();
    @Getter private final ArrayList<Material> blocks = new ArrayList<>();
    private final ConcurrentHashMap<Material, Integer> blockMaxDurability = new ConcurrentHashMap<>();

    public int getBlockMaxDurability(Material mat) {
        return blockMaxDurability.get(mat);
    }
    public void addClickDelay(UUID uuid) {
        clickDelay.add(uuid);
    }
    public void removeClickDelay(UUID uuid) {
        clickDelay.remove(uuid);
    }
    public boolean hasClickDelay(UUID uuid) {
        return clickDelay.contains(uuid);
    }

    public void load() {
        clickDelay.clear();
        blocks.clear();
        blockMaxDurability.clear();

        loadFiles();
    }

    public void loadFiles() {
        loadFile(File.LANG.name().toLowerCase(), File.LANG);
        loadFile(File.CONFIG.name().toLowerCase(), File.CONFIG);
        loadFile(File.BLOCKS.name().toLowerCase(), File.BLOCKS);
    }

    private void loadFile(String config, File file) {
        FileManager fileManager = CrackedBlocks.getPlugin().getFileManager();
        fileManager.createYML(config);
        CustomYML customYML = fileManager.getYML(config);
        YamlConfiguration yaml = customYML.getFile();

        switch (file) {
            case CONFIG -> {
                for (FileConfig value : FileConfig.values()) {
                    if (yaml.contains(value.getPath())) continue;
                    if (value.getConfigType().equals(ConfigType.BOOLEAN)) yaml.set(value.getPath(), Boolean.valueOf(value.getString()));
                    else if (value.getConfigType().equals(ConfigType.STRING)) yaml.set(value.getPath(), value.getString());
                    else if (value.getConfigType().equals(ConfigType.INT)) yaml.set(value.getPath(), Integer.parseInt(value.getString()));
                    else if (value.getConfigType().equals(ConfigType.STRING_LIST)) yaml.set(value.getPath(), new ArrayList<>(List.of(StringUtils.split(value.getString(), ','))));
                }
                String blockBreakEffect = yaml.getString("block-break-effect");
                if (blockBreakEffect != null) {
                    breakEffect = Effect.valueOf(blockBreakEffect);
                }
                String checker = yaml.getString("durability-material-checker");
                if (checker != null) {
                    checkerMaterial = Material.valueOf(checker);
                }
            }
            case LANG -> {
                for (FileLang value : FileLang.values()) {
                    if (!yaml.contains(value.getPath())) yaml.set(value.getPath(), value.getString());
                }
            }
            case BLOCKS -> {
                ConfigurationSection cBlocks = yaml.getConfigurationSection("blocks");
                if (cBlocks != null) {
                    cBlocks.getKeys(false).forEach(block -> {
                        int durability = cBlocks.getInt(block);
                        Material material = Material.valueOf(block);
                        blocks.add(material);
                        blockMaxDurability.put(material, durability);
                    });
                }
            }
        }
        customYML.saveFile();
    }
}
