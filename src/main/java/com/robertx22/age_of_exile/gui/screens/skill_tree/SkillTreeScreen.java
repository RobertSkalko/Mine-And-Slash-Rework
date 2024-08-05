package com.robertx22.age_of_exile.gui.screens.skill_tree;

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
import com.robertx22.age_of_exile.gui.screens.skill_tree.buttons.PerkPointPair;
import com.robertx22.age_of_exile.gui.screens.skill_tree.buttons.TreeOptimizationHandler;
import com.robertx22.age_of_exile.gui.screens.skill_tree.buttons.drawer.AllPerkButtonPainter;
import com.robertx22.age_of_exile.gui.screens.skill_tree.buttons.drawer.ButtonIdentifier;
import com.robertx22.age_of_exile.gui.screens.skill_tree.connections.PerkConnectionCache;
import com.robertx22.age_of_exile.gui.screens.skill_tree.connections.PerkConnectionRenderer;
import com.robertx22.age_of_exile.gui.screens.skill_tree.buttons.PerkScreenContext;
import com.robertx22.age_of_exile.gui.screens.skill_tree.delaycheck.ButtonStatusUpdateTask;
import com.robertx22.age_of_exile.gui.screens.skill_tree.delaycheck.RendererUpdateTask;
import com.robertx22.age_of_exile.gui.screens.skill_tree.opacity.OpacityController;
import com.robertx22.age_of_exile.mmorpg.MMORPG;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.saveclasses.PointData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.localization.Gui;
import com.robertx22.age_of_exile.uncommon.utilityclasses.ClientOnly;
import com.robertx22.library_of_exile.utils.Watch;
import it.unimi.dsi.fastutil.ints.Int2ReferenceOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public abstract class SkillTreeScreen extends BaseScreen implements INamedScreen, IAlertScreen {
    static ResourceLocation BIG_PANEL = new ResourceLocation(SlashRef.MODID, "textures/gui/skill_tree/bar.png");
    static ResourceLocation CON = SlashRef.id("textures/gui/skill_tree/skill_connection.png");
    static ResourceLocation BACKGROUND = SlashRef.guiId("skill_tree/background");
    private static int SEARCH_WIDTH = 100;
    private static int SEARCH_HEIGHT = 14;
    public static EditBox SEARCH = new EditBox(Minecraft.getInstance().font, 0, 0, SEARCH_WIDTH, SEARCH_HEIGHT, Component.translatable("fml.menu.mods.search"));
    public SchoolType schoolType;
    public PointData pointClicked = new PointData(0, 0);
    public int scrollX = 0;
    public int scrollY = 0;
    public HashMap<PointData, PerkButton> pointPerkButtonMap = new HashMap<>();
    public Minecraft mc = Minecraft.getInstance();
    public TalentTree school;
    public static float originalZoom = 0.3f;
    public float zoom = originalZoom;
    public float targetZoom = zoom;
    public PerkScreenContext ctx = new PerkScreenContext(this);
    int ticks = 0;
    int tick_count = 0;
    HashMap<AbstractWidget, PointData> originalButtonLocMap = new HashMap<>();
    PlayerData playerData = Load.player(mc.player);
    String msstring = "";
    private SkillTreeState currentState;
    private final VanillaState vanillaState;
    private final OptimizedState optimizedState;

    private final OpacityController opacityController = new OpacityController(null);
    public SkillTreeScreen(SchoolType type) {
        super(Minecraft.getInstance()
                .getWindow()
                .getGuiScaledWidth(), Minecraft.getInstance()
                .getWindow()
                .getGuiScaledHeight());
        this.schoolType = type;
        this.vanillaState = new VanillaState(this);
        this.optimizedState = new OptimizedState(this);
        this.currentState = TreeOptimizationHandler.isOptEnable() ? optimizedState : vanillaState;
    }

    public VanillaState getVanillaState() {
        return vanillaState;
    }

    public OptimizedState getOptimizedState() {
        return optimizedState;
    }

    public static int sizeX() {
        return Minecraft.getInstance().getWindow().getWidth();
    }

    public static int sizeY() {
        return Minecraft.getInstance().getWindow().getHeight();
    }

    public static boolean searchFocused() {
        if (SkillTreeScreen.SEARCH != null) {
            return SkillTreeScreen.SEARCH.isFocused();
        } else {
            // Handle the case when search is not initialized
            return false;
        }
    }

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

    private void renderConnection(GuiGraphics graphics, PerkConnectionRenderer connection) {

        graphics.pose().pushPose();
        //Watch watch = new Watch();
        PerkButton button1 = pointPerkButtonMap.get(connection.pair().perk1());
        PerkButton button2 = pointPerkButtonMap.get(connection.pair().perk2());
        if (!shouldRender(button1.getX(), button1.getY(), ctx) && !shouldRender(button2.getX(), button2.getY(), ctx)) return;

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
        Perk.Connection connection1 = connection.connection();
        if (connection1 == Perk.Connection.LINKED) {
            off = 0;
        }
        if (connection1 == Perk.Connection.POSSIBLE) {
            off = 6;
        }
        if (connection1 == Perk.Connection.BLOCKED) {
            off = 6 + 5;
        }

        graphics.blit(CON, 0, -3, length, 6, 0, off, length, 6, 50, 16);

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

    @Override
    public boolean mouseReleased(double x, double y, int ticks) {
        this.currentState.onMouseReleased(x, y, ticks);
        return super.mouseReleased(x, y, ticks);
    }

    private void renderConnections(GuiGraphics gui) {
        this.currentState.onRenderConnections(gui);
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

    @Override
    public void tick() {
        SEARCH.tick();
    }

    @Override
    protected void init() {
        super.init();
        this.currentState.onInit();
    }

    public void addButtonPublic(AbstractWidget b) {
        this.addRenderableWidget(b);
    }

    public void refreshButtons() {
        this.currentState.onRefreshButton();

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
        this.currentState.onGoToCenter();
    }

    private void newButton(AbstractWidget b) {

        this.addButtonPublic(b);

        originalButtonLocMap.put(b, new PointData(b.getX(), b.getY()));
        if (b instanceof PerkButton) {
            this.pointPerkButtonMap.put(((PerkButton) b).point, (PerkButton) b);
        }
    }

    @Override
    public void onClose() {
        super.onClose();
        this.currentState.onClose();
    }

    @Override
    public void removed() {
        super.removed();

        resetZoom();

    }

    private void resetZoom() {
        this.currentState.OnResetZoom();
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scroll) {
        this.currentState.onMouseScrolled(mouseX, mouseY, scroll);
        return true;
    }

    public PointData getZoomedPosition(PointData pos) {
        return new PointData((int) (this.zoom / (float) pos.x), (int) (this.zoom / (float) pos.y));
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {

        this.currentState.onMouseDragged(mouseX, mouseY, button, deltaX, deltaY);

        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }

    public void renderSuper(GuiGraphics gui, int x, int y, float ticks){
        super.render(gui, x, y, ticks);
    }


    @Override
    public void render(GuiGraphics gui, int x, int y, float ticks) {

        this.currentState.onRender(gui, x, y, ticks);

        this.tick_count++;

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
    abstract class SkillTreeState {
        public final SkillTreeScreen screen;

        public SkillTreeState(SkillTreeScreen screen) {
            this.screen = screen;
        }
        public abstract void onInit();
        public abstract void onRenderConnections(GuiGraphics gui);
        public abstract void onRender(GuiGraphics gui, int x, int y, float ticks);
        public abstract boolean onMouseScrolled(double mouseX, double mouseY, double scroll);
        public void onRefreshButton(){
            //Watch watch = new Watch();

            originalButtonLocMap.clear();
            pointPerkButtonMap.clear();

            // this.buttons.clear();
            this.screen.clearWidgets();

            this.screen.scrollX = 0;
            this.screen.scrollY = 0;

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

                    var button = new PerkButton(this.screen, playerData, school, e.getKey(), perk, x, y);

                    button.perkid = e.getValue();

                    this.screen.newButton(button);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }

            this.screen.addWidget(SEARCH);

        }
        public abstract void onMouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY);
        public void onGoToCenter(){
            this.screen.scrollX = 0;
            this.screen.scrollY = 0;
        }
        public void onClose() {
            OnResetZoom();
            SkillTreeScreen.SEARCH.setFocused(false);
        }
        public void OnResetZoom() {
            zoom = 1;
        }

        public abstract void onMouseReleased(double x, double y, int ticks);
    }
    public class VanillaState extends SkillTreeState {
        public HashSet<PerkConnectionRenderer> buttonConnections = new HashSet<>();

        public int mouseRecentlyClickedTicks = 0;

        public VanillaState(SkillTreeScreen screen) {
            super(screen);
        }


        @Override
        public void onInit() {
            try {


                SkillTreeScreen.SEARCH.setFocused(false);
                SkillTreeScreen.SEARCH.setCanLoseFocus(true);

                this.screen.school = ExileDB.TalentTrees().getFilterWrapped(x -> x.getSchool_type().equals(this.screen.schoolType)).list.get(0);

                refreshButtons();


                goToCenter();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onRenderConnections(GuiGraphics gui) {
            for (PerkConnectionRenderer con : this.buttonConnections) {
                this.screen.renderConnection(gui, con);
            }
        }

        @Override
        public void onRender(GuiGraphics gui, int x, int y, float ticks) {
            Watch watch = new Watch();


            ctx = new PerkScreenContext(this.screen);

            // String searchTerm = SkillTreeScreen.SEARCH.getValue();

            // Watch watch = new Watch();
            mouseRecentlyClickedTicks--;

            renderBackgroundDirt(gui, this.screen, 0);
            zoom = Mth.lerp(ClientConfigs.getConfig().SKILL_TREE_ZOOM_SPEED.get().floatValue(), zoom, targetZoom);
            gui.pose().scale(zoom, zoom, zoom);


            try {

                float addx = (1F / zoom - 1) * this.screen.width / 2F;
                float addy = (1F / zoom - 1) * this.screen.height / 2F;

                for (Renderable e : this.screen.renderables) {
                    if (e instanceof PerkButton b)
                        if (originalButtonLocMap.containsKey(b)) {

                            int xp = (int) (originalButtonLocMap.get(b).x + addx + scrollX);
                            int yp = (int) (originalButtonLocMap.get(b).y + addy + scrollY);

                            b.setX(xp);
                            b.setY(yp);

                            //b.search = searchTerm;
                        }
                }


                this.screen.renderConnections(gui);

                ticks++;

                if (mouseRecentlyClickedTicks > 1) {
                    addConnections();
                    mouseRecentlyClickedTicks = 0;
                }

                renderSuper(gui, x, y, ticks);

            } catch (Exception e) {
                e.printStackTrace();
            }


            gui.pose().scale(1F / zoom, 1F / zoom, 1F / zoom);

            renderPanels(gui);

            this.screen.msstring = watch.getPrint();

            //watch.print(" rendering ");

        }

        @Override
        public boolean onMouseScrolled(double mouseX, double mouseY, double scroll) {
            if (scroll < 0) {
                targetZoom -= 0.1F;
            }
            if (scroll > 0) {
                targetZoom += 0.1F;
            }

            this.screen.targetZoom = Mth.clamp(targetZoom, 0.15F, 1);

            this.screen.zoom = targetZoom;

            return true;
        }

        @Override
        public void onRefreshButton() {
            super.onRefreshButton();
            addConnections();
        }

        @Override
        public void onMouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
            this.screen.scrollX += 1F / zoom * deltaX;
            this.screen.scrollY += 1F / zoom * deltaY;
            scrollY = Mth.clamp(scrollY, -3333, 3333);
        }

        @Override
        public void onMouseReleased(double x, double y, int ticks) {
            mouseRecentlyClickedTicks = 25;
        }

        private void addConnections() {

            this.buttonConnections = new HashSet<>();

            var data = Load.player(ClientOnly.getPlayer());

            List<? extends GuiEventListener> children = this.screen.children();

            if (children.size() > 1500) {
                ConcurrentHashMap.KeySetView<Integer, Boolean> historyRendererHash = ConcurrentHashMap.newKeySet(2000);
                //use parallel if too many renderer
                ConcurrentHashMap.KeySetView<PerkConnectionRenderer, Boolean> renderers = ConcurrentHashMap.newKeySet(2000);
                children.parallelStream().forEach(b -> {
                    if (b instanceof PerkButton pb) {

                        Set<PointData> connections = this.screen.school.calcData.connections.getOrDefault(pb.point, Collections.EMPTY_SET);

                        for (PointData p : connections) {

                            PerkButton sb = this.screen.pointPerkButtonMap.get(p);
                            PerkPointPair pair = new PerkPointPair(pb.point, sb.point);
                            if (!historyRendererHash.contains(pair.hashCode())) {
                                if (sb == null) {
                                    continue;
                                }

                                var con = data.talents.getConnection(this.screen.school, sb.point, pb.point);
                                var result = new PerkConnectionRenderer(pair, con);
                                renderers.add(result);
                                historyRendererHash.add(pair.hashCode());
                            }

                        }
                    }

                });
                buttonConnections.addAll(renderers);
            } else {
                IntOpenHashSet historyRendererHash = new IntOpenHashSet(2000);
                children.forEach(b -> {
                    if (b instanceof PerkButton pb) {

                        Set<PointData> connections = this.screen.school.calcData.connections.getOrDefault(pb.point, Collections.EMPTY_SET);

                        for (PointData p : connections) {

                            PerkButton sb = this.screen.pointPerkButtonMap.get(p);
                            PerkPointPair pair = new PerkPointPair(pb.point, sb.point);
                            if (!historyRendererHash.contains(pair.hashCode())) {
                                if (sb == null) {
                                    continue;
                                }

                                var con = data.talents.getConnection(this.screen.school, sb.point, pb.point);
                                PerkConnectionRenderer result = new PerkConnectionRenderer(pair, con);
                                buttonConnections.add(result);
                                historyRendererHash.add(pair.hashCode());
                            }

                        }
                    }

                });

            }

        }



    }

    public class OptimizedState extends SkillTreeState {
        private int targetScrollX;
        private int targetScrollY;
        private boolean canSmoothHandleScroll;
        private boolean canSmoothHandleZoom;


        public AllPerkButtonPainter painter;

        public SearchHandler searchHandler;

        public OptimizedState(SkillTreeScreen screen) {
            super(screen);
            this.painter = AllPerkButtonPainter.getPainter(schoolType);
        }

        @Override
        public void onInit() {
            try {

                SkillTreeScreen.SEARCH.setFocused(false);
                SkillTreeScreen.SEARCH.setCanLoseFocus(true);

                this.screen.school = ExileDB.TalentTrees().getFilterWrapped(x -> x.getSchool_type().equals(this.screen.schoolType)).list.get(0);
                refreshButtons();
                this.searchHandler = new SearchHandler(this.screen);

                PerkConnectionCache.init(this.screen);

                painter.onSkillScreenOpen(this.screen.pointPerkButtonMap.values().stream().map(x -> x.getOptimizedState().buttonIdentifier).toList());

                goToCenter();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onRenderConnections(GuiGraphics gui) {
            int typeHash = this.screen.schoolType.toString().hashCode();
            for (PerkConnectionRenderer con : PerkConnectionCache.renderersCache.get(typeHash).values()) {
                con.handleTaskList();
                this.screen.renderConnection(gui, con);
            }
        }

        @Override
        public void onRender(GuiGraphics gui, int x, int y, float ticks) {
            Watch watch = new Watch();

            painter.resetRepaintSchedule();
            ctx = new PerkScreenContext(this.screen);

            renderBackgroundDirt(gui, this.screen, 0);
            handleZoom();
            handleScroll();
            gui.pose().scale(zoom, zoom, zoom);


            try {

                float addx = (1F / zoom - 1) * this.screen.width / 2F;
                float addy = (1F / zoom - 1) * this.screen.height / 2F;

                for (Renderable e : this.screen.renderables) {
                    if (e instanceof PerkButton b)
                        if (originalButtonLocMap.containsKey(b)) {

                            int xp = (int) (originalButtonLocMap.get(b).x + addx + scrollX);
                            int yp = (int) (originalButtonLocMap.get(b).y + addy + scrollY);

                            b.setX(xp);
                            b.setY(yp);

                            //b.search = searchTerm;
                        }
                }

                //must handle this before all the render thing
                if (!this.searchHandler.isUpdating) this.searchHandler.tickThis();
                opacityController.detectCurrentState(playerData);


                this.screen.renderConnections(gui);

                if (painter.isAllowedToPaint()) {
                    int connectionX = (int) (addx + scrollX);
                    int connectionY = (int) (addy + scrollY);
                    int startX = painter.minX - (school.calcData.center.x * PerkButton.SPACING);
                    int startY = painter.minY - (school.calcData.center.y * PerkButton.SPACING);
                    List<AllPerkButtonPainter.ResourceLocationAndSize> locations = painter.getCurrentPaintings();
                    int i = 0;

                    gui.setColor(1.0f, 1.0f, 1.0f, opacityController.getWholeImage());
                    for (AllPerkButtonPainter.ResourceLocationAndSize location : locations) {

                        gui.blit(location.location(), startX + i * location.width() + connectionX, startY + connectionY, 0, 0, location.width(), location.height(), location.width(), location.height());
                        i++;
                    }
                    gui.setColor(1.0f, 1.0f, 1.0f, 1.0f);

                }
                ticks++;

                renderSuper(gui, x, y, ticks);
            } catch (Exception e) {
                e.printStackTrace();
            }


            gui.pose().scale(1F / zoom, 1F / zoom, 1F / zoom);

            renderPanels(gui);

            this.screen.msstring = watch.getPrint();

        }

        @Override
        public boolean onMouseScrolled(double mouseX, double mouseY, double scroll) {
            targetScrollX -= (int) (((scroll < 0 || (scroll > 0 && zoom >= 0.5) ? 0 : 1F / zoom) * (mouseX - this.screen.width / 2F)) / 3);
            targetScrollY -= (int) (((scroll < 0 || (scroll > 0 && zoom >= 0.5) ? 0 : 1F / zoom) * (mouseY - this.screen.height / 2F)) / 3);
            if (scroll < 0) {
                targetZoom -= 0.1F;
            }
            if (scroll > 0) {
                targetZoom += 0.1F;
            }

            targetScrollX = Mth.clamp(targetScrollX, -3333, 3333);
            targetScrollY = Mth.clamp(targetScrollY, -3333, 3333);

            this.screen.targetZoom = ((float) Math.round(Mth.clamp(targetZoom, 0.15F, 1) * 10000)) / 10000;
            canSmoothHandleScroll = true;
            canSmoothHandleZoom = true;

            return true;
        }

        @Override
        public void onMouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
            canSmoothHandleScroll = false;
            canSmoothHandleZoom = false;
            this.screen.scrollX += 1F / zoom * deltaX;
            this.screen.scrollY += 1F / zoom * deltaY;

            this.screen.scrollX = Mth.clamp(this.screen.scrollX, -3333, 3333);
            this.screen.scrollY = Mth.clamp(this.screen.scrollY, -3333, 3333);
        }

        @Override
        public void onGoToCenter() {
            super.onGoToCenter();
            targetScrollX = 0;
            targetScrollY = 0;
        }

        @Override
        public void onClose() {
            super.onClose();
        }

        @Override
        public void OnResetZoom() {
            super.OnResetZoom();
            targetZoom = zoom;
        }

        @Override
        public void onMouseReleased(double x, double y, int ticks) {

        }

        private void handleZoom() {
            if (!canSmoothHandleZoom) {
                targetZoom = zoom;
                return;
            }
            if (zoom != targetZoom) {
                zoom = ((float) Math.round(zoom * 10000)) / 10000;
                if (zoom > targetZoom) {
                    if (zoom < targetZoom * 0.999f) {
                        zoom = targetZoom;
                    } else {
                        zoom = Mth.lerp(ClientConfigs.getConfig().SKILL_TREE_ZOOM_SPEED.get().floatValue(), zoom, targetZoom);
                    }
                } else {
                    if (zoom > targetZoom * 0.999f) {
                        zoom = targetZoom;
                    } else {
                        zoom = Mth.lerp(ClientConfigs.getConfig().SKILL_TREE_ZOOM_SPEED.get().floatValue(), zoom, targetZoom);
                    }
                }
            }
        }

        private void handleScroll() {
            if (!canSmoothHandleScroll) {
                targetScrollX = scrollX;
                targetScrollY = scrollY;
                return;
            }
            if (scrollX != targetScrollX) {
                if (scrollX > targetScrollX) {
                    if (scrollX < targetScrollX - 2) {
                        scrollX = targetScrollX;
                    } else {
                        scrollX = Mth.lerpInt(ClientConfigs.getConfig().SKILL_TREE_ZOOM_SPEED.get().floatValue(), scrollX, targetScrollX);
                    }
                } else {
                    if (scrollX > targetScrollX - 2) {
                        scrollX = targetScrollX;
                    } else {
                        scrollX = Mth.lerpInt(ClientConfigs.getConfig().SKILL_TREE_ZOOM_SPEED.get().floatValue(), scrollX, targetScrollX);
                    }
                }
            }

            if (scrollY != targetScrollY) {
                if (scrollY > targetScrollY) {
                    if (scrollY < targetScrollY - 2) {
                        scrollY = targetScrollY;
                    } else {
                        scrollY = Mth.lerpInt(ClientConfigs.getConfig().SKILL_TREE_ZOOM_SPEED.get().floatValue(), scrollY, targetScrollY);
                    }
                } else {
                    if (scrollY > targetScrollY - 2) {
                        scrollY = targetScrollY;
                    } else {
                        scrollY = Mth.lerpInt(ClientConfigs.getConfig().SKILL_TREE_ZOOM_SPEED.get().floatValue(), scrollY, targetScrollY);
                    }
                }
            }
        }


    }
}
