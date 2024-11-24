package util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImgLoader {

    public static BufferedImage getImageResource(String imgPath) {
        BufferedImage temp = null;
        try {
            temp = loadImage(imgPath);
        } catch (IOException e) {
            System.err.println("Error: Unable to load image resource '" + imgPath + "'.");
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
        return temp;
    }

    private static BufferedImage loadImage(String imgPath) throws IOException {
        var resourceStream = ImgLoader.class.getClassLoader().getResourceAsStream(imgPath);
        if (resourceStream == null) {
            throw new IllegalArgumentException("Error: Resource '" + imgPath + "' not found.");
        }
        return ImageIO.read(resourceStream);
    }

}
