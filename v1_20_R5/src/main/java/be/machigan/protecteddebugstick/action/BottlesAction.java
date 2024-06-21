package be.machigan.protecteddebugstick.action;

import be.machigan.protecteddebugstick.property.PropertyAction;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.BrewingStand;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class BottlesAction implements PropertyAction {
    private static final List<Integer> BOTTLES_EMPLACEMENT = Arrays.asList(0, 1, 2);
    @Override
    public void modify(@NotNull BlockData data, @NotNull Block block) throws ClassCastException {
        BrewingStand brewingStand = (BrewingStand) data;
        if (brewingStand.getBottles().isEmpty()) {
            brewingStand.setBottle(0, true);
        } else if (brewingStand.getBottles().size() == brewingStand.getMaximumBottles()) {
            BOTTLES_EMPLACEMENT.forEach(e -> brewingStand.setBottle(e, false));
        } else {
            brewingStand.setBottle(BOTTLES_EMPLACEMENT.stream().filter(e -> !brewingStand.getBottles().contains(e)).findFirst().stream().findFirst().get(), true);
        }
        block.setBlockData(brewingStand);
    }

    @Override
    public @NotNull String getValue(@NotNull BlockData data) throws ClassCastException {
        return Integer.toString(((BrewingStand) data).getBottles().size());
    }
}
