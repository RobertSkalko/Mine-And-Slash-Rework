package com.robertx22.age_of_exile.vanilla_mc.packets;

import com.robertx22.age_of_exile.database.data.perks.Perk;
import com.robertx22.age_of_exile.database.data.spell_school.SpellSchool;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.library_of_exile.main.MyPacket;
import com.robertx22.library_of_exile.packets.ExilePacketContext;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class AllocateClassPointPacket extends MyPacket<AllocateClassPointPacket> {

    public String id;
    public String schoolid;
    AllocateClassPointPacket.ACTION action;

    public enum ACTION {
        ALLOCATE, REMOVE
    }

    public AllocateClassPointPacket() {

    }

    public AllocateClassPointPacket(SpellSchool school, Perk perk, ACTION action) {
        this.id = perk.GUID();
        this.schoolid = school.GUID();
        this.action = action;
    }

    @Override
    public ResourceLocation getIdentifier() {
        return new ResourceLocation(SlashRef.MODID, "spell_alloc");
    }

    @Override
    public void loadFromData(FriendlyByteBuf tag) {
        id = tag.readUtf(100);
        schoolid = tag.readUtf(100);
        action = tag.readEnum(AllocateClassPointPacket.ACTION.class);

    }

    @Override
    public void saveToData(FriendlyByteBuf tag) {
        tag.writeUtf(id, 100);
        tag.writeUtf(schoolid, 100);
        tag.writeEnum(action);

    }

    @Override
    public void onReceived(ExilePacketContext ctx) {


        Perk perk = ExileDB.Perks()
                .get(this.id);
        SpellSchool school = ExileDB.SpellSchools()
                .get(this.schoolid);

        var data = Load.player(ctx.getPlayer()).ascClass;

        if (data.canLearn(ctx.getPlayer(), school, perk)) {
            data.learn(perk, school);
        }
        Load.player(ctx.getPlayer()).syncToClient(ctx.getPlayer());

    }

    @Override
    public MyPacket<AllocateClassPointPacket> newInstance() {
        return new AllocateClassPointPacket();
    }
}

