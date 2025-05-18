package be.machigan.protecteddebugstick.action;

import be.machigan.protecteddebugstick.property.PropertyAction;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Snow;
import org.jetbrains.annotations.NotNull;

public class LayersAction implements PropertyAction {
    @Override
    public void modify(@NotNull BlockData data, @NotNull Block block) throws ClassCastException {
        Snow snowData = (Snow) data;

        if (snowData.getLayers() == snowData.getMaximumLayers()) {
            snowData.setLayers(((Snow) data).getMinimumLayers());
        } else {
            snowData.setLayers(snowData.getLayers() + 1);
        }

        block.setBlockData(snowData);
    }

    @Override
    public @NotNull String getValue(@NotNull BlockData data) throws ClassCastException {
        return Integer.toString(((Snow) data).getLayers());
    }
}
