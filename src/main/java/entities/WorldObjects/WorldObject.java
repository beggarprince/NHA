package main.java.entities.WorldObjects;

import main.java.graphics.ScreenSettings;

import java.awt.image.BufferedImage;

//TODO should definitely rename this
//World as to not be confused with object in terms of programming
public class WorldObject {
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
    }

    public BufferedImage getSprite(){
        return sprite;
    }



}
