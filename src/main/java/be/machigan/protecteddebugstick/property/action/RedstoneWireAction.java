package be.machigan.protecteddebugstick.property.action;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.RedstoneWire;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class RedstoneWireAction implements PropertyAction {
    @Override
    public @NotNull void modify(@NotNull BlockData data, @NotNull Block block, @NotNull BlockFace blockFace) throws ClassCastException {
        RedstoneWire redstoneWireData = (RedstoneWire) data;

        try {
            redstoneWireData.getFace(blockFace);
            block.getFace(block);

        } catch (IllegalArgumentException ignored) {
            blockFace = (BlockFace) redstoneWireData.getAllowedFaces().toArray()[new Random().nextInt(redstoneWireData.getAllowedFaces().size())];
        }

        switch (redstoneWireData.getFace(blockFace)) {
            case NONE:
                redstoneWireData.setFace(blockFace, RedstoneWire.Connection.SIDE);
                break;
            case SIDE:
                redstoneWireData.setFace(blockFace, RedstoneWire.Connection.UP);
                break;
            case UP:
                redstoneWireData.setFace(blockFace, RedstoneWire.Connection.NONE);
                break;
        }

        block.setBlockData(redstoneWireData);
    }

    @Override
    public @NotNull String getValue(@NotNull BlockData data, @NotNull BlockFace blockFace) throws ClassCastException {
        RedstoneWire redstoneWireData = (RedstoneWire) data;

        try {
            redstoneWireData.getFace(blockFace);
        } catch (IllegalArgumentException ignored) {
            blockFace = (BlockFace) redstoneWireData.getAllowedFaces().toArray()[new Random().nextInt(redstoneWireData.getAllowedFaces().size())];
        }

        return blockFace.name().toLowerCase() + " facing " + ((RedstoneWire) data).getFace(blockFace).name().toLowerCase();
    }
}
