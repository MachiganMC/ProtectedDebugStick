package be.machigan.protecteddebugstick.action;

import be.machigan.protecteddebugstick.property.PropertyAction;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Repeater;
import org.jetbrains.annotations.NotNull;

public class LockedAction implements PropertyAction {

    @Override
    public void modify(@NotNull BlockData data, @NotNull Block block) throws ClassCastException {
        Repeater repeater = (Repeater) data;

        repeater.setLocked(!repeater.isLocked());

        block.setBlockData(repeater);
    }

    @Override
    public @NotNull String getValue(@NotNull BlockData data) throws ClassCastException {
        Repeater repeater = (Repeater) data;
        return Boolean.toString(repeater.isLocked());
    }
}
