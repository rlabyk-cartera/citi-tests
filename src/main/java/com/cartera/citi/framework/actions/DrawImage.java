package com.cartera.citi.framework.actions;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class DrawImage {

    public void drawImage(String imageName) {
        try {
            int width = 85, height = 20;

            // TYPE_INT_ARGB specifies the image format: 8-bit RGBA packed
            // into integer pixels
            BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

            Graphics2D ig2 = bi.createGraphics();


            Font font = new Font("TimesRoman", Font.BOLD, 20);
            ig2.setFont(font);
            String message = "Autotest";
            FontMetrics fontMetrics = ig2.getFontMetrics();
            int stringWidth = fontMetrics.stringWidth(message);
            int stringHeight = fontMetrics.getAscent();
            ig2.setPaint(Color.black);
            ig2.drawString(message, (width - stringWidth) / 2, height / 2 + stringHeight / 4);

            ImageIO.write(bi, "PNG", new File(imageName));

        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }
}
