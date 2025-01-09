package entities.Heroes;

import util.ImgLoader;
import graphics.ScreenSettings;

public class Soldier extends Hero{


    public Soldier(int health, int x, int y){
        super(health, x, y);
        //TODO I really need to fix the constructor to handle health
        this.health = 4;
        this.worldPositionX = x / ScreenSettings.TILE_SIZE;
        this.worldPositionY =y / ScreenSettings.TILE_SIZE;
        this.screenPositionX = x;
        this.screenPositionY = y;
        this.movementSpeed = 1;
        this.movementCycle = 0;
        this.image = ImgLoader.getImageResource("knight.png");
        this.basicAttackStrength = 4;
    }

    @Override
    public void behavior() {
        //Ideally something
            npcMoved();

       // if(cooldown != 0 )cooldown--;

    }

    @Override
    public void destroy() {
        image = null;
    }

    public String returnNpcType(){
        return "Soldier";
    }
}
