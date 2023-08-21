package com.robertx22.age_of_exile.saveclasses.item_classes;

import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.database.data.affixes.Affix;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.data.gear_types.bases.SlotFamily;
import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.database.data.requirements.bases.GearRequestedFor;
import com.robertx22.age_of_exile.database.data.unique_items.UniqueGear;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.gui.inv_gui.actions.auto_salvage.ToggleAutoSalvageRarity;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.RarityItems;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.*;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_parts.*;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.ICommonDataItem;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.uncommon.utilityclasses.LevelUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipUtils;
import com.robertx22.library_of_exile.utils.ItemstackDataSaver;
import com.robertx22.library_of_exile.wrappers.ExileText;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GearItemData implements ICommonDataItem<GearRarity> {


    // Stats
    public BaseStatsData baseStats = new BaseStatsData();
    public ImplicitStatsData imp = new ImplicitStatsData(); // implicit stats
    public GearAffixesData affixes = new GearAffixesData();
    public GearSocketsData sockets = new GearSocketsData();
    public UniqueStatsData uniqueStats;


    // Stats

    // i added rename ideas to comments. As tiny as possible while still allowing people to understand kinda what it is
    // apparently people had big issues with many storage mods, So i should try minimize the nbt.
    public String uniq = ""; // uniq_id
    public String rar = IRarity.COMMON_ID; // rar

    public int rp = -1; // pre_name rare prefix
    public int rs = -1; // suf_name rare suffix

    public int lvl = 1; // lvl
    public String gtype = "";
    // potential
    private String pot = IRarity.COMMON_ID;
    // potential number
    private int pn = 0;
    // salvagable
    public boolean sal = true;
    public boolean c = false; // corrupted

    private int qual = 0;
    private GearQualityType qt = GearQualityType.BASE;

    public int getQuality() {
        return qual;
    }

    public float getQualityBaseStatsMulti() {
        if (getQualityType() == GearQualityType.BASE) {
            return 1F + (getQuality() / 100F);
        }
        return 1;
    }

    public GearQualityType getQualityType() {
        return qt;
    }

    public enum GearQualityType {
        BASE(ChatFormatting.GOLD),
        POT(ChatFormatting.RED);

        public ChatFormatting color;

        GearQualityType(ChatFormatting color) {
            this.color = color;
        }
    }

    public void setQuality(int a, GearQualityType type) {
        this.qual = a;
        this.qt = type;
    }

    public boolean isCorrupted() {
        return c;
    }


    public int getTier() {
        return LevelUtils.levelToTier(lvl);
    }

    public int getLevel() {
        return this.lvl;
    }

    @Override
    public ToggleAutoSalvageRarity.SalvageType getSalvageType() {
        return ToggleAutoSalvageRarity.SalvageType.GEAR;
    }

    public StatRequirement getRequirement() {
        return GetBaseGearType().req;
    }

    public boolean canPlayerWear(EntityData data) {

        if (this.getLevel() > data.getLevel()) {
            return false;
        }

        if (!getRequirement().meetsReq(this.getLevel(), data)) {
            return false;
        }


        return true;

    }

    public boolean isValidItem() {

        return ExileDB.GearTypes()
                .isRegistered(gtype);
    }


    public int getEmptySockets() {

        return sockets.getTotalSockets() - this.sockets.getSocketedGemsCount();
    }

    public boolean canGetAffix(Affix affix) {

        if (affix.only_one_per_item && affixes.containsAffix(affix)) {
            return false;
        }

        return affix.meetsRequirements(new GearRequestedFor(this));

    }

    public int getPotentialNumber() {
        return pn;
    }

    public GearRarity.Potential getPotential() {
        return ExileDB.GearRarities().get(pot).pot;
    }


    public float getAdditionalPotentialMultiFromQuality() {
        if (getQualityType() != GearQualityType.POT) {
            return 0;
        }
        return getQuality() / 100F;
    }


    public ChatFormatting getPotentialColor() {
        return ExileDB.GearRarities().get(pot).textFormatting();
    }

    public void setPotentialRarity(GearRarity rar) {
        this.pot = rar.GUID();
    }

    public void setPotential(int potential) {
        this.pn = potential;
        if (pn < 0) {
            var lower = ExileDB.GearRarities().getFilterWrapped(x -> x.getHigherRarity() == ExileDB.GearRarities().get(pot)).list;
            if (this.isUnique()) {
                if (lower.isEmpty()) {
                    lower = Arrays.asList(ExileDB.GearRarities().get(IRarity.COMMON_ID));
                }
            }
            if (!lower.isEmpty()) {
                GearRarity newrar = lower.get(0);
                this.pn = newrar.pot.total;
                this.pot = newrar.GUID();
            } else {
                pn = 0;
            }
        }
    }

    public GearItemEnum getGearEnum() {

        if (this.isUnique()) {
            return GearItemEnum.UNIQUE;
        }

        return GearItemEnum.NORMAL;
    }

    @Override
    public String getRarityId() {
        return rar;
    }

    @Override
    public GearRarity getRarity() {
        return ExileDB.GearRarities()
                .get(this.rar);
    }

    public Component name(ItemStack stack) {
        return stack.getHoverName();
    }

    public void WriteOverDataThatShouldStay(GearItemData newdata) {

        newdata.sal = this.sal;

    }

    public BaseGearType GetBaseGearType() {
        return ExileDB.GearTypes()
                .get(gtype);
    }

    public List<MutableComponent> GetDisplayName(ItemStack stack) {

        try {
            ChatFormatting format = this.getRarity()
                    .textFormatting();

            if (useFullAffixedName()) {
                return getFullAffixedName();
            } else {
                if (isUnique()) {
                    return getUniqueName();
                } else {
                    return getTooManyAffixesName();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Arrays.asList();
    }


    private List<MutableComponent> getFullAffixedName() {
        List<MutableComponent> list = new ArrayList<>();
        ChatFormatting format = this.getRarity()
                .textFormatting();

        MutableComponent text = ExileText.emptyLine().get();

        if (affixes.hasPrefix()) {

            AffixData prefix = affixes.pre.get(0);

            text.append(prefix.BaseAffix()
                    .locName());
        }
        if (this.uniqueStats == null) {
            text.append(" ").append(GetBaseGearType().locName());
        } else {
            text.append(uniqueStats.getUnique(this)
                    .locName()
            );
        }

        if (affixes.hasSuffix()) {
            AffixData suffix = affixes.suf.get(0);
            text.append(" ")
                    .append(suffix.BaseAffix()
                            .locName());
        }

        text.withStyle(format);

        list.addAll(TooltipUtils.cutIfTooLong(text, format));

        return list;

    }

    private List<MutableComponent> getUniqueName() {
        List<MutableComponent> list = new ArrayList<>();
        ChatFormatting format = this.getRarity()
                .textFormatting();

        UniqueGear uniq = this.uniqueStats.getUnique(this);

        MutableComponent txt = ExileText.emptyLine().get().append(uniq.locName()
                .withStyle(format));

        if (!uniq.replaces_name) {
            txt.append(ExileText.ofText(format + " ").append(GetBaseGearType().locName().withStyle(ChatFormatting.BOLD))
                    .format(format).format(ChatFormatting.BOLD).get());
        }

        list.addAll(TooltipUtils.cutIfTooLong(txt, format));

        return list;
    }

    private List<MutableComponent> getTooManyAffixesName() {
        List<MutableComponent> list = new ArrayList<>();
        ChatFormatting format = this.getRarity()
                .textFormatting();


        Words prefix = RareItemAffixNames.getPrefix(this);
        Words suffix = RareItemAffixNames.getSuffix(this);

        if (prefix != null && suffix != null) {

            MutableComponent txt = ExileText.emptyLine().get();

            txt.append("").append(prefix.locName());

            txt.append(" ").append(suffix.locName()).withStyle(format);

            txt.append(" ").append(GetBaseGearType().locName());

            txt.withStyle(format);

            list.addAll(TooltipUtils.cutIfTooLong(txt, format));

            return list;
        }

        return list;

    }

    private boolean useFullAffixedName() {

        if (isUnique() && affixes.getNumberOfAffixes() == 0) {
            return false;
        }
        if (affixes.getNumberOfPrefixes() > 1) {
            return false;
        }
        if (affixes.getNumberOfSuffixes() > 1) {
            return false;
        }
        return true;
    }

    public List<IStatsContainer> GetAllStatContainers() {

        List<IStatsContainer> list = new ArrayList<IStatsContainer>();

        IfNotNullAdd(baseStats, list);

        IfNotNullAdd(imp, list);

        affixes.getAllAffixesAndSockets()
                .forEach(x -> IfNotNullAdd(x, list));

        IfNotNullAdd(sockets, list);

        IfNotNullAdd(uniqueStats, list);

        return list;

    }

    public List<IStatsContainer> GetAllStatContainersExceptBase() {
        return this.GetAllStatContainers().stream().filter(x -> x instanceof BaseStatsData == false).collect(Collectors.toList());


    }

    public List<ExactStatData> GetAllStats() {

        List<ExactStatData> list = new ArrayList<>();
        for (IStatsContainer x : GetAllStatContainers()) {

            List<ExactStatData> stats = x.GetAllStats(this);

            stats.forEach(s -> {
                list.add(s);
            });

        }
        return list;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void BuildTooltip(TooltipContext ctx) {
        GearTooltipUtils.BuildTooltip(this, ctx.stack, ctx.tooltip, ctx.data);
    }

    public List<IRerollable> GetAllRerollable() {
        List<IRerollable> list = new ArrayList<IRerollable>();
        IfNotNullAdd(baseStats, list);

        affixes.getAllAffixesAndSockets()
                .forEach(x -> IfNotNullAdd(x, list));

        list.add(imp);

        IfNotNullAdd(uniqueStats, list);
        return list;
    }

    private <T> void IfNotNullAdd(T obj, List<T> list) {
        if (obj != null) {
            list.add(obj);
        }
    }


    @Override
    public List<ItemStack> getSalvageResult(ItemStack stack) {

        if (this.sal) {

            int amount = 1;
            return Arrays.asList(new ItemStack(RarityItems.RARITY_STONE.get(getRarity().GUID()).get(), amount));
        }

        return Arrays.asList(ItemStack.EMPTY);
    }

    @Override
    public boolean isSalvagable() {
        if (this.isUnique()) {
            return false; // todo add salvage for uniques
        }
        return this.sal;
    }

    @Override
    public ItemstackDataSaver<GearItemData> getStackSaver() {
        return StackSaving.GEARS;
    }

    @Override
    public void saveToStack(ItemStack stack) {
        getStackSaver().saveTo(stack, this);
    }

    public boolean isWeapon() {
        try {
            if (GetBaseGearType()
                    .family()
                    .equals(SlotFamily.Weapon)) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


}
