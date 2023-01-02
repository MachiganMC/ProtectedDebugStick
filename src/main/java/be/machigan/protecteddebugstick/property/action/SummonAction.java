package be.machigan.protecteddebugstick.property.action;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.SculkShrieker;
import org.jetbrains.annotations.NotNull;

public class SummonAction implements PropertyAction {

    @Override
    public void modify(@NotNull BlockData data, @NotNull Block block, @NotNull BlockFace blockFace) throws ClassCastException {
        SculkShrieker sculkShrieker = (SculkShrieker) data;

        sculkShrieker.setCanSummon(!sculkShrieker.isCanSummon());

        block.setBlockData(data);
    }

    @Override
    public @NotNull String getValue(@NotNull BlockData data, @NotNull BlockFace blockFace) throws ClassCastException {
        SculkShrieker sculkShrieker = (SculkShrieker) data;
        return Boolean.toString(sculkShrieker.isCanSummon());
    }
}
