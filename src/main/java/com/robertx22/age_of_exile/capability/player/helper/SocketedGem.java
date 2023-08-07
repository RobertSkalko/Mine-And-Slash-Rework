package com.robertx22.age_of_exile.capability.player.helper;

import com.robertx22.age_of_exile.saveclasses.skill_gem.SkillGemData;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import com.robertx22.age_of_exile.uncommon.utilityclasses.PlayerUtils;
import com.robertx22.library_of_exile.wrappers.ExileText;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class SocketedGem {

    MyInventory inv;

    public int skillGem = 0;


    public int getMaxLinks() {
        if (getSkillData() == null) {
            return 0;
        } else {
            return getSkillData().links;
        }
    }

    public void removeSupportGemsIfTooMany(Player p) {

        if (getSupportDatas().size() > getMaxLinks()) {

            for (ItemStack s : this.getSupports()) {
                PlayerUtils.giveItem(s.copy(), p);
                s.shrink(100);
            }

            p.sendSystemMessage(ExileText.ofText("You can't equip that many support gems! You can increase the total links with Orb of Linking.").get());
        }
    }

    public SocketedGem(MyInventory inv, int skillGem) {
        this.inv = inv;
        this.skillGem = skillGem;
    }


    public int getHotbarSlot() {
        return skillGem / (GemInventoryHelper.SUPPORT_GEMS_PER_SKILL + 1); // todo is this correct
    }

    public ItemStack getSkill() {
        
        return inv.getItem(skillGem);
    }


    public SkillGemData getSkillData() {
        return StackSaving.SKILL_GEM.loadFrom(getSkill());
    }

    public List<ItemStack> getSupports() {
        List<ItemStack> list = new ArrayList<>();
        for (int i = 1 + skillGem; i < skillGem + GemInventoryHelper.SUPPORT_GEMS_PER_SKILL + 1; i++) {
            list.add(inv.getItem(i));
        }
        return list;
    }

    public List<SkillGemData> getSupportDatas() {
        List<SkillGemData> list = new ArrayList<>();
        for (ItemStack s : getSupports()) {
            SkillGemData d = StackSaving.SKILL_GEM.loadFrom(s);
            if (d != null && d.getSupport() != null) {
                list.add(d);
            }
        }
        return list;
    }

    public float getManaCostMulti() {

        float multi = 1;

        for (SkillGemData data : this.getSupportDatas()) {
            multi *= data.getSupport().manaMulti;

        }

        return multi;
    }

}
