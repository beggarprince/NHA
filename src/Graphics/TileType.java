package Graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;


//not yet implemented, atm will constantly be calling
public enum TileType {
    DIRT("dirt.png"),
    GRASS("grass.png");

    private BufferedImage image;

    // Enum constructor, loading the image
    TileType(String imagePath) {
        try {
            this.image = ImageIO.read(getClass()
                    .getClassLoader()
                    .getResourceAsStream(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to get the image associated with the tile type
    public BufferedImage getImage() {
        return image;
    }
}
