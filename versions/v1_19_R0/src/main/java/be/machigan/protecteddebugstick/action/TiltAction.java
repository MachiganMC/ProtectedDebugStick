package be.machigan.protecteddebugstick.action;

import be.machigan.protecteddebugstick.property.PropertyAction;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.BigDripleaf;
import org.jetbrains.annotations.NotNull;

public class TiltAction implements PropertyAction {

    @Override
    public void modify(@NotNull BlockData data, @NotNull Block block) throws ClassCastException {
        BigDripleaf dripleafData = (BigDripleaf) data;

        switch (dripleafData.getTilt()) {
            case NONE:
                dripleafData.setTilt(BigDripleaf.Tilt.PARTIAL);
                break;
            case PARTIAL:
                dripleafData.setTilt(BigDripleaf.Tilt.FULL);
                break;
            case FULL:
                dripleafData.setTilt(BigDripleaf.Tilt.NONE);
                break;
        }

        block.setBlockData(dripleafData);
    }

    @Override
    public @NotNull String getValue(@NotNull BlockData data) throws ClassCastException {
        BigDripleaf dripleafData = (BigDripleaf) data;
        return dripleafData.getTilt().name().toLowerCase();
    }
}
