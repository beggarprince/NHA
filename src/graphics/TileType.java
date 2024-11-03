package graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;


//TODO atm the csv system and the enum are not 1:1 in terms of naming convention, needs to be reorganized
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
