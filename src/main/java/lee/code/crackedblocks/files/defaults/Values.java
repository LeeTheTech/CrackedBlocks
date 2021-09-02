package lee.code.crackedblocks.files.defaults;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.file.FileConfiguration;

@AllArgsConstructor
public enum Values {
    DISABLED_BEDROCK_FLOOR("disabled-bedrock-floor-y", 0),
    DISABLED_BEDROCK_ROOF("disabled-bedrock-roof-y", 127),
    ;

    @Getter private final String path;
    @Getter private final double def;
    @Setter private static FileConfiguration file;

    public Double getDefault() {
        return this.def;
    }

    public Double getConfigValue() {
        return file.getDouble(this.path, this.def);
    }
}