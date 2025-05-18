package be.machigan.protecteddebugstick.action;

import be.machigan.protecteddebugstick.property.PropertyAction;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.RedstoneWire;
import org.jetbrains.annotations.NotNull;

public class RedstoneWireAction implements PropertyAction {
    private final BlockFace blockFace;

    public RedstoneWireAction(BlockFace blockFace) {
        this.blockFace = blockFace;
    }

    @Override
    public void modify(@NotNull BlockData data, @NotNull Block block) throws ClassCastException {
        RedstoneWire redstoneWireData = (RedstoneWire) data;

        switch (redstoneWireData.getFace(this.blockFace)) {
            case NONE:
                redstoneWireData.setFace(this.blockFace, RedstoneWire.Connection.SIDE);
                break;
            case SIDE:
                redstoneWireData.setFace(this.blockFace, RedstoneWire.Connection.UP);
                break;
            case UP:
                redstoneWireData.setFace(this.blockFace, RedstoneWire.Connection.NONE);
                break;
        }

        block.setBlockData(redstoneWireData);
    }

    @Override
    public @NotNull String getValue(@NotNull BlockData data) throws ClassCastException {
        return ((RedstoneWire) data).getFace(blockFace).name().toLowerCase();
    }
}
