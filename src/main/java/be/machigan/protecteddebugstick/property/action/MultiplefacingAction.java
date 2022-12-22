package be.machigan.protecteddebugstick.property.action;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.MultipleFacing;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class MultiplefacingAction implements PropertyAction {
    @NotNull private BlockFace clickedBlockFace;

    public MultiplefacingAction(@NotNull BlockFace clickedBlockFace) {
        this.clickedBlockFace = clickedBlockFace;
    }

    @Override
    public @NotNull String modify(@NotNull BlockData data, @NotNull Block block) throws ClassCastException {
        MultipleFacing multipleFacingData = (MultipleFacing) data;

        if (!multipleFacingData.getAllowedFaces().contains(BlockFace.UP) && clickedBlockFace.equals(BlockFace.UP)) {
            clickedBlockFace = (BlockFace) multipleFacingData.getAllowedFaces().toArray()[new Random().nextInt(multipleFacingData.getAllowedFaces().size())];
        }
        if (!((MultipleFacing) data).getAllowedFaces().contains(BlockFace.DOWN) && clickedBlockFace.equals(BlockFace.DOWN)) {
            clickedBlockFace = (BlockFace) multipleFacingData.getAllowedFaces().toArray()[new Random().nextInt(multipleFacingData.getAllowedFaces().size())];
        }

        multipleFacingData.setFace(clickedBlockFace, !multipleFacingData.hasFace(clickedBlockFace));
        block.setBlockData(multipleFacingData);
        return Boolean.toString(multipleFacingData.hasFace(clickedBlockFace)).toUpperCase();
    }

    public void setClickedBlockFace(@NotNull BlockFace clickedBlockFace) {
        this.clickedBlockFace = clickedBlockFace;
    }
}
