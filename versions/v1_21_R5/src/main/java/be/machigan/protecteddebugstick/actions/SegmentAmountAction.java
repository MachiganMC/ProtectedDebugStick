package be.machigan.protecteddebugstick.actions;

import be.machigan.protecteddebugstick.property.PropertyAction;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.LeafLitter;
import org.jetbrains.annotations.NotNull;

public class SegmentAmountAction implements PropertyAction {
    @Override
    public void modify(@NotNull BlockData data, @NotNull Block block) throws ClassCastException {
        LeafLitter leafLitter = (LeafLitter) data;
        if (leafLitter.getSegmentAmount() == leafLitter.getMaximumSegmentAmount()) {
            leafLitter.setSegmentAmount(1);
        } else {
            leafLitter.setSegmentAmount(leafLitter.getSegmentAmount() + 1);
        }
        block.setBlockData(leafLitter);
    }

    @Override
    public @NotNull String getValue(@NotNull BlockData data) throws ClassCastException {
        return String.valueOf(((LeafLitter) data).getSegmentAmount());
    }
}
