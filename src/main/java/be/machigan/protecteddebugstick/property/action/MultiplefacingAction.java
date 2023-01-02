package be.machigan.protecteddebugstick.property.action;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.MultipleFacing;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class MultiplefacingAction implements PropertyAction {

    @Override
    public void modify(@NotNull BlockData data, @NotNull Block block, @NotNull BlockFace blockFace) throws ClassCastException {
        MultipleFacing multipleFacingData = (MultipleFacing) data;

        if (!multipleFacingData.getAllowedFaces().contains(BlockFace.UP) && blockFace.equals(BlockFace.UP)) {
            blockFace = (BlockFace) multipleFacingData.getAllowedFaces().toArray()[new Random().nextInt(multipleFacingData.getAllowedFaces().size())];
        }
        if (!((MultipleFacing) data).getAllowedFaces().contains(BlockFace.DOWN) && blockFace.equals(BlockFace.DOWN)) {
            blockFace = (BlockFace) multipleFacingData.getAllowedFaces().toArray()[new Random().nextInt(multipleFacingData.getAllowedFaces().size())];
        }

        multipleFacingData.setFace(blockFace, !multipleFacingData.hasFace(blockFace));
        block.setBlockData(multipleFacingData);
    }

    @Override
    public @NotNull String getValue(@NotNull BlockData data, @NotNull BlockFace blockFace) throws ClassCastException {
        MultipleFacing multipleFacingData = (MultipleFacing) data;

        if (!multipleFacingData.getAllowedFaces().contains(BlockFace.UP) && blockFace.equals(BlockFace.UP)) {
            blockFace = (BlockFace) multipleFacingData.getAllowedFaces().toArray()[new Random().nextInt(multipleFacingData.getAllowedFaces().size())];
        }
        if (!((MultipleFacing) data).getAllowedFaces().contains(BlockFace.DOWN) && blockFace.equals(BlockFace.DOWN)) {
            blockFace = (BlockFace) multipleFacingData.getAllowedFaces().toArray()[new Random().nextInt(multipleFacingData.getAllowedFaces().size())];
        }

        return Boolean.toString(multipleFacingData.hasFace(blockFace));
    }
}
