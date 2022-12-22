package be.machigan.protecteddebugstick.property.action;

import org.bukkit.Axis;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Orientable;
import org.jetbrains.annotations.NotNull;

public class OrientableAction implements PropertyAction {
    @Override
    public @NotNull String modify(@NotNull BlockData data, @NotNull Block block) throws ClassCastException {
        Orientable orientableData = (Orientable) data;

        switch (orientableData.getAxis()) {
            case X:
                orientableData.setAxis(Axis.Y);
                break;
            case Y:
                orientableData.setAxis(Axis.Z);
                break;
            case Z:
                orientableData.setAxis(Axis.X);
                break;
        }
        block.setBlockData(orientableData);
        return orientableData.getAxis().name();
    }
}
