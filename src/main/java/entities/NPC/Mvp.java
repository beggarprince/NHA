package main.java.entities.NPC;

import main.java.Game.Scripts.MVPCaptured;
import main.java.entities.Direction;
import main.java.entities.NPC.Heroes.Hero;
import main.java.entities.NPC.Heroes.HeroList;
import main.java.graphics.ScreenSettings;
import main.java.level.Level;
import main.java.util.ImgLoader;

import java.awt.image.BufferedImage;

public class Mvp {

    private static Mvp instance;
    private static Hero kidnapper;
    private boolean kidnapped = false;
    final BufferedImage image = ImgLoader.getImageResource("sprites/monster/mvp.png");
    private int tsx = 0;
    private int tsy = 0;
    private int psx = 0;
    private int psy = 0;

    int counter = 0;
    int x = 32;
    int y = 68;
    int yd = 108;

    private Mvp(){
        setXY(Level.initialMVPCoordinate.x, Level.initialMVPCoordinate.y);
    }

    public void runMVPLogic(){
        if(!kidnapped){
            detectHeroCollision();
        }
        else{
            moveMvp();
        }
    }

    private void detectHeroCollision(){
        var heroList = HeroList.getInstance().getHeroes();

        for(Hero hero : heroList){
            if(((hero.tilePositionX -1 == tsx) || (hero.tilePositionX + 1 == tsx)) && hero.tilePositionY == tsy){
                kidnapper = hero;
                kidnapped = true;
            }
            else if(((hero.tilePositionX -1 == tsy) || (hero.tilePositionY + 1 == tsy)) && hero.tilePositionX == tsx){
                kidnapper = hero;
                kidnapped = true;
            }
        }
        if(kidnapped){
            kidnapper.kidnap();
            MVPCaptured.run();
            System.out.println("MVP kidnapped");
        }
    }

    private void moveMvp(){
        //check if kidnapper is alive
        if(kidnapper.health <= 0){
            kidnapped = false;
            return;
        }
        mvpMoveWithKidnapper();
    }


    private void mvpMoveWithKidnapper(){
        if(kidnapper.currDirection == Direction.RIGHT){
            if((kidnapper.screenPositionX - psx) > ScreenSettings.TILE_SIZE)psx++;

            if(kidnapper.screenPositionY > psy) psy++;
            if(kidnapper.screenPositionY < psy) psy--;

        }
        else if (kidnapper.currDirection == Direction.LEFT){
            if((psx - kidnapper.screenPositionX) > ScreenSettings.TILE_SIZE) psx--;
            if(kidnapper.screenPositionY > psy) psy++;
            if(kidnapper.screenPositionY < psy) psy--;
        }

        //Y
        else if (kidnapper.currDirection == Direction.DOWN){
            if((kidnapper.screenPositionY - psy) > ScreenSettings.TILE_SIZE){
                psy++;
            }
            if(kidnapper.screenPositionX > psx) psx++;
            if(kidnapper.screenPositionX < psx) psx--;
        }
        else if (kidnapper.currDirection == Direction.UP){
            if((psy - kidnapper.screenPositionY) > ScreenSettings.TILE_SIZE){
                psy--;
            }
            if(kidnapper.screenPositionX > psx) psx++;
            if(kidnapper.screenPositionX < psx) psx--;
        }
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
        this.psx = tsx * ScreenSettings.TILE_SIZE;
        this.psy = tsy * ScreenSettings.TILE_SIZE;
    }

    public int returnScreenPositionY(){
        return psy;
    }
    public int returnScreenPositionX(){
        return psx;
    }

    private void updateWorldPosition(){
        tsx = psx / ScreenSettings.TILE_SIZE;
        tsy = psy / ScreenSettings.TILE_SIZE;
    }


    //This needs to be redone but works for now
    public BufferedImage getSpriteFromSheet(){

        counter+=2;

        //This is the standing sprite animations
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

    public boolean mvpAtEntrance(){
        if(!kidnapped) return false;
        if(kidnapper.tilePositionX == Level.entryPoint.x && kidnapper.tilePositionY == Level.entryPoint.y) return true;
     //   System.out.println(kidnapper.tilePositionX + " " + kidnapper.tilePositionY);
        return false;
    }

    public boolean mvpIsCaptured(){
        return kidnapped;
    }
}


