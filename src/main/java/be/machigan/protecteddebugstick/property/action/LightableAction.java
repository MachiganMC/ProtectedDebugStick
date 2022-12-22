package be.machigan.protecteddebugstick.property.action;

import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Lightable;
import org.jetbrains.annotations.NotNull;

public class LightableAction implements PropertyAction{
    @Override
    public @NotNull String modify(@NotNull BlockData data, @NotNull Block block) throws ClassCastException {
        Lightable lightableData = (Lightable) data;

        lightableData.setLit(!lightableData.isLit());

        block.setBlockData(lightableData);
        return Boolean.toString(lightableData.isLit()).toUpperCase();
    }
}
