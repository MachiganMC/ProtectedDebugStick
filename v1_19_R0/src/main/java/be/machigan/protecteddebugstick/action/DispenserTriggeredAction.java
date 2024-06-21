package be.machigan.protecteddebugstick.action;

import be.machigan.protecteddebugstick.property.PropertyAction;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Dispenser;
import org.jetbrains.annotations.NotNull;

public class DispenserTriggeredAction implements PropertyAction {
    @Override
    public void modify(@NotNull BlockData data, @NotNull Block block) throws ClassCastException {
        Dispenser dispenser = (Dispenser) data;
        dispenser.setTriggered(!dispenser.isTriggered());
        block.setBlockData(dispenser);
    }

    @Override
    public @NotNull String getValue(@NotNull BlockData data) throws ClassCastException {
        return Boolean.toString(((Dispenser) data).isTriggered());
    }
}
