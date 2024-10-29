package com.robertx22.mine_and_slash.database.data.currency.reworked.item_mod.gear;

import com.robertx22.mine_and_slash.database.data.currency.reworked.item_mod.GearModification;
import com.robertx22.mine_and_slash.database.data.currency.reworked.item_mod.ItemModificationResult;
import com.robertx22.mine_and_slash.database.data.currency.reworked.item_mod.ItemModificationSers;
import com.robertx22.mine_and_slash.database.data.runewords.RuneWord;
import com.robertx22.mine_and_slash.itemstack.ExileStack;
import com.robertx22.mine_and_slash.itemstack.StackKeys;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_parts.SocketData;
import com.robertx22.mine_and_slash.uncommon.localization.Words;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;

public class ExtractSocketItemMod extends GearModification {

    public static enum SocketedType {
        GEM(Words.Gem) {
            @Override
            public boolean is(SocketData data) {
                return data.getGem() != null;
            }
        },

        RUNE(Words.Rune) {
            @Override
            public boolean is(SocketData data) {
                return data.getRune() != null;
            }
        };

        public Words word;

        SocketedType(Words word) {
            this.word = word;
        }

        public abstract boolean is(SocketData data);
    }

    public SocketedType type;


    public ExtractSocketItemMod(String id, SocketedType data) {
        super(ItemModificationSers.EXTRACT_SOCKET, id);
        this.type = data;
    }

    @Override
    public void modifyGear(ExileStack stack, ItemModificationResult r) {

        stack.get(StackKeys.GEAR).edit(gear -> {
            int index = gear.sockets.lastFilledSocketGemIndex(type);

            if (index > -1) {
                var socket = gear.sockets.getSocketed().get(index);
                if (socket.is(type)) {

                    RuneWord runeword = null;
                    if (gear.sockets.hasRuneWord()) {
                        runeword = gear.sockets.getRuneWord();
                    }


                    gear.sockets.getSocketed().remove(index);

                    ItemStack extractedSocketItem = socket.getOriginalItemStack();

                    r.extraItemsCreated.add(extractedSocketItem);

                    if (gear.sockets.hasRuneWord()) {
                        if (!runeword.hasMatchingRunesToCreate(gear)) {
                            gear.sockets.removeRuneword();
                        }
                    }
                }
            }
        });
    }


    @Override
    public OutcomeType getOutcomeType() {
        return OutcomeType.GOOD;
    }

    @Override
    public MutableComponent getDescWithParams() {
        return this.getDescParams(type.word.locName().withStyle(ChatFormatting.YELLOW));
    }


    @Override
    public Class<?> getClassForSerialization() {
        return ExtractSocketItemMod.class;
    }


    @Override
    public String locDescForLangFile() {
        return "Extracts %1$s from a Socket";
    }
}
