package be.machigan.protecteddebugstick.action;

import be.machigan.protecteddebugstick.property.PropertyAction;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Vault;
import org.jetbrains.annotations.NotNull;

public class VaultStateAction implements PropertyAction {
    private static final Vault.State[] VALUES = Vault.State.values();

    @Override
    public void modify(@NotNull BlockData data, @NotNull Block block) throws ClassCastException {
        Vault vault = (Vault) data;
        int index = vault.getTrialSpawnerState().ordinal();
        vault.setTrialSpawnerState(VALUES[index == VALUES.length - 1 ? 0 : index + 1]);
        block.setBlockData(vault);
    }

    @Override
    public @NotNull String getValue(@NotNull BlockData data) throws ClassCastException {
        return ((Vault) data).getTrialSpawnerState().name().toLowerCase();
    }
}
