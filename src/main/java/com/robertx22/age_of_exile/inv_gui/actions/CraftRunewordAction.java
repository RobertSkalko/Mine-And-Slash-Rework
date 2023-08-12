package com.robertx22.age_of_exile.inv_gui.actions;

import com.robertx22.age_of_exile.database.data.runewords.RuneWord;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearTooltipUtils;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import com.robertx22.age_of_exile.uncommon.utilityclasses.PlayerUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.StringUTIL;
import com.robertx22.library_of_exile.utils.SoundUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class CraftRunewordAction extends GuiAction {

    public RuneWord runeWord;

    ItemStack stack;

    public CraftRunewordAction(RuneWord runeWord) {

        this.runeWord = runeWord;
    }

    @Override
    public List<Component> getTooltip(Player p) {
        List<Component> list = new ArrayList<>();

        if (stack == null) {
            this.stack = runeWord.createGearItem(1); // todo
        }

        GearItemData data = StackSaving.GEARS.loadFrom(stack);

        if (data != null) {
            GearTooltipUtils.BuildTooltip(data, stack, list, Load.Unit(p));
        }

        String runes = StringUTIL.join(runeWord.runes.stream().map(x -> x.toUpperCase(Locale.ROOT)).collect(Collectors.toList()).listIterator(), ", ");

        list.add(Component.literal(runes).withStyle(ChatFormatting.YELLOW));


        return list;
    }

    // todo replace this with actual icons if i ever add actual runeword items
    public ResourceLocation getIcon() {
        return SlashRef.id("textures/gui/inv_gui/icons/runeword.png");
    }

    @Override
    public void doAction(Player p) {


        if (runeWord.hasRunesToCraft(p)) {

            SoundUtils.playSound(p, SoundEvents.ITEM_PICKUP);
            SoundUtils.playSound(p, SoundEvents.ANVIL_USE);

            runeWord.spendRunesToCraft(p);

            ItemStack stack = runeWord.createGearItem(Load.Unit(p).getLevel());

            PlayerUtils.giveItem(stack, p);
        }


    }

    @Override
    public String GUID() {
        return runeWord.GUID();
    }
}
