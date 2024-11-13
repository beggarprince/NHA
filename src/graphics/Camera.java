package graphics;

import io.KbInput;
import level.Level;
import util.Coordinate;

// Updated Camera class with Coordinate pairs for each corner
/*
* This class represents the corners of the camera in the window, with topLeft being the topLeft corner of the screen
* It is able to increment/decrement it's corner values to move the camera
* It tracks offsets so the GameCanvas will shift tiles around to create different view perspective
* It checks boundaries and prevents camera from going out of bounds
* Returns a boolean to signal the Engine that the topLeft corner is unchanged and the player will instead have to move away from the center position of the screen
* */
public class Camera {
    public Coordinate topLeftCrn;
    public int offsetX = 0;
    public int offsetY = 0;

    public Camera(Coordinate tl) {
        this.topLeftCrn = tl;
    }

    public boolean updateCameraPosition(KbInput kb) {
        // Determine if the camera should pan based on edge movement
        boolean isCameraPanned = true;

        // Check input and update camera coordinates
        if (kb.downPressed) {
            isCameraPanned = verifyValidCoordinate(topLeftCrn, 0, ScreenSettings.TILE_SIZE);
            if (!isCameraPanned) return false;
        } else if (kb.upPressed) {
            isCameraPanned = verifyValidCoordinate(topLeftCrn, 0, -ScreenSettings.TILE_SIZE);
            if (!isCameraPanned) return false;
        } else if (kb.leftPressed) {
            isCameraPanned = verifyValidCoordinate(topLeftCrn, -ScreenSettings.TILE_SIZE, 0);
            if (!isCameraPanned) return false;
        } else if (kb.rightPressed) {
            isCameraPanned = verifyValidCoordinate(topLeftCrn, ScreenSettings.TILE_SIZE, 0);
            if (!isCameraPanned) return false;
        } else {
            return false;
        }

        panCamera(kb);
        return isCameraPanned;
    }


    // Movement of the camera, nothing else
    private void panCamera(KbInput kb) {
        int movementAmount = ScreenSettings.TILE_SIZE;

        if (kb.downPressed) {
            topLeftCrn.y += movementAmount;
            offsetY++;

        } else if (kb.upPressed) {
            topLeftCrn.y -= movementAmount;
            offsetY--;
        } else if (kb.leftPressed) {
            topLeftCrn.x -= movementAmount;
            offsetX--;

        } else if (kb.rightPressed) {
            topLeftCrn.x += movementAmount;
            offsetX++;
        } else {
            System.out.println("No movement detected.");
        }
    }

    private boolean verifyValidCoordinate(Coordinate edge, int x, int y) {
        boolean xWithinLeftBound = (edge.x + x) >= 0;
        boolean xWithinRightBound = (edge.x + x + ScreenSettings.PX_SCREEN_WIDTH) < Level.levelColumns * ScreenSettings.TILE_SIZE;
        boolean yWithinUpperBound = (edge.y + y) >= 0;
        boolean yWithinLowerBound = (edge.y + y + ScreenSettings.PX_SCREEN_HEIGHT) < Level.levelRows * ScreenSettings.TILE_SIZE;


        return xWithinLeftBound &&
                xWithinRightBound &&
                yWithinUpperBound &&
                yWithinLowerBound;
    }

    //TODO
    public void centerCameraOnPlayer() {

    }
}