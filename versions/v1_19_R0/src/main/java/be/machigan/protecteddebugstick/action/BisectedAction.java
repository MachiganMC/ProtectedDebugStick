package be.machigan.protecteddebugstick.action;

import be.machigan.protecteddebugstick.property.PropertyAction;
import org.bukkit.Bukkit;
import org.bukkit.CropState;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Door;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class BisectedAction implements PropertyAction {
    private final static List<Material> BREAK_BLOCK_TYPE_ON_EDIT = new ArrayList<>(List.of(
            Material.ROSE_BUSH,
            Material.LILAC,
            Material.PEONY,
            Material.SUNFLOWER,
            Material.TALL_GRASS,
            Material.SMALL_DRIPLEAF
    ));

    @Override
    public void modify(@NotNull BlockData data, @NotNull Block block) throws ClassCastException {
        Bisected bisectedData = (Bisected) data;

        switch (bisectedData.getHalf()) {
            case TOP:
                bisectedData.setHalf(Bisected.Half.BOTTOM);
                break;
            case BOTTOM:
                bisectedData.setHalf(Bisected.Half.TOP);
        }
        block.setBlockData(bisectedData, applyPhysics(block, data));
    }

    private static boolean applyPhysics(Block block, BlockData data) {
        if (data instanceof Door) return false;
        return !BREAK_BLOCK_TYPE_ON_EDIT.contains(block.getType());
    }

    @Override
    public @NotNull String getValue(@NotNull BlockData data) throws ClassCastException {
        return ((Bisected) data).getHalf().name().toLowerCase();
    }

    public static void addNewMaterialToBreakTypeOnEdit(Material material) {
        BREAK_BLOCK_TYPE_ON_EDIT.add(material);
    }
}
