package lee.code.crackedblocks.files.defaults;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.file.FileConfiguration;

@AllArgsConstructor
public enum Settings {
    //boolean drop blocks
    DROP_BLOCKS("drop-blocks", true),
    //water protection
    WATER_PROTECTION("water-protection", true),
    ;

    @Getter private final String path;
    @Getter private final boolean def;
    @Setter private static FileConfiguration file;

    public Boolean getDefault() {
        return this.def;
    }

    public Boolean getConfigValue() {
        return file.getBoolean(this.path, this.def);
    }
}