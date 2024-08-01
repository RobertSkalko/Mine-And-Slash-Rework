package com.robertx22.age_of_exile.saveclasses.item_classes;

import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.database.data.affixes.Affix;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.data.gear_types.bases.SlotFamily;
import com.robertx22.age_of_exile.database.data.profession.PlayerUTIL;
import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.database.data.requirements.bases.GearRequestedFor;
import com.robertx22.age_of_exile.database.data.stat_compat.StatCompat;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.gui.inv_gui.actions.auto_salvage.ToggleAutoSalvageRarity;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.RarityItems;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.*;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_parts.*;
import com.robertx22.age_of_exile.saveclasses.item_classes.rework.DataKey;
import com.robertx22.age_of_exile.saveclasses.item_classes.rework.DataKeyHolder;
import com.robertx22.age_of_exile.saveclasses.item_classes.rework.GenericDataHolder;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.SimpleStatCtx;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.StatContext;
import com.robertx22.age_of_exile.uncommon.MathHelper;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.ICommonDataItem;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import com.robertx22.age_of_exile.uncommon.localization.Formatter;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.uncommon.utilityclasses.LevelUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipUtils;
import com.robertx22.library_of_exile.utils.ItemstackDataSaver;
import com.robertx22.library_of_exile.utils.RandomUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;
import java.util.stream.Collectors;


public class GearItemData implements ICommonDataItem<GearRarity> {

    public static KeyHolderClass KEYS = new KeyHolderClass();

    // with this custom data will be saved only when needed
    public static class KeyHolderClass extends DataKeyHolder {
        // public DataKey.RegistryKey<GearRarity> RARITY = of(new DataKey.RegistryKey<>("rar", ExileRegistryTypes.GEAR_RARITY));

        public DataKey.BooleanKey CORRUPT = of(new DataKey.BooleanKey("cr"));
        public DataKey.BooleanKey SALVAGING_DISABLED = of(new DataKey.BooleanKey("sl"));
        public DataKey.BooleanKey USED_SHARPENING_STONE = of(new DataKey.BooleanKey("us"));

        public DataKey.StringKey UNIQUE_ID = of(new DataKey.StringKey("uq"));

        public DataKey.IntKey QUALITY = of(new DataKey.IntKey("ql"));
        public DataKey.IntKey ENCHANT_TIMES = of(new DataKey.IntKey("et"));
        public DataKey.IntKey LEVEL_TIMES = of(new DataKey.IntKey("lt"));

    }

    // Stats
    public BaseStatsData baseStats = new BaseStatsData();
    public ImplicitStatsData imp = new ImplicitStatsData(); // implicit stats
    public GearAffixesData affixes = new GearAffixesData();
    public GearSocketsData sockets = new GearSocketsData();
    public UniqueStatsData uniqueStats;
    public GearInfusionData ench;

    public GenericDataHolder data = new GenericDataHolder();

    // Stats

    // i added rename ideas to comments. As tiny as possible while still allowing people to understand kinda what it is
    // apparently people had big issues with many storage mods, So i should try minimize the nbt.
    public String rar = IRarity.COMMON_ID; // rar


    public int lvl = 1; // lvl
    public String gtype = "";

    // potential
    // potential number
    private int pn = 0;
    // salvagable


    public int getQuality() {
        return data.get(KEYS.QUALITY);
    }

    public float getQualityBaseStatsMulti() {
        return 1F + (getQuality() / 100F);

    }


    public void setQuality(int a) {
        this.data.set(KEYS.QUALITY, a);
    }

    public boolean isCorrupted() {
        return data.get(KEYS.CORRUPT);
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

    @Override
    public String getSalvageConfigurationId() {
        return GetBaseGearType().gear_slot;
    }

    public StatRequirement getRequirement() {
        return GetBaseGearType().req;
    }

    public boolean canPlayerWear(EntityData data) {
        if (PlayerUTIL.isFake((Player) data.getEntity())) {
            return true;
        }
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


    public void setPotential(int potential) {
        this.pn = MathHelper.clamp(potential, 0, 1000000);
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


    public BaseGearType GetBaseGearType() {
        return ExileDB.GearTypes()
                .get(gtype);
    }

    public List<MutableComponent> GetDisplayName(ItemStack stack) {

        try {

            return getFullAffixedName();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return Arrays.asList();
    }

    private MutableComponent prefixChecker() {
        if (affixes.hasPrefix()) {
            // find strongest
            AffixData prefix = affixes.pre.stream().sorted(Comparator.comparingInt(x -> -x.p)).findFirst().get();
            return prefix.BaseAffix().locName();
        } else {
            return Component.literal("");
        }
    }

    private MutableComponent uniqueChecker() {
        var base = GetBaseGearType().locName();

        if (imp.has()) {
            base = imp.get().locName();
        }
        if (this.uniqueStats == null) {
            return base;
        } else {
            return Formatter.UNIQUE_NAME_FORMAT.locName(uniqueStats.getUnique(this).locName(), base).withStyle(getRarity().textFormatting());
        }
    }

    private MutableComponent suffixChecker() {

        if (affixes.hasSuffix()) {
            // find strongest
            AffixData suffix = affixes.suf.stream().sorted(Comparator.comparingInt(x -> -x.p)).findFirst().get();

            return suffix.BaseAffix().locName();
        } else {
            return Component.literal("");
        }
    }


    private List<MutableComponent> getFullAffixedName() {
        List<MutableComponent> list = new ArrayList<>();
        ChatFormatting format = this.getRarity()
                .textFormatting();

        // turns out the null mutablecomponent could remain a space-like char when form the name.
        //String[] name = processStrings(prefixChecker().getString(), uniqueChecker().getString(), suffixChecker().getString());
        MutableComponent text;

        String str1 = prefixChecker().getString();
        String str2 = uniqueChecker().getString();
        String str3 = suffixChecker().getString();

        // pre-gear-suf
        if (!str1.isEmpty() && !str2.isEmpty() && !str3.isEmpty()) {
            text = Formatter.GEAR_ITEM_NAME_ALL.locName(prefixChecker(), uniqueChecker(), suffixChecker());
        }
        // gear
        else if (str1.isEmpty() && !str2.isEmpty() && str3.isEmpty()) {
            text = Formatter.GEAR_ITEM_NAME_ONLY_GEAR.locName(uniqueChecker());
        }
        // pre-gear
        else if (!str1.isEmpty() && !str2.isEmpty() && str3.isEmpty()) {
            text = Formatter.GEAR_ITEM_NAME_PRE_GEAR.locName(prefixChecker(), uniqueChecker());
        }
        // another
        else {
            text = Formatter.GEAR_ITEM_NAME_ANOTHER.locName(prefixChecker(), uniqueChecker(), suffixChecker());
        }

        text.withStyle(format);

        list.addAll(TooltipUtils.cutIfTooLong(text, format));

        return list;

    }


    public List<IStatsContainer> GetAllStatContainers() {

        List<IStatsContainer> list = new ArrayList<IStatsContainer>();

        IfNotNullAdd(baseStats, list);

        IfNotNullAdd(imp, list);

        affixes.getAllAffixesAndSockets()
                .forEach(x -> IfNotNullAdd(x, list));

        IfNotNullAdd(sockets, list);

        IfNotNullAdd(uniqueStats, list);
        IfNotNullAdd(ench, list);

        return list;

    }

    public List<IStatsContainer> GetAllStatContainersExceptBase() {
        return this.GetAllStatContainers().stream().filter(x -> x instanceof BaseStatsData == false).collect(Collectors.toList());


    }

    public List<MutableComponent> getEnchantCompatTooltip(ItemStack stack) {
        var ench = getEnchantCompatStats(stack);

        List<MutableComponent> list = new ArrayList<>();

        if (ench == null) {
            return list;
        }

        list.add(Words.EnchantCompatStats.locName().withStyle(ChatFormatting.AQUA));
        for (ExactStatData stat : ench.stats) {
            list.addAll(stat.GetTooltipString());
        }

        return list;

    }

    public StatContext getEnchantCompatStats(ItemStack stack) {
        var list = new ArrayList<ExactStatData>();

        for (Map.Entry<Enchantment, Integer> en : stack.getAllEnchantments().entrySet()) {
            var id = ForgeRegistries.ENCHANTMENTS.getKey(en.getKey()).toString();
            // todo this could be cached
            for (StatCompat compat : ExileDB.StatCompat().getFilterWrapped(x -> x.isEnchantCompat() && x.enchant_id.equals(id)).list) {
                var result = compat.getEnchantCompatResult(stack, lvl);
                if (result != null) {
                    list.add(result);
                }
            }
        }
        if (list.isEmpty()) {
            return null;
        }
        StatContext ctx = new SimpleStatCtx(StatContext.StatCtxType.ENCHANT_COMPAT, list);
        return ctx;

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

        if (!data.get(KEYS.SALVAGING_DISABLED)) {
            if (this.isUnique()) {
                return Arrays.asList(new ItemStack(RandomUtils.randomFromList(RarityItems.RARITY_STONE.values().stream().toList()).get(), RandomUtils.RandomRange(2, 9)));
            }
            int amount = 1;

            return Arrays.asList(new ItemStack(RarityItems.RARITY_STONE.getOrDefault(getRarity().GUID(), RarityItems.RARITY_STONE.get(IRarity.COMMON_ID)).get(), amount));
        }

        return Arrays.asList(ItemStack.EMPTY);
    }

    @Override
    public boolean isSalvagable() {
        return !data.get(KEYS.SALVAGING_DISABLED);
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
