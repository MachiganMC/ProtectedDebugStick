package be.machigan.protecteddebugstick.action;

import be.machigan.protecteddebugstick.property.PropertyAction;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Wall;
import org.jetbrains.annotations.NotNull;

public class WallAction implements PropertyAction {
    private final BlockFace blockFace;

    public WallAction(BlockFace blockFace) {
        this.blockFace = blockFace;
    }
    
    @Override
    public void modify(@NotNull BlockData data, @NotNull Block block) throws ClassCastException {
        Wall wallData = (Wall) data;

        switch (wallData.getHeight(this.blockFace)) {
            case NONE:
                wallData.setHeight(this.blockFace, Wall.Height.LOW);
                break;
            case LOW:
                wallData.setHeight(this.blockFace, Wall.Height.TALL);
                break;
            case TALL:
                wallData.setHeight(this.blockFace, Wall.Height.NONE);
                break;
        }

        block.setBlockData(wallData);
    }

    @Override
    public @NotNull String getValue(@NotNull BlockData data) throws ClassCastException {
        return ((Wall) data).getHeight(this.blockFace).name().toLowerCase();
    }
}
