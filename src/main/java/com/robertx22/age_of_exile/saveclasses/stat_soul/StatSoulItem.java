package com.robertx22.age_of_exile.saveclasses.stat_soul;

import com.robertx22.age_of_exile.database.data.gear_slots.GearSlot;
import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.gui.texts.ExileTooltips;
import com.robertx22.age_of_exile.gui.texts.textblocks.AdditionalBlock;
import com.robertx22.age_of_exile.gui.texts.textblocks.NameBlock;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipContext;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.uncommon.utilityclasses.ClientOnly;
import com.robertx22.age_of_exile.uncommon.utilityclasses.LevelUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.PlayerUtils;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.ICreativeTabNbt;
import com.robertx22.library_of_exile.registry.IGUID;
import com.robertx22.library_of_exile.utils.LoadSave;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StatSoulItem extends Item implements IGUID, ICreativeTabNbt {

    public static String TAG = "stat_soul";

    public StatSoulItem() {
        super(new Properties());
    }

    public static boolean hasSoul(ItemStack stack) {
        return stack.hasTag() && stack.getTag()
                .contains(TAG);
    }

    public static StatSoulData getSoul(ItemStack stack) {
        StatSoulData data = LoadSave.Load(StatSoulData.class, new StatSoulData(), stack.getOrCreateTag(), TAG);
        return data;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player p, InteractionHand pUsedHand) {
        ItemStack itemstack = p.getItemInHand(pUsedHand);

        if (!pLevel.isClientSide) {

            if (StackSaving.STAT_SOULS.has(itemstack)) {

                StatSoulData data = StackSaving.STAT_SOULS.loadFrom(itemstack);

                var geardata = data.createGearData(null, p);

                Item item = geardata.GetBaseGearType().getRandomItem(data.getRarity());

                ItemStack stack = item.getDefaultInstance();

                StackSaving.GEARS.saveTo(stack, geardata);

                PlayerUtils.giveItem(stack, p);
                itemstack.shrink(1);
            }

        }

        return InteractionResultHolder.pass(p.getItemInHand(pUsedHand));

    }

    @Override
    public List<ItemStack> createAllVariationsForCreativeTabs() {
        var list = new ArrayList<ItemStack>();
        for (GearRarity rarity : ExileDB.GearRarities()
                .getList()) {
            for (GearSlot slot : ExileDB.GearSlots()
                    .getList()) {
                for (int i = 0; i <= LevelUtils.getMaxTier(); i++) {
                    StatSoulData data = new StatSoulData();
                    data.tier = i;
                    data.rar = rarity.GUID();
                    data.slot = slot.GUID();

                    ItemStack stack = data.toStack();
                    list.add(stack);
                }

            }
        }
        return list;
    }

    @Override
    public Component getName(ItemStack stack) {

        MutableComponent txt = Component.translatable(this.getDescriptionId());

        try {
            StatSoulData data = getSoul(stack);

            if (data == null) {
                return txt;
            } else {

                GearRarity rar = ExileDB.GearRarities()
                        .get(data.rar);
                GearSlot slot = ExileDB.GearSlots()
                        .get(data.slot);

                MutableComponent t = rar.locName();
                if (!data.canBeOnAnySlot()) {
                    t.append(" ")
                            .append(slot.locName());
                }

                t.append(" ")
                        .append(Words.Soul.locName())
                        .withStyle(rar.textFormatting());

                return t;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return txt;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag context) {
        try {
            StatSoulData data = StackSaving.STAT_SOULS.loadFrom(stack);
            if (data != null) {
                tooltip.clear();
                if (Screen.hasShiftDown() && data.gear != null) {
                    data.gear.BuildTooltip(new TooltipContext(stack, tooltip, Load.Unit(ClientOnly.getPlayer())));
                } else {
                    ExileTooltips exileTooltips = data.getTooltip(stack, false);
                    exileTooltips.accept(new NameBlock(Collections.singletonList(stack.getHoverName())));

                    Player p = ClientOnly.getPlayer();
                    if (p != null && p.isCreative()) {
                        exileTooltips.accept(new AdditionalBlock(Words.DRAG_NO_WORK_CREATIVE.locName().withStyle(ChatFormatting.RED, ChatFormatting.BOLD)));
                    }
                    tooltip.addAll(exileTooltips.release());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public String GUID() {
        return "stat_soul/stat_soul";
    }


}
