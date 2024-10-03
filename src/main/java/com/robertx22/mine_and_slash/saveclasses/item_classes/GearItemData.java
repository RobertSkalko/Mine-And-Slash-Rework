package com.robertx22.mine_and_slash.saveclasses.item_classes;

import com.robertx22.library_of_exile.utils.ItemstackDataSaver;
import com.robertx22.library_of_exile.utils.RandomUtils;
import com.robertx22.mine_and_slash.capability.entity.EntityData;
import com.robertx22.mine_and_slash.database.data.affixes.Affix;
import com.robertx22.mine_and_slash.database.data.gear_types.bases.BaseGearType;
import com.robertx22.mine_and_slash.database.data.gear_types.bases.SlotFamily;
import com.robertx22.mine_and_slash.database.data.profession.PlayerUTIL;
import com.robertx22.mine_and_slash.database.data.rarities.GearRarity;
import com.robertx22.mine_and_slash.database.data.requirements.bases.GearRequestedFor;
import com.robertx22.mine_and_slash.database.data.stat_compat.StatCompat;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.gui.inv_gui.actions.auto_salvage.ToggleAutoSalvageRarity;
import com.robertx22.mine_and_slash.itemstack.CustomItemData;
import com.robertx22.mine_and_slash.itemstack.ExileStack;
import com.robertx22.mine_and_slash.itemstack.StackKeys;
import com.robertx22.mine_and_slash.mmorpg.registers.common.items.RarityItems;
import com.robertx22.mine_and_slash.saveclasses.ExactStatData;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.IStatsContainer;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.StatRequirement;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.TooltipContext;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_parts.*;
import com.robertx22.mine_and_slash.saveclasses.unit.stat_ctx.SimpleStatCtx;
import com.robertx22.mine_and_slash.saveclasses.unit.stat_ctx.StatContext;
import com.robertx22.mine_and_slash.uncommon.datasaving.StackSaving;
import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.ICommonDataItem;
import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.IRarity;
import com.robertx22.mine_and_slash.uncommon.localization.Formatter;
import com.robertx22.mine_and_slash.uncommon.localization.Words;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.LevelUtils;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.TooltipUtils;
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

    // Stats
    public BaseStatsData baseStats = new BaseStatsData();
    public ImplicitStatsData imp = new ImplicitStatsData(); // implicit stats
    public GearAffixesData affixes = new GearAffixesData();
    public GearSocketsData sockets = new GearSocketsData();
    public UniqueStatsData uniqueStats;
    public GearInfusionData ench;

    //  public GenericDataHolder data = new GenericDataHolder();

    // Stats

    // i added rename ideas to comments. As tiny as possible while still allowing people to understand kinda what it is
    // apparently people had big issues with many storage mods, So i should try minimize the nbt.
    public String rar = IRarity.COMMON_ID; // rar

    public int lvl = 1; // lvl
    public String gtype = "";


    public float getQualityBaseStatsMulti(ExileStack stack) {
        return 1F + (stack.get(StackKeys.CUSTOM).getOrCreate().data.get(CustomItemData.KEYS.QUALITY) / 100F);
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

    public List<MutableComponent> GetDisplayName(ExileStack stack) {

        try {

            return getFullAffixedName(stack);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return Arrays.asList();
    }

    private MutableComponent prefixChecker(ExileStack stack) {
        if (affixes.hasPrefix()) {
            // find strongest
            AffixData prefix = affixes.pre.stream().sorted(Comparator.comparingInt(x -> -x.p)).findFirst().get();
            return prefix.BaseAffix().locName();
        } else {
            return Component.literal("");
        }
    }

    private MutableComponent uniqueChecker(ExileStack stack) {
        var base = GetBaseGearType().locName();

        if (imp.has()) {
            base = imp.get().locName();
        }
        if (this.uniqueStats == null) {
            return base;
        } else {
            var uniq = uniqueStats.getUnique(stack);
            if (uniq.replaces_name) {
                return uniq.locName().withStyle(getRarity().textFormatting());
            }
            return Formatter.UNIQUE_NAME_FORMAT.locName(uniqueStats.getUnique(stack).locName(), base).withStyle(getRarity().textFormatting());
        }
    }

    private MutableComponent suffixChecker(ExileStack stack) {

        if (affixes.hasSuffix()) {
            // find strongest
            AffixData suffix = affixes.suf.stream().sorted(Comparator.comparingInt(x -> -x.p)).findFirst().get();

            return suffix.BaseAffix().locName();
        } else {
            return Component.literal("");
        }
    }


    private List<MutableComponent> getFullAffixedName(ExileStack stack) {
        List<MutableComponent> list = new ArrayList<>();
        ChatFormatting format = this.getRarity()
                .textFormatting();

        // turns out the null mutablecomponent could remain a space-like char when form the name.
        //String[] name = processStrings(prefixChecker().getString(), uniqueChecker().getString(), suffixChecker().getString());
        MutableComponent text;

        String str1 = prefixChecker(stack).getString();
        String str2 = uniqueChecker(stack).getString();
        String str3 = suffixChecker(stack).getString();

        // pre-gear-suf
        if (!str1.isEmpty() && !str2.isEmpty() && !str3.isEmpty()) {
            text = Formatter.GEAR_ITEM_NAME_ALL.locName(prefixChecker(stack), uniqueChecker(stack), suffixChecker(stack));
        }
        // gear
        else if (str1.isEmpty() && !str2.isEmpty() && str3.isEmpty()) {
            text = Formatter.GEAR_ITEM_NAME_ONLY_GEAR.locName(uniqueChecker(stack));
        }
        // pre-gear
        else if (!str1.isEmpty() && !str2.isEmpty() && str3.isEmpty()) {
            text = Formatter.GEAR_ITEM_NAME_PRE_GEAR.locName(prefixChecker(stack), uniqueChecker(stack));
        }
        // another
        else {
            text = Formatter.GEAR_ITEM_NAME_ANOTHER.locName(prefixChecker(stack), uniqueChecker(stack), suffixChecker(stack));
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

    public List<ExactStatData> GetAllStats(ExileStack stack) {

        List<ExactStatData> list = new ArrayList<>();
        for (IStatsContainer x : GetAllStatContainers()) {
            List<ExactStatData> stats = x.GetAllStats(stack);
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


    private <T> void IfNotNullAdd(T obj, List<T> list) {
        if (obj != null) {
            list.add(obj);
        }
    }


    @Override
    public List<ItemStack> getSalvageResult(ExileStack stack) {

        if (!stack.get(StackKeys.CUSTOM).getOrCreate().data.get(CustomItemData.KEYS.SALVAGING_DISABLED)) {
            if (this.isUnique()) {
                return Arrays.asList(new ItemStack(RandomUtils.randomFromList(RarityItems.RARITY_STONE.values().stream().toList()).get(), RandomUtils.RandomRange(2, 9)));
            }
            int amount = 1;

            return Arrays.asList(new ItemStack(RarityItems.RARITY_STONE.getOrDefault(getRarity().GUID(), RarityItems.RARITY_STONE.get(IRarity.COMMON_ID)).get(), amount));
        }

        return Arrays.asList(ItemStack.EMPTY);
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
