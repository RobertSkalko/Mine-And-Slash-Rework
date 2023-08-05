package com.robertx22.age_of_exile.vanilla_mc.packets.spells;

import com.robertx22.age_of_exile.capability.player.EntitySpellCap;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.SpellCastContext;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.library_of_exile.main.MyPacket;
import com.robertx22.library_of_exile.packets.ExilePacketContext;
import net.minecraft.world.entity.player.Player;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class TellServerToCancelSpellCast extends MyPacket<TellServerToCancelSpellCast> {

    @Override
    public ResourceLocation getIdentifier() {
        return new ResourceLocation(SlashRef.MODID, "cancelspell");
    }

    @Override
    public void loadFromData(FriendlyByteBuf tag) {

    }

    @Override
    public void saveToData(FriendlyByteBuf tag) {

    }

    @Override
    public void onReceived(ExilePacketContext ctx) {
        Player player = ctx.getPlayer();

        EntitySpellCap.ISpellsCap spells = Load.spells(player);

        if (spells.getCastingData()
            .getSpellBeingCast() != null) {

            SpellCastContext sctx = new SpellCastContext(player, spells.getCastingData().castingTicksDone, spells.getCastingData()
                .getSpellBeingCast());

            spells.getCastingData()
                .tryCast(sctx);
            spells.getCastingData()
                .cancelCast(player);

            spells.syncToClient(player);
        }
    }

    @Override
    public MyPacket<TellServerToCancelSpellCast> newInstance() {
        return new TellServerToCancelSpellCast();
    }
}

