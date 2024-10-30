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

    public boolean updateCameraPosition(kbInput kb) {
        // Determine if the camera should pan based on edge movement
        boolean isCameraPanned = false;

        // Check input and update camera coordinates
        if (kb.downPressed) {
            isCameraPanned = verifyValidCoordinate(topLeft, 0, ScreenSettings.TILE_SIZE);
            if(!isCameraPanned) return false;
        } else if (kb.upPressed) {
            isCameraPanned = verifyValidCoordinate(topLeft, 0, -ScreenSettings.TILE_SIZE);
            if(!isCameraPanned) return false;
        } else if (kb.leftPressed) {
            isCameraPanned = verifyValidCoordinate(topLeft, -ScreenSettings.TILE_SIZE, 0);
            if(!isCameraPanned) return false;
        } else if (kb.rightPressed) {
            isCameraPanned = verifyValidCoordinate(topLeft, ScreenSettings.TILE_SIZE, 0);
            if(!isCameraPanned) return false;
        } else {
            // No movement input, camera does not need updating
            return true;
        }

            panCamera(kb);
            return  isCameraPanned;
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