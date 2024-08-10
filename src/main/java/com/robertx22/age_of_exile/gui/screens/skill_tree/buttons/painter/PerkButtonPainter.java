package com.robertx22.age_of_exile.gui.screens.skill_tree.buttons.painter;

import com.mojang.blaze3d.platform.NativeImage;
import com.robertx22.age_of_exile.database.data.perks.PerkStatus;
import com.robertx22.age_of_exile.database.data.talent_tree.TalentTree;
import com.robertx22.age_of_exile.gui.screens.skill_tree.ExileTreeTexture;
import com.robertx22.age_of_exile.gui.screens.skill_tree.PainterController;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Base64;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class PerkButtonPainter {


    public final static HashMap<ResourceLocation, BufferedImage> handledBufferedImage = new HashMap<>(10000);

    public final static HashSet<ResourceLocation> handledLocation = new HashSet<>(10000);

    public final static ConcurrentLinkedQueue<ButtonIdentifier> waitingToBePaintedQueue = new ConcurrentLinkedQueue<>();
    public final static ConcurrentLinkedQueue<PainterController.BufferedImagePack> waitingToBeRegisteredQueue = new ConcurrentLinkedQueue<>();
    public static final HashSet<String> addHistory = new HashSet<>(1000);

    private static BufferedImage tryPaintWholeIcon(ResourceLocation color, ResourceLocation border, ResourceLocation perk, ButtonIdentifier identifier, PerkStatus status) throws IOException {
        ResourceManager resourceManager = Minecraft.getInstance().getResourceManager();

        try (InputStream colorStream = resourceManager.open(color)) {
            try (InputStream borderStream = resourceManager.open(border)) {
                try (InputStream perkStream = resourceManager.open(perk)) {
                    BufferedImage co = ImageIO.read(colorStream);
                    BufferedImage bo = ImageIO.read(borderStream);
                    BufferedImage pe = ImageIO.read(perkStream);
                    int baseSize = identifier.perk().type.size;
                    int offColor = (baseSize - 20) / 2;
                    int offPerk = (int) identifier.perk().type.getOffset();


                    AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, status.getOpacity());
                    BufferedImage alphaImage = new BufferedImage(co.getWidth(), co.getHeight(), BufferedImage.TYPE_INT_ARGB);

                    Graphics2D alphaImageGraphics = alphaImage.createGraphics();
                    alphaImageGraphics.setComposite(alphaComposite);

                    alphaImageGraphics.setRenderingHint(java.awt.RenderingHints.KEY_INTERPOLATION,
                            java.awt.RenderingHints.VALUE_INTERPOLATION_BILINEAR);

                    alphaImageGraphics.drawImage(co, 0, 0, co.getWidth(), co.getHeight(), null);

                    alphaImageGraphics.dispose();

                    co = alphaImage;

                    if (identifier.perk().type.size - bo.getWidth() != 0D) {
                        int newWidth = identifier.perk().type.size;
                        int newHeight = identifier.perk().type.size;
                        BufferedImage scaledImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);

                        Graphics2D g2d = scaledImage.createGraphics();
                        g2d.setComposite(alphaComposite);

                        g2d.setRenderingHint(java.awt.RenderingHints.KEY_INTERPOLATION,
                                java.awt.RenderingHints.VALUE_INTERPOLATION_BILINEAR);

                        g2d.drawImage(bo, 0, 0, newWidth, newHeight, null);

                        g2d.dispose();

                        bo = scaledImage;

                    }

                    if (identifier.perk().type.iconSize - pe.getWidth() != 0D) {
                        int newWidth = identifier.perk().type.iconSize;
                        int newHeight = identifier.perk().type.iconSize;
                        BufferedImage scaledImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
                        Graphics2D g2d = scaledImage.createGraphics();

                        g2d.setRenderingHint(java.awt.RenderingHints.KEY_INTERPOLATION,
                                java.awt.RenderingHints.VALUE_INTERPOLATION_BILINEAR);

                        g2d.drawImage(pe, 0, 0, newWidth, newHeight, null);

                        g2d.dispose();

                        pe = scaledImage;

                    }

                    BufferedImage finalImage = new BufferedImage(baseSize, baseSize, BufferedImage.TYPE_INT_ARGB);
                    Graphics2D g = finalImage.createGraphics();
                    g.drawImage(co, offColor, offColor, null);
                    g.drawImage(pe, offPerk, offPerk, null);
                    g.drawImage(bo, 0, 0, null);
                    g.dispose();


                    return finalImage;
                }

            }
        }
    }


    public static boolean addToWait(ButtonIdentifier identifier) {
        //don't let any same RL pass
        //it still makes me confused, I've tried to use perk.hashCode() to filter same identifier out but it always says there are some buttons which have the same perk but also have different color+border+icon texture.
        String rl = identifier.getCurrentButtonLocation().toString();
        if (!addHistory.contains(rl)) {
            waitingToBePaintedQueue.add(identifier);
            addHistory.add(rl);
            return true;
        }
        return false;
    }

    public static void handlePaintQueue() {
        while (!waitingToBePaintedQueue.isEmpty()) {
            //though use acquire() directly is ok here, but I still don't wanna block any thread.
            PainterController.paintLimiter.tryAcquire(Duration.ofNanos(300));
            ButtonIdentifier identifier = waitingToBePaintedQueue.poll();
            HashMap<PerkStatus, List<ResourceLocation>> allNewLocation = identifier.getAllNewLocation();
            allNewLocation.forEach((k, v) -> {
                try {
                    BufferedImage image = PerkButtonPainter.tryPaintWholeIcon(v.get(0), v.get(1), v.get(2), identifier, k);
                    ResourceLocation newLocation = (getNewLocation(identifier.tree(), v.get(0), v.get(1),v.get(2)));
                    PainterController.BufferedImagePack bufferedImagePack = new PainterController.BufferedImagePack(image, newLocation);
                    waitingToBeRegisteredQueue.add(bufferedImagePack);
                    handledBufferedImage.put(newLocation, image);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    public static void handleRegisterQueue() {
        if (waitingToBeRegisteredQueue.isEmpty()) {
            return;
        }
        PainterController.isRegistering = true;
        while (!waitingToBeRegisteredQueue.isEmpty()){
            //can't use acquire() here, I run this on main thread.
            if (!PainterController.registerLimiter.tryAcquire(Duration.ofNanos(300))){
                break;
            }
            PainterController.BufferedImagePack pack = waitingToBeRegisteredQueue.poll();
            ResourceLocation resourceLocation = pack.resourceLocation();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {
                ImageIO.write(pack.image(), "PNG", baos);
            } catch (Exception e) {
                e.printStackTrace();
            }
            byte[] byteArray = baos.toByteArray();
            PaintingTransformer.map.put(resourceLocation.toString(), Base64.getEncoder().encodeToString(byteArray.clone()));
            try (NativeImage image = NativeImage.read(byteArray)) {
                ExileTreeTexture exileTreeTexture = new ExileTreeTexture(resourceLocation, image);
                Minecraft.getInstance().getTextureManager().register(resourceLocation, exileTreeTexture);
                handledLocation.add(resourceLocation);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
        PainterController.isRegistering = false;

    }

    public static ResourceLocation getNewLocation(TalentTree tree, ResourceLocation color, ResourceLocation border, ResourceLocation perk){
        //todo sometimes it'll got a weird bug about the getSchool_type() don't have return value due to ClassNotFoundException, means that it can't find the SchoolType class but it really makes no sense. won't crash the game tho.
        return new ResourceLocation(PainterController.nameSpace, tree.getSchool_type().toString().hashCode() + "_" + color.getPath() + "_" + border.getPath() + "_" + perk.getPath());
    }

}
