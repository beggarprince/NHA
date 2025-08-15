package   entities.WorldObjects;

import   graphics.ScreenSettings;

import java.awt.*;
import java.awt.image.BufferedImage;


public abstract class WorldObject {
    public int tilePositionX;
    public int tilePositionY;
    public int screenPositionX;
    public int screenPositionY;
    protected BufferedImage sprite;


    public WorldObject(int tilePositionX, int tilePositionY){
        this.tilePositionX = tilePositionX;
        this.tilePositionY = tilePositionY;
        this.screenPositionX = tilePositionX * ScreenSettings.TILE_SIZE;
        this.screenPositionY = tilePositionY * ScreenSettings.TILE_SIZE;
        WorldObjectManager.INSTANCE.determineListEntry(this);
    }

    public BufferedImage getSprite(){
        return sprite;
    }

    public abstract void behavior();


    public abstract Image getImage();
}
