package com.robertx22.age_of_exile.saveclasses.skill_gem;

import com.robertx22.age_of_exile.database.data.aura.AuraGem;
import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.support_gem.SupportGem;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.gui.inv_gui.actions.auto_salvage.ToggleAutoSalvageRarity;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.RarityItems;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.SkillGemsItems;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipContext;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.ICommonDataItem;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipUtils;
import com.robertx22.library_of_exile.utils.ItemstackDataSaver;
import com.robertx22.library_of_exile.wrappers.ExileText;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SkillGemData implements ICommonDataItem<GearRarity> {


    @Override
    public void BuildTooltip(TooltipContext ctx) {

    }


    @Override
    public List<ItemStack> getSalvageResult(ItemStack stack) {
        if (!isSalvagable()) {
            return Arrays.asList();
        }
        int amount = 1; // todo
        return Arrays.asList(new ItemStack(RarityItems.RARITY_STONE.get(getRarity().GUID()).get(), amount));
    }

    @Override
    public ToggleAutoSalvageRarity.SalvageType getSalvageType() {
        return ToggleAutoSalvageRarity.SalvageType.SPELL;
    }

    @Override
    public boolean isSalvagable() {
        return sal;
    }

    @Override
    public int getLevel() {
        return 1; // wait what
    }

    @Override
    public ItemstackDataSaver<SkillGemData> getStackSaver() {
        return StackSaving.SKILL_GEM;
    }

    @Override
    public void saveToStack(ItemStack stack) {
        getStackSaver().saveTo(stack, this);
    }


    public String id = "";
    public SkillGemType type = SkillGemType.SKILL;
    public int perc = 0;
    public String rar = IRarity.COMMON_ID;
    public int links = 1;
    public boolean sal = true;


    public enum SkillGemType {
        SKILL(), SUPPORT(), AURA();


    }


    @Override
    public String getRarityId() {
        return rar;
    }

    public GearRarity getRarity() {
        return ExileDB.GearRarities().get(rar);
    }

    public int getStatPercent() {
        return perc;
    }

    public Spell getSpell() {
        if (type == SkillGemType.SKILL) {
            return ExileDB.Spells().get(id);
        }
        return null;
    }

    public boolean canPlayerWear(Player p) {

        int req = getGeneric().getRequiredLevel();

        if (Load.Unit(p).getLevel() < req) {
            return false;
        }

        return true;
    }

    public AuraGem getAura() {
        if (type == SkillGemType.AURA) {
            return ExileDB.AuraGems().get(id);
        }
        return null;
    }

    public SupportGem getSupport() {
        if (type == SkillGemType.SUPPORT) {
            return ExileDB.SupportGems().get(id);
        }
        return null;
    }


    public ISkillGem getGeneric() {
        if (getSupport() != null) {
            return getSupport();
        }
        if (getSpell() != null) {
            return getSpell();
        }
        if (getAura() != null) {
            return getAura();
        }
        return null;
    }

    public PlayStyle getStyle() {
        return getGeneric().getStyle();
    }


    private MutableComponent stars() {

        return Component.literal("Support Slots: " + links + "/5").withStyle(ChatFormatting.DARK_PURPLE);

    }


    public List<Component> getTooltip(Player p) {

        List<Component> list = new ArrayList<>();

        GearRarity rar = getRarity();

        int req = getGeneric().getRequiredLevel();

        if (this.type == SkillGemType.SKILL) {
            Spell spell = getSpell();

            if (spell == null) {
                return list;
            }
            for (Component c : spell.GetTooltipString(new TooltipInfo(p))) {
                list.add(c);
            }

            list.add(ExileText.emptyLine().get());
            if (req > 0) {
                list.add(TooltipUtils.level(req));

            }

            list.add(ExileText.emptyLine().get());
            list.add(TooltipUtils.rarity(rar));

            list.add(stars());


            return list;
        }

        ISkillGem generic = getGeneric();


        list.add(generic.locName().withStyle(rar.textFormatting()));
        list.add(ExileText.emptyLine().get());


        if (this.type == SkillGemType.SUPPORT) {
            SupportGem supp = getSupport();
            for (ExactStatData ex : supp.GetAllStats(Load.Unit(p), this)) {
                list.addAll(ex.GetTooltipString(new TooltipInfo(p)));
            }

            list.add(ExileText.emptyLine().get());

            list.add(ExileText.ofText("Mana Cost Multiplier: " + (int) (supp.manaMulti * 100) + "%").get());

            if (supp.isOneOfAKind()) {
                list.add(Component.literal("Only One Allowed: " + supp.one_of_a_kind));
            }
        }

        if (this.type == SkillGemType.AURA) {
            AuraGem aura = getAura();

            for (ExactStatData ex : aura.GetAllStats(Load.Unit(p), this)) {
                list.addAll(ex.GetTooltipString(new TooltipInfo(p)));
            }

            list.add(ExileText.emptyLine().get());

            list.add(ExileText.ofText("Aura Reservation: " + (int) (aura.reservation * 100)).get());


            int spiritLeft = (int) Load.player(p).getSkillGemInventory().getRemainingSpirit(p);

            list.add(ExileText.emptyLine().get());

            list.add(Component.literal("Remaining Aura Capacity: " + spiritLeft).withStyle(ChatFormatting.AQUA));


        }

        if (req > 0) {
            list.add(TooltipUtils.level(req));

        }

        list.add(ExileText.emptyLine().get());

        list.add(TooltipUtils.rarity(rar));

        return list;


    }

    public Item getItem() {
        return SkillGemsItems.get(this);
    }

}
