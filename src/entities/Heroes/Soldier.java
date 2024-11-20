package entities.Heroes;

import graphics.ImgLoader;
import graphics.ScreenSettings;

public class Soldier extends Hero{


    public Soldier(int health, int x, int y){
        super(health, x, y);
        System.out.println(worldPositionX + " " + worldPositionY);
        this.worldPositionX = x / ScreenSettings.TILE_SIZE;
        this.worldPositionY =y / ScreenSettings.TILE_SIZE;
        this.screenPositionX = x;
        this.screenPositionY = y;
        this.movementSpeed = 1;
        currDirection = getRandomDirection(x, y);
        this.image = ImgLoader.getImageResource("knight.png");
    }

    @Override
    public void behavior() {
        //Ideally something
        //We see if we can move this direction
        if(validateWalkableDirection(currDirection, worldPositionX, worldPositionY)){
            move(movementSpeed);
            updateWorldPosition();

            //resetMovementCycle();
        }
        else{
            System.out.println("Hero cannot move");
        }

    }

    @Override
    public void destroy() {
        image = null;
    }
}
