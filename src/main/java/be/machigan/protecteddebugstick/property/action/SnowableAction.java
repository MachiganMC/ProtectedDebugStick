package be.machigan.protecteddebugstick.property.action;

import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Snowable;
import org.jetbrains.annotations.NotNull;

public class SnowableAction implements PropertyAction {
    @Override
    public @NotNull String modify(@NotNull BlockData data, @NotNull Block block) throws ClassCastException {
        Snowable snowableData = (Snowable) data;

        snowableData.setSnowy(!snowableData.isSnowy());

        block.setBlockData(snowableData);
        return Boolean.toString(snowableData.isSnowy());
    }
}
