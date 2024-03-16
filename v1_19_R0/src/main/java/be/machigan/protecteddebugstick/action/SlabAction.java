package be.machigan.protecteddebugstick.action;

import be.machigan.protecteddebugstick.property.PropertyAction;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Slab;
import org.jetbrains.annotations.NotNull;

public class SlabAction implements PropertyAction {
    @Override
    public void modify(@NotNull BlockData data, @NotNull Block block) throws ClassCastException {
        Slab slabData = (Slab) data;

        switch (slabData.getType()) {
            case TOP:
                slabData.setType(Slab.Type.BOTTOM);
                break;
            case BOTTOM:
                slabData.setType(Slab.Type.TOP);
                break;
        }

        block.setBlockData(slabData);
    }

    @Override
    public @NotNull String getValue(@NotNull BlockData data) throws ClassCastException {
        return ((Slab) data).getType().name().toLowerCase();
    }
}
