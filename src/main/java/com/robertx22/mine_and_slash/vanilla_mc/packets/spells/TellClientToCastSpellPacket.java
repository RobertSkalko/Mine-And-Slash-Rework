package com.robertx22.mine_and_slash.vanilla_mc.packets.spells;

import com.robertx22.mine_and_slash.database.data.spells.components.Spell;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.SpellCastContext;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import com.robertx22.library_of_exile.main.MyPacket;
import com.robertx22.library_of_exile.packets.ExilePacketContext;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class TellClientToCastSpellPacket extends MyPacket<TellClientToCastSpellPacket> {

    public String spellid = "";
    public int enid = 0;

    public TellClientToCastSpellPacket(LivingEntity en, Spell spell) {
        this.spellid = spell.GUID();
        this.enid = en.getId();
    }

    public TellClientToCastSpellPacket() {
    }

    @Override
    public ResourceLocation getIdentifier() {
        return new ResourceLocation(SlashRef.MODID, "tellclienttocastspell");
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

        LivingEntity en = (LivingEntity) ctx.getPlayer().level().getEntity(enid);

        Spell spell = ExileDB.Spells()
                .get(spellid);
        SpellCastContext c = new SpellCastContext(en, 0, spell);

        spell.cast(c);

    }

    @Override
    public MyPacket<TellClientToCastSpellPacket> newInstance() {
        return new TellClientToCastSpellPacket();
    }
}
