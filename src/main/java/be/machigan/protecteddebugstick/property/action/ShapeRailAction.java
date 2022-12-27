package be.machigan.protecteddebugstick.property.action;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Rail;
import org.jetbrains.annotations.NotNull;

public class ShapeRailAction implements PropertyAction{
    @Override
    public @NotNull void modify(@NotNull BlockData data, @NotNull Block block, @NotNull BlockFace blockFace) throws ClassCastException {
        Rail railData = (Rail) data;

        if (railData.getShapes().size() == 10) {
            switch (railData.getShape()) {
                case ASCENDING_NORTH:
                    railData.setShape(Rail.Shape.ASCENDING_EAST);
                    break;
                case ASCENDING_EAST:
                    railData.setShape(Rail.Shape.ASCENDING_SOUTH);
                    break;
                case ASCENDING_SOUTH:
                    railData.setShape(Rail.Shape.ASCENDING_WEST);
                    break;
                case ASCENDING_WEST:
                    railData.setShape(Rail.Shape.NORTH_EAST);
                    break;
                case NORTH_EAST:
                    railData.setShape(Rail.Shape.NORTH_SOUTH);
                    break;
                case NORTH_SOUTH:
                    railData.setShape(Rail.Shape.NORTH_WEST);
                    break;
                case NORTH_WEST:
                    railData.setShape(Rail.Shape.EAST_WEST);
                    break;
                case EAST_WEST:
                    railData.setShape(Rail.Shape.SOUTH_EAST);
                    break;
                case SOUTH_EAST:
                    railData.setShape(Rail.Shape.SOUTH_WEST);
                    break;
                case SOUTH_WEST:
                    railData.setShape(Rail.Shape.ASCENDING_NORTH);
                    break;
            }

        } else if (railData.getShapes().size() == 6) {
            switch (railData.getShape()) {
                case NORTH_SOUTH:
                    railData.setShape(Rail.Shape.EAST_WEST);
                    break;
                case EAST_WEST:
                    railData.setShape(Rail.Shape.ASCENDING_EAST);
                    break;
                case ASCENDING_EAST:
                    railData.setShape(Rail.Shape.ASCENDING_WEST);
                    break;
                case ASCENDING_WEST:
                    railData.setShape(Rail.Shape.ASCENDING_NORTH);
                    break;
                case ASCENDING_NORTH:
                    railData.setShape(Rail.Shape.ASCENDING_SOUTH);
                    break;
                case ASCENDING_SOUTH:
                    railData.setShape(Rail.Shape.NORTH_SOUTH);
                    break;
            }
        }

        block.setBlockData(railData);
    }

    @Override
    public @NotNull String getValue(@NotNull BlockData data, @NotNull BlockFace blockFace) throws ClassCastException {
        return ((Rail) data).getShape().name().toLowerCase();
    }
}
