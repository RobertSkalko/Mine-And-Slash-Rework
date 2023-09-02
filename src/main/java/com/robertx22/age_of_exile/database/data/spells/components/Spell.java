package com.robertx22.age_of_exile.database.data.spells.components;

import com.robertx22.age_of_exile.aoe_data.database.spells.SpellDesc;
import com.robertx22.age_of_exile.database.data.StatMod;
import com.robertx22.age_of_exile.database.data.exile_effects.ExileEffect;
import com.robertx22.age_of_exile.database.data.spells.SpellTag;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.SpellCastContext;
import com.robertx22.age_of_exile.database.data.value_calc.MaxLevelProvider;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.saveclasses.skill_gem.ISkillGem;
import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import com.robertx22.age_of_exile.uncommon.effectdatas.SpendResourceEvent;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.EventData;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import com.robertx22.age_of_exile.uncommon.enumclasses.WeaponTypes;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocDesc;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.age_of_exile.uncommon.utilityclasses.MapManager;
import com.robertx22.age_of_exile.uncommon.utilityclasses.StringUTIL;
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class Spell implements ISkillGem, IGUID, IAutoGson<Spell>, JsonExileRegistry<Spell>, IAutoLocName, IAutoLocDesc, MaxLevelProvider {
    public static Spell SERIALIZER = new Spell();

    public static String DEFAULT_EN_NAME = "default_entity_name";
    public static String CASTER_NAME = "caster";

    public int weight = 1000;
    public String identifier = "";
    public AttachedSpell attached = new AttachedSpell();
    public SpellConfiguration config = new SpellConfiguration();

    public boolean manual_tip = false;
    public List<String> disabled_dims = new ArrayList<>();
    public String effect_tip = "";

    public transient String locDesc = "";
    public transient List<StatMod> statsForSkillGem = new ArrayList<>();

    public boolean isAllowedInDimension(Level world) {
        if (true) { // todo not sure if neeeded
            return true;
        }
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

            if (ctx.isLastCastTick) {
                this.cast(ctx);
            } else {
                if (ctx.ticksInUse > 0 && ctx.ticksInUse % castEveryXTicks == 0) {
                    this.cast(ctx);
                }
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
    }

    public SpendResourceEvent getManaCostCtx(SpellCastContext ctx) {
        float cost = this.getCalculatedManaCost(ctx);
        SpendResourceEvent event = new SpendResourceEvent(ctx.caster, ResourceType.mana, cost);
        event.calculateEffects();
        return event;
    }

    public final int getCalculatedManaCost(SpellCastContext ctx) {
        return (int) ctx.event.data.getNumber(EventData.MANA_COST).number;
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


        list.add(Component.literal(ChatFormatting.BLUE + "Mana Cost: " + getCalculatedManaCost(ctx)));
        if (config.usesCharges()) {
            list.add(Component.literal(ChatFormatting.YELLOW + "Max Charges: " + config.charges));
            list.add(Component.literal(ChatFormatting.YELLOW + "Charge Regen: " + config.charge_regen / 20 + "s"));

        } else {
            list.add(Component.literal(ChatFormatting.YELLOW + "Cooldown: " + (getCooldownTicks(ctx) / 20) + "s"));
        }

        int casttime = getCastTimeTicks(ctx);

        if (casttime == 0) {
            list.add(Component.literal(ChatFormatting.GREEN + "Cast time: " + "Instant"));

        } else {
            list.add(Component.literal(ChatFormatting.GREEN + "Cast time: " + casttime / 20 + "s"));

        }

        list.add(ExileText.emptyLine().get());

        list.add(getConfig().castingWeapon.predicate.text);

        list.add(ExileText.emptyLine().get());

        if (this.config.times_to_cast > 1) {
            list.add(ExileText.emptyLine().get());
            list.add(Component.literal("Casted " + config.times_to_cast + " times during channel.").withStyle(ChatFormatting.RED));

        }

        if (true || Screen.hasShiftDown()) {

            Set<ExileEffect> effect = new HashSet<>();

            if (ExileDB.ExileEffects()
                    .isRegistered(effect_tip)) {
                effect.add(ExileDB.ExileEffects()
                        .get(effect_tip));
            }

            try {
                this.getAttached()
                        .getAllComponents()
                        .forEach(x -> {
                            x.acts.forEach(a -> {
                                if (a.has(MapField.EXILE_POTION_ID)) {
                                    effect.add(a.getExileEffect());
                                }
                            });
                        });
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                effect.forEach(x -> list.addAll(x.GetTooltipString(info, ctx.calcData)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        

        int currentlvl = getLevelOf(info.player);

        String taglist = StringUTIL.join(this.config.tags.stream().map(x -> x.locname).iterator(), ", ");
        MutableComponent tagtext = Component.literal("Tags: ").append(taglist);

        list.add(tagtext);

        list.add(ExileText.emptyLine().get());

        list.add(Component.literal("Quality: " + currentlvl + "%").withStyle(ChatFormatting.GOLD));

        TooltipUtils.removeDoubleBlankLines(list);

        return list;

    }

    public int getLevelOf(LivingEntity en) {
        if (en instanceof Player p) {
            var gem = Load.player(p).getSkillGemInventory().getSpellGem(this);
            if (gem != null && gem.getSkillData() != null) {
                return (int) (gem.getSkillData().perc); // todo make sure this works
            }
        }
        return 1;
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

    public transient String locName;

    @Override
    public String locNameForLangFile() {
        return locName;
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
        return 100;
    }

    @Override
    public int getMaxLevelWithBonuses() {
        return getMaxLevel();
    }

    @Override
    public PlayStyle getStyle() {
        return this.config.getStyle();
    }
}
