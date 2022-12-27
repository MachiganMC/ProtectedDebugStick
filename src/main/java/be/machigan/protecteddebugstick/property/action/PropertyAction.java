package be.machigan.protecteddebugstick.property.action;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.jetbrains.annotations.NotNull;

public interface PropertyAction {

    @NotNull void modify(@NotNull BlockData data, @NotNull Block block, @NotNull BlockFace blockFace) throws ClassCastException;

    @NotNull String getValue(@NotNull BlockData data, @NotNull BlockFace blockFace) throws ClassCastException;
}
