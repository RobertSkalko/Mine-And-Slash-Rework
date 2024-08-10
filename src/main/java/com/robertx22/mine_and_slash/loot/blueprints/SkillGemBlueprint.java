package com.robertx22.mine_and_slash.loot.blueprints;

import com.robertx22.mine_and_slash.database.data.aura.AuraGem;
import com.robertx22.mine_and_slash.database.data.rarities.GearRarity;
import com.robertx22.mine_and_slash.database.data.spells.components.Spell;
import com.robertx22.mine_and_slash.database.data.support_gem.SupportGem;
import com.robertx22.mine_and_slash.database.registry.ExileRegistryTypes;
import com.robertx22.mine_and_slash.loot.LootInfo;
import com.robertx22.mine_and_slash.loot.blueprints.bases.RegistryPart;
import com.robertx22.mine_and_slash.saveclasses.skill_gem.SkillGemData;
import com.robertx22.mine_and_slash.uncommon.datasaving.StackSaving;
import com.robertx22.library_of_exile.registry.ExileRegistry;
import net.minecraft.world.item.ItemStack;

public class SkillGemBlueprint extends RarityItemBlueprint implements ITypeBlueprint {

    public SkillGemBlueprint(LootInfo info, SkillGemData.SkillGemType type) {
        super(info);
        this.rarity.setupChances(info);
        this.type = type;
    }

    RegistryPart<SupportGem> SUPP = new RegistryPart<>(this, ExileRegistryTypes.SUPPORT_GEM, x -> info.level >= x.min_lvl);
    RegistryPart<AuraGem> AURA = new RegistryPart<>(this, ExileRegistryTypes.AURA, x -> info.level >= x.min_lvl);
    RegistryPart<Spell> SPELL = new RegistryPart<>(this, ExileRegistryTypes.SPELL, x -> info.level >= x.min_lvl);

    SkillGemData.SkillGemType type;


    public SkillGemData createData() {

        GearRarity rar = rarity.get();

        String id = getType().get().GUID();

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


    public RegistryPart<? extends ExileRegistry> getType() {
        if (this.type == SkillGemData.SkillGemType.AURA) {
            return AURA;
        }
        if (this.type == SkillGemData.SkillGemType.SUPPORT) {
            return SUPP;
        }
        if (this.type == SkillGemData.SkillGemType.SKILL) {
            return SPELL;
        }
        return null;
    }

    @Override
    public void setType(String type) {
        getType().set(type);
    }
}
