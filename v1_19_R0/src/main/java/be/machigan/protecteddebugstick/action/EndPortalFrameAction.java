package be.machigan.protecteddebugstick.action;

import be.machigan.protecteddebugstick.property.PropertyAction;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.EndPortalFrame;
import org.jetbrains.annotations.NotNull;

public class EndPortalFrameAction implements PropertyAction {

    @Override
    public void modify(@NotNull BlockData data, @NotNull Block block, @NotNull BlockFace blockFace) throws ClassCastException {
        EndPortalFrame endPortalFrame = (EndPortalFrame) data;

        endPortalFrame.setEye(!endPortalFrame.hasEye());

        block.setBlockData(endPortalFrame);
    }

    @Override
    public @NotNull String getValue(@NotNull BlockData data, @NotNull BlockFace blockFace) throws ClassCastException {
        EndPortalFrame endPortalFrame = (EndPortalFrame) data;
        return Boolean.toString(endPortalFrame.hasEye());
    }
}
