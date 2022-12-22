package be.machigan.protecteddebugstick.property.action;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Wall;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Random;

public class WallAction implements PropertyAction {
    @NotNull private BlockFace clickedBlockFace;

    public WallAction(BlockFace clickedBlockFace) {
        this.clickedBlockFace = clickedBlockFace;
    }

    @Override
    public @NotNull String modify(@NotNull BlockData data, @NotNull Block block) throws ClassCastException {
        Wall wallData = (Wall) data;

        try {
            ((Wall) data).getHeight(clickedBlockFace);
        } catch (ArrayIndexOutOfBoundsException ignored){
            clickedBlockFace = Arrays.asList(BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST)
                    .get(new Random().nextInt(4));
        }

        switch (wallData.getHeight(clickedBlockFace)) {
            case NONE:
                wallData.setHeight(clickedBlockFace, Wall.Height.LOW);
                break;
            case LOW:
                wallData.setHeight(clickedBlockFace, Wall.Height.TALL);
                break;
            case TALL:
                wallData.setHeight(clickedBlockFace, Wall.Height.NONE);
                break;
        }
        block.setBlockData(wallData);
        return wallData.getHeight(clickedBlockFace).name();
    }

    public void setClickedBlockFace(@NotNull BlockFace clickedBlockFace) {
        this.clickedBlockFace = clickedBlockFace;
    }
}
