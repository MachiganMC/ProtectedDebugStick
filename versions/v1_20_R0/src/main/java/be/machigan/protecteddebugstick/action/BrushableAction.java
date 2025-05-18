package be.machigan.protecteddebugstick.action;

import be.machigan.protecteddebugstick.property.PropertyAction;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Brushable;
import org.jetbrains.annotations.NotNull;

public class BrushableAction implements PropertyAction {
    @Override
    public void modify(@NotNull BlockData data, @NotNull Block block) throws ClassCastException {
        Brushable brushable = (Brushable) data;
        if (brushable.getDusted() == brushable.getMaximumDusted()) {
            brushable.setDusted(0);
        } else {
            brushable.setDusted(brushable.getDusted() + 1);
        }
        block.setBlockData(brushable);
    }

    @Override
    public @NotNull String getValue(@NotNull BlockData data) throws ClassCastException {
        return Integer.toString(((Brushable) data).getDusted());
    }
}
