package be.machigan.protecteddebugstick.action;

import be.machigan.protecteddebugstick.property.PropertyAction;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.TrialSpawner;
import org.jetbrains.annotations.NotNull;

public class TrialSpawnerStateAction implements PropertyAction {
    private static final TrialSpawner.State[] VALUES = TrialSpawner.State.values();

    @Override
    public void modify(@NotNull BlockData data, @NotNull Block block) throws ClassCastException {
        TrialSpawner trialSpawner = (TrialSpawner) data;
        int index = trialSpawner.getTrialSpawnerState().ordinal();
        trialSpawner.setTrialSpawnerState(VALUES[index == VALUES.length - 1 ? 0 : index + 1]);
        block.setBlockData(trialSpawner);
    }

    @Override
    public @NotNull String getValue(@NotNull BlockData data) throws ClassCastException {
        return ((TrialSpawner) data).getTrialSpawnerState().name().toLowerCase();
    }
}
