package com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases;

import com.robertx22.mine_and_slash.saveclasses.item_classes.GearItemData;
import com.robertx22.mine_and_slash.tags.all.SlotTags;
import com.robertx22.mine_and_slash.uncommon.datasaving.StackSaving;
import com.robertx22.mine_and_slash.uncommon.localization.Chats;
import net.minecraft.ChatFormatting;
import net.minecraft.world.entity.LivingEntity;

import java.util.function.Predicate;

public class SpellPredicates {
    private static Predicate<LivingEntity> SHOOTABLE_PRED = x -> {
        try {

            GearItemData data = StackSaving.GEARS.loadFrom(x.getMainHandItem());
            return data != null && data.GetBaseGearType()
                    .getTags()
                    .contains(SlotTags.ranged_weapon);
        } catch (Exception e) {
            return false;
        }

    };

    private static Predicate<LivingEntity> MELEE_PRED = x -> {
        try {
            GearItemData data = StackSaving.GEARS.loadFrom(x.getMainHandItem());
            return data != null && data.GetBaseGearType()
                    .getTags()
                    .contains(SlotTags.melee_weapon);
        } catch (Exception e) {
            return false;
        }
    };

    private static Predicate<LivingEntity> MAGE_PRED = x -> {
        try {
            GearItemData data = StackSaving.GEARS.loadFrom(x.getMainHandItem());
            return data != null && data.GetBaseGearType()
                    .getTags()
                    .contains(SlotTags.mage_weapon);
        } catch (Exception e) {
            return false;
        }
    };
    private static Predicate<LivingEntity> NON_MAGE_PRED = x -> {
        return !MAGE_PRED.test(x);
    };
    private static Predicate<LivingEntity> ANY = x -> {
        return true;
    };

    public static SpellPredicate REQUIRE_SHOOTABLE = new SpellPredicate(SHOOTABLE_PRED, Chats.REQUIRE_RANGED.locName().withStyle(ChatFormatting.GREEN));
    public static SpellPredicate REQUIRE_MAGE_WEAPON = new SpellPredicate(MAGE_PRED, Chats.REQUIRE_MAGE.locName().withStyle(ChatFormatting.GREEN));
    public static SpellPredicate REQUIRE_MELEE = new SpellPredicate(MELEE_PRED, Chats.REQUIRE_MELEE.locName().withStyle(ChatFormatting.GOLD));
    public static SpellPredicate ANY_ITEM = new SpellPredicate(ANY, Chats.ANY_ITEM.locName().withStyle(ChatFormatting.GOLD));
    public static SpellPredicate NON_MAGE_WEAPON = new SpellPredicate(NON_MAGE_PRED, Chats.NONE_MAGE.locName().withStyle(ChatFormatting.GOLD));

}

