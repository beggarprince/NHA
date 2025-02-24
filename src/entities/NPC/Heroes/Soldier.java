package entities.NPC.Heroes;

import entities.Direction;
import util.ImgLoader;
import graphics.ScreenSettings;

public class Soldier extends Hero{

   // BufferedImage image =  ImgLoader.getImageResource("knight_mvRight.png");

    public Soldier(int health, int x, int y){
        super(96, x, y);
        //TODO I really need to fix the constructor to handle health
        this.health = 96;
        this.worldPositionX = x / ScreenSettings.TILE_SIZE;
        this.worldPositionY =y / ScreenSettings.TILE_SIZE;
        this.screenPositionX = x;
        this.screenPositionY = y;
        this.movementSpeed = 1;
        this.movementCycle = 0;
        this.basicAttackStrength = 4;
        this.image = ImgLoader.getImageResource("sprites/hero/knight_mvRight.png");
        this.cooldown = 60;
        this.fxIndex = 1;
    }

    @Override
    public void behavior() {

        //Ideally something
            moveNpcAndSignal();
            //setSprite(); // Move this to the movement function or something idek anymore

       // if(cooldown != 0 )cooldown--;

    }

    //TODO make this an abstract function
    private void setSprite(){
        if(this.currDirection == Direction.RIGHT) this.image = ImgLoader.getImageResource("sprites/hero/knight_mvRight.png");
       // else if(this.currDirection == Direction.LEFT) this.image = ImgLoader.getImageResource("knight_mvLeft.png");
    }

    @Override
    public void destroy() {
        image = null;
    }

    public String returnNpcType(){
        return "Soldier";
    }
}
