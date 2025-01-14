package graphics;

import entities.Player;
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
//TODO make this a singleton, when are we ever going to have two cameras?
public class Camera {
    public Coordinate topLeftCrn;
    public int offsetX = 0;
    public int offsetY = 0;



    public Camera(Coordinate tl) {
        this.topLeftCrn = tl;
    }

    private void resetDirection(KbInput kb){
    kb.up = false;
    kb.down = false;
    kb.left = false;
    kb.right = false;

}

    public boolean attemptCameraPan(KbInput kb) {
        // Determine if the camera should pan based on edge movement
        boolean moved = false;
        resetDirection(kb);

        // Check input and update camera coordinates
        if (kb.downPressed) {
            if(verifyValidCoordinate(topLeftCrn, 0, ScreenSettings.TILE_SIZE)) kb.down = true;

        } else if (kb.upPressed) {
            if(verifyValidCoordinate(topLeftCrn, 0, -ScreenSettings.TILE_SIZE)) kb.up = true;
        }

        if (kb.leftPressed) {
            if(verifyValidCoordinate(topLeftCrn, -ScreenSettings.TILE_SIZE, 0) )kb.left  = true;
        } else if (kb.rightPressed) {
            if( verifyValidCoordinate(topLeftCrn, ScreenSettings.TILE_SIZE, 0)) kb.right = true;
        }

       // System.out.println(up + " " + down +" " + left +" " + right);

        if( kb.up ||  kb.down ||  kb.left ||  kb.right) moved = panCamera(kb);


        return moved;
    }


    // Movement of the camera, nothing else
        //Handle player being offset?
    private boolean panCamera(KbInput kb ) {
        int movementAmount = ScreenSettings.TILE_SIZE;

        if ( kb.down) {
            topLeftCrn.y += movementAmount;
            offsetY++;

        } else if (kb.up) {
            topLeftCrn.y -= movementAmount;
            offsetY--;
        }

        if ( kb.left) {
            topLeftCrn.x -= movementAmount;
            offsetX--;

        } else if ( kb.right) {
            topLeftCrn.x += movementAmount;
            offsetX++;
        }
        return true;
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

    //TODO

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

        // Clamp the ideal positions so we don't go outside the level boundaries
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
}