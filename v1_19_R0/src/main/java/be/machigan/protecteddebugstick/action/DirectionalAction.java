package be.machigan.protecteddebugstick.action;

import be.machigan.protecteddebugstick.property.PropertyAction;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.jetbrains.annotations.NotNull;

public class DirectionalAction implements PropertyAction {
    @Override
    public void modify(@NotNull BlockData data, @NotNull Block block, @NotNull BlockFace blockFace) throws ClassCastException {
        Directional directionalData = (Directional) data;

        if (directionalData.getFaces().size() == 6) {
            switch (directionalData.getFacing()) {
                case NORTH:
                    directionalData.setFacing(BlockFace.WEST);
                    break;
                case WEST:
                    directionalData.setFacing(BlockFace.SOUTH);
                    break;
                case SOUTH:
                    directionalData.setFacing(BlockFace.EAST);
                    break;
                case EAST:
                    directionalData.setFacing(BlockFace.UP);
                    break;
                case UP:
                    directionalData.setFacing(BlockFace.DOWN);
                    break;
                case DOWN:
                    directionalData.setFacing(BlockFace.NORTH);
                    break;
            }

        } else if (directionalData.getFaces().size() == 4) {
            switch (directionalData.getFacing()) {
                case NORTH:
                    directionalData.setFacing(BlockFace.WEST);
                    break;
                case WEST:
                    directionalData.setFacing(BlockFace.SOUTH);
                    break;
                case SOUTH:
                    directionalData.setFacing(BlockFace.EAST);
                    break;
                case EAST:
                    directionalData.setFacing(BlockFace.NORTH);
                    break;
            }
        }

        block.setBlockData(directionalData);
    }

    @Override
    public @NotNull String getValue(@NotNull BlockData data, @NotNull BlockFace blockFace) throws ClassCastException {
        return ((Directional) data).getFacing().name().toLowerCase();
    }
}
