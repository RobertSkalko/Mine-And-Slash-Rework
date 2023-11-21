package com.robertx22.age_of_exile.gui.screens.character_screen;

import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.datapacks.stats.CoreStat;
import com.robertx22.age_of_exile.database.data.stats.effects.defense.MaxElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.AllAttributes;
import com.robertx22.age_of_exile.database.data.stats.types.defense.Armor;
import com.robertx22.age_of_exile.database.data.stats.types.defense.DodgeRating;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalPenetration;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.offense.WeaponDamage;
import com.robertx22.age_of_exile.database.data.stats.types.resources.energy.Energy;
import com.robertx22.age_of_exile.database.data.stats.types.resources.energy.EnergyRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.HealthRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.magic_shield.MagicShield;
import com.robertx22.age_of_exile.database.data.stats.types.resources.magic_shield.MagicShieldRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.Mana;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.ManaRegen;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.gui.bases.BaseScreen;
import com.robertx22.age_of_exile.gui.bases.INamedScreen;
import com.robertx22.age_of_exile.gui.buttons.CharacterStatsButtons;
import com.robertx22.age_of_exile.gui.buttons.FavorButton;
import com.robertx22.age_of_exile.gui.buttons.ProfessionLevelsButton;
import com.robertx22.age_of_exile.gui.inv_gui.GuiInventoryGrids;
import com.robertx22.age_of_exile.gui.screens.OpenInvGuiScreen;
import com.robertx22.age_of_exile.gui.screens.OpenJewelsScreen;
import com.robertx22.age_of_exile.gui.screens.OpenSkillGems;
import com.robertx22.age_of_exile.gui.screens.skill_tree.TalentsScreen;
import com.robertx22.age_of_exile.gui.screens.spell.AscendancyClassScreen;
import com.robertx22.age_of_exile.gui.wiki.BestiaryScreen;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.vanilla_mc.packets.AllocateStatPacket;
import com.robertx22.library_of_exile.main.Packets;
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


public class MainHubScreen extends BaseScreen implements INamedScreen {
    private static final ResourceLocation LEFT = new ResourceLocation(SlashRef.MODID, "textures/gui/main_hub/buttons_backwards.png");
    static ResourceLocation RIGHT = new ResourceLocation(SlashRef.MODID, "textures/gui/main_hub/buttons.png");

    static int sizeX = 256;
    static int sizeY = 178;

    Minecraft mc = Minecraft.getInstance();


    public enum StatType {
        RESOURCE("resource"),
        DAMAGE("damage"),
        DEFENSE("defense"),
        MISC("misc");

        String id;

        StatType(String id) {
            this.id = id;
        }

        public ResourceLocation getIcon() {

            return new ResourceLocation(SlashRef.MODID, "textures/gui/stat_groups/" + id + ".png");
        }
    }


    // todo implement this elsewhere
    public static HashMap<StatType, List<List<Stat>>> STAT_MAP = new HashMap<>();

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

    static <T extends Stat> void addRemaining(StatType type) {

        List<Stat> list = new ArrayList<>();

        for (List<List<Stat>> l : STAT_MAP.values()) {
            for (List<Stat> s : l) {
                for (Stat st : s) {
                    list.add(st);
                }
            }
        }

        var v = Load.Unit(Minecraft.getInstance().player).getUnit().getStats().stats.values().stream()
                .filter(x -> x.isNotZero() && !x.GetStat().is_long && list.stream().noneMatch(e -> e.GUID().equals(x.getId())))
                .map(t -> t.GetStat()).collect(Collectors.toList());

        if (!STAT_MAP.containsKey(type)) {
            STAT_MAP.put(type, new ArrayList<>());
        }
        STAT_MAP.get(StatType.MISC).add(v);

    }

    static {

        addTo(StatType.RESOURCE, Arrays.asList(Health.getInstance(), MagicShield.getInstance(), Mana.getInstance(), Energy.getInstance(), HealthRegen.getInstance(), MagicShieldRegen.getInstance(), ManaRegen.getInstance(), EnergyRegen.getInstance()));

        addTo(StatType.DEFENSE, Arrays.asList(Armor.getInstance(), DodgeRating.getInstance()));
        addTo(StatType.DEFENSE, new ElementalResist(Elements.Elemental).generateAllSingleVariations());
        addTo(StatType.DEFENSE, new MaxElementalResist(Elements.Elemental).generateAllSingleVariations());

        addTo(StatType.DAMAGE, Arrays.asList(WeaponDamage.getInstance()));
        addTo(StatType.DAMAGE, Arrays.asList(Stats.CRIT_CHANCE.get(), Stats.CRIT_DAMAGE.get()));
        addTo(StatType.DAMAGE, Stats.ELEMENTAL_SPELL_DAMAGE.getAll());
        addTo(StatType.DAMAGE, Stats.ELEMENTAL_DAMAGE.getAll());
        addTo(StatType.DAMAGE, new ElementalPenetration(Elements.Elemental).generateAllSingleVariations());

        addRemaining(StatType.MISC);

    }

    public MainHubScreen() {
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


        xpos = guiLeft + 78;
        ypos = guiTop + 105;

        int YSEP = 20;

        // TODO MAKE STATIC IDS
        xpos = guiLeft + 35;
        ypos = guiTop + 25;

        publicAddButton(new AllocateStatButton(AllAttributes.STR_ID, xpos, ypos));
        ypos += YSEP;
        publicAddButton(new AllocateStatButton(AllAttributes.INT_ID, xpos, ypos));
        ypos += YSEP;
        publicAddButton(new AllocateStatButton(AllAttributes.DEX_ID, xpos, ypos));


        xpos = guiLeft + 12;
        ypos = guiTop + 90;


        // hub buttons

        List<INamedScreen> rightButtons = new ArrayList<>();
        rightButtons.add(new AscendancyClassScreen());
        rightButtons.add(new OpenSkillGems());
        rightButtons.add(new TalentsScreen());
        rightButtons.add(new OpenJewelsScreen());

        List<INamedScreen> leftButtons = new ArrayList<>();

        leftButtons.add(new BestiaryScreen());


        leftButtons.add(new OpenInvGuiScreen(Words.Salvaging, "salvage", GuiInventoryGrids.ofSalvageConfig()));


        // screens.add(new SpellScreen());


        publicAddButton(new FavorButton(guiLeft + sizeX / 2 - FavorButton.FAVOR_BUTTON_SIZE_X / 2, guiTop - FavorButton.FAVOR_BUTTON_SIZE_Y));
        publicAddButton(new ProfessionLevelsButton(guiLeft + sizeX / 2 - ProfessionLevelsButton.SX / 2, guiTop + 150));


        int x = guiLeft + sizeX - 1;
        int y = guiTop + 20;

        for (INamedScreen screen : rightButtons) {
            publicAddButton(new MainHubButton(true, RIGHT, screen, x, y));
            y += MainHubButton.ySize + 0;
        }


        x = guiLeft - MainHubButton.xSize;
        y = guiTop + 20;
        for (INamedScreen screen : leftButtons) {
            this.publicAddButton(new MainHubButton(false, LEFT, screen, x, y));
            y += MainHubButton.ySize + 0;
        }

        int xp = guiLeft + 77;
        int yp = guiTop + 120;

        for (StatType type : StatType.values()) {
            publicAddButton(new CharacterStatsButtons(type, xp, yp));
            xp += 30;
        }


        publicAddButton(new PlayerGearButton(mc.player, this, this.guiLeft + MainHubScreen.sizeX / 2 - PlayerGearButton.xSize / 2, this.guiTop + 10));

    }

    private static final ResourceLocation BACKGROUND = new ResourceLocation(SlashRef.MODID, "textures/gui/stats.png");
    private static final ResourceLocation WIDE_BACKGROUND = new ResourceLocation(SlashRef.MODID, "textures/gui/full_stats_panel.png");

    @Override
    public void render(GuiGraphics gui, int x, int y, float ticks) {

        ResourceLocation loc;

        loc = BACKGROUND;


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


        int p = Load.player(mc.player).statPoints
                .getFreePoints(mc.player);
        if (p > 0) {
            String points = "Points: " + p;
            gui.drawString(mc.font, points, guiLeft + sizeX / 2 - mc.font.width(points) / 2, guiTop + sizeY + 15, ChatFormatting.GREEN.getColor());
        }

    }


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


}


