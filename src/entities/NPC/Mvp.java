package entities.NPC;

import graphics.ScreenSettings;
import util.ImgLoader;

import java.awt.image.BufferedImage;
import java.nio.Buffer;

public class Mvp {

    private static Mvp instance;

    final BufferedImage image = ImgLoader.getImageResource("sprites/monster/mvp.png");
    private int tsx = 0;
    private int tsy = 0;

    public boolean dragged = false;
    int counter = 0;
    int x = 32;
    int y = 68;
    int yd = 108;

    private Mvp(){

    }

    public static Mvp getInstance(){
        if(instance == null){
            instance = new Mvp();
        }
        return instance;
    }

    public int getPositionX() {
        return tsx;
    }

    public int getPositionY(){
        return tsy;
    }

    public void setXY(int tilePositionX, int tilePositionY){
        this.tsx = tilePositionX;
        this.tsy = tilePositionY;
        System.out.println("tsx set to "+ tsx);
    }

    public int returnScreenPositionY(){
        return tsy * ScreenSettings.TILE_SIZE;
    }
    public int returnScreenPositionX(){
        return tsx * ScreenSettings.TILE_SIZE;
    }


    //This is going to need some metadata
    public BufferedImage getSpriteFromSheet(){

        counter+=2;

        if(counter == 30){
            x += 128;
        }
        if(counter == 60){
            x += 128;
        }
        if(counter == 90){
            x += 128;
        }
        if(counter == 120){
            x += 128;
        }

        if(counter == 150) {
            x = 32;
            y += yd;
        }
        if(counter == 180){
            x += 128;
        }
        if(counter == 210){
            x += 128;
        }
        if(counter == 240){
            x += 128;
        }
        if(counter == 270){
            x += 128;
        }
        if(counter == 300) {
            x = 32;
            y -= yd;
            counter = 0;
        }


        BufferedImage img = image.getSubimage(x  , y, 68,68);
        return img;
    }
}


