package graphics;

import util.Coordinate;

// Updated Camera class with Coordinate pairs for each corner
public class Camera {
    public Coordinate topLeft;
    public int offsetX = 0;
    public int offsetY = 0;

    public Camera(Coordinate tl) {
        this.topLeft = tl;
    }

    public void posUpdate(Coordinate player) {

        //Horizontal
        //Less than 0
        if (player.x + offsetX < topLeft.x *ScreenSettings.TILE_SIZE) {
     //       System.out.println("player.x: " + player.x + "< topLeft.x: " + topLeft.x + "\n");
            topLeft.x--;
            offsetX++;
        }

        //Greater than Max
        else if (player.x +offsetX  > topLeft.x* ScreenSettings.TILE_SIZE + ScreenSettings.SCREEN_WIDTH) {
       //     System.out.println("player.x: " + player.x + "> topRight.x: " + topRight.x + "\n");
            topLeft.x++;
            offsetX--;
        }

        //Vertical
        //Less than 0
        if (player.y + offsetY < topLeft.y * ScreenSettings.TILE_SIZE) {
            topLeft.y--;
            offsetY++;
        }

        //Greater than max
        else if (player.y+ offsetY > topLeft.y * ScreenSettings.TILE_SIZE + ScreenSettings.SCREEN_HEIGHT) {
            topLeft.y++;
            offsetY--;
        }
    }

    public void printPlayerAndCameraInfo(Coordinate player) {
        System.out.println("NEW\n");
        System.out.println("Player Coordinates:");
        System.out.println("Absolute: (" + player.x + ", " + player.y + ")");
        System.out.println("Divided by Tile Size: ("
                + (player.x / ScreenSettings.TILE_SIZE) + ", "
                + (player.y / ScreenSettings.TILE_SIZE) + ")\n");

        // Calculate the player's position relative to the top-left corner
        int relativeX = player.x - (topLeft.x * ScreenSettings.TILE_SIZE);
        int relativeY = player.y - (topLeft.y * ScreenSettings.TILE_SIZE);

        System.out.println("Player Coordinates Relative to Top-Left:");
        System.out.println("Relative: (" + relativeX + ", " + relativeY + ")\n");

        System.out.println("Camera Top-Left Tile Coordinates:");
        System.out.println("X: " + topLeft.x);
        System.out.println("Y: " + topLeft.y );

        System.out.println("Camera Offsets:");
        System.out.println("OffsetX: " + offsetX);
        System.out.println("OffsetY: " + offsetY + "\n");
    }

}