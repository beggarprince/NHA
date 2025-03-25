package main.java.entities.NPC.Heroes;

import main.java.entities.Direction;
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
        pathfinding.logPath(false);
        lastTilePosX = tilePositionX;
        lastTilePosY = tilePositionY;
        this.hasMVP = false;
    }
    int lastTilePosX;
    int lastTilePosY;

    @Override
    public void behavior() {

        if(this.health <= 0){
            isDead = true;

            return;
        }

          if(moveNpcAndSignalTrueIfWeMove()){

            //This is how we know we are at a new tile
            if(detectNewTile()){
                pathfinding.logPath(hasMVP);

                if(pathfinding.currentlyBacktracking || hasMVP) {
                    currDirection = pathfinding.determinePath(hasMVP);
                }
            }
        }

        //This means we hit a wall
        else if(couldNotMoveForward){
            this.currDirection = pathfinding.determinePath(hasMVP);
            couldNotMoveForward = false;
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
