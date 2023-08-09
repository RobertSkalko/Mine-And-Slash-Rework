package com.robertx22.age_of_exile.mmorpg.registers.common;

import com.robertx22.age_of_exile.capability.chunk.ChunkData;
import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.capability.player.EntitySpellData;
import com.robertx22.age_of_exile.capability.player.PlayerBackpackData;
import com.robertx22.age_of_exile.capability.player.PlayerData;
import com.robertx22.age_of_exile.capability.world.WorldData;
import com.robertx22.age_of_exile.mmorpg.ForgeEvents;
import com.robertx22.library_of_exile.components.PlayerCapabilities;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;

import java.util.function.Consumer;

public class SlashCapabilities {


    public static void register() {

        ForgeEvents.registerForgeEvent(RegisterCapabilitiesEvent.class, x -> {
            x.register(EntityData.class);
            x.register(WorldData.class);
            x.register(PlayerData.class);
            x.register(PlayerBackpackData.class);
            x.register(ChunkData.class);
            x.register(EntitySpellData.SpellCap.class);
        });

        MinecraftForge.EVENT_BUS.addGenericListener(Level.class, (Consumer<AttachCapabilitiesEvent<Level>>) x -> {
            x.addCapability(WorldData.RESOURCE, new WorldData(x.getObject()));
        });


        MinecraftForge.EVENT_BUS.addGenericListener(LevelChunk.class, (Consumer<AttachCapabilitiesEvent<LevelChunk>>) x -> {
            x.addCapability(ChunkData.RESOURCE, new ChunkData(x.getObject()));
        });

        MinecraftForge.EVENT_BUS.addGenericListener(Entity.class, (Consumer<AttachCapabilitiesEvent<Entity>>) x -> {

            if (x.getObject() instanceof LivingEntity en) {
                x.addCapability(EntitySpellData.RESOURCE, new EntitySpellData.SpellCap(en));
                x.addCapability(EntityData.RESOURCE, new EntityData(en));
            }
            if (x.getObject() instanceof Player p) {
                x.addCapability(PlayerData.RESOURCE, new PlayerData(p));
                x.addCapability(PlayerBackpackData.RESOURCE, new PlayerBackpackData(p));
            }
        });

        PlayerCapabilities.register(EntityData.INSTANCE, new EntityData(null)); // todo will forge's async screw with this?
        PlayerCapabilities.register(EntitySpellData.INSTANCE, new EntitySpellData.SpellCap(null)); // todo will forge's async screw with this?
        PlayerCapabilities.register(PlayerData.INSTANCE, new PlayerData(null)); // todo will forge's async screw with this?
        PlayerCapabilities.register(PlayerBackpackData.INSTANCE, new PlayerBackpackData(null)); // todo will forge's async screw with this?

    }
}
