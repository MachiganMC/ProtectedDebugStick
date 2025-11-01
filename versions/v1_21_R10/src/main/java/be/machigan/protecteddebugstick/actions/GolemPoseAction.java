package be.machigan.protecteddebugstick.actions;

import be.machigan.protecteddebugstick.property.PropertyAction;
import org.bukkit.block.Block;

import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.CopperGolemStatue;
import org.jetbrains.annotations.NotNull;

public class GolemPoseAction implements PropertyAction {
    @Override
    public void modify(@NotNull BlockData data, @NotNull Block block) throws ClassCastException {
        CopperGolemStatue statue = (CopperGolemStatue) data;
        statue.setCopperGolemPose(
                switch (statue.getCopperGolemPose()) {
                    case STANDING -> CopperGolemStatue.CopperGolemPose.SITTING;
                    case SITTING -> CopperGolemStatue.CopperGolemPose.RUNNING;
                    case RUNNING -> CopperGolemStatue.CopperGolemPose.STAR;
                    case STAR -> CopperGolemStatue.CopperGolemPose.STANDING;
                }
        );
        block.setBlockData(statue);
    }

    @NotNull
    @Override
    public String getValue(@NotNull BlockData data) throws ClassCastException {
        return ((CopperGolemStatue) data).getCopperGolemPose().toString().toLowerCase();
    }
}
