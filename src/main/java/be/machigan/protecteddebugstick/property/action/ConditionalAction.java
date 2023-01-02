package be.machigan.protecteddebugstick.property.action;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.CommandBlock;
import org.jetbrains.annotations.NotNull;

public class ConditionalAction implements PropertyAction {

    @Override
    public void modify(@NotNull BlockData data, @NotNull Block block, @NotNull BlockFace blockFace) throws ClassCastException {
        CommandBlock commandBlock = (CommandBlock) data;

        commandBlock.setConditional(!commandBlock.isConditional());

        block.setBlockData(commandBlock);
    }

    @Override
    public @NotNull String getValue(@NotNull BlockData data, @NotNull BlockFace blockFace) throws ClassCastException {
        CommandBlock commandBlock = (CommandBlock) data;
        return Boolean.toString(commandBlock.isConditional());
    }
}
