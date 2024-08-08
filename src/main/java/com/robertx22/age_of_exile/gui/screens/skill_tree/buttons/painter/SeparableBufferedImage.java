package com.robertx22.age_of_exile.gui.screens.skill_tree.buttons.painter;

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
        this.separateTo = bytes.length / (25 * 1024);
    }

    public List<BufferedImage> getSeparatedImage() {
        if (this.separateTo == 0){
            return Collections.singletonList(originalImage);
        }
        ImmutableList.Builder<BufferedImage> builder = ImmutableList.builder();
        boolean isDivisible = width % separateTo == 0;
        int handleWidth;
        int leftPart = 0;
        int singleWidth = 0;
        if (isDivisible) {
            handleWidth = width;
        } else {
            handleWidth = width - (width % separateTo);
            leftPart = width % separateTo;
        }
        singleWidth = handleWidth / separateTo;
        int a = 0;
        while (a < separateTo) {
            builder.add(originalImage.getSubimage(a * singleWidth, 0, singleWidth, height));
            a++;
        }
        if (leftPart != 0){
            builder.add(originalImage.getSubimage(handleWidth, 0, leftPart, height));
        }
        return builder.build();
    }

}
