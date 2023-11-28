package com.robertx22.age_of_exile.vanilla_mc.items.gearitems.bases;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.library_of_exile.vanilla_util.main.VanillaUTIL;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.enchantment.Enchantment;

public abstract class SingleTargetWeapon extends Item implements IAutoLocName {

    public SingleTargetWeapon(Tiers mat, Properties settings, String locname) {

        super(settings);
        this.locname = locname;

    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return super.canApplyAtEnchantingTable(stack, enchantment) || Items.DIAMOND_SWORD.canApplyAtEnchantingTable(stack, enchantment);
    }

    String locname;
    public float attackSpeed = -2.4F;

    @Override
    public boolean hurtEnemy(ItemStack p_77644_1_, LivingEntity p_77644_2_, LivingEntity p_77644_3_) {
        p_77644_1_.hurtAndBreak(1, p_77644_3_, (p_220045_0_) -> {
            p_220045_0_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
        });
        return true;
    }

    @Override
    public final String locNameForLangFile() {
        return locname;
    }

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Gear_Items;
    }

    @Override
    public String locNameLangFileGUID() {
        return VanillaUTIL.REGISTRY.items().getKey(this)
                .toString();
    }


    @Override
    public String GUID() {
        return "";
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot slot) {
        Multimap<Attribute, AttributeModifier> map = HashMultimap.create();
        if (slot == EquipmentSlot.MAINHAND) {
            map.put(
                    Attributes.ATTACK_DAMAGE,
                    new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", 6,
                            AttributeModifier.Operation.ADDITION
                    )
            );
            map.put(
                    Attributes.ATTACK_SPEED,
                    new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier",
                            (double) this.attackSpeed, AttributeModifier.Operation.ADDITION));

        }

        return map;
    }

}
