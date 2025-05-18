package be.machigan.protecteddebugstick.action;

import be.machigan.protecteddebugstick.property.PropertyAction;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.CommandBlock;
import org.jetbrains.annotations.NotNull;

public class ConditionalAction implements PropertyAction {

    @Override
    public void modify(@NotNull BlockData data, @NotNull Block block) throws ClassCastException {
        CommandBlock commandBlock = (CommandBlock) data;

        commandBlock.setConditional(!commandBlock.isConditional());

        block.setBlockData(commandBlock);
    }

    @Override
    public @NotNull String getValue(@NotNull BlockData data) throws ClassCastException {
        CommandBlock commandBlock = (CommandBlock) data;
        return Boolean.toString(commandBlock.isConditional());
    }
}
