package be.machigan.protecteddebugstick.property.action;

import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Snow;
import org.jetbrains.annotations.NotNull;

public class LayersAction implements PropertyAction {
    @Override
    public @NotNull String modify(@NotNull BlockData data, @NotNull Block block) throws ClassCastException {
        Snow snowData = (Snow) data;

        if (snowData.getLayers() == snowData.getMaximumLayers()) {
            snowData.setLayers(((Snow) data).getMinimumLayers());
        } else {
            snowData.setLayers(snowData.getLayers() + 1);
        }

        block.setBlockData(snowData);
        return Integer.toString(snowData.getLayers());
    }
}
