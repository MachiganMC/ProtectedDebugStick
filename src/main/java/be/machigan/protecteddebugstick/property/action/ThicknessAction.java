package be.machigan.protecteddebugstick.property.action;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.PointedDripstone;
import org.jetbrains.annotations.NotNull;

public class ThicknessAction implements PropertyAction {

    @Override
    public void modify(@NotNull BlockData data, @NotNull Block block, @NotNull BlockFace blockFace) throws ClassCastException {
        PointedDripstone dripstone = (PointedDripstone) data;

        switch (dripstone.getThickness()) {
            case TIP:
                dripstone.setThickness(PointedDripstone.Thickness.TIP_MERGE);
                break;
            case TIP_MERGE:
                dripstone.setThickness(PointedDripstone.Thickness.FRUSTUM);
                break;
            case FRUSTUM:
                dripstone.setThickness(PointedDripstone.Thickness.MIDDLE);
                break;
            case MIDDLE:
                dripstone.setThickness(PointedDripstone.Thickness.BASE);
                break;
            case BASE:
                dripstone.setThickness(PointedDripstone.Thickness.TIP);
                break;
        }

        block.setBlockData(dripstone);
    }

    @Override
    public @NotNull String getValue(@NotNull BlockData data, @NotNull BlockFace blockFace) throws ClassCastException {
        PointedDripstone dripstone = (PointedDripstone) data;
        return dripstone.getThickness().name().toLowerCase();
    }
}
