package be.machigan.protecteddebugstick.event;

import be.machigan.protecteddebugstick.persistent.ChunkDataHandler;
import be.machigan.protecteddebugstick.persistent.LocationListDataType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;

public class OnUpdate implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public static void onUpdate(BlockPhysicsEvent e) {
        if (ChunkDataHandler.isPresentAt((e.getBlock().getLocation())))
            e.setCancelled(true);
    }

    @EventHandler
    public static void onSeedGrow(BlockGrowEvent e) {
        if (ChunkDataHandler.isPresentAt((e.getBlock().getLocation())))
            e.setCancelled(true);
    }

    @EventHandler
    public static void onBreak(BlockBreakEvent e) {
        if (e.isCancelled()) return;
        ChunkDataHandler.removeLocation(e.getBlock().getLocation());
    }

    @EventHandler
    public static void onExtends(BlockPistonExtendEvent e) {
        if (e.isCancelled())
            return;
        e.getBlocks().forEach(block -> ChunkDataHandler.removeLocation(block.getLocation()));
    }

    @EventHandler
    public static void onRetracts(BlockPistonRetractEvent e) {
        if (e.isCancelled()) return;
        e.getBlocks().forEach(block -> ChunkDataHandler.removeLocation(block.getLocation()));
    }
}
