package be.machigan.protecteddebugstick.property.action;

import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Slab;
import org.jetbrains.annotations.NotNull;

public class SlabAction implements PropertyAction{
    @Override
    public @NotNull String modify(@NotNull BlockData data, @NotNull Block block) throws ClassCastException {
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
        return slabData.getType().name();
    }
}
