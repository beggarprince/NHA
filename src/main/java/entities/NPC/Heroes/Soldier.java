package main.java.entities.NPC.Heroes;

import main.java.util.ImgLoader;
import main.java.graphics.ScreenSettings;

public class Soldier extends Hero{

    //TODO the rest can have static images, this can't. Figure out why
  //  public static BufferedImage image =  ImgLoader.getImageResource("sprites/hero/knight_mvRight.png");

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
        this.pathfinding = new HeroPathfinder(this);
        lastTilePosX = tilePositionX;
        lastTilePosY = tilePositionY;
    }
    int lastTilePosX;
    int lastTilePosY;

    @Override
    public void behavior() {

        //Ideally something
        if(moveNpcAndSignalTrueIfWeMove()){ //TODO this is temp until the refactor where i'll use a "atNewTile" boolean

            if(lastTilePosX != tilePositionX || lastTilePosY != tilePositionY){
                lastTilePosY = tilePositionY;
                lastTilePosX = tilePositionX;

                System.out.println("At new tile");
                pathfinding.logPath();
            }

        }
        else if(changedDirectionInMoveTempBoolean == true){
            this.currDirection = pathfinding.determinePath();
            System.out.println(pathfinding.determinePath());
        }


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
