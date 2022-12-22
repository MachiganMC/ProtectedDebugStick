package be.machigan.protecteddebugstick.property.action;

import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Waterlogged;
import org.jetbrains.annotations.NotNull;

public class WaterLoggedAction implements PropertyAction {
    @Override
    public @NotNull String modify(@NotNull BlockData data, @NotNull Block block) throws ClassCastException {
        Waterlogged waterLoggedData = (Waterlogged) data;
        waterLoggedData.setWaterlogged(!waterLoggedData.isWaterlogged());
        block.setBlockData(waterLoggedData);
        return Boolean.toString(waterLoggedData.isWaterlogged()).toUpperCase();
    }
}
