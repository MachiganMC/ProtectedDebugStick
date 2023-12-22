package be.machigan.protecteddebugstick.action;

import be.machigan.protecteddebugstick.property.PropertyAction;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Wall;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Random;

public class WallAction implements PropertyAction {

    @Override
    public void modify(@NotNull BlockData data, @NotNull Block block, @NotNull BlockFace blockFace) throws ClassCastException {
        Wall wallData = (Wall) data;

        try {
            wallData.getHeight(blockFace);
        } catch (ArrayIndexOutOfBoundsException ignored){
            blockFace = Arrays.asList(BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST)
                    .get(new Random().nextInt(4));
        }

        switch (wallData.getHeight(blockFace)) {
            case NONE:
                wallData.setHeight(blockFace, Wall.Height.LOW);
                break;
            case LOW:
                wallData.setHeight(blockFace, Wall.Height.TALL);
                break;
            case TALL:
                wallData.setHeight(blockFace, Wall.Height.NONE);
                break;
        }

        block.setBlockData(wallData);
    }

    @Override
    public @NotNull String getValue(@NotNull BlockData data, @NotNull BlockFace blockFace) throws ClassCastException {
        try {
            ((Wall) data).getHeight(blockFace);
        } catch (ArrayIndexOutOfBoundsException ignored){
            blockFace = Arrays.asList(BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST)
                    .get(new Random().nextInt(4));
        }

        return ((Wall) data).getHeight(blockFace).name().toLowerCase();
    }
}
