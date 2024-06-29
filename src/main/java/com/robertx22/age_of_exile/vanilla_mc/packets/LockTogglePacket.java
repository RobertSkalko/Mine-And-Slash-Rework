package com.robertx22.age_of_exile.vanilla_mc.packets;

import com.robertx22.age_of_exile.database.data.profession.Crafting_State;
import com.robertx22.age_of_exile.database.data.profession.ProfessionBlockEntity;
import com.robertx22.age_of_exile.database.data.profession.ProfessionRecipe;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.localization.Chats;
import com.robertx22.library_of_exile.main.MyPacket;
import com.robertx22.library_of_exile.packets.ExilePacketContext;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;

public class LockTogglePacket extends MyPacket<LockTogglePacket> {

    public BlockPos block_pos;

    public LockTogglePacket() {
    }

    public LockTogglePacket(BlockPos pos) {
        this.block_pos = pos;
    }

    @Override
    public ResourceLocation getIdentifier() {
        return new ResourceLocation(SlashRef.MODID, "locktoggle");
    }

    @Override
    public void loadFromData(FriendlyByteBuf friendlyByteBuf) {
        this.block_pos = friendlyByteBuf.readBlockPos();
    }

    @Override
    public void saveToData(FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeBlockPos(this.block_pos);
    }

    @Override
    public void onReceived(ExilePacketContext exilePacketContext) {
        BlockEntity be = exilePacketContext.getPlayer().level().getBlockEntity(this.block_pos);
        if (be instanceof ProfessionBlockEntity) {
            ProfessionBlockEntity pbe = (ProfessionBlockEntity) be;
            if (pbe.craftingState == Crafting_State.ACTIVE && pbe.recipe_locked) {
                if ((pbe.ownerUUID != null && pbe.ownerUUID.compareTo(exilePacketContext.getPlayer().getUUID()) == 0) || pbe.ownerUUID == null)
                    exilePacketContext.getPlayer().sendSystemMessage(Component.literal("This Station is currently claimed by another player").withStyle(ChatFormatting.RED, ChatFormatting.BOLD));
            } else if (pbe.craftingState == Crafting_State.IDLE && pbe.recipe_locked) {
                exilePacketContext.getPlayer().sendSystemMessage(Component.literal("Stop auto crafting before unlocking the recipe").withStyle(ChatFormatting.RED, ChatFormatting.BOLD));
            } else if (pbe.craftingState == Crafting_State.STOPPED && pbe.recipe_locked) {
                pbe.recipe_locked = false;
                pbe.last_recipe = null;
                //pbe.show.clearContent();
                if (pbe.ownerUUID != null && pbe.ownerUUID.compareTo(exilePacketContext.getPlayer().getUUID()) != 0) {
                    pbe.ownerUUID = null;
                }
            } else if (pbe.craftingState == Crafting_State.ACTIVE && !pbe.recipe_locked) {
                exilePacketContext.getPlayer().sendSystemMessage(Component.literal("Stop auto crafting before locking the recipe").withStyle(ChatFormatting.RED, ChatFormatting.BOLD));
            } else if (pbe.craftingState == Crafting_State.STOPPED && !pbe.recipe_locked) {
                ProfessionRecipe recipe = pbe.getCurrentRecipe();
                if (recipe == null) {
                    exilePacketContext.getPlayer().sendSystemMessage(Chats.PROF_RECIPE_NOT_FOUND.locName().withStyle(ChatFormatting.RED, ChatFormatting.BOLD));
                    return;
                }

                int ownerLvl = Load.player(exilePacketContext.getPlayer()).professions.getLevel(recipe.profession);
                if (recipe.getLevelRequirement() > ownerLvl) {
                    exilePacketContext.getPlayer().sendSystemMessage(Chats.PROF_RECIPE_LEVEL_NOT_ENOUGH.locName(recipe.profession, recipe.getLevelRequirement(), ownerLvl).withStyle(ChatFormatting.RED, ChatFormatting.BOLD));
                    return;
                }
                pbe.recipe_locked = true;
                pbe.last_recipe = recipe;
                var showstack = recipe.toResultStackForJei();
                showstack.setCount(1);
                //pbe.show.setItem(0, showstack);
            } else {
                exilePacketContext.getPlayer().sendSystemMessage(Component.literal("Unhandled Case(Report The Following):  " + pbe.recipe_locked + " + " + pbe.craftingState.name()).withStyle(ChatFormatting.RED, ChatFormatting.BOLD));
            }
            pbe.setChanged();
        }
    }

    @Override
    public MyPacket<LockTogglePacket> newInstance() {
        return new LockTogglePacket();
    }
}
