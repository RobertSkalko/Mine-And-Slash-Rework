package com.robertx22.age_of_exile.loot.blueprints;

import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.loot.LootInfo;
import com.robertx22.age_of_exile.loot.blueprints.bases.GearRarityPart;
import com.robertx22.age_of_exile.saveclasses.skill_gem.SkillGemData;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import net.minecraft.world.item.ItemStack;

public class SkillGemBlueprint extends ItemBlueprint {
    public SkillGemBlueprint(LootInfo info, SkillGemData.SkillGemType type) {
        super(info);
        this.rarity.setupChances(info);
        this.type = type;
    }

    public GearRarityPart rarity = new GearRarityPart(this);
    SkillGemData.SkillGemType type;


    public SkillGemData createData() {
        GearRarity rar = rarity.get();


        String id = "";
        if (type == SkillGemData.SkillGemType.AURA) {
            id = ExileDB.AuraGems().random().GUID();
        }
        if (type == SkillGemData.SkillGemType.SUPPORT) {
            id = ExileDB.SupportGems().random().GUID();
        }
        if (type == SkillGemData.SkillGemType.SKILL) {
            id = ExileDB.Spells().random().GUID();
        }


        SkillGemData data = new SkillGemData();
        data.id = id;
        data.type = type;
        data.rar = rar.GUID();
        data.perc = rar.stat_percents.random();

        return data;
    }

    @Override
    ItemStack generate() {

        var data = createData();

        ItemStack stack = data.getItem().getDefaultInstance();

        StackSaving.SKILL_GEM.saveTo(stack, data);

        return stack;
    }


}
