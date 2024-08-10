package com.robertx22.mine_and_slash.mmorpg.registers.common.items;

import com.robertx22.mine_and_slash.mmorpg.registers.deferred_wrapper.Def;
import com.robertx22.mine_and_slash.mmorpg.registers.deferred_wrapper.RegObj;
import com.robertx22.mine_and_slash.saveclasses.skill_gem.SkillGemData;
import com.robertx22.mine_and_slash.saveclasses.skill_gem.SkillGemItem;
import com.robertx22.mine_and_slash.uncommon.enumclasses.PlayStyle;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class SkillGemsItems {


    public static RegObj<SkillGemItem> SUPP_STR = Def.item(() -> new SkillGemItem(), "skill_gems/support/str");
    public static RegObj<SkillGemItem> SUPP_DEX = Def.item(() -> new SkillGemItem(), "skill_gems/support/dex");
    public static RegObj<SkillGemItem> SUPP_INT = Def.item(() -> new SkillGemItem(), "skill_gems/support/int");

    public static RegObj<SkillGemItem> SKILL_STR = Def.item(() -> new SkillGemItem(), "skill_gems/skill/str");
    public static RegObj<SkillGemItem> SKILL_DEX = Def.item(() -> new SkillGemItem(), "skill_gems/skill/dex");
    public static RegObj<SkillGemItem> SKILL_INT = Def.item(() -> new SkillGemItem(), "skill_gems/skill/int");

    public static RegObj<SkillGemItem> AURA_STR = Def.item(() -> new SkillGemItem(), "skill_gems/aura/str");
    public static RegObj<SkillGemItem> AURA_DEX = Def.item(() -> new SkillGemItem(), "skill_gems/aura/dex");
    public static RegObj<SkillGemItem> AURA_INT = Def.item(() -> new SkillGemItem(), "skill_gems/aura/int");

    public static Item get(SkillGemData data) {

        if (data.type == SkillGemData.SkillGemType.AURA) {
            if (data.getStyle() == PlayStyle.INT) {
                return AURA_INT.get();
            }
            if (data.getStyle() == PlayStyle.STR) {
                return AURA_STR.get();
            }
            if (data.getStyle() == PlayStyle.DEX) {
                return AURA_DEX.get();
            }
        }
        if (data.type == SkillGemData.SkillGemType.SKILL) {
            if (data.getStyle() == PlayStyle.INT) {
                return SKILL_INT.get();
            }
            if (data.getStyle() == PlayStyle.STR) {
                return SKILL_STR.get();
            }
            if (data.getStyle() == PlayStyle.DEX) {
                return SKILL_DEX.get();
            }
        }
        if (data.type == SkillGemData.SkillGemType.SUPPORT) {
            if (data.getStyle() == PlayStyle.INT) {
                return SUPP_INT.get();
            }
            if (data.getStyle() == PlayStyle.STR) {
                return SUPP_STR.get();
            }
            if (data.getStyle() == PlayStyle.DEX) {
                return SUPP_DEX.get();
            }
        }

        return Items.AIR;
    }

    public static void init() {

    }


}
