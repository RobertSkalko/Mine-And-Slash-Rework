package com.robertx22.age_of_exile.mmorpg.registers.common;

import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.capability.player.EntitySpellCap;
import com.robertx22.age_of_exile.capability.player.RPGPlayerData;
import com.robertx22.library_of_exile.components.PlayerCapabilities;
import com.robertx22.library_of_exile.main.ForgeEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;

import java.util.function.Consumer;

public class SlashCapabilities {


    public static void register() {

        ForgeEvents.registerForgeEvent(RegisterCapabilitiesEvent.class, x -> {
            x.register(EntityData.class);
            x.register(RPGPlayerData.class);
            x.register(EntitySpellCap.SpellCap.class);
            // todo
        });


        MinecraftForge.EVENT_BUS.addGenericListener(Entity.class, (Consumer<AttachCapabilitiesEvent<Entity>>) x -> {
            if (x.getObject() instanceof LivingEntity en) {
                x.addCapability(EntitySpellCap.RESOURCE, new EntitySpellCap.SpellCap(en));
                x.addCapability(EntityData.RESOURCE, new EntityData(en));
            }
            if (x.getObject() instanceof Player p) {
                x.addCapability(RPGPlayerData.RESOURCE, new RPGPlayerData(p));

            }
        });

        PlayerCapabilities.register(EntityData.INSTANCE, new EntityData(null)); // todo will forge's async screw with this?
        PlayerCapabilities.register(EntitySpellCap.INSTANCE, new EntitySpellCap.SpellCap(null)); // todo will forge's async screw with this?
        PlayerCapabilities.register(RPGPlayerData.INSTANCE, new RPGPlayerData(null)); // todo will forge's async screw with this?

    }
}
