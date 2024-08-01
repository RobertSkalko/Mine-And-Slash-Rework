package com.robertx22.age_of_exile.gui.screens.skill_tree.buttons.drawer;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.platform.Window;
import com.robertx22.age_of_exile.database.data.perks.Perk;
import com.robertx22.age_of_exile.database.data.talent_tree.TalentTree;
import com.robertx22.age_of_exile.event_hooks.ontick.OnClientTick;
import com.robertx22.age_of_exile.gui.screens.skill_tree.ExileTreeTexture;
import com.robertx22.age_of_exile.gui.screens.skill_tree.PainterController;
import com.robertx22.age_of_exile.gui.screens.skill_tree.buttons.PerkButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Queue;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// todo maybe need rewrite this with state machine design mode
public class AllPerkButtonPainter {

    private final int typeHash;

    //todo is this really a good collection to store changed renderers? Slow in searching.
    //anyway we prob won't have that much data at the same time.

    private final HashSet<ButtonIdentifier> updateInThinRun = new HashSet<>();
    private final HashSet<ButtonIdentifier> updateOnThisScreen = new HashSet<>();
    private final InitState initState;
    private final PaintState paintState;
    private final RegisterState registerState;
    private final StandbyState standByState;
    private final ExecutorService paintThread = Executors.newSingleThreadExecutor();
    private final int maxRepaintTimer = 3 * 60 * 20;
    public PainterState state;
    public int maxX = 0;
    public int minX = 10000;
    public int maxY = 0;
    public int minY = 10000;
    private int drawInWindowWidth = 0;
    private boolean isPainting = false;
    private boolean isRepainting = false;
    private int repaintTimer = maxRepaintTimer;

    public AllPerkButtonPainter(TalentTree.SchoolType type) {
        this.typeHash = type.toString().hashCode();
        this.initState = new InitState(this);
        this.paintState = new PaintState(this);
        this.registerState = new RegisterState(this);
        this.standByState = new StandbyState(this);
        this.state = this.initState;
    }

    public static AllPerkButtonPainter getPainter(TalentTree.SchoolType schoolType) {
        AllPerkButtonPainter allPerkButtonPainter;
        int i = schoolType.toString().hashCode();
        if (!OnClientTick.container.containsKey(i)) {
            allPerkButtonPainter = new AllPerkButtonPainter(schoolType);
            OnClientTick.container.put(i, allPerkButtonPainter);
        }
        AllPerkButtonPainter allPerkButtonPainter1 = OnClientTick.container.get(i);
        return allPerkButtonPainter1;
    }

    public void onSkillScreenOpen(Collection<ButtonIdentifier> identifiers) {
        if (this.initState.cache.isEmpty()) this.initState.cache.addAll(identifiers);
        if (this.state instanceof InitState) startANewRun();
    }

    public void startANewRun() {
        repaint();
    }

    public void tryRegister() {
        this.state.onRegister();
    }

    public void scheduleRepaint() {
        if (this.updateOnThisScreen.isEmpty()) return;
        if (repaintTimer > 0) {
            repaintTimer--;
        } else {
            startANewRun();
            repaintTimer = maxRepaintTimer;
        }
    }

    public List<ResourceLocationAndSize> getCurrentPaintings() {
        return this.standByState.getHandledContainer();
    }

    public void addToUpdate(ButtonIdentifier identifier) {
        this.updateOnThisScreen.add(identifier);
    }


    public ResourceLocation getThisLocation() {
        return new ResourceLocation(PainterController.nameSpace, "" + typeHash);
    }

    public boolean isAllowedToPaint() {
        return this.state instanceof StandbyState;
    }

    public boolean isThisButtonIsUpdating(PerkButton button) {
        return this.updateOnThisScreen.contains(button.buttonIdentifier) || this.updateInThinRun.contains(button.buttonIdentifier) || this.paintState.waitingToBePainted.contains(button.buttonIdentifier);
    }

    public void checkIfNeedRepaint() {
        // due to window size change
        if (this.drawInWindowWidth != 0 && Minecraft.getInstance().getWindow().getGuiScaledWidth() != this.drawInWindowWidth) {
            this.drawInWindowWidth = Minecraft.getInstance().getWindow().getGuiScaledWidth();
            repaint();
        }
    }

    private void repaint() {
        this.state.onRepaint();
        this.repaintTimer = maxRepaintTimer;
    }

    @SuppressWarnings("all")
    private <T> T changeState(T state) {
        this.state = (PainterState) state;
        return state;
    }

    public record ResourceLocationAndSize(ResourceLocation location, int width, int height) {

    }

    abstract class PainterState<T> {

        public final AllPerkButtonPainter painter;

        public PainterState(AllPerkButtonPainter painter) {
            this.painter = painter;
        }

        public abstract void onInit();

        public abstract void onPaint();

        public abstract void onRegister();

        public abstract void onRepaint();

        public abstract Collection<T> getHandledContainer();

        public PainterState transferData(Collection<T> data) {
            this.getHandledContainer().addAll(data);
            return this;
        }

    }

    class InitState extends PainterState<ButtonIdentifier> {

        private final HashSet<ButtonIdentifier> cache = new HashSet<>(3000);

        public InitState(AllPerkButtonPainter painter) {
            super(painter);
        }

        @Override
        public void onInit() {
            if (cache.isEmpty()) return;
            System.out.println("init all painter");
            painter.changeState(painter.paintState).transferData(cache).onPaint();
        }

        @Override
        public void onPaint() {

        }

        @Override
        public void onRegister() {

        }

        @Override
        public void onRepaint() {
            painter.changeState(painter.paintState).onRepaint();
        }

        @Override
        public Set<ButtonIdentifier> getHandledContainer() {
            return cache;
        }
    }

    class PaintState extends PainterState<ButtonIdentifier> {
        public final ConcurrentLinkedQueue<ButtonIdentifier> waitingToBePainted = new ConcurrentLinkedQueue<>();
        private CompletableFuture<Void> voidCompletableFuture;

        public PaintState(AllPerkButtonPainter painter) {
            super(painter);
        }

        @Override
        public void onInit() {

        }

        @Override
        public void onPaint() {
            if (voidCompletableFuture == null || voidCompletableFuture.isDone()) {


                voidCompletableFuture = CompletableFuture.runAsync(() -> {
                    SeparableBufferedImage image;
                    try {
                        image = tryPaint();
                    } catch (InterruptedException | IOException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("add to register!");
                    painter.changeState(painter.registerState).transferData(image.getSeparatedImage());
                }, paintThread);

            }
        }

        @Override
        public void onRegister() {

        }

        @Override
        public void onRepaint() {
            if (voidCompletableFuture != null) voidCompletableFuture.cancel(true);
            waitingToBePainted.clear();
            painter.changeState(painter.registerState).onRepaint();
        }


        @Override
        public Queue<ButtonIdentifier> getHandledContainer() {
            return this.waitingToBePainted;
        }

        private SeparableBufferedImage tryPaint() throws InterruptedException, IOException {
            Minecraft mc = Minecraft.getInstance();
            Window window = mc.getWindow();
            BufferedImage image = new BufferedImage(window.getGuiScaledWidth() * PerkButton.SPACING, window.getGuiScaledHeight() * PerkButton.SPACING, BufferedImage.TYPE_INT_ARGB);

            painter.drawInWindowWidth = window.getGuiScaledWidth();
            Graphics2D graphics = image.createGraphics();

            float halfx = mc.getWindow().getGuiScaledWidth() / 2F;
            float halfy = mc.getWindow().getGuiScaledHeight() / 2F;
            int maxX = 0;
            int minX = 10000;
            int maxY = 0;
            int minY = 10000;
            // prefer a little multi.
            float singleButtonZoom = 1.4f;
            while (!waitingToBePainted.isEmpty()) {
                PainterController.paintLimiter.acquire();
                ButtonIdentifier identifier = waitingToBePainted.poll();
                Perk.PerkType type = identifier.perk().getType();
                ResourceLocation wholeTexture = identifier.getCurrentButtonLocation();


                BufferedImage singleButton = PerkButtonPainter.handledBufferedImage.get(wholeTexture);
                if (singleButton == null) {
                    waitingToBePainted.add(identifier);
                    Thread.sleep(2000);
                    continue;
                }

                int singleButtonSize = (int) (type.size * singleButtonZoom + 1);
                BufferedImage redesignSingleButton = new BufferedImage(singleButtonSize, singleButtonSize, BufferedImage.TYPE_INT_ARGB);
                Graphics2D redesignSingleButtonGraphics = redesignSingleButton.createGraphics();

                redesignSingleButtonGraphics.drawImage(singleButton, 0, 0, singleButtonSize, singleButtonSize, null);

                redesignSingleButtonGraphics.dispose();
                singleButton = redesignSingleButton;

                float x = (identifier.point().x) * PerkButton.SPACING;
                float y = (identifier.point().y) * PerkButton.SPACING;

                x -= singleButtonSize / 2F;
                y -= singleButtonSize / 2F;

                int tx = (int) (halfx + x);
                int ty = (int) (halfy + y);
                maxX = Math.max(tx, maxX);
                minX = Math.min(tx, minX);
                maxY = Math.max(ty, maxY);
                minY = Math.min(ty, minY);
                AffineTransform affineTransform = new AffineTransform();

                affineTransform.translate(tx, ty);

                graphics.drawImage(singleButton, affineTransform, null);
                updateInThinRun.add(identifier);
            }
            graphics.dispose();
            //unless there are some icons bigger than 50
            var newImage = image.getSubimage(minX, minY, (int) (maxX - minX + 50 * singleButtonZoom), (int) (maxY - minY + 50 * singleButtonZoom));
            painter.minX = minX;
            painter.minY = minY;
            painter.maxX = maxX;
            painter.maxY = maxY;
            return new SeparableBufferedImage(newImage);

        }
    }

    class RegisterState extends PainterState<BufferedImage> {
        private final ConcurrentLinkedQueue<BufferedImage> needRegister = new ConcurrentLinkedQueue<>();
        private final int max = 5;
        private List<ResourceLocationAndSize> results = new ArrayList<>();
        private int timer = max;

        private int counter = 0;

        public RegisterState(AllPerkButtonPainter painter) {
            super(painter);
        }

        @Override
        public void onInit() {

        }

        @Override
        public void onPaint() {

        }

        @Override
        public void onRegister() {
            if (timer > 0) {
                timer--;
            } else {
                handleOne();
                tryCleanUp();
                timer = max;
            }
        }

        @Override
        public void onRepaint() {
            cleanUpAndChangeState();
        }


        @Override
        public Queue<BufferedImage> getHandledContainer() {
            return needRegister;
        }

        @Override
        public PainterState transferData(Collection<BufferedImage> data) {
            needRegister.clear();
            results.clear();
            super.transferData(data);
            return this;
        }

        private void handleOne() {
            if (needRegister.isEmpty()) return;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            BufferedImage singleImage = needRegister.poll();
            try {
                ImageIO.write(singleImage, "PNG", baos);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try (NativeImage nativeImage = NativeImage.read(baos.toByteArray())) {
                baos.close();
                ResourceLocation location = new ResourceLocation(getThisLocation().getNamespace(), getThisLocation().getPath() + "_" + counter);
                counter++;
                ExileTreeTexture exileTreeTexture = new ExileTreeTexture(location, nativeImage);
                TextureManager textureManager = Minecraft.getInstance().getTextureManager();
                textureManager.release(location);
                textureManager.register(location, exileTreeTexture);
                results.add(new ResourceLocationAndSize(location, singleImage.getWidth(), singleImage.getHeight()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

        private void clearHandledCollection() {
            updateInThinRun.clear();
            results.clear();
        }

        private void resetTimer() {
            this.counter = 0;
            this.timer = max;

        }

        private void tryCleanUp() {
            if (!needRegister.isEmpty()) return;
            System.out.println("clean up!");
            painter.changeState(painter.standByState).transferData(results);
            resetTimer();
            clearHandledCollection();
            updateOnThisScreen.removeAll(updateInThinRun);
            updateInThinRun.clear();

        }

        private void cleanUpAndChangeState() {
            resetTimer();
            clearHandledCollection();
            painter.changeState(painter.standByState);
            painter.state.onRepaint();
        }
    }

    class StandbyState extends PainterState<ResourceLocationAndSize> {
        private List<ResourceLocationAndSize> locations = new ArrayList<>();

        public StandbyState(AllPerkButtonPainter painter) {
            super(painter);
        }

        @Override
        public void onInit() {

        }

        @Override
        public void onPaint() {

        }

        @Override
        public void onRegister() {

        }

        @Override
        public void onRepaint() {
            System.out.println("repaint!");
            locations.clear();
            painter.updateInThinRun.clear();
            painter.updateOnThisScreen.clear();
            painter.changeState(painter.initState).onInit();
        }


        @Override
        public List<ResourceLocationAndSize> getHandledContainer() {
            return locations;
        }

        @Override
        public PainterState transferData(Collection<ResourceLocationAndSize> data) {
            locations.clear();
            return super.transferData(data);
        }
    }


}
