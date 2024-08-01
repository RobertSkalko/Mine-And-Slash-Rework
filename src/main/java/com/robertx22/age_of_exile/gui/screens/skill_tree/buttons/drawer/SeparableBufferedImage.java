package com.robertx22.age_of_exile.gui.screens.skill_tree.buttons.drawer;

import com.google.common.collect.ImmutableList;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class SeparableBufferedImage {

    private final BufferedImage originalImage;
    private final int width;
    private final int height;

    private final int separateTo;

    public SeparableBufferedImage(BufferedImage originalImage) throws IOException {
        this.originalImage = originalImage;
        this.width = originalImage.getWidth();
        this.height = originalImage.getHeight();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(originalImage, "PNG", byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        byteArrayOutputStream.close();
        byteArrayOutputStream = null;
        System.out.println("length is " + bytes.length);
        this.separateTo = bytes.length / (25 * 1024);
        System.out.println("separate to " + separateTo);
    }

    public List<BufferedImage> getSeparatedImage() {
        if (this.separateTo == 0){
            Collections.singletonList(originalImage);
        }
        System.out.println("separate");
        ImmutableList.Builder<BufferedImage> builder = ImmutableList.builder();
        int handleWidth;
        int leftPart = 0;
        int singleWidth = 0;
        if (width % separateTo != 0) {
            handleWidth = width - (width % separateTo);
            leftPart = width % separateTo;
        } else {
            handleWidth = width;
        }
        singleWidth = handleWidth / separateTo;
        int a = 0;
        while (a < separateTo) {
            builder.add(originalImage.getSubimage(a * singleWidth, 0, singleWidth, height));
            a++;
        }
        if (leftPart != 0){
            builder.add(originalImage.getSubimage(width - handleWidth, 0, leftPart, height));
        }
        return builder.build();
    }

}
