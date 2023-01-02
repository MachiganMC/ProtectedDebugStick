package be.machigan.protecteddebugstick.property.action;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Bamboo;
import org.jetbrains.annotations.NotNull;

public class BambooLeavesAction implements PropertyAction{

    @Override
    public void modify(@NotNull BlockData data, @NotNull Block block, @NotNull BlockFace blockFace) throws ClassCastException {
        Bamboo bambooData = (Bamboo) data;

        switch (bambooData.getLeaves()) {
            case NONE:
                bambooData.setLeaves(Bamboo.Leaves.SMALL);
                break;
            case SMALL:
                bambooData.setLeaves(Bamboo.Leaves.LARGE);
                break;
            case LARGE:
                bambooData.setLeaves(Bamboo.Leaves.NONE);
                break;
        }

        block.setBlockData(bambooData);
    }

    @Override
    public @NotNull String getValue(@NotNull BlockData data, @NotNull BlockFace blockFace) throws ClassCastException {
        Bamboo bambooData = (Bamboo) data;
        return bambooData.getLeaves().name().toLowerCase();
    }
}
