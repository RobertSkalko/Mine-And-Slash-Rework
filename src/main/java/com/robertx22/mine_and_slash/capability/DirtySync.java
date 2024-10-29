package com.robertx22.mine_and_slash.capability;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import java.util.function.Consumer;

public class DirtySync {

    private boolean dirty = false;

    private String id;
    private Consumer<Entity> sync;

    public DirtySync(String id, Consumer<Entity> sync) {
        this.sync = sync;
        this.id = id;
    }

    public void setDirty() {
        this.dirty = true;
    }

    // should only be used when you need instant sync
    public void setDirtyAndSync(Player p) {
        setDirty();
        onTickTrySync(p);
    }

    public void onTickTrySync(Entity p) {
        if (dirty) {
            dirty = false;
            sync.accept(p);

            onSynced(p);

            if (p instanceof Player pl) {
                //  pl.sendSystemMessage(Component.literal(id));
            }
        }
    }

    public void onSynced(Entity p) {

    }

}
