package graphics;

import io.kbInput;
import level.LevelCreate;
import util.Coordinate;

// Updated Camera class with Coordinate pairs for each corner
public class Camera {
    public Coordinate topLeft;
    public int offsetX = 0;
    public int offsetY = 0;

    //This would be the starting stage, tbh could just start based off the player pos
    public Camera(Coordinate tl) {
        this.topLeft = tl;
    }

    //Will attempt to pan and notify the engine if it panned or whether we have to displace the player
    public boolean cameraUpdate( kbInput kb) {
        //code to decide if camera pans
        //ATM it will only pan at the edge
        boolean cameraPanned = false;
        //Nasty if else

        if(kb.downPressed){

            cameraPanned = verifyValidCoordinate(topLeft, 0, ScreenSettings.TILE_SIZE);
        }
        else if(kb.upPressed){
            cameraPanned = verifyValidCoordinate(topLeft, 0, -ScreenSettings.TILE_SIZE);
        }
        else if(kb.leftPressed){
            cameraPanned = verifyValidCoordinate(topLeft, -ScreenSettings.TILE_SIZE , 0);
        }
        else if(kb.rightPressed){
            cameraPanned = verifyValidCoordinate(topLeft , ScreenSettings.TILE_SIZE, 0);
        }
        else{
            //No change to camera, no need to get the player sprite moving
            return true;
        }

        //Pan it
        if(cameraPanned){
            panCamera(kb);
            return true;
        }
        return false;
    }

    // Movement of the camera, nothing else
    private void panCamera(kbInput kb) {
        int movementAmount = ScreenSettings.TILE_SIZE;

        if (kb.downPressed) {
            topLeft.y += movementAmount;
            offsetY++;

        } else if (kb.upPressed) {
            topLeft.y -= movementAmount;
            offsetY--;
        } else if (kb.leftPressed) {
            topLeft.x -= movementAmount;
            offsetX--;

        } else if (kb.rightPressed) {
            topLeft.x += movementAmount;
            offsetX++;
        } else {
            System.out.println("No movement detected.");
        }
    }

    //FUNCTIONAL
    private boolean verifyValidCoordinate(Coordinate edge, int x, int y) {
        boolean xWithinLeftBound = (edge.x + x) >= 0;
        boolean xWithinRightBound = (edge.x + x + ScreenSettings.SCREEN_WIDTH) < LevelCreate.levelColumns * ScreenSettings.TILE_SIZE;
        boolean yWithinUpperBound = (edge.y + y) >= 0;
        boolean yWithinLowerBound = (edge.y + y + ScreenSettings.SCREEN_HEIGHT) < LevelCreate.levelRows * ScreenSettings.TILE_SIZE;


        if (xWithinLeftBound &&
                xWithinRightBound &&
                yWithinUpperBound &&
                yWithinLowerBound) {
            return true;
        }
        return false;
    }

    //TODO
    public void centerCameraOnPlayer(){

    }
}