package com.robertx22.mine_and_slash.vanilla_mc.packets.spells;

import com.robertx22.library_of_exile.main.MyPacket;
import com.robertx22.library_of_exile.main.Packets;
import com.robertx22.library_of_exile.packets.ExilePacketContext;
import com.robertx22.mine_and_slash.a_libraries.player_animations.PlayerAnimations;
import com.robertx22.mine_and_slash.database.data.spells.components.Spell;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class TellClientEntityCastingSpell extends MyPacket<TellClientEntityCastingSpell> {

    public String spellid = "";
    public int enid = 0;
    public PlayerAnimations.CastEnum type;

    public static void sendUpdates(PlayerAnimations.CastEnum e, Player p, Spell spell) {
        var packet = new TellClientEntityCastingSpell(e, p, spell);
        // i think tracking is only entities besides the player?
        Packets.sendToTracking(packet, p);
        Packets.sendToClient(p, packet);
    }

    private TellClientEntityCastingSpell(PlayerAnimations.CastEnum e, LivingEntity en, Spell spell) {
        this.spellid = spell.GUID();
        this.enid = en.getId();
        this.type = e;
    }

    public TellClientEntityCastingSpell() {
    }

    @Override
    public ResourceLocation getIdentifier() {
        return new ResourceLocation(SlashRef.MODID, "tellclienttocastspell");
    }

    @Override
    public void loadFromData(FriendlyByteBuf tag) {
        this.spellid = tag.readUtf();
        this.type = tag.readEnum(PlayerAnimations.CastEnum.class);
        this.enid = tag.readInt();
    }

    @Override
    public void saveToData(FriendlyByteBuf tag) {
        tag.writeUtf(spellid);
        tag.writeEnum(type);
        tag.writeInt(enid);
    }

    @Override
    public void onReceived(ExilePacketContext ctx) {

        LivingEntity en = (LivingEntity) ctx.getPlayer().level().getEntity(enid);

        if (en instanceof Player p) {
            Spell spell = ExileDB.Spells().get(spellid);
            PlayerAnimations.onSpellCast(p, spell, type);

        }
    }

    @Override
    public MyPacket<TellClientEntityCastingSpell> newInstance() {
        return new TellClientEntityCastingSpell();
    }
}
