package lee.code.crackedblocks.files.file;

import lee.code.crackedblocks.CrackedBlocks;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;


@AllArgsConstructor
public enum FileConfig {
    DROP_BLOCKS("drop-blocks", "true", ConfigType.BOOLEAN),
    WATER_PROTECTION("water-protection", "true", ConfigType.BOOLEAN),
    DISABLED_BEDROCK_ROOF_Y("disabled-bedrock-roof-y", "127", ConfigType.INT),
    DISABLED_BEDROCK_FLOOR_Y("disabled-bedrock-floor-y", "-64", ConfigType.INT),
    DISABLED_BEDROCK_FLOOR_WORLDS("disabled-bedrock-floor-worlds", "world,test_world", ConfigType.STRING_LIST),
    DISABLED_BEDROCK_ROOF_WORLDS("disabled-bedrock-roof-worlds", "world_nether,test_nether", ConfigType.STRING_LIST),
    DURABILITY_MATERIAL_CHECKER("durability-material-checker", "STICK", ConfigType.STRING),
    BLOCK_BREAK_EFFECT("block-break-effect", "MOBSPAWNER_FLAMES", ConfigType.STRING),
    ;

    @Getter private final String path;
    @Getter private final String string;
    @Getter private final ConfigType configType;

    public String getString(String[] variables) {
        return CrackedBlocks.getPlugin().getFileManager().getStringFromFile(File.CONFIG.name().toLowerCase(), path, variables);
    }

    public boolean getBoolean() {
        return CrackedBlocks.getPlugin().getFileManager().getBooleanFromFile(File.CONFIG.name().toLowerCase(), path);
    }

    public int getInt() {
        return CrackedBlocks.getPlugin().getFileManager().getIntFromFile(File.CONFIG.name().toLowerCase(), path);
    }

    public List<String> getStringList() {
        return CrackedBlocks.getPlugin().getFileManager().getStringListFromFile(File.CONFIG.name().toLowerCase(), path);
    }
}

