package com.robertx22.age_of_exile.capability.player.helper;

import com.robertx22.age_of_exile.database.data.aura.AuraGem;
import com.robertx22.age_of_exile.database.data.stats.types.spirit.SpiritReservation;
import com.robertx22.age_of_exile.saveclasses.skill_gem.SkillGemData;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.MiscStatCtx;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.StatContext;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import com.robertx22.age_of_exile.uncommon.utilityclasses.PlayerUtils;
import com.robertx22.library_of_exile.wrappers.ExileText;
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

    public GemInventoryHelper(MyInventory inv, MyInventory auras) {
        this.inv = inv;
        this.auras = auras;


    }

    public MyInventory getGemsInv() {
        return inv;
    }

    public MyInventory getAuraInv() {
        return auras;
    }

    public ItemStack getSkillGem(int num) {
        int index = num * (SUPPORT_GEMS_PER_SKILL + 1);
        return inv.getItem(index);
    }


    public List<ItemStack> getSupportGems(int num) {

        int index = num * (SUPPORT_GEMS_PER_SKILL + 1);

        var list = new ArrayList<ItemStack>();

        for (int i = index; i < SUPPORT_GEMS_PER_SKILL; i++) {
            list.add(inv.getItem(i));
        }
        return list;

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

    public float getSpiritReserved(Player p, float multi) {

        float res = 0;

        for (SkillGemData aura : getAurasGems()) {
            res += aura.getAura().reservation * multi;
        }

        return res;
    }

    public void removeAurasIfCantWear(Player p) {

        float multi = Load.Unit(p).getUnit().getCalculatedStat(SpiritReservation.getInstance()).getReverseMultiplier();


        if (getSpiritReserved(p, multi) > 1) {
            for (ItemStack s : getAuras()) {
                PlayerUtils.giveItem(s.copy(), p);
                s.shrink(100);
            }
            p.sendSystemMessage(ExileText.ofText("You lack spirit to equip all these auras.").get());
        }

        if (hasDuplicates()) {
            for (ItemStack s : getAuras()) {
                PlayerUtils.giveItem(s.copy(), p);
                s.shrink(100);
            }
            p.sendSystemMessage(ExileText.ofText("You can't equip duplicate aura gems.").get());
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

                ctx.add(new MiscStatCtx(aura.GetAllStats(Load.Unit(en), data)));

            }
        }

        return ctx;
    }

}
