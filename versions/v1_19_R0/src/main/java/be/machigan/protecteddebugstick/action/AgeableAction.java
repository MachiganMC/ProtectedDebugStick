package be.machigan.protecteddebugstick.action;

import be.machigan.protecteddebugstick.property.PropertyAction;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.jetbrains.annotations.NotNull;

public class AgeableAction implements PropertyAction {
    @Override
    public void modify(@NotNull BlockData data, @NotNull Block block) throws ClassCastException {
        Ageable ageableData = (Ageable) data;
        if (ageableData.getAge() == ageableData.getMaximumAge()) {
            ageableData.setAge(0);
        } else {
            ageableData.setAge(ageableData.getAge() + 1);
        }

        block.setBlockData(ageableData);
    }

    @Override
    public @NotNull String getValue(@NotNull BlockData data) throws ClassCastException {
        return Integer.toString(((Ageable) data).getAge());
    }
}
