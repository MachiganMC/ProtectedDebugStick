package be.machigan.protecteddebugstick.action;

import be.machigan.protecteddebugstick.property.PropertyAction;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Hangable;
import org.jetbrains.annotations.NotNull;

public class HangableAction implements PropertyAction {

    @Override
    public void modify(@NotNull BlockData data, @NotNull Block block) throws ClassCastException {
        Hangable hangableData = (Hangable) data;

        hangableData.setHanging(!hangableData.isHanging());

        block.setBlockData(hangableData);
    }

    @Override
    public @NotNull String getValue(@NotNull BlockData data) throws ClassCastException {
        Hangable hangableData = (Hangable) data;
        return Boolean.toString(hangableData.isHanging());
    }
}
