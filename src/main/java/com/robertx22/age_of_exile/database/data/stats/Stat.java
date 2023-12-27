package com.robertx22.age_of_exile.database.data.stats;

import com.robertx22.age_of_exile.database.data.StatMod;
import com.robertx22.age_of_exile.database.data.stats.datapacks.base.BaseDatapackStat;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseDamageIncreaseEffect;
import com.robertx22.age_of_exile.database.data.stats.name_regex.StatNameRegex;
import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.mmorpg.UNICODE;
import com.robertx22.age_of_exile.saveclasses.item_classes.tooltips.TooltipStatWithContext;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.modify.IStatCtxModifier;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocDesc;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffect;
import com.robertx22.age_of_exile.uncommon.localization.Formatter;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.uncommon.utilityclasses.ClientTextureUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipUtils;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.IGUID;
import com.robertx22.library_of_exile.registry.IWeighted;
import com.robertx22.library_of_exile.registry.JsonExileRegistry;
import com.robertx22.library_of_exile.wrappers.ExileText;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;

public abstract class Stat implements IGUID, IAutoLocName, IWeighted, IAutoLocDesc, JsonExileRegistry<BaseDatapackStat> {

    public static String VAL1 = "[VAL1]";
    static ChatFormatting FORMAT = ChatFormatting.GRAY;
    static ChatFormatting NUMBER = ChatFormatting.GREEN;

    public static String format(String str) {

        str = FORMAT + str;

        str = str.replace(VAL1, NUMBER + VAL1 + FORMAT);

        return str;
    }

    public Stat() {

    }


    public boolean show = true;

    // can't serialize
    public transient IStatEffect statEffect = null;
    public transient IStatCtxModifier statContextModifier;

    public float min = -1000;
    public float max = Integer.MAX_VALUE;
    public float base = 0;
    public boolean is_perc = false;
    public StatScaling scaling = StatScaling.NONE;
    public boolean is_long = false;
    public String icon = UNICODE.STAR;
    public int order = 100;
    public String format = ChatFormatting.AQUA.getName();
    public StatGroup group = StatGroup.Misc;
    private MultiUseType multiUseType = MultiUseType.MULTIPLY_STAT;


    public void setUsesMoreMultiplier() {
        this.multiUseType = MultiUseType.MULTIPLICATIVE_DAMAGE;
    }

    public MultiUseType getMultiUseType() {
        if (this.statEffect instanceof BaseDamageIncreaseEffect) {
            this.multiUseType = MultiUseType.MULTIPLICATIVE_DAMAGE;
            return MultiUseType.MULTIPLICATIVE_DAMAGE;
        }
        return multiUseType;
    }

    public enum MultiUseType {
        // todo will this still be confusing
        MULTIPLY_STAT(Words.MULTIPLY_STAT_INCREASED, Words.MULTIPLY_STAT_REDUCED),
        MULTIPLICATIVE_DAMAGE(Words.MULTIPLICATIVE_DAMAGE_MORE, Words.MULTIPLICATIVE_DAMAGE_LESS);

        public Words prefixWord;
        public Words prefixLessWord;

        MultiUseType(Words prefixWord, Words prefixLessWord) {
            this.prefixWord = prefixWord;
            this.prefixLessWord = prefixLessWord;
        }

    }


    public ChatFormatting getFormat() {
        return ChatFormatting.getByName(format);
    }

    public String getIconNameFormat() {
        return getIconNameFormat(locNameForLangFile());
    }

    public String getFormatAndIcon() {
        return getFormat() + icon;
    }

    public String getIconNameFormat(String str) {
        return this.getFormat() + this.icon + " " + str + ChatFormatting.GRAY;
    }

    public MutableComponent getMutableIconNameFormat() {
        return Formatter.ICON_AND_DAMAGE_IN_SPELL_DAMAGE_PROPORTION.locName(this.getFormat() + this.icon, this.locName());
    }

    @Override
    public boolean isFromDatapack() {
        return false;
    }

    @Override
    public boolean isRegistryEntryValid() {
        return true;
    }

    public StatNameRegex getStatNameRegex() {
        return StatNameRegex.BASIC;
    }

    public final StatScaling getScaling() {
        return scaling;
    }

    public final float scale(ModType mod, float stat, float lvl) {
        if (mod.isFlat()) {
            return getScaling().scale(stat, lvl);
        }
        return stat;
    }

    public List<MutableComponent> getCutDescTooltip() {
        List<MutableComponent> list = new ArrayList<>();

        List<Component> cut = TooltipUtils.cutIfTooLong(locDesc());

        for (int i = 0; i < cut.size(); i++) {

            MutableComponent comp = ExileText.emptyLine().get();
            if (i == 0) {
                comp.append(" [");
            }
            comp.append(cut.get(i));

            if (i == cut.size() - 1) {
                comp.append("]");
            }

            list.add(comp);

            comp.withStyle(ChatFormatting.BLUE);

        }
        return list;
    }

    // this is used for alltraitmods, check if confused
    @Override
    public int Weight() {
        return 1000;
    }

    @Override
    public ExileRegistryType getExileRegistryType() {
        return ExileRegistryTypes.STAT;
    }

    public static ResourceLocation DEFAULT_ICON = new ResourceLocation(SlashRef.MODID, "textures/gui/stat_icons/default.png");

    public ResourceLocation getIconLocation() {
        return new ResourceLocation(SlashRef.MODID, "textures/gui/stat_icons/" + group.id + "/" + GUID() + ".png");
    }

    transient ResourceLocation cachedIcon = null;

    public ResourceLocation getIconForRendering() {
        if (cachedIcon == null) {
            ResourceLocation id = getIconLocation();
            if (ClientTextureUtils.textureExists(id)) {
                cachedIcon = id;
            } else {
                cachedIcon = Stat.DEFAULT_ICON;
            }
        }
        return cachedIcon;
    }

    @Override
    public AutoLocGroup locDescGroup() {
        return AutoLocGroup.Stats;
    }

    @Override
    public String locNameLangFileGUID() {
        return SlashRef.MODID + ".stat." + GUID();
    }

    @Override
    public String locDescLangFileGUID() {
        return SlashRef.MODID + ".stat_desc." + GUID();
    }

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Stats;
    }

    public boolean IsPercent() {
        return is_perc;
    }

    public abstract Elements getElement();

    @OnlyIn(Dist.CLIENT)
    public List<MutableComponent> getTooltipList(TooltipStatWithContext info) {
        return info.statinfo.tooltipInfo.statTooltipType.impl.getTooltipList(null, info);
    }

    public StatMod mod(float v1, float v2) {
        return new StatMod(v1, v2, this);
    }

    public enum StatGroup {
        MAIN("main"),
        WEAPON("weapon"),
        CORE("core"),
        ELEMENTAL("elemental"),
        RESTORATION("restoration"),
        Misc("misc");

        public String id;

        StatGroup(String id) {
            this.id = id;
        }
    }

}
