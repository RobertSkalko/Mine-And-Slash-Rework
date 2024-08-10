package com.robertx22.mine_and_slash.capability.player.helper;

import com.robertx22.mine_and_slash.database.data.spells.components.Spell;
import com.robertx22.mine_and_slash.saveclasses.skill_gem.MaxLinks;
import com.robertx22.mine_and_slash.saveclasses.skill_gem.SkillGemData;
import com.robertx22.mine_and_slash.saveclasses.spells.SpellCastingData;
import com.robertx22.mine_and_slash.uncommon.datasaving.StackSaving;
import com.robertx22.mine_and_slash.uncommon.localization.Chats;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.PlayerUtils;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SocketedGem {

    MyInventory inv;

    public SpellCastingData.InsertedSpell spell;

    public int skillGem = 0;

    public SocketedGem(MyInventory inv, SpellCastingData.InsertedSpell spell, int skillGem) {
        this.inv = inv;
        this.spell = spell;
        this.skillGem = skillGem;
    }


    public MaxLinks getMaxLinks(Player p) {
        if (getSkillData() == null) {
            return new MaxLinks(0, false, false);
        } else {
            return getSkillData().getMaxLinks(p);

        }
    }

    public void removeSupportGemsIfTooMany(Player p) {

        if (getSupportDatas().size() > getMaxLinks(p).links) {
            for (ItemStack s : this.getSupports()) {
                PlayerUtils.giveItem(s.copy(), p);
                s.shrink(100);
            }
            p.sendSystemMessage(Chats.CANT_EQUIP_THAT_MANY_SUPPORTS.locName());
        }


        HashMap<String, Integer> map = new HashMap<>();

        boolean toomany = false;

        for (SkillGemData data : this.getSupportDatas()) {
            if (data.getSupport() == null) {
                continue;
            }
            map.put(data.getSupport().id, map.getOrDefault(data.getSupport().id, 0) + 1);
            if (map.get(data.getSupport().id) > 1) {
                toomany = true;
                break;
            }
            if (data.getSupport().isOneOfAKind()) {
                String id = data.getSupport().one_of_a_kind;
                map.put(id, map.getOrDefault(id, 0) + 1);

                if (map.get(id) > 1) {
                    toomany = true;
                    break;
                }
            }
        }

        if (toomany) {
            for (ItemStack s : this.getSupports()) {
                PlayerUtils.giveItem(s.copy(), p);
                s.shrink(100);
                p.sendSystemMessage(Chats.CANT_USE_MULTIPLE_SAME_SUPPORTS.locName());

            }
        }

    }


    public int getHotbarSlot() {
        return skillGem / (GemInventoryHelper.SUPPORT_GEMS_PER_SKILL + 1); // todo is this correct
    }

    public Spell getSpell() {
        var data = getSkillData();
        if (data != null) {
            return data.getSpell();
        }
        return null;
    }


    public SkillGemData getSkillData() {
        return this.spell.getData();
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
