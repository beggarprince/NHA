package main.java.graphics.Sprite;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImgLoader {

    final static String path = "main/resources/";
    //These need to be stored by kvp of
        // StringPath, Buffered image
    //To avoid loading resources
    //But in a separate file
    public static BufferedImage getImageResource(String imgPath) {
        //System.out.println("Getting image " + imgPath);
        BufferedImage temp = null;
        try {
            temp = loadImage(path + imgPath);
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
