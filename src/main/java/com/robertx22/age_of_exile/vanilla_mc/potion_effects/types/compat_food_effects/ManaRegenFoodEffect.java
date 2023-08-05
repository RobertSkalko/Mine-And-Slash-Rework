package com.robertx22.age_of_exile.vanilla_mc.potion_effects.types.compat_food_effects;

import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.ChatFormatting;

import java.util.ArrayList;
import java.util.List;

public class ManaRegenFoodEffect extends FoodEffectPotion {

    public static ManaRegenFoodEffect INSTANCE = new ManaRegenFoodEffect(3035801);

    protected ManaRegenFoodEffect(int color) {
        super(color);
    }

    @Override
    public ResourceType resourceType() {
        return ResourceType.mana;
    }

    @Override
    public List<Component> GetTooltipString(TooltipInfo info, int duration, int amplifier) {
        List<Component> list = new ArrayList<>();
        int val = (int) getTotalRestored(info.unitdata, amplifier);
        list.add(new TextComponent("Restores " + val + " Mana over " + duration / 20 + "s").withStyle(ChatFormatting.AQUA));
        return list;
    }

}
