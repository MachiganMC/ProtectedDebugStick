package be.machigan.protecteddebugstick.property.action;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Waterlogged;
import org.jetbrains.annotations.NotNull;

public class WaterLoggedAction implements PropertyAction {
    @Override
    public @NotNull void modify(@NotNull BlockData data, @NotNull Block block, @NotNull BlockFace blockFace) throws ClassCastException {
        Waterlogged waterLoggedData = (Waterlogged) data;

        waterLoggedData.setWaterlogged(!waterLoggedData.isWaterlogged());

        block.setBlockData(waterLoggedData);
    }

    @Override
    public @NotNull String getValue(@NotNull BlockData data, @NotNull BlockFace blockFace) throws ClassCastException {
        return Boolean.toString(((Waterlogged) data).isWaterlogged());
    }
}
