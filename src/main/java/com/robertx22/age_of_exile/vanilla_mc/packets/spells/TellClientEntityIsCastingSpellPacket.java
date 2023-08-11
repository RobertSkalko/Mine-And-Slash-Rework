package com.robertx22.age_of_exile.vanilla_mc.packets.spells;

import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.library_of_exile.main.MyPacket;
import com.robertx22.library_of_exile.packets.ExilePacketContext;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class TellClientEntityIsCastingSpellPacket extends MyPacket<TellClientEntityIsCastingSpellPacket> {

    public String spellid = "";
    public int enid = 0;

    public TellClientEntityIsCastingSpellPacket(LivingEntity en, Spell spell) {
        this.spellid = spell.GUID();
        this.enid = en.getId();
    }

    public TellClientEntityIsCastingSpellPacket() {
    }

    @Override
    public ResourceLocation getIdentifier() {
        return new ResourceLocation(SlashRef.MODID, "tell_client_entity_is_casting_spell");
    }

    @Override
    public void loadFromData(FriendlyByteBuf tag) {
        this.spellid = tag.readUtf(30);
        this.enid = tag.readInt();
    }

    @Override
    public void saveToData(FriendlyByteBuf tag) {
        tag.writeUtf(spellid);
        tag.writeInt(enid);
    }

    @Override
    public void onReceived(ExilePacketContext ctx) {

        // todo delete this eventually

        /*
        LivingEntity en = (LivingEntity) ctx.getPlayer().level().getEntity(enid);

        Spell spell = ExileDB.Spells()
                .get(spellid);
        SpellCastContext c = new SpellCastContext(en, 0, spell);

        spell.getAttached()
                .tryActivate(Spell.CASTER_NAME, SpellCtx.onTick(en, en, CalculatedSpellData.create(Load.Unit(en)
                        .getLevel(), en, spell)));

         */

    }

    @Override
    public MyPacket<TellClientEntityIsCastingSpellPacket> newInstance() {
        return new TellClientEntityIsCastingSpellPacket();
    }
}
