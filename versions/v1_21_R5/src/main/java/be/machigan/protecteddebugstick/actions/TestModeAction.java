package be.machigan.protecteddebugstick.actions;

import be.machigan.protecteddebugstick.property.PropertyAction;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.TestBlock;
import org.jetbrains.annotations.NotNull;

public class TestModeAction implements PropertyAction {
    @Override
    public void modify(@NotNull BlockData data, @NotNull Block block) throws ClassCastException {
        TestBlock testBlock = (TestBlock) data;
        testBlock.setMode(
                switch (testBlock.getMode()) {
                    case LOG -> TestBlock.Mode.FAIL;
                    case FAIL -> TestBlock.Mode.START;
                    case START -> TestBlock.Mode.ACCEPT;
                    case ACCEPT -> TestBlock.Mode.LOG;
                }
        );
        block.setBlockData(testBlock);
    }

    @Override
    public @NotNull String getValue(@NotNull BlockData data) throws ClassCastException {
        return ((TestBlock) data).getMode().toString();
    }
}
