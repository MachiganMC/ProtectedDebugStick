package be.machigan.protecteddebugstick.property;

import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.jetbrains.annotations.NotNull;

public interface PropertyAction {

    void modify(@NotNull BlockData data, @NotNull Block block) throws ClassCastException;

    @NotNull String getValue(@NotNull BlockData data) throws ClassCastException;

    default @NotNull String getValue(Block block) throws ClassCastException {
        return this.getValue(block.getBlockData());
    }
}
