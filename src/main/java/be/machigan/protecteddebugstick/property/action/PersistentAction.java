package be.machigan.protecteddebugstick.property.action;

import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Leaves;
import org.jetbrains.annotations.NotNull;

public class PersistentAction implements PropertyAction{
    @Override
    public @NotNull String modify(@NotNull BlockData data, @NotNull Block block) throws ClassCastException {
        Leaves leavesData = (Leaves) data;
        leavesData.setPersistent(!leavesData.isPersistent());
        block.setBlockData(leavesData);
        return Boolean.toString(((Leaves) data).isPersistent()).toUpperCase();
    }
}
