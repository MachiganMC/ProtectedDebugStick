package be.machigan.protecteddebugstick.action;

import be.machigan.protecteddebugstick.property.PropertyAction;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.RespawnAnchor;
import org.jetbrains.annotations.NotNull;

public class ChargesAction implements PropertyAction {

    @Override
    public void modify(@NotNull BlockData data, @NotNull Block block) throws ClassCastException {
        RespawnAnchor respawnAnchor = (RespawnAnchor) data;

        if (respawnAnchor.getCharges() == respawnAnchor.getMaximumCharges()) {
            respawnAnchor.setCharges(0);
        } else {
            respawnAnchor.setCharges(respawnAnchor.getCharges() + 1);
        }

        block.setBlockData(respawnAnchor);
    }

    @Override
    public @NotNull String getValue(@NotNull BlockData data) throws ClassCastException {
        RespawnAnchor respawnAnchor = (RespawnAnchor) data;
        return Integer.toString(respawnAnchor.getCharges());
    }
}
