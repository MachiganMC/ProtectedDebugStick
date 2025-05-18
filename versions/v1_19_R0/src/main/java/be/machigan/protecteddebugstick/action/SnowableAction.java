package be.machigan.protecteddebugstick.action;

import be.machigan.protecteddebugstick.property.PropertyAction;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Snowable;
import org.jetbrains.annotations.NotNull;

public class SnowableAction implements PropertyAction {
    @Override
    public void modify(@NotNull BlockData data, @NotNull Block block) throws ClassCastException {
        Snowable snowableData = (Snowable) data;

        snowableData.setSnowy(!snowableData.isSnowy());

        block.setBlockData(snowableData);
    }

    @Override
    public @NotNull String getValue(@NotNull BlockData data) throws ClassCastException {
        return Boolean.toString(((Snowable) data).isSnowy());
    }
}
