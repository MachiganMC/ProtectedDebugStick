package be.machigan.protecteddebugstick.action;

import be.machigan.protecteddebugstick.property.PropertyAction;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.SculkShrieker;
import org.jetbrains.annotations.NotNull;

public class SummonAction implements PropertyAction {

    @Override
    public void modify(@NotNull BlockData data, @NotNull Block block) throws ClassCastException {
        SculkShrieker sculkShrieker = (SculkShrieker) data;

        sculkShrieker.setCanSummon(!sculkShrieker.isCanSummon());

        block.setBlockData(data);
    }

    @Override
    public @NotNull String getValue(@NotNull BlockData data) throws ClassCastException {
        SculkShrieker sculkShrieker = (SculkShrieker) data;
        return Boolean.toString(sculkShrieker.isCanSummon());
    }
}
