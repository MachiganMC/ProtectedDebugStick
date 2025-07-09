package be.machigan.protecteddebugstick.action;

import be.machigan.protecteddebugstick.property.PropertyAction;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Bed;
import org.jetbrains.annotations.NotNull;

public class BedPartAction implements PropertyAction {
    @Override
    public void modify(@NotNull BlockData data, @NotNull Block block) throws ClassCastException {
        Bed bed = (Bed) data;
        bed.setPart(
                bed.getPart() == Bed.Part.HEAD ? Bed.Part.FOOT : Bed.Part.HEAD
        );
        block.setBlockData(bed, false);
    }

    @Override
    public @NotNull String getValue(@NotNull BlockData data) throws ClassCastException {
        return ((Bed) data).getPart().name().toLowerCase();
    }
}
