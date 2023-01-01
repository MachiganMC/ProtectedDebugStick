package be.machigan.protecteddebugstick.property.action;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.BlockData;
import org.jetbrains.annotations.NotNull;

public class BisectedAction implements PropertyAction{
    @Override
    public void modify(@NotNull BlockData data, @NotNull Block block, @NotNull BlockFace blockFace) throws ClassCastException {
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
    public @NotNull String getValue(@NotNull BlockData data, @NotNull BlockFace blockFace) throws ClassCastException {
        return ((Bisected) data).getHalf().name().toLowerCase();
    }
}
