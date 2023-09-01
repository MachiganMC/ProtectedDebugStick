package be.machigan.protecteddebugstick.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.jetbrains.annotations.NotNull;

public class OnUse implements Listener {
    @EventHandler
    public static void onUse(PlayerInteractEvent e) {
        if (!PropertyValidator.isPlayerHoldADebugStick(e))
            return;

        e.setCancelled(true);
        if (!PropertyValidator.isPlayerClickOnABlock(e.getClickedBlock()))
            return;

        PropertyValidator validator = new PropertyValidator(e.getPlayer(), e.getClickedBlock(), e.getBlockFace());

        if (validator.isWorldBlackListed()) {
            validator.sendWorldBlackListedMessage();
            return;
        }

        if (validator.isMaterialBlackListed()) {
            validator.sendMaterialBlackListedMessage();
            return;
        }

        if (validator.getValidProperties().isEmpty()) {
            validator.sendNoValidPropertyMessage();
            return;
        }

        if (validator.isDebugStickContainsCorruptedData()) {
            validator.sendDebugStickContainsCorruptedDataMessage();
            return;
        }

        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            handleRightClick(validator);
            return;
        }

        if (e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
            handleLeftClick(validator);
        }
    }

    private static void handleRightClick(@NotNull PropertyValidator validator) {
        if (validator.playerCannotEditWithCurrentDebugStick()) {
            validator.sendPlayerCannotEditWithCurrentDebugStickMessage();
            return;
        }

        if (!validator.playerCanEditAtLocation()) {
            validator.sendCannotEditAtLocationMessage();
            return;
        }

        if (validator.getPlayer().isSneaking()) {
            validator.removeFromEditedBlocks();
            return;
        }

        validator.getCurrentPropertyOnDebugStick().edit(validator.getPlayer(), validator.getClickedBlock(), validator.getFace());
    }

    private static void handleLeftClick(@NotNull PropertyValidator validator) {
        if (validator.getPlayer().isSneaking()) {
            if (!validator.playerCanSeePropertiesWithCurrentDebutStick()) {
                validator.sendPlayerCannotSeePropertiesWithCurrentDebugStick();
                return;
            }
            validator.sendListEditablePropertiesMessage();
            return;
        }

        if (validator.playerCannotEditWithCurrentDebugStick()) {
            validator.sendPlayerCannotEditWithCurrentDebugStickMessage();
            return;
        }

        validator.changeCurrentPropertyOnDebugStick();
        validator.sendPropertyOnDebugStickChangedMessage();
    }
}
