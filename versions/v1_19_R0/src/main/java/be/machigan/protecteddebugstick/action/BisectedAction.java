package be.machigan.protecteddebugstick.action;

import be.machigan.protecteddebugstick.property.PropertyAction;
import org.bukkit.block.Block;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.BlockData;
import org.jetbrains.annotations.NotNull;

public class BisectedAction implements PropertyAction {
    @Override
    public void modify(@NotNull BlockData data, @NotNull Block block) throws ClassCastException {
        Bisected bisectedData = (Bisected) data;

        switch (bisectedData.getHalf()) {
            case TOP:
                bisectedData.setHalf(Bisected.Half.BOTTOM);
                break;
            case BOTTOM:
                bisectedData.setHalf(Bisected.Half.TOP);
        }

        block.setBlockData(bisectedData);
    }

    @Override
    public @NotNull String getValue(@NotNull BlockData data) throws ClassCastException {
        return ((Bisected) data).getHalf().name().toLowerCase();
    }
}
