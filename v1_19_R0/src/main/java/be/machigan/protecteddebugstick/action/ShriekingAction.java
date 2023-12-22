package be.machigan.protecteddebugstick.action;

import be.machigan.protecteddebugstick.property.PropertyAction;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.SculkShrieker;
import org.jetbrains.annotations.NotNull;

public class ShriekingAction implements PropertyAction {

    @Override
    public void modify(@NotNull BlockData data, @NotNull Block block, @NotNull BlockFace blockFace) throws ClassCastException {
        SculkShrieker sculkShrieker = (SculkShrieker) data;

        sculkShrieker.setShrieking(!sculkShrieker.isShrieking());

        block.setBlockData(sculkShrieker);
    }

    @Override
    public @NotNull String getValue(@NotNull BlockData data, @NotNull BlockFace blockFace) throws ClassCastException {
        SculkShrieker sculkShrieker = (SculkShrieker) data;
        return Boolean.toString(sculkShrieker.isShrieking());
    }
}
