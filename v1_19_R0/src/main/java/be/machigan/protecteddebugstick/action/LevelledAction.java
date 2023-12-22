package be.machigan.protecteddebugstick.action;

import be.machigan.protecteddebugstick.property.PropertyAction;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Levelled;
import org.jetbrains.annotations.NotNull;

public class LevelledAction implements PropertyAction {

    @Override
    public void modify(@NotNull BlockData data, @NotNull Block block, @NotNull BlockFace blockFace) throws ClassCastException {
        Levelled levelled = (Levelled) data;

        if (levelled.getLevel() == levelled.getMaximumLevel()) {
            levelled.setLevel(1);
        } else {
            levelled.setLevel(levelled.getLevel() + 1);
        }

        block.setBlockData(levelled);
    }

    @Override
    public @NotNull String getValue(@NotNull BlockData data, @NotNull BlockFace blockFace) throws ClassCastException {
        Levelled levelled = (Levelled) data;
        return Integer.toString(levelled.getLevel());
    }
}
