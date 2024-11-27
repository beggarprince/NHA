package entities.Heroes;

import util.ImgLoader;
import graphics.ScreenSettings;

public class Soldier extends Hero{


    public Soldier(int health, int x, int y){
        super(health, x, y);
        this.worldPositionX = x / ScreenSettings.TILE_SIZE;
        this.worldPositionY =y / ScreenSettings.TILE_SIZE;
        this.screenPositionX = x;
        this.screenPositionY = y;
        this.movementSpeed = 1;
        this.movementCycle = 0;
        this.image = ImgLoader.getImageResource("knight.png");
    }

    @Override
    public void behavior() {
        //Ideally something
        //We see if we can move this direction

        npcMoved();


    }

    @Override
    public void destroy() {
        image = null;
    }
}
