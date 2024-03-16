package be.machigan.protecteddebugstick.action;

import be.machigan.protecteddebugstick.property.PropertyAction;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.CaveVinesPlant;
import org.jetbrains.annotations.NotNull;

public class BerryAction implements PropertyAction {

    @Override
    public void modify(@NotNull BlockData data, @NotNull Block block) throws ClassCastException {
        CaveVinesPlant caveVinesPlant = (CaveVinesPlant) data;

        caveVinesPlant.setBerries(!caveVinesPlant.isBerries());

        block.setBlockData(caveVinesPlant);
    }

    @Override
    public @NotNull String getValue(@NotNull BlockData data) throws ClassCastException {
        CaveVinesPlant caveVinesPlant = (CaveVinesPlant) data;
        return Boolean.toString(caveVinesPlant.isBerries());
    }
}
