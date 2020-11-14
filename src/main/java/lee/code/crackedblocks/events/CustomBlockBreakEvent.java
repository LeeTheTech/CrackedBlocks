package lee.code.crackedblocks.events;

import lombok.Getter;
import org.bukkit.block.Block;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class CustomBlockBreakEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    @Getter private final Block block;

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public CustomBlockBreakEvent(Block block) {
        this.block = block;
    }
}
