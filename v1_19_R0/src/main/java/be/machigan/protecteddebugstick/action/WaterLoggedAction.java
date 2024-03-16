package be.machigan.protecteddebugstick.action;

import be.machigan.protecteddebugstick.property.PropertyAction;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Waterlogged;
import org.jetbrains.annotations.NotNull;

public class WaterLoggedAction implements PropertyAction {
    @Override
    public void modify(@NotNull BlockData data, @NotNull Block block) throws ClassCastException {
        Waterlogged waterLoggedData = (Waterlogged) data;

        waterLoggedData.setWaterlogged(!waterLoggedData.isWaterlogged());

        block.setBlockData(waterLoggedData);
    }

    @Override
    public @NotNull String getValue(@NotNull BlockData data) throws ClassCastException {
        return Boolean.toString(((Waterlogged) data).isWaterlogged());
    }
}
