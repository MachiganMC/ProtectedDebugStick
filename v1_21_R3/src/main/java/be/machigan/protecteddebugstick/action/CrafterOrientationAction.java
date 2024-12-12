package be.machigan.protecteddebugstick.action;

import be.machigan.protecteddebugstick.property.PropertyAction;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Crafter;
import org.jetbrains.annotations.NotNull;

public class CrafterOrientationAction implements PropertyAction {
    private static final Crafter.Orientation[] VALUES = Crafter.Orientation.values();
    @Override
    public void modify(@NotNull BlockData data, @NotNull Block block) throws ClassCastException {
        Crafter crafter = (Crafter) data;
        int index = crafter.getOrientation().ordinal();
        crafter.setOrientation(VALUES[index == VALUES.length - 1 ? 0 : index + 1]);
        block.setBlockData(crafter);
    }

    @Override
    public @NotNull String getValue(@NotNull BlockData data) throws ClassCastException {
        return ((Crafter) data).getOrientation().name().toLowerCase();
    }
}
