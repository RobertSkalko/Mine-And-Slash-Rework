package com.robertx22.age_of_exile.database.data.spells.components;

import com.robertx22.age_of_exile.aoe_data.database.spells.SpellDesc;
import com.robertx22.age_of_exile.database.data.StatMod;
import com.robertx22.age_of_exile.database.data.exile_effects.ExileEffect;
import com.robertx22.age_of_exile.database.data.game_balance_config.GameBalanceConfig;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.SpellCastContext;
import com.robertx22.age_of_exile.database.data.value_calc.MaxLevelProvider;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.saveclasses.skill_gem.ISkillGem;
import com.robertx22.age_of_exile.saveclasses.spells.SpellCastingData;
import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import com.robertx22.age_of_exile.tags.all.SpellTags;
import com.robertx22.age_of_exile.tags.imp.SpellTag;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import com.robertx22.age_of_exile.uncommon.effectdatas.SpendResourceEvent;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.EventData;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import com.robertx22.age_of_exile.uncommon.enumclasses.WeaponTypes;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocDesc;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.age_of_exile.uncommon.localization.Chats;
import com.robertx22.age_of_exile.uncommon.localization.Gui;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.uncommon.utilityclasses.MapManager;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipUtils;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.IAutoGson;
import com.robertx22.library_of_exile.registry.IGUID;
import com.robertx22.library_of_exile.registry.JsonExileRegistry;
import com.robertx22.library_of_exile.wrappers.ExileText;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.apache.commons.lang3.StringUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public final class Spell implements ISkillGem, IGUID, IAutoGson<Spell>, JsonExileRegistry<Spell>, IAutoLocName, IAutoLocDesc, MaxLevelProvider {
    public static Spell SERIALIZER = new Spell();

    public static String DEFAULT_EN_NAME = "default_entity_name";
    public static String CASTER_NAME = "caster";

    public int weight = 1000;
    public String identifier = "";
    public AttachedSpell attached = new AttachedSpell();
    public SpellConfiguration config = new SpellConfiguration();

    public int min_lvl = 1;

    public int default_lvl = 0;

    public String lvl_based_on_spell = "";
    public String show_other_spell_tooltip = "";

    public boolean manual_tip = false;
    public List<String> disabled_dims = new ArrayList<>();
    public String effect_tip = "";

    public int max_lvl = 16; // first lvl unlocks spell, then every 3 lvls unlocks a supp gem slot?

    public List<StatMod> statsForSkillGem = new ArrayList<>();

    public transient String locDesc = "";

    public boolean isAllowedInDimension(Level world) {

        if (disabled_dims.isEmpty()) {
            return true;
        }
        return disabled_dims.stream()
                .map(x -> new ResourceLocation(x))
                .noneMatch(x -> x.equals(MapManager.getResourceLocation(world)));

    }

    public AttachedSpell getAttached() {
        return attached;
    }

    public List<ExactStatData> getStats(Player p) {
        int perc = (int) ((getLevelOf(p) / (float) getMaxLevelWithBonuses()) * 100F);
        var stats = statsForSkillGem.stream().map(x -> x.ToExactStat(perc, Load.Unit(p).getLevel())).collect(Collectors.toList());
        return stats;
    }

    public boolean is(SpellTag tag) {
        return config.tags.contains(tag);
    }

    public SpellConfiguration getConfig() {
        return config;
    }

    public void validate() {
        for (ComponentPart x : this.attached.getAllComponents()) {
            x.validate();
        }
    }

    public final ResourceLocation getIconLoc() {
        return getIconLoc(GUID());
    }

    public static final ResourceLocation getIconLoc(String id) {
        return new ResourceLocation(SlashRef.MODID, "textures/gui/spells/icons/" + id + ".png");
    }

    public WeaponTypes getWeapon(LivingEntity en) {
        try {
            if (getStyle() != PlayStyle.INT) {
                ItemStack stack = en.getMainHandItem();
                GearItemData gear = StackSaving.GEARS.loadFrom(stack);
                if (gear != null) {
                    return gear.GetBaseGearType().weapon_type;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return WeaponTypes.none;
    }

    public final void onCastingTick(SpellCastContext ctx) {

        int timesToCast = (int) ctx.spell.getConfig().times_to_cast;

        if (timesToCast > 1) {

            int castTimeTicks = (int) getCastTimeTicks(ctx);

            // if i didnt do this then cast time reduction would reduce amount of spell hits.
            int castEveryXTicks = castTimeTicks / timesToCast;

            if (timesToCast > 1) {
                if (castEveryXTicks < 1) {
                    castEveryXTicks = 1;
                }
            }

            if (ctx.ticksInUse > 0 && ctx.ticksInUse % castEveryXTicks == 0) {
                this.cast(ctx);
            }

        } else if (timesToCast < 1) {
            System.out.println("Times to cast spell is: " + timesToCast + " . this seems like a bug.");
        }

    }


    public void cast(SpellCastContext ctx) {

        LivingEntity caster = ctx.caster;

        ctx.castedThisTick = true;

        if (this.config.swing_arm) {
            caster.swingTime = -1; // this makes sure hand swings
            caster.swing(InteractionHand.MAIN_HAND);
        }


        attached.onCast(SpellCtx.onCast(caster, ctx.calcData));


    }

    public final int getCooldownTicks(SpellCastContext ctx) {
        return (int) ctx.event.data.getNumber(EventData.COOLDOWN_TICKS).number;
    }

    public final int getCastTimeTicks(SpellCastContext ctx) {
        return (int) ctx.event.data.getNumber(EventData.CAST_TICKS).number;
    }

    @Override
    public String GUID() {
        return identifier;
    }

    public void spendResources(SpellCastContext ctx) {
        getManaCostCtx(ctx).Activate();
        getEnergyCostCtx(ctx).Activate();
    }

    public SpendResourceEvent getManaCostCtx(SpellCastContext ctx) {
        float cost = this.getCalculatedManaCost(ctx);
        SpendResourceEvent event = new SpendResourceEvent(ctx.caster, ResourceType.mana, cost);
        event.calculateEffects();
        return event;
    }

    public SpendResourceEvent getEnergyCostCtx(SpellCastContext ctx) {
        float cost = this.getCalculatedEnergyCost(ctx);
        SpendResourceEvent event = new SpendResourceEvent(ctx.caster, ResourceType.energy, cost);
        event.calculateEffects();
        return event;
    }

    public final int getCalculatedManaCost(SpellCastContext ctx) {
        return (int) ctx.event.data.getNumber(EventData.MANA_COST).number;
    }

    public final int getCalculatedEnergyCost(SpellCastContext ctx) {
        return (int) ctx.event.data.getNumber(EventData.ENERGY_COST).number;
    }

    public final List<Component> GetTooltipString(TooltipInfo info) {

        SpellCastContext ctx = new SpellCastContext(info.player, 0, this);

        List<Component> list = new ArrayList<>();

        list.add(locName().withStyle(ChatFormatting.RED, ChatFormatting.BOLD));

        list.add(ExileText.emptyLine().get());

        if (true || Screen.hasShiftDown()) {

            SpellDesc.getTooltip(ctx.caster, this)
                    .forEach(x -> list.add(Component.literal(x)));

        }

        list.add(ExileText.emptyLine().get());


        int mana = getCalculatedManaCost(ctx);
        int ene = getCalculatedEnergyCost(ctx);

        if (mana > 0) {
            list.add(Words.MANA_COST.locName(mana).withStyle(ChatFormatting.BLUE));
        }
        if (ene > 0) {
            list.add(Words.ENE_COST.locName(ene).withStyle(ChatFormatting.GREEN));

        }
        if (config.usesCharges()) {

            list.add(Words.MAX_CHARGES.locName(config.charges).withStyle(ChatFormatting.YELLOW));
            list.add(Words.CHARGE_REGEN.locName(config.charge_regen / 20).withStyle(ChatFormatting.YELLOW));

        } else {
            list.add(Words.COOLDOWN.locName(getCooldownTicks(ctx) / 20).withStyle(ChatFormatting.YELLOW));
        }

        int casttime = getCastTimeTicks(ctx);


        if (casttime == 0) {
            list.add(Words.INSTANT_CAST.locName().withStyle(ChatFormatting.GREEN));
        } else {
            list.add(Words.CAST_TIME.locName(casttime / 20).withStyle(ChatFormatting.GREEN));
        }

        list.add(ExileText.emptyLine().get());

        list.add(getConfig().castingWeapon.predicate.text);

        list.add(ExileText.emptyLine().get());

        if (this.config.times_to_cast > 1) {
            list.add(ExileText.emptyLine().get());

            list.add(Words.CASTED_TIMES_CHANNEL.locName(config.times_to_cast).withStyle(ChatFormatting.RED));
        }

        boolean showeffect = Screen.hasShiftDown();


        List<ExileEffect> effect = new ArrayList<>();
        List<String> ticks = new ArrayList<>();
        //In CTE2 the duration of some effects is not a integer.
        DecimalFormat decimalFormat = new DecimalFormat("0.0");

        if (ExileDB.ExileEffects().isRegistered(effect_tip)) {
            effect.add(ExileDB.ExileEffects().get(effect_tip));
        }

        try {
            this.getAttached()
                    .getAllComponents()
                    .forEach(x -> {
                        x.acts.forEach(a -> {
                            if (a.has(MapField.EXILE_POTION_ID)) {
                                effect.add(a.getExileEffect());
                                //first format the double, then if the number have ".0", simply remove it
                                ticks.add(StringUtils.remove(decimalFormat.format(a.getOrDefault(MapField.POTION_DURATION, 0D) / 20), ".0"));
                            }
                        });
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        MutableComponent showEffectTip = Component.literal("");
        try {
            if (showeffect) {
                //int lvl = this.getLevelOf(ctx.caster);
                //int perc = (int) (((float) lvl / (float) getMaxLevel()) * 100f);
                AtomicInteger i = new AtomicInteger();
                effect.forEach(x -> {
                    list.add(x.locName().withStyle(ChatFormatting.BLUE));
                    List<ExactStatData> stats = x.getExactStats(ctx.caster, this, 1, 1);
                    for (ExactStatData stat : stats) {
                        list.addAll(stat.GetTooltipString(info));
                    }
                    list.add(Words.LASTS_SEC.locName(ticks.get(i.get())));
                    i.getAndIncrement();
                    list.add(ExileText.emptyLine().get());
                });

            } else {
                if (!effect.isEmpty()) {
                    showEffectTip = Words.SHIFT_TO_SHOW_EFFECT.locName().withStyle(ChatFormatting.BLUE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (!this.statsForSkillGem.isEmpty()) {
            list.add(Words.SPELL_STATS.locName());
            for (ExactStatData stat : getStats(info.player)) {
                list.addAll(stat.GetTooltipString(info));
            }
        }

        MutableComponent taglist = TooltipUtils.getMutableTags(this.config.tags.getTags(SpellTag.SERIALIZER).stream().map(x -> x.locName()).iterator(), Gui.TAG_SEPARATOR.locName());
        MutableComponent tagtext = Words.TAGS.locName().append(taglist);

        list.add(tagtext);

        if(!showEffectTip.getString().isBlank()){
            list.add(showEffectTip);
        }

        if (info.hasShiftDown && this.config.tags.contains(SpellTags.has_pet_ability)) {

            list.clear(); // tooltip too long otherwise

            list.add(Words.PET_BASIC.locName());

            Spell spell = config.getSummonBasicSpell();

            list.addAll(spell.GetTooltipString(info));

        }
        if (!this.show_other_spell_tooltip.isEmpty()) {
            if (info.hasAltDown) {
                list.clear(); // tooltip too long otherwise
                Spell other = ExileDB.Spells().get(show_other_spell_tooltip);
                list.addAll(other.GetTooltipString(info));
            } else {
                list.add(Chats.ALT_TO_SHOW_OTHER_SPELL.locName().withStyle(ChatFormatting.BLUE));
            }
        }


        TooltipUtils.removeDoubleBlankLines(list);

        return list;

    }

    public int getLevelOf(LivingEntity en) {
        if (!lvl_based_on_spell.isEmpty()) {
            var other = ExileDB.Spells().get(lvl_based_on_spell);
            if (!other.lvl_based_on_spell.equals(this.lvl_based_on_spell)) {// just to make sure it doesnt loop forever
                return other.getLevelOf(en);
            }
        }

        int lvl = 0;

        if (en instanceof Player p) {
            Optional<SpellCastingData.InsertedSpell> opt = Load.player(p).spellCastingData.getAllHotbarSpells().stream().filter(x -> x.id.equals(GUID())).findAny();
            if (opt.isPresent()) {
                lvl = opt.get().rank;
            }
        }
        if (lvl < default_lvl) {
            lvl = default_lvl;
        }


        return lvl;
    }


    @Override
    public Class<Spell> getClassForSerialization() {
        return Spell.class;
    }

    @Override
    public ExileRegistryType getExileRegistryType() {
        return ExileRegistryTypes.SPELL;
    }

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Spells;
    }

    @Override
    public int Weight() {
        return weight;
    }

    @Override
    public String locNameLangFileGUID() {
        return SlashRef.MODID + ".spell." + GUID();
    }

    public String loc_name;

    @Override
    public String locNameForLangFile() {
        return loc_name;
    }

    @Override
    public AutoLocGroup locDescGroup() {
        return AutoLocGroup.Spells;
    }

    @Override
    public String locDescLangFileGUID() {
        return "spell.desc." + GUID();
    }

    @Override
    public String locDescForLangFile() {
        return locDesc;
    }

    @Override
    public int getCurrentLevel(LivingEntity en) {
        return getLevelOf(en);
    }

    @Override
    public int getMaxLevel() {
        return max_lvl;
    }

    @Override
    public int getMaxLevelWithBonuses() {
        return getMaxLevel() + GameBalanceConfig.get().MAX_BONUS_SPELL_LEVELS;
    }

    @Override
    public int getRequiredLevel() {
        return min_lvl;
    }

    @Override
    public PlayStyle getStyle() {
        return this.config.getStyle();
    }
}
