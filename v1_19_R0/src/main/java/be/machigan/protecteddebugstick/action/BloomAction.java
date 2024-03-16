package be.machigan.protecteddebugstick.action;

import be.machigan.protecteddebugstick.property.PropertyAction;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.SculkCatalyst;
import org.jetbrains.annotations.NotNull;

public class BloomAction implements PropertyAction {

    @Override
    public void modify(@NotNull BlockData data, @NotNull Block block) throws ClassCastException {
        SculkCatalyst sculkCatalyst = (SculkCatalyst) data;

        sculkCatalyst.setBloom(!sculkCatalyst.isBloom());

        block.setBlockData(sculkCatalyst);
    }

    @Override
    public @NotNull String getValue(@NotNull BlockData data) throws ClassCastException {
        SculkCatalyst sculkCatalyst = (SculkCatalyst) data;
        return Boolean.toString(sculkCatalyst.isBloom());
    }
}
