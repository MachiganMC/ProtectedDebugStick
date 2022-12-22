package be.machigan.protecteddebugstick.property.action;

import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.jetbrains.annotations.NotNull;

public interface PropertyAction {

    @NotNull String modify(@NotNull BlockData data, @NotNull Block block) throws ClassCastException;
}
