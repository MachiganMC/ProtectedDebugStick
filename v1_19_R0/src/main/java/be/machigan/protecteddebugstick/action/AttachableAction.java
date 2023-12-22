package be.machigan.protecteddebugstick.action;

import be.machigan.protecteddebugstick.property.PropertyAction;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Attachable;
import org.bukkit.block.data.BlockData;
import org.jetbrains.annotations.NotNull;

public class AttachableAction implements PropertyAction {

    @Override
    public void modify(@NotNull BlockData data, @NotNull Block block, @NotNull BlockFace blockFace) throws ClassCastException {
        Attachable attachableData = (Attachable) data;

        attachableData.setAttached(!attachableData.isAttached());

        block.setBlockData(attachableData);
    }

    @Override
    public @NotNull String getValue(@NotNull BlockData data, @NotNull BlockFace blockFace) throws ClassCastException {
        Attachable attachableData = (Attachable) data;
        return Boolean.toString(attachableData.isAttached());
    }
}