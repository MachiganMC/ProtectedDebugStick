package be.machigan.protecteddebugstick.action;

import be.machigan.protecteddebugstick.property.PropertyAction;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Openable;
import org.jetbrains.annotations.NotNull;

public class OpenableAction implements PropertyAction {

    @Override
    public void modify(@NotNull BlockData data, @NotNull Block block) throws ClassCastException {
        Openable openable = (Openable) data;

        openable.setOpen(!openable.isOpen());

        block.setBlockData(openable);
    }

    @Override
    public @NotNull String getValue(@NotNull BlockData data) throws ClassCastException {
        Openable openable = (Openable) data;
        return Boolean.toString(openable.isOpen());
    }
}
