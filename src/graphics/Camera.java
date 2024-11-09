package graphics;

import io.kbInput;
import level.Level;
import util.Coordinate;

// Updated Camera class with Coordinate pairs for each corner
public class Camera {
    public Coordinate topLeftCrn;
    public int offsetX = 0;
    public int offsetY = 0;

    public Camera(Coordinate tl) {
        this.topLeftCrn = tl;
    }

    public boolean updateCameraPosition(kbInput kb) {
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
    private void panCamera(kbInput kb) {
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


        if (xWithinLeftBound &&
                xWithinRightBound &&
                yWithinUpperBound &&
                yWithinLowerBound) {
            return true;
        }
        return false;
    }

    //TODO
    public void centerCameraOnPlayer() {

    }
}