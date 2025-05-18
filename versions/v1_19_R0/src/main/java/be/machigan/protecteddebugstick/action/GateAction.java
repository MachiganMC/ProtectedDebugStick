package be.machigan.protecteddebugstick.action;

import be.machigan.protecteddebugstick.property.PropertyAction;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Gate;
import org.jetbrains.annotations.NotNull;

public class GateAction implements PropertyAction {

    @Override
    public void modify(@NotNull BlockData data, @NotNull Block block) throws ClassCastException {
        Gate gate = (Gate) data;

        gate.setInWall(!gate.isInWall());

        block.setBlockData(gate);
    }

    @Override
    public @NotNull String getValue(@NotNull BlockData data) throws ClassCastException {
        Gate gate = (Gate) data;
        return Boolean.toString(gate.isInWall());
    }
}
