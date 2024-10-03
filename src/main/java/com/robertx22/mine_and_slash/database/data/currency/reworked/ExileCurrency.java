package com.robertx22.mine_and_slash.database.data.currency.reworked;

import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.IAutoGson;
import com.robertx22.library_of_exile.registry.IWeighted;
import com.robertx22.library_of_exile.registry.JsonExileRegistry;
import com.robertx22.library_of_exile.utils.RandomUtils;
import com.robertx22.library_of_exile.vanilla_util.main.VanillaUTIL;
import com.robertx22.mine_and_slash.database.data.currency.base.ModifyResult;
import com.robertx22.mine_and_slash.database.data.currency.base.ResultItem;
import com.robertx22.mine_and_slash.database.data.currency.loc_reqs.LocReqContext;
import com.robertx22.mine_and_slash.database.data.currency.reworked.item_mod.ItemModification;
import com.robertx22.mine_and_slash.database.data.currency.reworked.item_mod.ItemMods;
import com.robertx22.mine_and_slash.database.data.currency.reworked.item_req.ItemReqs;
import com.robertx22.mine_and_slash.database.data.currency.reworked.item_req.ItemRequirement;
import com.robertx22.mine_and_slash.database.data.currency.reworked.keys.ExileKey;
import com.robertx22.mine_and_slash.database.data.currency.reworked.keys.ExileKeyHolder;
import com.robertx22.mine_and_slash.database.data.currency.reworked.keys.IdKey;
import com.robertx22.mine_and_slash.database.data.currency.reworked.keys.MaxUsesKey;
import com.robertx22.mine_and_slash.database.data.profession.ExplainedResult;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.database.registry.ExileRegistryTypes;
import com.robertx22.mine_and_slash.gui.texts.ExileTooltips;
import com.robertx22.mine_and_slash.gui.texts.textblocks.AdditionalBlock;
import com.robertx22.mine_and_slash.gui.texts.textblocks.NameBlock;
import com.robertx22.mine_and_slash.gui.texts.textblocks.RarityBlock;
import com.robertx22.mine_and_slash.gui.texts.textblocks.WorksOnBlock;
import com.robertx22.mine_and_slash.gui.texts.textblocks.dropblocks.LeagueBlock;
import com.robertx22.mine_and_slash.itemstack.ExileStack;
import com.robertx22.mine_and_slash.itemstack.StackKeys;
import com.robertx22.mine_and_slash.loot.req.DropRequirement;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import com.robertx22.mine_and_slash.mmorpg.UNICODE;
import com.robertx22.mine_and_slash.uncommon.interfaces.IAutoLocName;
import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.IRarity;
import com.robertx22.mine_and_slash.uncommon.localization.Chats;
import com.robertx22.mine_and_slash.uncommon.localization.Itemtips;
import com.robertx22.mine_and_slash.uncommon.localization.Words;
import com.robertx22.mine_and_slash.wip.ExileCached;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.*;

public class ExileCurrency implements IAutoLocName, IAutoGson<ExileCurrency>, JsonExileRegistry<ExileCurrency>, IRarity {

    public static ExileCurrency SERIALIZER = new ExileCurrency();

    String id;

    public int weight = 1000;

    public transient String locname = "";

    List<ItemModData> pick_one_item_mod = new ArrayList<>();
    List<ItemModData> always_do_item_mods = new ArrayList<>();
    List<String> req = new ArrayList<>();

    String rar = IRarity.RARE_ID;

    public DropRequirement drop_req = DropRequirement.Builder.of().build();

    public PotentialData potential = new PotentialData(true, 1);

    public String item_id = "";

    public static class PotentialData {

        public boolean needs_potential = true;

        public PotentialData(int potential_cost) {
            this.potential_cost = potential_cost;
        }

        public int potential_cost = 0;

        public PotentialData(boolean needs_potential, int potential_cost) {
            this.needs_potential = needs_potential;
            this.potential_cost = potential_cost;
        }

        public boolean has(ExileStack stack) {
            if (!needs_potential) {
                return true;
            }
            if (potential_cost < 1) {
                return true;
            }
            return stack.get(StackKeys.POTENTIAL).hasAndTrue(x -> x.potential >= this.potential_cost);
        }
    }

    public Item getItem() {
        return VanillaUTIL.REGISTRY.items().get(new ResourceLocation(item_id));
    }

    public static class ItemModData implements IWeighted {
        public String id = "";
        public int weight = 1000;

        public ItemModData(String id, int weight) {
            this.id = id;
            this.weight = weight;
        }

        public ItemModification get() {
            return ExileDB.ItemMods().get(id);
        }

        @Override
        public int Weight() {
            return weight;
        }
    }


    public WorksOnBlock.ItemType item_type = WorksOnBlock.ItemType.GEAR;


    @Override
    public String getRarityId() {
        return rar;
    }


    public static ExileCached<HashMap<Item, ExileCurrency>> CACHED_MAP = new ExileCached<>(() -> {
        HashMap<Item, ExileCurrency> map = new HashMap<>();
        for (ExileCurrency cur : ExileDB.Currency().getList()) {
            var i = cur.getItem();
            if (i != Items.AIR) {
                map.put(i, cur);
            }
        }
        return map;
    });

    public static Optional<ExileCurrency> get(ItemStack stack) {
        var res = CACHED_MAP.get().get(stack.getItem());
        return Optional.ofNullable(res);
    }

    public ResultItem modifyItem(LocReqContext ctx) {
        ExileStack stack = ctx.stack;

        boolean bad = false;
        try {

            if (this.potential.potential_cost > 0) {
                stack.get(StackKeys.POTENTIAL).edit(x -> x.spend(potential.potential_cost));
            }

            for (ItemModData mod : this.always_do_item_mods) {
                mod.get().applyMod(stack);
            }
            var picked = RandomUtils.weightedRandom(this.pick_one_item_mod);

            picked.get().applyMod(stack);

            if (picked.get().getOutcomeType() == ItemModification.OutcomeType.BAD) {
                bad = true;
            }


        } catch (Exception e) {
            e.printStackTrace();
            return new ResultItem(stack, ModifyResult.ERROR, ExplainedResult.failure(Component.literal("Code error caused the action to fail.")));
        }

        var res = new ResultItem(stack, ModifyResult.SUCCESS, ExplainedResult.success());
        if (bad) {
            res.outcome = ItemModification.OutcomeType.BAD;
        }
        return res;
    }

    public List<Component> getTooltip() {

        ExileTooltips tip = new ExileTooltips();

        tip.accept(new NameBlock(locName().withStyle(ChatFormatting.BOLD)));
        tip.accept(new RarityBlock(getRarity()));
        tip.accept(WorksOnBlock.usableOn(this.item_type));
        tip.accept(new AdditionalBlock(() -> {
            List<MutableComponent> all = new ArrayList<>();


            int totalWeight = this.pick_one_item_mod.stream().mapToInt(IWeighted::Weight).sum();


            if (!this.pick_one_item_mod.isEmpty()) {
                all.add(Component.empty());

                all.add(Words.RANDOM_OUTCOME.locName().withStyle(ChatFormatting.GREEN, ChatFormatting.BOLD));

                for (ItemModData data : this.pick_one_item_mod) {
                    var color = data.get().getOutcomeType().color;
                    all.add(getChanceTooltip(data, totalWeight).withStyle(color));
                }
            }

            if (!this.always_do_item_mods.isEmpty()) {
                all.add(Component.empty());
                all.add(Words.ALWAYS_DOES.locName().withStyle(ChatFormatting.RED, ChatFormatting.BOLD));
                for (ItemModData data : this.always_do_item_mods) {
                    var color = data.get().getOutcomeType().color;
                    all.add(getChanceTooltip(data, data.weight).withStyle(color));
                }
            }
            all.add(Component.empty());

            all.add(Words.Requirements.locName().withStyle(ChatFormatting.DARK_PURPLE, ChatFormatting.BOLD));
            for (String s : this.req) {
                var req = ExileDB.ItemReq().get(s);
                all.add(Component.literal(UNICODE.ROTATED_CUBE + " ").append(req.getDescWithParams()).withStyle(ChatFormatting.LIGHT_PURPLE));
            }
            return all;
        }));

        if (this.potential.potential_cost > 0) {
            tip.accept(new AdditionalBlock(Collections.singletonList(Words.POTENTIAL_COST.locName(Component.literal("" + potential.potential_cost).withStyle(ChatFormatting.GOLD)).withStyle(ChatFormatting.GOLD))));
        } else {
            tip.accept(new AdditionalBlock(Collections.singletonList(Words.NOT_A_POTENTIAL_CONSUMER.locName().withStyle(ChatFormatting.GOLD))));
        }
        tip.accept(new AdditionalBlock(Words.Currency.locName().withStyle(ChatFormatting.GOLD)));

        if (this.drop_req.getLeague() != null) {
            tip.accept(new LeagueBlock(drop_req.getLeague()));
        }

        return tip.release();
    }

    private MutableComponent getChanceTooltip(ItemModData mod, int totalweight) {
        int chance = (int) (((float) mod.weight / (float) totalweight) * 100F);

        return Component.literal(UNICODE.STAR + " ").append(Itemtips.OUTCOME_TIP.locName(mod.get().getDescWithParams(), Component.literal(chance + "%").withStyle(ChatFormatting.YELLOW)));
    }

    public ExplainedResult canItemBeModified(LocReqContext context) {
        if (!this.item_type.worksOn.apply(context.stack.getStack())) {
            return ExplainedResult.failure(Words.THIS_IS_NOT_A.locName(item_type.name.locName().withStyle(ChatFormatting.RED)));
        }
        if (!this.potential.has(context.stack)) {
            return ExplainedResult.failure(Chats.GEAR_NO_POTENTIAL.locName());
        }
        for (String s : this.req) {
            var r = ExileDB.ItemReq().get(s);
            if (!r.isValid(context.stack)) {
                // todo do i want to add custom fail messages instead?
                return ExplainedResult.failure(r.getDescWithParams());
            }
        }
        return ExplainedResult.success();
    }

    @Override
    public void onLoadedFromJson() {
        CACHED_MAP.clear();
    }

    @Override
    public ExileRegistryType getExileRegistryType() {
        return ExileRegistryTypes.CURRENCY;
    }

    @Override
    public Class<ExileCurrency> getClassForSerialization() {
        return ExileCurrency.class;
    }

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Currency_Items;
    }

    @Override
    public String locNameLangFileGUID() {
        return SlashRef.MODID + ".currency." + GUID();
    }

    @Override
    public String locNameForLangFile() {
        return locname;
    }

    @Override
    public String GUID() {
        return id;
    }

    @Override
    public int Weight() {
        return weight;
    }


    public static class Builder {

        private List<ItemModData> pickOneMods = new ArrayList<>();
        private List<ItemModData> useAllMods = new ArrayList<>();
        private List<String> req = new ArrayList<>();

        private DropRequirement drop = DropRequirement.Builder.of().build();
        private String id;
        private PotentialData pot = new PotentialData(1);
        private int weight = 1000;
        private String name;
        private WorksOnBlock.ItemType type;
        private String rar = IRarity.RARE_ID;

        public static Builder of(String id, String name, WorksOnBlock.ItemType type) {
            return new Builder(id, name, type);
        }

        public Builder(String id, String name, WorksOnBlock.ItemType type) {
            this.id = id;
            this.type = type;
            this.name = name;
        }


        public Builder addModification(ExileKey<ItemModification, ?> modification, int weight) {
            this.pickOneMods.add(new ItemModData(modification.GUID(), weight));
            return this;
        }

        public Builder addRequirement(ExileKey<ItemRequirement, ?> requirement) {
            this.req.add(requirement.GUID());
            return this;
        }

        public Builder dropsOnlyIn(DropRequirement req) {
            this.drop = req;
            return this;
        }

        public Builder rarity(String rar) {
            this.rar = rar;
            return this;
        }

        public Builder maxUses(MaxUsesKey key) {
            this.useAllMods.add(new ItemModData(ItemMods.INSTANCE.MAXIMUM_USES.get(key).GUID(), 1));
            this.addRequirement(ItemReqs.INSTANCE.MAXIMUM_USES.get(key));
            return this;
        }

        public Builder weight(int w) {
            this.weight = w;
            return this;
        }

        public Builder potentialCost(int cost) {
            this.pot.potential_cost = cost;
            return this;
        }


        public ExileKey<ExileCurrency, IdKey> build(ExileKeyHolder<ExileCurrency> holder) {
            return new ExileKey<>(holder, new IdKey(id), (x, y) -> {
                return buildCurrency(holder);
            }, id);
        }

        public ExileCurrency buildCurrency(ExileKeyHolder<ExileCurrency> holder) {
            ExileCurrency currency = new ExileCurrency();
            currency.id = id;
            currency.pick_one_item_mod = this.pickOneMods;
            currency.req = this.req;
            currency.always_do_item_mods = useAllMods;
            currency.item_type = type;
            currency.rar = rar;
            currency.locname = name;
            currency.weight = weight;
            currency.drop_req = drop;
            currency.potential = pot;
            currency.item_id = holder.getItemId(currency.id).toString();
            return currency;
        }
    }
}
