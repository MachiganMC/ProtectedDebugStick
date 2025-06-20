package be.machigan.protecteddebugstick.actions;

import be.machigan.protecteddebugstick.property.PropertyAction;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.block.data.BlockData;
import org.jetbrains.annotations.NotNull;

public class WaxedAction implements PropertyAction {
    @Override
    public void modify(@NotNull BlockData data, @NotNull Block block) throws ClassCastException {
        Sign sign = (Sign) block.getState();
        sign.setWaxed(!sign.isWaxed());
        sign.update();
    }

    @Override
    public @NotNull String getValue(@NotNull BlockData data) throws ClassCastException {
        return "";
    }

    @Override
    public @NotNull String getValue(Block block) throws ClassCastException {
        return String.valueOf(((Sign) block.getState()).isWaxed());
    }
}
