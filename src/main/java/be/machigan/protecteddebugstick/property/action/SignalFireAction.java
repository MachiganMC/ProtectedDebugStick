package be.machigan.protecteddebugstick.property.action;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Campfire;
import org.jetbrains.annotations.NotNull;

public class SignalFireAction implements PropertyAction{

    @Override
    public void modify(@NotNull BlockData data, @NotNull Block block, @NotNull BlockFace blockFace) throws ClassCastException {
        Campfire campfireData = (Campfire) data;

        campfireData.setSignalFire(!campfireData.isSignalFire());

        block.setBlockData(campfireData);
    }

    @Override
    public @NotNull String getValue(@NotNull BlockData data, @NotNull BlockFace blockFace) throws ClassCastException {
        Campfire campfireData = (Campfire) data;
        return Boolean.toString(campfireData.isSignalFire());
    }
}
