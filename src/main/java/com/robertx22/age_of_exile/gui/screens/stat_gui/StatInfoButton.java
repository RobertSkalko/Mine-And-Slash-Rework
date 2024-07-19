package com.robertx22.age_of_exile.gui.screens.stat_gui;

import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.database.data.stats.IUsableStat;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.datapacks.test.DataPackStatEffect;
import com.robertx22.age_of_exile.database.data.stats.datapacks.test.DatapackStat;
import com.robertx22.age_of_exile.mmorpg.MMORPG;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.uncommon.utilityclasses.ClientOnly;
import com.robertx22.age_of_exile.uncommon.utilityclasses.NumberUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipUtils;
import com.robertx22.library_of_exile.utils.GuiUtils;
import com.robertx22.library_of_exile.utils.RenderUtils;
import com.robertx22.library_of_exile.utils.TextUTIL;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StatInfoButton extends ImageButton implements IStatInfoButton {

    public static int xSize = 20;
    public static int ySize = 20;


    StatData stat;

    StatInfoType type;

    public StatInfoButton(StatInfoType type, StatData stat, int xPos, int yPos) {
        super(xPos, yPos, xSize, ySize, 0, 0, 0, SlashRef.guiId("stat_gui/info_button"), xSize, ySize, (button) -> {

        });
        this.type = type;
        this.stat = stat;
    }

    @Override
    public void render(GuiGraphics gui, int x, int y, float ticks) {
        super.render(gui, x, y, ticks);


        if (stat == null || stat.GetStat() == null) {
            return;
        }

        if (this.isHoveredOrFocused()) {
            List<MutableComponent> tooltip = type.getTooltip(stat);
            this.setTooltip(Tooltip.create(TextUTIL.mergeList(tooltip)));
        }

        int iconX = 5;
        int iconY = 5;

        int numX = 10;
        int numY = 28;

        if (type.hasIcon) {
            RenderUtils.render16Icon(gui, this.type.getIcon(), getX() + iconX - 3, getY() + iconY - 3);
        }
        var text = type.getRenderText(stat, Load.Unit(ClientOnly.getPlayer()));
      
        if (text != null) {
            GuiUtils.renderScaledText(gui, getX() + numX, getY() + numY, 0.8F, text.getString(), ChatFormatting.YELLOW);
        }
    }

    public enum StatInfoType {

        CURRENT_VALUE("current_value", true) {
            @Override
            public MutableComponent getRenderText(StatData data, EntityData unit) {
                String p = data.GetStat().IsPercent() ? "%" : "";
                return Component.literal(MMORPG.DECIMAL_FORMAT.format(data.getValue()) + p);
            }

            @Override
            public boolean shouldShow(StatData data) {
                return true;
            }

            @Override
            public List<MutableComponent> getTooltip(StatData data) {
                return Arrays.asList(Words.CurrentValueInfo.locName());
            }
        },
        DMG_MULTI("dmg_multi", true) {
            @Override
            public MutableComponent getRenderText(StatData data, EntityData unit) {
                String p = "x" + MMORPG.DECIMAL_FORMAT.format(data.getMoreStatTypeMulti());
                return Component.literal(p);
            }

            @Override
            public boolean shouldShow(StatData data) {
                return data.getMoreStatTypeMulti() != 1 && data.GetStat().getMultiUseType() == Stat.MultiUseType.MULTIPLICATIVE_DAMAGE;
            }

            @Override
            public List<MutableComponent> getTooltip(StatData data) {
                return Arrays.asList(Words.DmgMultiInfo.locName());
            }
        },
        USABLE_VALUE("usable_value", true) {
            @Override
            public MutableComponent getRenderText(StatData data, EntityData unit) {
                return Component.literal(data.GetStat() instanceof IUsableStat u ? NumberUtils.singleDigitFloat(u.getUsableValue(unit.getUnit(), (int) data.getValue(), unit.getLevel()) * 100F) + "%" : "");
            }

            @Override
            public boolean shouldShow(StatData data) {
                return data.GetStat() instanceof IUsableStat;
            }

            @Override
            public List<MutableComponent> getTooltip(StatData data) {
                return TooltipUtils.splitLongText(Words.UsableValueInfo.locName());
            }
        },
        MIN_VAL("min", true) {
            @Override
            public boolean shouldShow(StatData data) {
                return true;
            }

            @Override
            public MutableComponent getRenderText(StatData data, EntityData unit) {
                return Component.literal(data.GetStat().getMinCapTooltipText() + (data.GetStat().IsPercent() ? "%" : ""));
            }

            @Override
            public List<MutableComponent> getTooltip(StatData data) {
                return Arrays.asList(Words.MincapInfo.locName());
            }
        },
        SOFTCAP("softcap", true) {
            @Override
            public List<MutableComponent> getTooltip(StatData data) {
                return Arrays.asList(Words.SoftcapInfo.locName());
            }

            @Override
            public MutableComponent getRenderText(StatData data, EntityData unit) {
                return Component.literal(data.GetStat().getDefaultSoftCap() + "" + (data.GetStat().IsPercent() ? "%" : ""));
            }

            @Override
            public boolean shouldShow(StatData data) {
                return data.GetStat().hasSoftCap();
            }
        },
        HARD_CAP("hardcap", true) {
            @Override
            public boolean shouldShow(StatData data) {
                return true;
            }

            @Override
            public MutableComponent getRenderText(StatData data, EntityData unit) {
                return Component.literal(data.GetStat().getHardCapTooltipText() + (data.GetStat().IsPercent() ? "%" : ""));
            }

            @Override
            public List<MutableComponent> getTooltip(StatData data) {
                return Arrays.asList(Words.HardcapInfo.locName());
            }
        },


        INFO("info", true) {
            @Override
            public boolean shouldShow(StatData data) {
                return true;
            }

            @Override
            public MutableComponent getRenderText(StatData data, EntityData unit) {
                return null;
            }

            @Override
            public List<MutableComponent> getTooltip(StatData data) {

                List<MutableComponent> t = new ArrayList<>();

                Stat stat = data.GetStat();

                t.add(Component.literal("Id: ").append(stat.GUID()));


                // todo ideally all stats should be reworked into this one or similar

                if (stat instanceof DatapackStat d) {
                    for (DataPackStatEffect ef : d.effect) {
                        t.addAll(ef.getTooltip());
                    }
                }

                return t;
            }
        };

        public String id;
        public boolean hasIcon = false;


        StatInfoType(String id, boolean hasIcon) {
            this.id = id;
            this.hasIcon = hasIcon;
        }

        public abstract boolean shouldShow(StatData data);

        public abstract MutableComponent getRenderText(StatData data, EntityData unit);

        public abstract List<MutableComponent> getTooltip(StatData data);

        public ResourceLocation getIcon() {
            return SlashRef.guiId("stat_gui/info_button_icons/" + id);
        }
    }

}
