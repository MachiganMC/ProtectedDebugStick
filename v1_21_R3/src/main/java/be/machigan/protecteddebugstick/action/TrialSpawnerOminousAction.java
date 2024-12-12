package be.machigan.protecteddebugstick.action;

import be.machigan.protecteddebugstick.property.PropertyAction;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.TrialSpawner;
import org.jetbrains.annotations.NotNull;

public class TrialSpawnerOminousAction implements PropertyAction {
    @Override
    public void modify(@NotNull BlockData data, @NotNull Block block) throws ClassCastException {
        TrialSpawner trialSpawner = (TrialSpawner) data;
        trialSpawner.setOminous(!trialSpawner.isOminous());
        block.setBlockData(trialSpawner);
    }

    @Override
    public @NotNull String getValue(@NotNull BlockData data) throws ClassCastException {
        return Boolean.toString(((TrialSpawner) data).isOminous());
    }
}
