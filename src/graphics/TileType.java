package graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;


public enum TileType {
    DIRT("dirt.png"),
    GRASS("grass.png"),
    BRICK("brick.png"),
    PATH("gravel.png"),
    MANA("mana.png");

    private BufferedImage image;

    // Enum constructor, loading the image
    TileType(String imagePath) {
            this.image = imgLoader.getImage(imagePath);

    }

    // Method to get the image associated with the tile type
    public BufferedImage getImage() {
        return image;
    }
}
