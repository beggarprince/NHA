package main.java.graphics;

import main.java.entities.Player;
import main.java.level.Level;
import main.java.util.Coordinate;

// Updated Camera class with Coordinate pairs for each corner
/*
* This class represents the corners of the camera in the window, with topLeft being the topLeft corner of the screen
* It is able to increment/decrement it's corner values to move the camera
* It tracks offsets so the GameCanvas will shift tiles around to create different view perspective
* It checks boundaries and prevents camera from going out of bounds
* Returns a boolean to signal the main.java.Engine that the topLeft corner is unchanged and the player will instead have to move away from the center position of the screen
* */
//TODO make this a singleton, when are we ever going to have two cameras?
public class Camera {
    public Coordinate topLeftCrn;
    public int offsetX = 0;
    public int offsetY = 0;
    public Coordinate cinematicCamera;

    public Camera(Coordinate tl) {
        this.topLeftCrn = tl;
        this.cinematicCamera = new Coordinate(0,0);
    }

    public boolean attemptCameraPan( int movementType) {
        // Determine if the camera should pan based on edge movement
        boolean cameraPanEligible = true;

        // Check input and update camera coordinates
        //DOWN
        if (movementType == 3) {
            cameraPanEligible = verifyValidCoordinate(topLeftCrn, 0, ScreenSettings.TILE_SIZE);
            if (!cameraPanEligible) return false;
        }
        //UP
        else if (movementType == 1) {
            cameraPanEligible = verifyValidCoordinate(topLeftCrn, 0, -ScreenSettings.TILE_SIZE);
            if (!cameraPanEligible) return false;
        }
        //LEFT
        else if (movementType == 4) {
            cameraPanEligible = verifyValidCoordinate(topLeftCrn, -ScreenSettings.TILE_SIZE, 0);
            if (!cameraPanEligible) return false;
        }
        //RIGHT
        else if (movementType == 2) {
            cameraPanEligible = verifyValidCoordinate(topLeftCrn, ScreenSettings.TILE_SIZE, 0);
            if (!cameraPanEligible) return false;
        } else {
            return false;
        }

        panCamera(movementType);
        return cameraPanEligible; //this is always true
    }


    // Movement of the camera, nothing else
    private void panCamera(int movementType) {
        int movementAmount = ScreenSettings.TILE_SIZE;

        //DOWN
        if (movementType == 3) {
            topLeftCrn.y += movementAmount;
            offsetY++;

        }
        //UP
        else if (movementType == 1) {
            topLeftCrn.y -= movementAmount;
            offsetY--;
        }
        //LEFT
        else if (movementType == 4) {
            topLeftCrn.x -= movementAmount;
            offsetX--;

        }
        //RIGHT
        else if (movementType == 2) {
            topLeftCrn.x += movementAmount;
            offsetX++;
        } else {
            System.out.println("No movement detected.");
        }
    }



    private boolean verifyValidCoordinate(Coordinate edge, int x, int y) {
        boolean xWithinLeftBound = (edge.x + x + ScreenSettings.STYLE_OFFSET) >= 0;
        boolean xWithinRightBound = (edge.x + x + ScreenSettings.PX_SCREEN_WIDTH - ScreenSettings.STYLE_OFFSET) <= Level.levelColumns * ScreenSettings.TILE_SIZE;
        boolean yWithinUpperBound = (edge.y + y + ScreenSettings.STYLE_OFFSET) >= 0;
        boolean yWithinLowerBound = (edge.y + y + ScreenSettings.PX_SCREEN_HEIGHT - ScreenSettings.STYLE_OFFSET) <= Level.levelRows * ScreenSettings.TILE_SIZE;


        return xWithinLeftBound &&
                xWithinRightBound &&
                yWithinUpperBound &&
                yWithinLowerBound;
    }


    public void centerCameraOnPlayer(Player player) {
        // Half the screen width/height in pixels
        int halfWidth = ScreenSettings.PX_SCREEN_WIDTH / 2;
        int halfHeight = ScreenSettings.PX_SCREEN_HEIGHT / 2;

        // Player's current position in pixel coordinates
        int playerX = player.playerScreenPosition.x;
        int playerY = player.playerScreenPosition.y;

        // Ideal camera position so the player is at the center
        int idealX = playerX - halfWidth;
        int idealY = playerY - halfHeight;

        // Clamp the ideal positions so we don't go outside the main.java.level boundaries
        // Minimum possible top-left x/y is 0
        if (idealX < 0) {
            idealX = 0;
        }
        if (idealY < 0) {
            idealY = 0;
        }

        // Maximum possible top-left x is (totalLevelWidth - screenWidth)
        // maximum possible top-left y is (totalLevelHeight - screenHeight)
        int maxX = Level.levelColumns * ScreenSettings.TILE_SIZE - ScreenSettings.PX_SCREEN_WIDTH;
        int maxY = Level.levelRows * ScreenSettings.TILE_SIZE - ScreenSettings.PX_SCREEN_HEIGHT;

        if (idealX > maxX) {
            idealX = maxX;
        }
        if (idealY > maxY) {
            idealY = maxY;
        }

        // Update the camera's top-left coordinate
        topLeftCrn.x = idealX;
        topLeftCrn.y = idealY;

        offsetX = topLeftCrn.x / ScreenSettings.TILE_SIZE;
        offsetY = topLeftCrn.y / ScreenSettings.TILE_SIZE;
    }

    public void setCinematicCamera(int x, int y){
        cinematicCamera.x = x;
        cinematicCamera.y = y;
    }
}