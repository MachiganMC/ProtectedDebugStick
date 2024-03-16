package be.machigan.protecteddebugstick.action;

import be.machigan.protecteddebugstick.property.PropertyAction;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Rotatable;
import org.jetbrains.annotations.NotNull;

public class RotatableAction implements PropertyAction {
    @Override
    public void modify(@NotNull BlockData data, @NotNull Block block) throws ClassCastException {
        Rotatable rotatableData = (Rotatable) data;

        switch (rotatableData.getRotation()) {
            case NORTH:
                rotatableData.setRotation(BlockFace.NORTH_NORTH_EAST);
                break;
            case NORTH_NORTH_EAST:
                rotatableData.setRotation(BlockFace.NORTH_EAST);
                break;
            case NORTH_EAST:
                rotatableData.setRotation(BlockFace.EAST_NORTH_EAST);
                break;
            case EAST_NORTH_EAST:
                rotatableData.setRotation(BlockFace.EAST);
                break;
            case EAST:
                rotatableData.setRotation(BlockFace.EAST_SOUTH_EAST);
                break;
            case EAST_SOUTH_EAST:
                rotatableData.setRotation(BlockFace.SOUTH_EAST);
                break;
            case SOUTH_EAST:
                rotatableData.setRotation(BlockFace.SOUTH_SOUTH_EAST);
                break;
            case SOUTH_SOUTH_EAST:
                rotatableData.setRotation(BlockFace.SOUTH);
                break;
            case SOUTH:
                rotatableData.setRotation(BlockFace.SOUTH_SOUTH_WEST);
                break;
            case SOUTH_SOUTH_WEST:
                rotatableData.setRotation(BlockFace.SOUTH_WEST);
                break;
            case SOUTH_WEST:
                rotatableData.setRotation(BlockFace.WEST_SOUTH_WEST);
                break;
            case WEST_SOUTH_WEST:
                rotatableData.setRotation(BlockFace.WEST);
                break;
            case WEST:
                rotatableData.setRotation(BlockFace.WEST_NORTH_WEST);
                break;
            case WEST_NORTH_WEST:
                rotatableData.setRotation(BlockFace.NORTH_WEST);
                break;
            case NORTH_WEST:
                rotatableData.setRotation(BlockFace.NORTH_NORTH_WEST);
                break;
            case NORTH_NORTH_WEST:
                rotatableData.setRotation(BlockFace.NORTH);
                break;

        }

        block.setBlockData(rotatableData);
    }

    @Override
    public @NotNull String getValue(@NotNull BlockData data) throws ClassCastException {
        return ((Rotatable) data).getRotation().name().toLowerCase();
    }
}
