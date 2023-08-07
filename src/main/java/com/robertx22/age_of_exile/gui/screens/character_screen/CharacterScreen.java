package com.robertx22.age_of_exile.gui.screens.character_screen;

import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.database.data.stats.IUsableStat;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.datapacks.stats.CoreStat;
import com.robertx22.age_of_exile.database.data.stats.types.UnknownStat;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.AllAttributes;
import com.robertx22.age_of_exile.database.data.stats.types.defense.Armor;
import com.robertx22.age_of_exile.database.data.stats.types.defense.DodgeRating;
import com.robertx22.age_of_exile.database.data.stats.types.generated.BonusAttackDamage;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalPenetration;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.loot.TreasureQuality;
import com.robertx22.age_of_exile.database.data.stats.types.loot.TreasureQuantity;
import com.robertx22.age_of_exile.database.data.stats.types.misc.BonusExp;
import com.robertx22.age_of_exile.database.data.stats.types.offense.SkillDamage;
import com.robertx22.age_of_exile.database.data.stats.types.resources.energy.Energy;
import com.robertx22.age_of_exile.database.data.stats.types.resources.energy.EnergyRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.HealthRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.Mana;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.ManaRegen;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.gui.bases.BaseScreen;
import com.robertx22.age_of_exile.gui.bases.INamedScreen;
import com.robertx22.age_of_exile.gui.screens.skill_tree.TalentsScreen;
import com.robertx22.age_of_exile.gui.screens.spell.SpellScreen;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.uncommon.utilityclasses.NumberUtils;
import com.robertx22.age_of_exile.vanilla_mc.packets.AllocateStatPacket;
import com.robertx22.library_of_exile.main.Packets;
import com.robertx22.library_of_exile.utils.GuiUtils;
import com.robertx22.library_of_exile.utils.RenderUtils;
import com.robertx22.library_of_exile.utils.TextUTIL;
import com.robertx22.library_of_exile.wrappers.ExileText;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


public class CharacterScreen extends BaseScreen implements INamedScreen {

    static int sizeX = 256;
    static int sizeY = 178;

    Minecraft mc = Minecraft.getInstance();

    public enum StatType {
        HUB("hub"),
        MAIN("main"),
        ELEMENTAL("elemental"),
        RESISTS("resists"),
        MISC("misc");

        String id;

        StatType(String id) {
            this.id = id;
        }

        ResourceLocation getIcon() {
            return new ResourceLocation(SlashRef.MODID, "textures/gui/stat_groups/" + id + ".png");
        }
    }

    boolean isMainScreen() {
        return this.statToShow == StatType.HUB;
    }

    public StatType statToShow = StatType.HUB;

    static HashMap<StatType, List<List<Stat>>> STAT_MAP = new HashMap<>();

    static <T extends Stat> void addTo(StatType type, List<T> stats) {

        List<Stat> list = stats.stream()
                .map(x -> (Stat) x)
                .collect(Collectors.toList());

        if (!STAT_MAP.containsKey(type)) {
            STAT_MAP.put(type, new ArrayList<>());
        }
        STAT_MAP.get(type)
                .add(list);
    }

    static {

        addTo(StatType.MAIN, Arrays.asList(Health.getInstance(), Mana.getInstance(), Energy.getInstance()));
        addTo(StatType.MAIN, Arrays.asList(HealthRegen.getInstance(), ManaRegen.getInstance(), EnergyRegen.getInstance()));
        addTo(StatType.MAIN, Arrays.asList(Armor.getInstance(), DodgeRating.getInstance()));
        addTo(StatType.MAIN, Arrays.asList(Stats.CRIT_CHANCE.get(), Stats.CRIT_DAMAGE.get(), Stats.CRIT_CHANCE.get(), Stats.CRIT_DAMAGE.get()));
        addTo(StatType.MAIN, Arrays.asList(Stats.ACCURACY.get(), SkillDamage.getInstance(), Stats.CAST_SPEED.get(), Stats.COOLDOWN_REDUCTION.get()));

        addTo(StatType.ELEMENTAL, new BonusAttackDamage(Elements.Elemental).generateAllPossibleStatVariations());
        addTo(StatType.ELEMENTAL, Stats.ELEMENTAL_SPELL_DAMAGE.getAll());
        addTo(StatType.ELEMENTAL, Stats.ELEMENTAL_DAMAGE.getAll());

        addTo(StatType.RESISTS, new ElementalResist(Elements.Elemental).generateAllPossibleStatVariations());
        addTo(StatType.RESISTS, new ElementalPenetration(Elements.Elemental).generateAllPossibleStatVariations());

        addTo(StatType.MISC, Arrays.asList(BonusExp.getInstance(), TreasureQuality.getInstance(), TreasureQuantity.getInstance()));

    }

    public CharacterScreen() {
        super(sizeX, sizeY);
    }

    @Override
    public ResourceLocation iconLocation() {
        return new ResourceLocation(SlashRef.MODID, "textures/gui/main_hub/icons/stat_overview.png");
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public boolean mouseReleased(double x, double y, int ticks) {
        return super.mouseReleased(x, y, ticks);
    }

    @Override
    public Words screenName() {
        return Words.Character;
    }

    @Override
    public void init() {
        super.init();


        this.clearWidgets();

        //this.children.clear();

        // CORE STATS
        int xpos = guiLeft + 75;
        int ypos = guiTop + 25;

        if (this.isMainScreen()) {


            if (true) {

                xpos = guiLeft + 78;
                ypos = guiTop + 105;

                int YSEP = 20;

                // TODO MAKE STATIC IDS
                xpos = guiLeft + 159;
                ypos = guiTop + 105;

                publicAddButton(new AllocateStatButton(AllAttributes.STR_ID, xpos, ypos));
                ypos += YSEP;
                publicAddButton(new AllocateStatButton(AllAttributes.INT_ID, xpos, ypos));
                ypos += YSEP;
                publicAddButton(new AllocateStatButton(AllAttributes.DEX_ID, xpos, ypos));

            }
        }

        if (isMainScreen()) {
            xpos = guiLeft + 12;
            ypos = guiTop + 90;
        } else {
            xpos = guiLeft + 12;
            ypos = guiTop + 12;
        }

        if (!isMainScreen()) {

            int XSPACING = 240 / STAT_MAP.get(statToShow)
                    .size();
            int YSPACING = 19;

            int ynum = 0;
            for (List<Stat> list : STAT_MAP.get(statToShow)) {
                for (Stat stat : list) {
                    if (stat.show) {
                        publicAddButton(new StatButton(stat, xpos, ypos + (YSPACING * ynum)));

                        ynum++;
                    }
                }
                ynum = 0;
                xpos += XSPACING;
            }
        }

        if (this.isMainScreen()) {


            // hub buttons

            List<INamedScreen> screens = new ArrayList<>();
            screens.add(new SpellScreen());

            screens.add(new TalentsScreen());


            int x = guiLeft + sizeX - 1;
            int y = guiTop + 20;

            for (INamedScreen screen : screens) {
                publicAddButton(new MainHubButton(screen, x, y));
                y += MainHubButton.ySize + 0;
            }
        }

        int i = 0;
        for (StatType group : StatType.values()) {
            this.publicAddButton(new StatPageButton(this, group, guiLeft - StatPageButton.SIZEX, guiTop + 15 + (i * (StatPageButton.SIZEY + 1))));
            i++;
        }

        if (isMainScreen()) {
            publicAddButton(new PlayerGearButton(mc.player, this, this.guiLeft + CharacterScreen.sizeX / 2 - PlayerGearButton.xSize / 2, this.guiTop + 10));
        }
    }

    private static final ResourceLocation BACKGROUND = new ResourceLocation(SlashRef.MODID, "textures/gui/stats.png");
    private static final ResourceLocation WIDE_BACKGROUND = new ResourceLocation(SlashRef.MODID, "textures/gui/full_stats_panel.png");

    @Override
    public void render(GuiGraphics gui, int x, int y, float ticks) {

        ResourceLocation loc;

        if (isMainScreen()) {
            loc = BACKGROUND;
        } else {
            loc = WIDE_BACKGROUND;
        }

        gui.setColor(1.0F, 1.0F, 1.0F, 1.0F);

        gui.blit(loc, mc.getWindow()
                        .getGuiScaledWidth() / 2 - sizeX / 2,
                mc.getWindow()
                        .getGuiScaledHeight() / 2 - sizeY / 2, 0, 0, sizeX, sizeY
        );

        super.render(gui, x, y, ticks);

        children().forEach(b -> {
            //  b.renderToolTip(matrix, x, y);
        });


        int p = Load.playerRPGData(mc.player).statPoints
                .getFreePoints(mc.player);
        if (p > 0) {
            String points = "Points: " + p;
            gui.drawString(mc.font, points, guiLeft + sizeX / 2 - mc.font.width(points) / 2, guiTop + sizeY + 25, ChatFormatting.GREEN.getColor());
        }

    }

    private static final ResourceLocation STAT_PAGE_BUTTON_TEXT = new ResourceLocation(SlashRef.MODID, "textures/gui/main_hub/buttons_backwards.png");

    static int PLUS_BUTTON_SIZE_X = 13;
    static int PLUS_BUTTON_SIZE_Y = 13;

    public static class AllocateStatButton extends ImageButton {
        static int SIZEX = 18;
        static int SIZEY = 18;
        static ResourceLocation BUTTON_TEX = new ResourceLocation(SlashRef.MODID, "textures/gui/plus_button.png");

        Stat stat;

        public AllocateStatButton(String stat, int xPos, int yPos) {
            super(xPos, yPos, SIZEX, SIZEY, 0, 0, SIZEY, BUTTON_TEX, (button) -> {
                Packets.sendToServer(new AllocateStatPacket(ExileDB.Stats()
                        .get(stat)));
            });
            this.stat = ExileDB.Stats()
                    .get(stat);
        }


        public void setTooltipMod() {


            Minecraft mc = Minecraft.getInstance();

            List<Component> tooltip = new ArrayList<>();

            tooltip.add(stat
                    .locName()
                    .withStyle(ChatFormatting.GREEN));

            tooltip.add(ExileText.ofText("").get());

            tooltip.addAll(((CoreStat) stat).getCoreStatTooltip(Load.Unit(mc.player), Load.Unit(mc.player)
                    .getUnit()
                    .getCalculatedStat(stat)));

            setTooltip(Tooltip.create(TextUTIL.mergeList(tooltip)));
        }


        @Override
        public void render(GuiGraphics gui, int x, int y, float f) {

            setTooltipMod();

            super.render(gui, x, y, f);

            Minecraft mc = Minecraft.getInstance();

            String txt = ((int) Load.Unit(mc.player)
                    .getUnit()
                    .getCalculatedStat(stat)
                    .getValue()) + "";

            RenderUtils.render16Icon(gui, stat.getIconForRendering(), this.getX() - 20, this.getY() + 1);

            gui.drawString(mc.font, txt, this.getX() + SIZEX + 4, this.getY() + 4, ChatFormatting.YELLOW.getColor());

        }

    }

    private static final ResourceLocation BUTTON_TEX = new ResourceLocation(SlashRef.MODID, "textures/gui/button.png");
    static int STAT_BUTTON_SIZE_X = 18;
    static int STAT_BUTTON_SIZE_Y = 18;

    public static class StatButton extends ImageButton {

        Stat stat;
        public String presetValue = "";

        public StatButton(Stat stat, int xPos, int yPos) {
            super(xPos, yPos, STAT_BUTTON_SIZE_X, STAT_BUTTON_SIZE_Y, 0, 0, STAT_BUTTON_SIZE_Y, BUTTON_TEX, (button) -> {
            });

            this.stat = stat;
        }


        public void setModTooltip() {
            List<Component> tooltip = new ArrayList<>();

            tooltip.add(stat
                    .locName()
                    .withStyle(ChatFormatting.GREEN));

            tooltip.addAll(stat
                    .getCutDescTooltip());

            setTooltip(Tooltip.create(TextUTIL.mergeList(tooltip)));

        }


        @Override
        public void render(GuiGraphics gui, int x, int y, float f) {


            if (!(stat instanceof UnknownStat)) {

                setModTooltip();

                Minecraft mc = Minecraft.getInstance();
                String str = getStatString(Load.Unit(mc.player)
                        .getUnit()
                        .getCalculatedStat(stat), Load.Unit(mc.player));

                if (!presetValue.isEmpty()) {
                    str = presetValue;
                }

                ResourceLocation res = stat
                        .getIconForRendering();

                RenderUtils.render16Icon(gui, res, this.getX(), this.getY());

                gui.drawString(mc.font, str, this.getX() + STAT_BUTTON_SIZE_X, this.getY() + 2, ChatFormatting.GOLD.getColor());

            }
        }

    }

    public static String getStatString(StatData data, EntityData unitdata) {
        Stat stat = data.GetStat();

        String v1 = NumberUtils.formatForTooltip(data
                .getValue());

        String str = "";

        str = v1;

        if (stat.IsPercent()) {
            str += '%';
        }

        if (stat instanceof IUsableStat && stat instanceof Health == false) {
            IUsableStat usable = (IUsableStat) stat;

            String value = NumberUtils.format(
                    usable.getUsableValue((int) data
                            .getValue(), unitdata.getLevel()) * 100);

            str = "" + value + "%";

        }
        return str;

    }

    static class StatPageButton extends ImageButton {

        public static int SIZEX = 105;
        public static int SIZEY = 28;

        StatType page;

        public StatPageButton(CharacterScreen screen, StatType page, int xPos, int yPos) {
            super(xPos + 1, yPos, SIZEX, SIZEY, 0, 0, SIZEY, STAT_PAGE_BUTTON_TEXT, (button) -> {
                screen.statToShow = page;
                screen.init();
            });
            this.page = page;
        }

        @Override
        public void render(GuiGraphics gui, int mouseX, int mouseY, float delta) {
            Minecraft mc = Minecraft.getInstance();

            super.render(gui, mouseX, mouseY, delta);

            RenderUtils.render16Icon(gui, page.getIcon(), this.getX() + 80, this.getY() + 6);

            if (isHovered()) {
                String txt = page.id;

                gui.drawString(Minecraft.getInstance().font, txt, this.getX() + SIZEX - 30 - mc.font.width(txt), this.getY() + 9, ChatFormatting.YELLOW.getColor());
            }

        }

        /*
        public void renderToolTip(PoseStack matrix, int x, int y) {
            if (isInside(x, y)) {

                List<Component> tooltip = new ArrayList<>();
                GuiUtils.renderTooltip(matrix, tooltip, x, y);

            }
        }

         */

        public boolean isInside(int x, int y) {
            return GuiUtils.isInRect(this.getX(), this.getY(), PLUS_BUTTON_SIZE_X, PLUS_BUTTON_SIZE_Y, x, y);
        }

    }

}


