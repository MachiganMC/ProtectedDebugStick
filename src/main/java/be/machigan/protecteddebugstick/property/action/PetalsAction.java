package be.machigan.protecteddebugstick.property.action;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.PinkPetals;
import org.jetbrains.annotations.NotNull;

public class PetalsAction implements PropertyAction {
    @Override
    public void modify(@NotNull BlockData data, @NotNull Block block, @NotNull BlockFace blockFace) throws ClassCastException {
        PinkPetals petals = (PinkPetals) data;
        petals.setFlowerAmount(
                petals.getFlowerAmount() == petals.getMaximumFlowerAmount() ?
                        1 :
                        petals.getFlowerAmount() + 1
        );
        block.setBlockData(petals);
    }

    @Override
    public @NotNull String getValue(@NotNull BlockData data, @NotNull BlockFace blockFace) throws ClassCastException {
        return Integer.toString(((PinkPetals) data).getFlowerAmount());
    }
}
