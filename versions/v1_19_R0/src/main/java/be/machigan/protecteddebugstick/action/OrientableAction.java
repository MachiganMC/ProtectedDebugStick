package be.machigan.protecteddebugstick.action;

import be.machigan.protecteddebugstick.property.PropertyAction;
import org.bukkit.Axis;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Orientable;
import org.jetbrains.annotations.NotNull;

public class OrientableAction implements PropertyAction {
    @Override
    public void modify(@NotNull BlockData data, @NotNull Block block) throws ClassCastException {
        Orientable orientableData = (Orientable) data;

        switch (orientableData.getAxis()) {
            case X:
                orientableData.setAxis(block.getType() == Material.NETHER_PORTAL ? Axis.Z : Axis.Y);
                break;
            case Y:
                orientableData.setAxis(Axis.Z);
                break;
            case Z:
                orientableData.setAxis(Axis.X);
                break;
        }
        block.setBlockData(orientableData);
    }

    @Override
    public @NotNull String getValue(@NotNull BlockData data) throws ClassCastException {
        return ((Orientable) data).getAxis().name().toLowerCase();
    }
}
