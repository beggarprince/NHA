package graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImgLoader {

    public static BufferedImage getImageResource(String imgPath) {
        BufferedImage temp = null;
        try {
            temp = loadImage(imgPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return temp;
    }


    private static BufferedImage loadImage(String imgPath) throws IOException {
        return ImageIO.read(ImgLoader.class.getClassLoader().getResourceAsStream(imgPath));
    }

}
