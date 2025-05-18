package be.machigan.protecteddebugstick.action;

import be.machigan.protecteddebugstick.property.PropertyAction;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Vault;
import org.jetbrains.annotations.NotNull;

public class VaultOminousAction implements PropertyAction {
    @Override
    public void modify(@NotNull BlockData data, @NotNull Block block) throws ClassCastException {
        Vault vault = (Vault) data;
        vault.setOminous(!vault.isOminous());
        block.setBlockData(vault);
    }

    @Override
    public @NotNull String getValue(@NotNull BlockData data) throws ClassCastException {
        return Boolean.toString(((Vault) data).isOminous());
    }
}
