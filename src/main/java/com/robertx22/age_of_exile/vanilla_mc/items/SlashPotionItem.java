package com.robertx22.age_of_exile.vanilla_mc.items;

import com.robertx22.age_of_exile.database.data.currency.base.IShapedRecipe;
import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.RarityItems;
import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.effectdatas.EventBuilder;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.RestoreType;
import com.robertx22.age_of_exile.uncommon.utilityclasses.HealthUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.StringUTIL;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.AutoItem;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.RarityStoneItem;
import com.robertx22.library_of_exile.utils.SoundUtils;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.stream.Collectors;

public class SlashPotionItem extends AutoItem implements IShapedRecipe {

    String rar;

    @Override
    public String locNameForLangFile() {
        return getRarity().textFormatting() + StringUTIL.capitalise(rar) + " " + type.name + " Potion";
    }

    @Override
    public String GUID() {
        return null;
    }

    @Override
    public ShapedRecipeBuilder getRecipe() {
        var type = this.type.craftItem;

        return shaped(this, 16)
                .define('r', RarityStoneItem.of(rar))
                .define('b', Items.GLASS_BOTTLE)
                .define('c', type)
                .pattern("rcr")
                .pattern("rbr")
                .pattern("rrr")
                .unlockedBy("player_level", trigger());
    }

    public enum Type {
        HP("Restoration", Items.POTATO), MANA("Invigoration", Items.CARROT);
        String name;

        Item craftItem;

        Type(String name, Item craftItem) {
            this.name = name;
            this.craftItem = craftItem;
        }
    }

    Type type;

    public SlashPotionItem(String rar, Type type) {
        super(new Properties().stacksTo(64));
        this.rar = rar;
        this.type = type;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player p, InteractionHand pUsedHand) {
        ItemStack stack = p.getItemInHand(pUsedHand);


        if (!pLevel.isClientSide) {

            if (type == Type.HP) {
                EventBuilder.ofRestore(p, p, ResourceType.health, RestoreType.potion, HealthUtils.getMaxHealth(p) * getHealPercent() / 100F).build().Activate();
                EventBuilder.ofRestore(p, p, ResourceType.magic_shield, RestoreType.potion, Load.Unit(p).getUnit().magicShieldData().getValue() * getHealPercent() / 100F).build().Activate();
            } else {
                EventBuilder.ofRestore(p, p, ResourceType.mana, RestoreType.potion, Load.Unit(p).getUnit().manaData().getValue() * getHealPercent() / 100F).build().Activate();
                EventBuilder.ofRestore(p, p, ResourceType.energy, RestoreType.potion, Load.Unit(p).getUnit().energyData().getValue() * getHealPercent() / 100F).build().Activate();
            }


            for (SlashPotionItem c : getCooldownItems()) {
                p.getCooldowns().addCooldown(c, 20 * 30);
            }

            SoundUtils.playSound(p, SoundEvents.GENERIC_DRINK);
            stack.shrink(1);
        }


        return InteractionResultHolder.pass(p.getItemInHand(pUsedHand));

    }

    public List<SlashPotionItem> getCooldownItems() {

        if (type == Type.HP) {
            return RarityItems.HEALTH_POTIONS.values().stream().map(x -> x.get()).collect(Collectors.toList());
        } else {
            return RarityItems.RESOURCE_POTIONS.values().stream().map(x -> x.get()).collect(Collectors.toList());

        }
    }

    public float getHealPercent() {
        var r = getRarity();

        return r.stat_percents.max; // todo maybe make separate value

    }

    public GearRarity getRarity() {
        return ExileDB.GearRarities().get(rar);
    }
}
