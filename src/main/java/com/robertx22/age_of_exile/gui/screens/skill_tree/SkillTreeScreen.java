package com.robertx22.age_of_exile.gui.screens.skill_tree;

import com.google.common.collect.Sets;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.math.Axis;
import com.robertx22.age_of_exile.capability.player.PlayerData;
import com.robertx22.age_of_exile.config.forge.ClientConfigs;
import com.robertx22.age_of_exile.database.data.perks.Perk;
import com.robertx22.age_of_exile.database.data.stats.types.UnknownStat;
import com.robertx22.age_of_exile.database.data.talent_tree.TalentTree;
import com.robertx22.age_of_exile.database.data.talent_tree.TalentTree.SchoolType;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.gui.bases.BaseScreen;
import com.robertx22.age_of_exile.gui.bases.IAlertScreen;
import com.robertx22.age_of_exile.gui.bases.INamedScreen;
import com.robertx22.age_of_exile.gui.screens.skill_tree.buttons.PerkButton;
import com.robertx22.age_of_exile.gui.screens.skill_tree.buttons.PerkConnectionRender;
import com.robertx22.age_of_exile.gui.screens.skill_tree.buttons.PerkScreenContext;
import com.robertx22.age_of_exile.mmorpg.MMORPG;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.saveclasses.PointData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.localization.Gui;
import com.robertx22.age_of_exile.uncommon.utilityclasses.ClientOnly;
import com.robertx22.library_of_exile.utils.Watch;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

import java.awt.*;
import java.util.*;

public abstract class SkillTreeScreen extends BaseScreen implements INamedScreen, IAlertScreen {
    static ResourceLocation BIG_PANEL = new ResourceLocation(SlashRef.MODID, "textures/gui/skill_tree/bar.png");

    public SchoolType schoolType;

    //public HashSet<PerkConnectionRender> buttonConnections = new HashSet<>();

    static ResourceLocation CON = SlashRef.id("textures/gui/skill_tree/skill_connection.png");

    private void renderConnection(GuiGraphics graphics, PerkConnectionRender2 connection) {

        graphics.pose().pushPose();
        //Watch watch = new Watch();
        PerkButton button1 = pointPerkButtonMap.get(connection.pair.perk1);
        PerkButton button2 = pointPerkButtonMap.get(connection.pair.perk2);

        PerkScreenContext ctx = new PerkScreenContext(this);

        double xadd = button1.perk.getType().size / 2F; // todo idk if this is the problem or
        double yadd = button1.perk.getType().size / 2F;


        double connectionX = button1.getX() + xadd;
        double connectionY = button1.getY() + yadd;

        connectionX -= ctx.scrollX;
        connectionY -= ctx.scrollY;


        graphics.pose().translate(connectionX + scrollX, connectionY + scrollY, 0);
        float rotation = getAngleBetweenButtons(button1, button2);
        graphics.pose().mulPose(Axis.ZP.rotation(rotation));
        int length = (int) getDistanceBetweenButtons(button1, button2);

        //graphics.pose().scale(1F, 1.5F, 1F); // thicken it a bit

        int off = 0;

        if (connection.connection == Perk.Connection.LINKED) {
            off = 0;
        }
        if (connection.connection == Perk.Connection.POSSIBLE) {
            off = 6;
        }
        if (connection.connection == Perk.Connection.BLOCKED) {
            off = 6 + 5;
        }

        graphics.blit(CON, 0, -3, length, 6, 0, off, length, 6, 50, 16);
        //System.out.println("blit time: " + watch1.getPrint());
        graphics.pose().popPose();
    }

    protected float getDistanceBetweenButtons(PerkButton button1, PerkButton button2) {
        float x1 = button1.getX() + button1.getWidth() / 2F;
        float y1 = button1.getY() + button1.getHeight() / 2F;
        float x2 = button2.getX() + button2.getWidth() / 2F;
        float y2 = button2.getY() + button2.getHeight() / 2F;
        return getDistanceBetweenPoints(x1, y1, x2, y2);
    }

    protected float getDistanceBetweenPoints(float x1, float y1, float x2, float y2) {
        return Mth.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
    }


    protected float getAngleBetweenButtons(PerkButton button1, PerkButton button2) {
        float x1 = button1.getX() + button1.getWidth() / 2F;
        float y1 = button1.getY() + button1.getHeight() / 2F;
        float x2 = button2.getX() + button2.getWidth() / 2F;
        float y2 = button2.getY() + button2.getHeight() / 2F;
        return getAngleBetweenPoints(x1, y1, x2, y2);
    }

    protected float getAngleBetweenPoints(float x1, float y1, float x2, float y2) {
        return (float) Mth.atan2(y2 - y1, x2 - x1);
    }

    public SkillTreeScreen(SchoolType type) {
        super(Minecraft.getInstance()
                .getWindow()
                .getGuiScaledWidth(), Minecraft.getInstance()
                .getWindow()
                .getGuiScaledHeight());
        this.schoolType = type;

    }

    public PointData pointClicked = new PointData(0, 0);
    public int mouseRecentlyClickedTicks = 0;

    @Override
    public boolean mouseReleased(double x, double y, int ticks) {

        mouseRecentlyClickedTicks = 25;

        return super.mouseReleased(x, y, ticks);

    }

    public static int sizeX() {
        return Minecraft.getInstance().getWindow().getWidth();
    }

    public static int sizeY() {
        return Minecraft.getInstance().getWindow().getHeight();
    }

    int ticks = 0;


    private void renderConnections(GuiGraphics gui) {

        for (PerkConnectionRender2 con : PerkConnectionCache.rendersCache.get(this.schoolType.toString().hashCode()).values()) {
            this.renderConnection(gui, con);
        }
    }


    public boolean shouldRender(int x, int y, PerkScreenContext ctx) {

        // todo this doesnt seem to work perfect but at least it stops rendering  some offscreen buttons
        if (x >= ctx.offsetX + 10 && x < ctx.offsetX + (sizeX()) * ctx.getZoomMulti() - 10) {
            if (y >= ctx.offsetY + 10 && y < ctx.offsetY + (sizeY()) * ctx.getZoomMulti() - 10) {
                return true;
            }
        }
        return false;

    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 32) { // space
            this.goToCenter();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);

    }

    int tick_count = 0;
    public int scrollX = 0;
    public int scrollY = 0;

    HashMap<AbstractWidget, PointData> originalButtonLocMap = new HashMap<>();
    public HashMap<PointData, PerkButton> pointPerkButtonMap = new HashMap<>();


    public Minecraft mc = Minecraft.getInstance();

    PlayerData playerData = Load.player(mc.player);

    public TalentTree school;

    private static int SEARCH_WIDTH = 100;
    private static int SEARCH_HEIGHT = 14;
    public static EditBox SEARCH = new EditBox(Minecraft.getInstance().font, 0, 0, SEARCH_WIDTH, SEARCH_HEIGHT, Component.translatable("fml.menu.mods.search"));

    public static boolean searchFocused() {
        if (SkillTreeScreen.SEARCH != null) {
            return SkillTreeScreen.SEARCH.isFocused();
        } else {
            // Handle the case when search is not initialized
            return false;
        }
    }

    @Override
    public void tick() {
        SEARCH.tick();
    }

    @Override
    protected void init() {
        super.init();

        try {


            SkillTreeScreen.SEARCH.setFocused(false);
            SkillTreeScreen.SEARCH.setCanLoseFocus(true);

            this.school = ExileDB.TalentTrees().getFilterWrapped(x -> x.getSchool_type().equals(this.schoolType)).list.get(0);
            refreshButtons();
            PerkConnectionCache.init(this);
            goToCenter();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void addButtonPublic(AbstractWidget b) {
        this.addRenderableWidget(b);
    }

    /*private void addConnections() {

        buttonConnections = new HashSet<>();

        HashSet<PointData> def = new HashSet();

        Set<Set<PointData>> cons = new HashSet<>();
        var data = Load.player(ClientOnly.getPlayer());


        new ArrayList<>(children()).forEach(b -> {
            if (b instanceof PerkButton) {
                PerkButton pb = (PerkButton) b;

                Set<PointData> connections = this.school.calcData.connections.getOrDefault(pb.point, def);

                for (PointData p : connections) {

                    if (cons.stream().anyMatch(x -> x.contains(p) && x.contains(pb.point))) {
                        continue;
                    }

                    cons.add(Sets.newHashSet(p, pb.point));

                    PerkButton sb = this.pointPerkButtonMap.get(p);

                    if (sb == null) {
                        continue;
                    }

                    var con = data.talents.getConnection(school, sb.point, pb.point);
                    var result = new PerkConnectionRender(pb.point, sb.point, con);
                    buttonConnections.add(result);
                }
            }

        });

    }*/


    public void refreshButtons() {

        //Watch watch = new Watch();

        originalButtonLocMap.clear();
        pointPerkButtonMap.clear();

        // this.buttons.clear();
        this.clearWidgets();

        this.scrollX = 0;
        this.scrollY = 0;

        for (Map.Entry<PointData, String> e : school.calcData.perks.entrySet()) {

            Perk perk = ExileDB.Perks().get(e.getValue());

            if (perk == null) {
                perk = ExileDB.Perks().get(new UnknownStat().GUID()); // we show unknown stat so its visible ingame that something is wrong on the GUI
                //continue;
            }

            try {

                var pos = getPosForPoint(e.getKey(), perk);

                int x = pos.x;
                int y = pos.y;

                var button = new PerkButton(this, playerData, school, e.getKey(), perk, x, y);

                button.perkid = e.getValue();

                this.newButton(button);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        //addConnections();

        this.addWidget(SEARCH);

    }

    private Point getPosForPoint(PointData point, Perk perk) {

        float halfx = mc.getWindow().getGuiScaledWidth() / 2F;
        float halfy = mc.getWindow().getGuiScaledHeight() / 2F;

        float x = (point.x - school.calcData.center.x) * PerkButton.SPACING;
        float y = (point.y - school.calcData.center.y) * PerkButton.SPACING;

        // todo
        x -= perk.getType().size / 2F;
        y -= perk.getType().size / 2F;

        float tx = (int) (halfx + x);
        float ty = (int) (halfy + y);


        return new Point((int) tx, (int) ty);

    }

    public void goToCenter() {
        this.scrollX = 0;
        this.scrollY = 0;
    }

    private void newButton(AbstractWidget b) {

        this.addButtonPublic(b);

        originalButtonLocMap.put(b, new PointData(b.getX(), b.getY()));
        if (b instanceof PerkButton) {
            this.pointPerkButtonMap.put(((PerkButton) b).point, (PerkButton) b);
        }
    }


    public float zoom = 0.3F;
    public float targetZoom = zoom;


    @Override
    public void onClose() {

        super.onClose();

        resetZoom();

        SkillTreeScreen.SEARCH.setFocused(false);

    }

    @Override
    public void removed() {
        super.removed();

        resetZoom();

    }

    private void resetZoom() {
        zoom = 1;
        targetZoom = zoom;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scroll) {
        if (scroll < 0) {
            targetZoom -= 0.1F;
        }
        if (scroll > 0) {
            targetZoom += 0.1F;
        }

        this.targetZoom = Mth.clamp(targetZoom, 0.15F, 1);

        this.zoom = targetZoom;

        return true;
    }

    public PointData getZoomedPosition(PointData pos) {
        return new PointData((int) (this.zoom / (float) pos.x), (int) (this.zoom / (float) pos.y));
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        this.scrollX += 1F / zoom * deltaX;
        this.scrollY += 1F / zoom * deltaY;

        scrollY = Mth.clamp(scrollY, -3333, 3333);

        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }

    public PerkScreenContext ctx = new PerkScreenContext(this);


    @Override
    public void render(GuiGraphics gui, int x, int y, float ticks) {

        Watch watch = new Watch();


        ctx = new PerkScreenContext(this);

        // String searchTerm = SkillTreeScreen.SEARCH.getValue();

        // Watch watch = new Watch();
        mouseRecentlyClickedTicks--;

        renderBackgroundDirt(gui, this, 0);
        zoom = Mth.lerp(ClientConfigs.getConfig().SKILL_TREE_ZOOM_SPEED.get().floatValue(), zoom, targetZoom);

        gui.pose().scale(zoom, zoom, zoom);

        try {

            float addx = (1F / zoom - 1) * this.width / 2F;
            float addy = (1F / zoom - 1) * this.height / 2F;

            for (Renderable e : this.renderables) {
                if (e instanceof PerkButton b)
                    if (originalButtonLocMap.containsKey(b)) {

                        int xp = (int) (originalButtonLocMap.get(b).x + addx + scrollX);
                        int yp = (int) (originalButtonLocMap.get(b).y + addy + scrollY);

                        b.setX(xp);
                        b.setY(yp);

                        //b.search = searchTerm;
                    }
            }
            //Watch watch1 = new Watch();

            PerkConnectionCache.updateRenders(this);
            //System.out.println(watch1.getPrint());

            this.renderConnections(gui);


            ticks++;



            super.render(gui, x, y, ticks);

            this.tick_count++;

        } catch (Exception e) {
            e.printStackTrace();
        }


        gui.pose().scale(1F / zoom, 1F / zoom, 1F / zoom);

        renderPanels(gui);

        this.msstring = watch.getPrint();

        //watch.print(" rendering ");
    }


    String msstring = "";

    static ResourceLocation BACKGROUND = SlashRef.guiId("skill_tree/background");

    public static void renderBackgroundDirt(GuiGraphics gui, Screen screen, int vOffset) {
        //copied from Screen

        Tesselator tessellator = Tesselator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuilder();

        RenderSystem.setShaderTexture(0, BACKGROUND);

        Minecraft mc = Minecraft.getInstance();

        // todo test
        //gui.setColor(0.25F, 0.25F, 0.25F, 1.0F);
        int i = 32;
        gui.blit(BACKGROUND, 0, 0, 0, 0.0F, 0.0F, mc.screen.width, mc.screen.height, 32, 32);
        //gui.setColor(1.0F, 1.0F, 1.0F, 1.0F);

    }


    private void renderPanels(GuiGraphics gui) {

        Minecraft mc = Minecraft.getInstance();

        RenderSystem.enableDepthTest();


        int BG_WIDTH = 256;
        int BG_HEIGHT = 22;

        int xp = (int) (mc.getWindow().getGuiScaledWidth() / 2F - BG_WIDTH / 2F);
        int yp = 0;

        gui.blit(BIG_PANEL, xp, yp, 0, 0, BG_WIDTH, BG_HEIGHT);

        RenderSystem.enableDepthTest();

        int savedx = xp;
        int savedy = yp;


        int points = schoolType.getFreePoints(Load.Unit(mc.player), playerData.talents);

        MutableComponent text = Gui.TALENT_POINTS.locName().append(points + "");

        int yx = 4;

        gui.drawString(mc.font, text, savedx + 15, yx, ChatFormatting.YELLOW.getColor());
        text = Gui.TALENT_RESET_POINTS.locName().append(String.valueOf(playerData.talents.reset_points));
        gui.drawString(mc.font, text, savedx + (BG_WIDTH) - mc.font.width(text) - 15, yx, ChatFormatting.YELLOW.getColor());

        int tx = savedx + BG_WIDTH;
        tx = (tx - savedx) / 2 + savedx - SEARCH_WIDTH / 2;
        yx = yx - (SEARCH_HEIGHT - mc.font.lineHeight) / 2;

        SkillTreeScreen.SEARCH.setX(tx);
        SkillTreeScreen.SEARCH.setY(yx);
        SkillTreeScreen.SEARCH.render(gui, 0, 0, 0);


        if (MMORPG.RUN_DEV_TOOLS) {
            MutableComponent debug = Component.literal("Widgets: " + this.children().size() + " - " + msstring);
            gui.drawString(mc.font, debug, savedx + 277, yx, ChatFormatting.GREEN.getColor());
        }
    }

}
