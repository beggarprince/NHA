package entities.Enemy;
import graphics.ScreenSettings;
import graphics.imgLoader;
import util.Coordinate;

import java.awt.image.BufferedImage;

public class Slime extends Enemy {
    private BufferedImage slimeImage;

    public Slime(Coordinate position) {
        super(1, position); // Slime has a default health of 1
        this.worldPosX = position.x / ScreenSettings.TILE_SIZE;
        this.worldPosY = position.y / ScreenSettings.TILE_SIZE;
        setImage();
    }

    @Override
    protected void setImage() {
        slimeImage = imgLoader.getImage("slime.png");
    }

    // Getter for the image if needed
    public BufferedImage getImage() {
        return slimeImage;
    }

    public void behavior(){
        worldPosX += ScreenSettings.TILE_SIZE;
    }

}
