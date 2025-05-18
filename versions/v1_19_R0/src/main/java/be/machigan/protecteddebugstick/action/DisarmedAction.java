package be.machigan.protecteddebugstick.action;

import be.machigan.protecteddebugstick.property.PropertyAction;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Tripwire;
import org.jetbrains.annotations.NotNull;

public class DisarmedAction implements PropertyAction {

    @Override
    public void modify(@NotNull BlockData data, @NotNull Block block) throws ClassCastException {
        Tripwire tripwire = (Tripwire) data;

        tripwire.setDisarmed(!tripwire.isDisarmed());

        block.setBlockData(tripwire);
    }

    @Override
    public @NotNull String getValue(@NotNull BlockData data) throws ClassCastException {
        Tripwire tripwire = (Tripwire) data;
        return Boolean.toString(tripwire.isDisarmed());
    }
}
