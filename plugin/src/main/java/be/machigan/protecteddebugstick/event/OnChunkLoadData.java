package be.machigan.protecteddebugstick.event;

import be.machigan.protecteddebugstick.persistent.ChunkDataHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;

public class OnChunkLoadData implements Listener {
    @EventHandler
    public void onLoad(ChunkLoadEvent e) {
        ChunkDataHandler.load(e.getChunk());
    }

    @EventHandler
    public void onUnload(ChunkUnloadEvent e) {
        ChunkDataHandler.save(e.getChunk());
        ChunkDataHandler.unload(e.getChunk());
    }
}
