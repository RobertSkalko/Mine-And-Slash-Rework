package com.robertx22.mine_and_slash.vanilla_mc.packets;

import com.robertx22.mine_and_slash.database.data.spells.summons.entity.SummonEntity;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.AllyOrEnemy;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.EntityFinder;
import com.robertx22.library_of_exile.main.MyPacket;
import com.robertx22.library_of_exile.packets.ExilePacketContext;
import com.robertx22.library_of_exile.utils.SoundUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class UnsummonPacket extends MyPacket<UnsummonPacket> {
    @Override
    public ResourceLocation getIdentifier() {
        return SlashRef.id("unsummon");
    }

    @Override
    public void loadFromData(FriendlyByteBuf friendlyByteBuf) {

    }

    @Override
    public void saveToData(FriendlyByteBuf friendlyByteBuf) {

    }

    @Override
    public void onReceived(ExilePacketContext ctx) {

        Player p = ctx.getPlayer();

        SoundUtils.playSound(p, SoundEvents.GENERIC_DEATH);

        EntityFinder.Setup<LivingEntity> finder = EntityFinder.start(p, LivingEntity.class, p.blockPosition())
                .finder(EntityFinder.SelectionType.RADIUS)
                .searchFor(AllyOrEnemy.casters_summons)
                .radius(100);

        for (LivingEntity en : finder.build()) {
            if (en instanceof SummonEntity) {
                en.discard();
            }
        }
    }

    @Override
    public MyPacket<UnsummonPacket> newInstance() {
        return new UnsummonPacket();
    }
}
