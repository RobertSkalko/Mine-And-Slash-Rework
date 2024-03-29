package com.robertx22.age_of_exile.vanilla_mc.packets;

import com.robertx22.age_of_exile.database.data.profession.Crafting_State;
import com.robertx22.age_of_exile.database.data.profession.ProfessionBlockEntity;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.library_of_exile.main.MyPacket;
import com.robertx22.library_of_exile.packets.ExilePacketContext;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
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
            if(pbe.craftingState == Crafting_State.ACTIVE) {
                if(pbe.ownerUUID.compareTo(exilePacketContext.getPlayer().getUUID()) == 0){
                    pbe.craftingState = Crafting_State.STOPPED;
                    pbe.ownerUUID = null;
                }else{
                    exilePacketContext.getPlayer().sendSystemMessage(Component.literal("This Station is currently claimed by another player").withStyle(ChatFormatting.RED, ChatFormatting.BOLD));
                }
            }else if(pbe.craftingState == Crafting_State.IDLE){
                pbe.craftingState = Crafting_State.STOPPED;
                pbe.ownerUUID = null;
            }else{
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
