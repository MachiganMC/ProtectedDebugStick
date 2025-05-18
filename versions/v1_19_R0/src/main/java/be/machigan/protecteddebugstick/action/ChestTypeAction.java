package be.machigan.protecteddebugstick.action;

import be.machigan.protecteddebugstick.property.PropertyAction;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Chest;
import org.jetbrains.annotations.NotNull;

public class ChestTypeAction implements PropertyAction {
    @Override
    public void modify(@NotNull BlockData data, @NotNull Block block) throws ClassCastException {
        Chest chest = (Chest) data;
        switch (chest.getType()) {
            case SINGLE -> chest.setType(Chest.Type.RIGHT);
            case RIGHT -> chest.setType(Chest.Type.LEFT);
            case LEFT -> chest.setType(Chest.Type.SINGLE);
        }
        block.setBlockData(chest);
    }

    @Override
    public @NotNull String getValue(@NotNull BlockData data) throws ClassCastException {
        return ((Chest) data).getType().name().toLowerCase();
    }
}
