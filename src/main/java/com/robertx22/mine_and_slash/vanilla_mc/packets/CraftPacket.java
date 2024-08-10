package com.robertx22.mine_and_slash.vanilla_mc.packets;

import com.robertx22.mine_and_slash.database.data.profession.Crafting_State;
import com.robertx22.mine_and_slash.database.data.profession.ProfessionBlockEntity;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import com.robertx22.library_of_exile.main.MyPacket;
import com.robertx22.library_of_exile.packets.ExilePacketContext;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;

public class CraftPacket extends MyPacket<CraftPacket> {

    public BlockPos block_pos;

    public CraftPacket() {

    }

    public CraftPacket(BlockPos pos) {
        this.block_pos = pos;
    }


    @Override
    public ResourceLocation getIdentifier() {
        return SlashRef.id("crafttoggle");
    }

    @Override
    public void loadFromData(FriendlyByteBuf buf) {
        this.block_pos = buf.readBlockPos();
    }

    @Override
    public void saveToData(FriendlyByteBuf buf) {
        buf.writeBlockPos(block_pos);
    }

    @Override
    public void onReceived(ExilePacketContext exilePacketContext) {
        BlockEntity be = exilePacketContext.getPlayer().level().getBlockEntity(this.block_pos);
        if (be instanceof ProfessionBlockEntity) {
            ProfessionBlockEntity pbe = (ProfessionBlockEntity) be;

            pbe.ownerUUID = exilePacketContext.getPlayer().getUUID();

            if (pbe.craftingState == Crafting_State.IDLE || pbe.craftingState == Crafting_State.ACTIVE) {
                pbe.craftingState = Crafting_State.STOPPED;
                // pbe.ownerUUID = null;
            } else {
                pbe.craftingState = Crafting_State.ACTIVE;
                pbe.ownerUUID = exilePacketContext.getPlayer().getUUID();
            }
            pbe.setChanged();
        }
    }

    @Override
    public MyPacket<CraftPacket> newInstance() {
        return new CraftPacket();
    }
}
