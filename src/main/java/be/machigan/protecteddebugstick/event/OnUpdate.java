package be.machigan.protecteddebugstick.event;

import be.machigan.protecteddebugstick.ProtectedDebugStick;
import be.machigan.protecteddebugstick.def.DebugStick;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.metadata.MetadataValue;

import java.util.List;

public class OnUpdate implements Listener {

    @EventHandler
    public static void onUpdate(BlockPhysicsEvent e) {
        Block block = e.getBlock();
        List<MetadataValue> values = block.getMetadata(DebugStick.METADATA_NAME_FORCE_VALUE);
        if (values.isEmpty())
            return;

        if (values.get(0).asBoolean())
            e.setCancelled(true);
    }

    @EventHandler
    public static void onSeedGrow(BlockGrowEvent e) {
        Block block = e.getBlock();
        List<MetadataValue> values = block.getMetadata(DebugStick.METADATA_NAME_FORCE_VALUE);
        if (values.isEmpty())
            return;

        if (values.get(0).asBoolean())
            e.setCancelled(true);
    }

    @EventHandler
    public static void onBreak(BlockBreakEvent e) {
        if (e.isCancelled())
            return;

        Block block = e.getBlock();
        List<MetadataValue> values = block.getMetadata(DebugStick.METADATA_NAME_FORCE_VALUE);
        if (!values.isEmpty())
            block.removeMetadata(DebugStick.METADATA_NAME_FORCE_VALUE, ProtectedDebugStick.getInstance());
    }

    @EventHandler
    public static void onExtends(BlockPistonExtendEvent e) {
        if (e.isCancelled())
            return;
        for (Block block : e.getBlocks())
            if (block.hasMetadata(DebugStick.METADATA_NAME_FORCE_VALUE))
                block.removeMetadata(DebugStick.METADATA_NAME_FORCE_VALUE, ProtectedDebugStick.getInstance());
    }

    @EventHandler
    public static void onRetracts(BlockPistonRetractEvent e) {
        if (e.isCancelled())
            return;
        for (Block block : e.getBlocks())
            if (block.hasMetadata(DebugStick.METADATA_NAME_FORCE_VALUE))
                block.removeMetadata(DebugStick.METADATA_NAME_FORCE_VALUE, ProtectedDebugStick.getInstance());
    }
}
