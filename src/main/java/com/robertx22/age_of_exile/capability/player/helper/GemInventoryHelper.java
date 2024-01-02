package com.robertx22.age_of_exile.capability.player.helper;

import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.database.data.aura.AuraGem;
import com.robertx22.age_of_exile.database.data.aura.AuraGems;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.stats.types.spirit.AuraCapacity;
import com.robertx22.age_of_exile.saveclasses.skill_gem.SkillGemData;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.AuraStatCtx;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.StatContext;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import com.robertx22.age_of_exile.uncommon.localization.Chats;
import com.robertx22.age_of_exile.uncommon.utilityclasses.PlayerUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class GemInventoryHelper {
    public static int TOTAL_AURAS = 9;

    public static int MAX_SKILL_GEMS = 8;
    public static int SUPPORT_GEMS_PER_SKILL = 5;
    public static int TOTAL_SLOTS = MAX_SKILL_GEMS * (1 + SUPPORT_GEMS_PER_SKILL);


    MyInventory inv;
    MyInventory auras;

    Player player;

    public GemInventoryHelper(Player p, MyInventory inv, MyInventory auras) {
        this.player = p;
        this.inv = inv;
        this.auras = auras;
    }


    /*
    public List<SkillGemData> getAllSkillGems() {
        List<SkillGemData> list = new ArrayList<>();
        for (int i = 0; i < MAX_SKILL_GEMS; i++) {
            list.add(this.getHotbarGem(i).getSkillData());
        }
        list.removeIf(x -> x == null || x.getSpell() == null);
        return list;

    }

     */

    public void removeSupportGemsIfTooMany(Player p) {
        for (int i = 0; i < MAX_SKILL_GEMS; i++) {
            this.getHotbarGem(i).removeSupportGemsIfTooMany(p);
        }
    }

    public MyInventory getGemsInv() {
        return inv;
    }

    public MyInventory getAuraInv() {
        return auras;
    }


    public SocketedGem getHotbarGem(int hotbar) {
        try {
            int index = hotbar;
            int invindex = hotbar * (SUPPORT_GEMS_PER_SKILL + 1);

            return new SocketedGem(this.getGemsInv(), Load.player(player).spellCastingData.getSpellData(index), invindex);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public SocketedGem getIndexGem(int index) {
        try {
            int invindex = index * (SUPPORT_GEMS_PER_SKILL + 1);

            return new SocketedGem(this.getGemsInv(), Load.player(player).spellCastingData.getSpellData(index), invindex);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public SocketedGem getSpellGem(Spell spell) {
        for (int i = 0; i < MAX_SKILL_GEMS; i++) {
            int index = i;
            int invindex = index * (SUPPORT_GEMS_PER_SKILL + 1);

            var gem = getIndexGem(index);
            if (gem != null && gem.getSkillData() != null && gem.getSkillData().getSpell() != null && gem.getSkillData().getSpell().GUID().equals(spell.GUID())) {
                return new SocketedGem(this.getGemsInv(), Load.player(player).spellCastingData.getSpellData(index), invindex);
            }
        }
        return null;
    }


    public List<ItemStack> getAuras() {
        var list = new ArrayList<ItemStack>();

        for (int i = 0; i < auras.getContainerSize(); i++) {
            list.add(auras.getItem(i));
        }
        return list;

    }

    public List<SkillGemData> getAurasGems() {
        var list = new ArrayList<SkillGemData>();

        for (ItemStack stack : getAuras()) {
            SkillGemData data = StackSaving.SKILL_GEM.loadFrom(stack);

            if (data != null) {
                list.add(data);
            }
        }
        return list;
    }

    public int getSpiritReserved(Player p) {
        int res = 0;

        var data = Load.Unit(p).getUnit();

        for (SkillGemData aura : getAurasGems()) {

            float cost = aura.getAura().reservation * 100F;

            // todo need better ways, this one is equals based on instance, so its never true lol
            var stat = Stats.SPECIFIC_AURA_COST.get(new AuraGems.AuraInfo(aura.getAura()));
            if (data.getCalculatedStat(stat).isNotZero()) {
                float multi = data.getCalculatedStat(stat).getMultiplier();
                cost *= multi;
            }

            res += cost;
        }

        return res;
    }

    public int getRemainingSpirit(Player p) {

        int reserved = getSpiritReserved(p);
        int spirit = getTotalSpirit(p);

        return spirit - reserved;
    }

    public int getTotalSpirit(Player p) {
        int num = (int) Load.Unit(p).getUnit().getCalculatedStat(AuraCapacity.getInstance()).getValue();
        if (num < 1) {
            num = (int) AuraCapacity.getInstance().base;
        }
        return num;
    }


    public void removeAurasIfCantWear(Player p) {

        if (getRemainingSpirit(p) < 0) {
            for (ItemStack s : getAuras()) {
                PlayerUtils.giveItem(s.copy(), p);
                s.shrink(100);
            }
            p.sendSystemMessage(Chats.LACK_AURA_CAPACITY.locName());
        }

        if (hasDuplicates()) {
            for (ItemStack s : getAuras()) {
                PlayerUtils.giveItem(s.copy(), p);
                s.shrink(100);
            }
            p.sendSystemMessage(Chats.NO_DUPLICATE_AURA.locName());
        }
    }


    private boolean hasDuplicates() {
        List<String> list = getAurasGems().stream().map(x -> x.id).collect(Collectors.toList());
        Set<String> set = new HashSet<String>(list);

        if (set.size() < list.size()) {
            return true;
        }
        return false;
    }

    public List<StatContext> getAuraStats(LivingEntity en) {
        List<StatContext> ctx = new ArrayList<>();

        for (ItemStack stack : getAuras()) {
            SkillGemData data = StackSaving.SKILL_GEM.loadFrom(stack);
            if (data != null) {
                AuraGem aura = data.getAura();
                ctx.add(new AuraStatCtx(aura.GetAllStats(Load.Unit(en), data)));
            }
        }

        return ctx;
    }

}
