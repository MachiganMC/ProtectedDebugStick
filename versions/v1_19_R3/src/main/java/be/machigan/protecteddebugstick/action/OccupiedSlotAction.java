package be.machigan.protecteddebugstick.action;

import be.machigan.protecteddebugstick.property.PropertyAction;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.ChiseledBookshelf;
import org.jetbrains.annotations.NotNull;

public class OccupiedSlotAction implements PropertyAction {
    @Override
    public void modify(@NotNull BlockData data, @NotNull Block block) throws ClassCastException {
        ChiseledBookshelf bookshelf = (ChiseledBookshelf) data;
        boolean setOccupied = !bookshelf.isSlotOccupied(bookshelf.getMaximumOccupiedSlots() - 1);
        for (int slot = 0; slot < bookshelf.getMaximumOccupiedSlots(); slot++) {
            if (bookshelf.isSlotOccupied(slot) != setOccupied) {
                bookshelf.setSlotOccupied(slot, setOccupied);
                break;
            }
        }
        block.setBlockData(bookshelf);
    }

    @Override
    public @NotNull String getValue(@NotNull BlockData data) throws ClassCastException {
        StringBuilder builder = new StringBuilder("slots [");
        ChiseledBookshelf bookshelf = (ChiseledBookshelf) data;
        for (int slot = 0; slot < bookshelf.getMaximumOccupiedSlots(); slot++) {
            builder.append(slot + 1)
                    .append("➜")
                    .append(bookshelf.isSlotOccupied(slot) ? "✔" : "✘");
            if (slot < bookshelf.getMaximumOccupiedSlots() - 1)
                builder.append(", ");
        }
        builder.append("]");
        return builder.toString();
    }
}
