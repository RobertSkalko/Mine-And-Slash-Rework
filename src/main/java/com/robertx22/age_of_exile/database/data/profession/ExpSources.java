package com.robertx22.age_of_exile.database.data.profession;

import com.robertx22.age_of_exile.database.data.profession.stat.ProfExp;
import com.robertx22.age_of_exile.saveclasses.prof_tool.ProfessionToolData;
import com.robertx22.age_of_exile.uncommon.MathHelper;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import com.robertx22.age_of_exile.uncommon.utilityclasses.LevelUtils;
import com.robertx22.library_of_exile.vanilla_util.main.VanillaUTIL;
import com.robertx22.temp.SkillItemTier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ExpSources {

    public static String REQ_GROWTH_STAGE = "req_growth_stage";
    public HashMap<Integer, List<ExpSource>> map = new HashMap<>();

    public enum Type {
        BLOCK, FARM_ITEM, ENTITY, CRAFTING, BLOCK_TAG
    }

    public void add(SkillItemTier tier, Type type, String id, int exp, String... req) {
        if (!map.containsKey(tier.tier)) {
            map.put(tier.tier, new ArrayList<>());
        }

        map.get(tier.tier).add(new ExpSource(type, exp, id, Arrays.asList(req)));


    }

    public static class ExpSource {

        public Type type;
        public int exp;
        public String id;
        public List<String> req;

        public ExpSource(Type type, int exp, String id, List<String> req) {
            this.type = type;
            this.exp = exp;
            this.id = id;
            this.req = req;
        }

        public boolean matches(Object obj) {

            if (type == Type.BLOCK_TAG) {
                if (obj instanceof TagKey<?> in) {
                    return in.location().equals(new ResourceLocation(id));
                }
            }
            if (type == Type.BLOCK) {
                if (obj instanceof Block in) {
                    return VanillaUTIL.REGISTRY.blocks().getKey(in).equals(new ResourceLocation(id));
                }
            }
            if (type == Type.FARM_ITEM) {
                if (obj instanceof Item in) {
                    return VanillaUTIL.REGISTRY.items().getKey(in).equals(new ResourceLocation(id));
                }
            }
            if (type == Type.ENTITY) {
                if (obj instanceof EntityType in) {
                    return BuiltInRegistries.ENTITY_TYPE.getKey(in).equals(new ResourceLocation(id));
                }
            }

            return false;
        }

    }


    public ExpData getData(Object obj) {
        for (SkillItemTier value : SkillItemTier.values()) {
            if (this.map.containsKey(value.tier)) {
                var opt = map.get(value.tier).stream().filter(x -> x.matches(obj)).findAny();
                if (opt.isPresent()) {
                    return new ExpData(opt.get().exp, value.tier, opt.get().req);
                }
            }
        }
        return null;
    }

    public ExpData getDefaultExp() {
        return new ExpData(50, 0, new ArrayList<>());
    }

    public ExpData exp(int exp, int tier) {
        return new ExpData(exp, tier, new ArrayList<>());
    }

    public class ExpData {


        public int exp;
        public int tier;
        public List<String> req = new ArrayList<>();

        public ExpData(int exp, int tier, List<String> req) {
            this.exp = exp;
            this.tier = tier;
            this.req = req;
        }

        public void levelTool(Player p, Profession pro, int xpgiven) {
            if (!pro.tool_tag.isEmpty()) {
                ItemStack stack = p.getMainHandItem();
                if (ProfessionToolData.isCorrectTool(pro, stack)) {
                    ProfessionToolData data = null;

                    if (StackSaving.TOOL.has(stack)) {
                        data = StackSaving.TOOL.loadFrom(stack);
                    } else {
                        data = new ProfessionToolData();
                        data.lvl = 1;
                        data.prof = pro.GUID();
                    }
                    data.addExp(p, stack, xpgiven);
                    StackSaving.TOOL.saveTo(stack, data);
                }
            }
        }

        public void giveExp(Player p, Profession pro) {
            float lvlmulti = MathHelper.clamp((float) Load.player(p).professions.getLevel(pro.GUID()) / (float) getLevelOfMastery(), 0F, 1F);

            var fx = LevelUtils.scaleExpReward(exp, Load.player(p).professions.getLevel(pro.id));

            fx *= Load.Unit(p).getUnit().getCalculatedStat(new ProfExp(pro.id)).getMultiplier();
            fx *= lvlmulti;

            levelTool(p, pro, fx);

            Load.player(p).professions.addExp(p, pro.GUID(), fx);
        }


        public int getLevelOfMastery() {
            return SkillItemTier.of(tier).levelRange.getMinLevel();
        }

        public float getLootChanceMulti(Player p, Profession pro) {
            float lvlmulti = MathHelper.clamp((float) Load.player(p).professions.getLevel(pro.GUID()) / (float) getLevelOfMastery(), 0F, 1F);
            return exp / 100F * lvlmulti;
        }
    }
}
