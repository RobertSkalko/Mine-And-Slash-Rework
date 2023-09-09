package com.robertx22.age_of_exile.vanilla_mc.packets;

import com.robertx22.age_of_exile.database.data.profession.ProfessionBlockEntity;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.library_of_exile.main.MyPacket;
import com.robertx22.library_of_exile.packets.ExilePacketContext;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class CraftPacket extends MyPacket<CraftPacket> {


    public String recipe;
    public BlockPos pos;

    public CraftPacket(String recipe, BlockPos pos) {
        this.recipe = recipe;
        this.pos = pos;
    }


    @Override
    public ResourceLocation getIdentifier() {
        return SlashRef.id("open_backpack");
    }

    @Override
    public void loadFromData(FriendlyByteBuf buf) {

        this.recipe = buf.readUtf();
        this.pos = buf.readBlockPos();
    }

    @Override
    public void saveToData(FriendlyByteBuf buf) {
        buf.writeUtf(recipe);
        buf.writeBlockPos(pos);
    }

    @Override
    public void onReceived(ExilePacketContext ctx) {
        if (ctx.getPlayer().level().getBlockEntity(pos) instanceof ProfessionBlockEntity be) {
            //   be.craft(ExileDB.Recipes().get(recipe));
        }
    }

    @Override
    public MyPacket<CraftPacket> newInstance() {
        return new CraftPacket("", BlockPos.ZERO);
    }
}
