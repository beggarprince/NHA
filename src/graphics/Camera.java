package graphics;

import io.kbInput;
import level.LevelCreate;
import util.Coordinate;

// Updated Camera class with Coordinate pairs for each corner
public class Camera {
    public Coordinate topLeft;
    public int offsetX = 0;
    public int offsetY = 0;

    public Camera(Coordinate tl) {
        this.topLeft = tl;
    }

    //Will attempt to pan and notify the engine if it panned or whether we have to displace the player
    public boolean cameraUpdate(Coordinate player, kbInput kb) {
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
        //    System.out.println("Player " + player.x / ScreenSettings.TILE_SIZE+ " " + player.y / ScreenSettings.TILE_SIZE);
          //  System.out.println(topLeft.x / ScreenSettings.TILE_SIZE+ " " + topLeft.y / ScreenSettings.TILE_SIZE+ "\n");
            return true;
        }

        //At this point the camera is at the edge and cannot move, so we must move the player sprite instead
        //Or we simply don't want to move teh camera
     //   System.out.println("Camera did not pan");
        return false;
    }

    // Movement of the camera, nothing else
    private void panCamera(kbInput kb) {
        int movementAmount = ScreenSettings.TILE_SIZE;

        if (kb.downPressed) {
            topLeft.y += movementAmount;
            offsetY++;
         //   System.out.println("Camera panned down by " + movementAmount + " units.");
        } else if (kb.upPressed) {
            topLeft.y -= movementAmount;
            offsetY--;
         //   System.out.println("Camera panned up by " + movementAmount + " units.");
        } else if (kb.leftPressed) {
            topLeft.x -= movementAmount;
            offsetX--;
           // System.out.println("Camera panned left by " + movementAmount + " units.");

        } else if (kb.rightPressed) {
            topLeft.x += movementAmount;
            offsetX++;
          //  System.out.println("Camera panned right by " + movementAmount + " units.");
        } else {
            System.out.println("No movement detected.");
        }
    }



    //FUNCTIONAL
    private boolean verifyValidCoordinate(Coordinate edge, int x, int y) {
        boolean xWithinLeftBound = (edge.x + x) >= 0;
        boolean xWithinRightBound = (edge.x + x + ScreenSettings.SCREEN_WIDTH) < LevelCreate.levelx * ScreenSettings.TILE_SIZE;
        boolean yWithinUpperBound = (edge.y + y) >= 0;
        boolean yWithinLowerBound = (edge.y + y + ScreenSettings.SCREEN_HEIGHT) < LevelCreate.levely * ScreenSettings.TILE_SIZE;

//        if (!xWithinLeftBound) {
//            System.out.println("Failed: x is out of left bound");
//        }
//        if (!xWithinRightBound) {
//            System.out.println("Failed: x is out of right bound");
//        }
//        if (!yWithinUpperBound) {
//            System.out.println("Failed: y is out of upper bound");
//        }
//        if (!yWithinLowerBound) {
//            System.out.println("Failed: y is out of lower bound");
//        }

        if (xWithinLeftBound && xWithinRightBound && yWithinUpperBound && yWithinLowerBound) {
           // System.out.println("All cases passed: valid coordinate");
            return true;
        }
        return false;
    }


}