package com.robertx22.age_of_exile.vanilla_mc.items.gemrunes;

import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.aoe_data.datapacks.models.IAutoModel;
import com.robertx22.age_of_exile.aoe_data.datapacks.models.ItemModelManager;
import com.robertx22.age_of_exile.database.data.StatMod;
import com.robertx22.age_of_exile.database.data.currency.IItemAsCurrency;
import com.robertx22.age_of_exile.database.data.currency.base.Currency;
import com.robertx22.age_of_exile.database.data.currency.base.GearCurrency;
import com.robertx22.age_of_exile.database.data.currency.base.GearOutcome;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.LocReqContext;
import com.robertx22.age_of_exile.database.data.gear_types.bases.SlotFamily;
import com.robertx22.age_of_exile.database.data.runes.Rune;
import com.robertx22.age_of_exile.database.data.stats.types.defense.Armor;
import com.robertx22.age_of_exile.database.data.stats.types.defense.DodgeRating;
import com.robertx22.age_of_exile.database.data.stats.types.resources.energy.EnergyRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.HealthRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.magic_shield.MagicShield;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.ManaRegen;
import com.robertx22.age_of_exile.database.data.stats.types.spirit.AuraCapacity;
import com.robertx22.age_of_exile.database.data.stats.types.spirit.AuraEffect;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_parts.SocketData;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.uncommon.utilityclasses.ClientOnly;
import com.robertx22.age_of_exile.vanilla_mc.LuckyRandom;
import com.robertx22.library_of_exile.registry.IGUID;
import com.robertx22.library_of_exile.registry.IWeighted;
import com.robertx22.library_of_exile.vanilla_util.main.VanillaUTIL;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;


public class RuneItem extends Item implements IGUID, IAutoModel, IAutoLocName, IWeighted, IItemAsCurrency {

    public int weight = 1000;

    @Override
    public Currency currencyEffect(ItemStack stack) {
        return new RuneCurrency();
    }


    public class RuneCurrency extends GearCurrency {

        @Override
        public List<GearOutcome> getOutcomes() {
            return Arrays.asList(
                    new GearOutcome() {
                        @Override
                        public Words getName() {
                            return Words.Rune;
                        }

                        @Override
                        public OutcomeType getOutcomeType() {
                            return OutcomeType.GOOD;
                        }

                        @Override
                        public ItemStack modify(LocReqContext ctx, GearItemData gear, ItemStack stack) {
                            gear.sockets.removeRune();


                            //todo actually make this based on gear rarities
                            var rune = new SocketData();
                            rune.g = RuneItem.this.type.id;
                            rune.p = LuckyRandom.randomInt(0, 100, LuckyRandom.LuckyUnlucky.UNLUCKY, 5);

                            gear.sockets.getSocketed().add(rune);

                            gear.saveToStack(stack);
                            return stack;
                        }

                        @Override
                        public int Weight() {
                            return 1000;
                        }
                    }
            );
        }

        @Override
        public int getPotentialLoss() {
            return 0;
        }

        @Override
        public boolean canBeModified(GearItemData data) {

            Rune rune = ExileDB.Runes().get(RuneItem.this.type.id);

            if (rune.getFor(data.GetBaseGearType().family()).isEmpty()) {
                return false;
            }

            int runes = (int) data.sockets.getSocketed().stream().filter(x -> x.isRune()).count();
            return data.getEmptySockets() > 0 || runes == 1;
        }

        @Override
        public String locDescForLangFile() {
            return "";
        }

        @Override
        public String locNameForLangFile() {
            return "";
        }

        @Override
        public String GUID() {
            return "rune";
        }

        @Override
        public int Weight() {
            return 0;
        }
    }

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Misc;
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (pLevel.isClientSide) {
            if (pPlayer.isDiscrete()) {
                ClientOnly.ALLrunewordsScreen(pPlayer);
            } else {
                ClientOnly.runewordsScreen(pPlayer);
            }
        }
        return InteractionResultHolder.pass(pPlayer.getItemInHand(pUsedHand));

    }

    @Override
    public Component getName(ItemStack stack) {
        return Component.translatable(this.getDescriptionId()).withStyle(ChatFormatting.GOLD);
    }

    @Override
    public String locNameLangFileGUID() {
        return VanillaUTIL.REGISTRY.items().getKey(this)
                .toString();
    }

    @Override
    public String locNameForLangFile() {
        return type.locName + " Rune";
    }

    @Override
    public void generateModel(ItemModelManager manager) {
        manager.generated(this);
    }

    public RuneType type;

    public RuneItem(RuneType type) {
        super(new Properties().stacksTo(64));
        this.type = type;

        this.weight = type.weight;
    }

    @Override
    public String GUID() {
        return "runes/" + type.id;
    }


    @Override
    public int Weight() {
        return weight;
    }

    public enum RuneType {
        YUN(100, "yun", "Yun", 4, 0.8F, () -> StatPerType.of().addArmor(AuraEffect.getInstance().mod(1, 10).percent())),
        VEN(100, "ven", "Ven", 4, 0.8F, () -> StatPerType.of().addJewerly(AuraCapacity.getInstance().mod(1, 10))),
        NOS(1000, "nos", "Nos", 0, 0F, () -> StatPerType.of().addArmor(Health.getInstance().mod(1, 10).percent())),
        MOS(1000, "mos", "Mos", 0, 0f, () -> StatPerType.of().addArmor(MagicShield.getInstance().mod(1, 10).percent())),
        ITA(1000, "ita", "Ita", 0, 0f, () -> StatPerType.of().addJewerly(ManaRegen.getInstance().mod(1, 10).percent())),
        CEN(1000, "cen", "Cen", 1, 0.1F, () -> StatPerType.of().addArmor(Armor.getInstance().mod(1, 10).percent())),
        FEY(1000, "fey", "Fey", 1, 0.2F, () -> StatPerType.of().addJewerly(EnergyRegen.getInstance().mod(1, 10).percent())),
        DOS(1000, "dos", "Dos", 1, 0.1F, () -> StatPerType.of().addArmor(DodgeRating.getInstance().mod(1, 10).percent())),
        ANO(1000, "ano", "Ano", 2, 0.3F, () -> StatPerType.of().addJewerly(HealthRegen.getInstance().mod(1, 10).percent())),
        TOQ(1000, "toq", "Toq", 2, 0.4F, () -> StatPerType.of().addWeapon(Stats.CRIT_CHANCE.get().mod(5, 15))),
        ORU(500, "oru", "Oru", 4, 0.6F, () -> StatPerType.of().addWeapon(Stats.CRIT_DAMAGE.get().mod(5, 25))),
        WIR(200, "wir", "Wir", 4, 0.7F, () -> StatPerType.of().addWeapon(Stats.TOTAL_DAMAGE.get().mod(5, 20))),
        ENO(1000, "eno", "Eno", 3, 0.5F, () -> StatPerType.of().addArmor(Stats.MANA_COST.get().mod(2, 8))),
        HAR(1000, "har", "Har", 3, 0.4f, () -> StatPerType.of().addArmor(Stats.HEAL_STRENGTH.get().mod(3, 15))),
        XER(1000, "xer", "Xer", 3, 0.5f, () -> StatPerType.of().addArmor(Stats.SUMMON_DAMAGE.get().mod(3, 15)));

        public String id;
        public String locName;
        public int tier;
        public int weight;
        public float lvlmin;

        public Supplier<StatPerType> stats;

        RuneType(int weight, String id, String locName, int tier, float lvl, Supplier<StatPerType> stats) {
            this.id = id;
            this.locName = locName;
            this.tier = tier;
            this.weight = weight;
            this.lvlmin = lvl;
            this.stats = stats;
        }

    }

    public Rune getRune() {
        return ExileDB.Runes()
                .get(type.id);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag context) {

        try {

            tooltip.addAll(statsTooltip());


            tooltip.add(Component.literal("Click or Sneak+Click to Open RuneWord Crafting/Showcase"));
            tooltip.add(Component.literal("One allowed per Item."));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public List<Component> statsTooltip() {
        List<Component> tooltip = new ArrayList<>();

        int lvl = Load.Unit(ClientOnly.getPlayer()).getLevel();

        Rune gem = this.getRune();


        // TooltipInfo info = new TooltipInfo();

        List<StatMod> wep = gem.getFor(SlotFamily.Weapon);
        if (!wep.isEmpty()) {
            tooltip.add(Component.literal(""));

            tooltip.add(Component.literal("On Weapon:").withStyle(ChatFormatting.RED));
            for (StatMod x : wep) {
                tooltip.addAll(x.getEstimationTooltip(lvl));
            }
        }

        List<StatMod> armor = gem.getFor(SlotFamily.Armor);
        if (!armor.isEmpty()) {
            tooltip.add(Component.literal(""));

            tooltip.add(Component.literal("On Armor:").withStyle(ChatFormatting.BLUE));
            for (StatMod x : armor) {
                tooltip.addAll(x.getEstimationTooltip(lvl));
            }
        }
        List<StatMod> jewelry = gem.getFor(SlotFamily.Jewelry);
        if (!jewelry.isEmpty()) {
            tooltip.add(Component.literal(""));

            tooltip.add(Component.literal("On Jewelry:").withStyle(ChatFormatting.LIGHT_PURPLE));
            for (StatMod x : jewelry) {
                tooltip.addAll(x.getEstimationTooltip(lvl));
            }
        }

        tooltip.add(Component.literal(""));

        return tooltip;
    }
}
