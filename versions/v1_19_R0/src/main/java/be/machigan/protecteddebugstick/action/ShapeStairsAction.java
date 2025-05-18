package be.machigan.protecteddebugstick.action;

import be.machigan.protecteddebugstick.property.PropertyAction;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Stairs;
import org.jetbrains.annotations.NotNull;

public class ShapeStairsAction implements PropertyAction {
    @Override
    public void modify(@NotNull BlockData data, @NotNull Block block) throws ClassCastException {
        Stairs stairsData = (Stairs) data;

        switch (stairsData.getShape()) {
            case STRAIGHT:
                stairsData.setShape(Stairs.Shape.INNER_RIGHT);
                break;
            case INNER_RIGHT:
                stairsData.setShape(Stairs.Shape.OUTER_LEFT);
                break;
            case OUTER_LEFT:
                stairsData.setShape(Stairs.Shape.INNER_LEFT);
                break;
            case INNER_LEFT:
                stairsData.setShape(Stairs.Shape.OUTER_RIGHT);
                break;
            case OUTER_RIGHT:
                stairsData.setShape(Stairs.Shape.STRAIGHT);
                break;
        }

        block.setBlockData(stairsData);
    }

    @Override
    public @NotNull String getValue(@NotNull BlockData data) throws ClassCastException {
        return ((Stairs) data).getShape().name().toLowerCase();
    }
}
