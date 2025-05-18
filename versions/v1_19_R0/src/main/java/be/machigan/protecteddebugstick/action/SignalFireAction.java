package be.machigan.protecteddebugstick.action;

import be.machigan.protecteddebugstick.property.PropertyAction;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Campfire;
import org.jetbrains.annotations.NotNull;

public class SignalFireAction implements PropertyAction {

    @Override
    public void modify(@NotNull BlockData data, @NotNull Block block) throws ClassCastException {
        Campfire campfireData = (Campfire) data;

        campfireData.setSignalFire(!campfireData.isSignalFire());

        block.setBlockData(campfireData);
    }

    @Override
    public @NotNull String getValue(@NotNull BlockData data) throws ClassCastException {
        Campfire campfireData = (Campfire) data;
        return Boolean.toString(campfireData.isSignalFire());
    }
}
