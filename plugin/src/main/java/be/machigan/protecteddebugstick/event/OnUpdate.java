package be.machigan.protecteddebugstick.event;

import be.machigan.protecteddebugstick.persistent.LocationListDataType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;

public class OnUpdate implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public static void onUpdate(BlockPhysicsEvent e) {
        if (LocationListDataType.isPresent(e.getBlock())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public static void onSeedGrow(BlockGrowEvent e) {
        if (LocationListDataType.isPresent(e.getBlock()))
            e.setCancelled(true);
    }

    @EventHandler
    public static void onBreak(BlockBreakEvent e) {
        if (e.isCancelled())
            return;
        LocationListDataType.removeBlock(e.getBlock());
    }

    @EventHandler
    public static void onExtends(BlockPistonExtendEvent e) {
        if (e.isCancelled())
            return;
        e.getBlocks().forEach(LocationListDataType::removeBlock);
    }

    @EventHandler
    public static void onRetracts(BlockPistonRetractEvent e) {
        if (e.isCancelled())
            return;
        e.getBlocks().forEach(LocationListDataType::removeBlock);
    }
}
