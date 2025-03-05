package main.java.entities.NPC.Heroes;

import main.java.entities.Direction;
import main.java.util.ImgLoader;
import main.java.graphics.ScreenSettings;

public class Soldier extends Hero{

    public Soldier(int health, int x, int y){
        super(96, x, y);
        this.health = 96;
        this.tilePositionX = x / ScreenSettings.TILE_SIZE;
        this.tilePositionY =y / ScreenSettings.TILE_SIZE;
        this.screenPositionX = x;
        this.screenPositionY = y;
        this.movementSpeed = 1;
        this.movementCycle = 0;
        this.basicAttackStrength = 4;
        this.image = ImgLoader.getImageResource("sprites/hero/knight_mvRight.png");
        this.combatCooldown = 60;
        this.fxIndex = 1;
    }

    @Override
    public void behavior() {

        //Ideally something
        moveNpcAndSignal();

       // if(cooldown != 0 )cooldown--;

    }


    @Override
    public void destroy() {
        image = null;
    }

    public String returnNpcType(){
        return "Soldier";
    }

    @Override
    protected void spriteHandler() {

    }
}
