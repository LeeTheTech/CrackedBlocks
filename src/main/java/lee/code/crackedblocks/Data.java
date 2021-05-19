package lee.code.crackedblocks;

import lee.code.crackedblocks.xseries.XMaterial;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Material;

public class Data {

    //click delay map
    private final List<UUID> playerClickDelay = new ArrayList<>();

    //block List
    @Getter private final List<Material> blocks = new ArrayList<>();

    //block max durability
    private final HashMap<Material, Integer> blockMaxDurability = new HashMap<>();

    //disable bedrock floor List
    @Getter @Setter private List<String> disabledBedrockFloorWorlds = new ArrayList<>();

    //disable bedrock roof List
    @Getter @Setter private List<String> disabledBedrockRoofWorlds = new ArrayList<>();

    //get block max durability
    public int getBlockMaxDurability(Material mat) {
        return blockMaxDurability.get(mat);
    }

    //get block max durability
    private void setBlockMaxDurability(Material mat, int amount) {
        blockMaxDurability.put(mat, amount);
    }

    //add to block list
    private void addBlock(Material mat) {
        blocks.add(mat);
    }

    //check if player is on menu click delay
    public boolean getPlayerClickDelay(UUID uuid) {
        return playerClickDelay.contains(uuid);
    }

    //add a player to a click delay list for runnable
    public void addPlayerClickDelay(UUID uuid) {
        playerClickDelay.add(uuid);
    }

    //remove a player to a click delay list
    public void removePlayerClickDelay(UUID uuid) {
        playerClickDelay.remove(uuid);
    }

    @Getter @Setter private Material checkerItem;
    @Getter @Setter private String breakEffect;

    private void clearData() {
        blocks.clear();
        blockMaxDurability.clear();
        disabledBedrockFloorWorlds.clear();
        disabledBedrockRoofWorlds.clear();
    }

    public void loadData() {
        CrackedBlocks plugin = CrackedBlocks.getPlugin();

        clearData();

        FileConfiguration file = plugin.getFile("config").getData();
        ConfigurationSection config = file.getConfigurationSection("blocks");

        if (config != null) {
            config.getKeys(false).forEach(block -> {
                addBlock(XMaterial.valueOf(block).parseMaterial());
                setBlockMaxDurability(XMaterial.valueOf(block).parseMaterial(), file.getInt("blocks." + block + ".durability"));
            });

            setDisabledBedrockFloorWorlds(file.getStringList("world-options.disable-bedrock-floor"));
            setDisabledBedrockRoofWorlds(file.getStringList("world-options.disable-bedrock-roof"));
            setCheckerItem(XMaterial.valueOf(file.getString("durability-material-checker")).parseMaterial());
            setBreakEffect(file.getString("block-break-effect"));
        }
    }
}
