package be.machigan.protecteddebugstick.action;

import be.machigan.protecteddebugstick.property.PropertyAction;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.SculkSensor;
import org.jetbrains.annotations.NotNull;

public class PhaseAction implements PropertyAction {

    @Override
    public void modify(@NotNull BlockData data, @NotNull Block block, @NotNull BlockFace blockFace) throws ClassCastException {
        SculkSensor sculkSensor = (SculkSensor) data;

        switch (sculkSensor.getPhase()) {
            case INACTIVE:
                sculkSensor.setPhase(SculkSensor.Phase.ACTIVE);
                break;
            case ACTIVE:
                sculkSensor.setPhase(SculkSensor.Phase.COOLDOWN);
                break;
            case COOLDOWN:
                sculkSensor.setPhase(SculkSensor.Phase.INACTIVE);
                break;
        }

        block.setBlockData(sculkSensor);
    }

    @Override
    public @NotNull String getValue(@NotNull BlockData data, @NotNull BlockFace blockFace) throws ClassCastException {
        SculkSensor sculkSensor = (SculkSensor) data;
        return sculkSensor.getPhase().name().toLowerCase();
    }
}
