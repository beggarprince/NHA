package main.java.graphics;
//Screen settings that will be used across the game
public class ScreenSettings {
    // Screen settings
    public static final int ORIGINAL_TILE_SIZE = 48; // 16x16
    //TODO implement way to get resolution and set to 3 at 4k and 2 below
    public static final int SCALE = 1;
    public static final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE;
    public static final int STYLE_OFFSET = TILE_SIZE * 2; // Mainly for testing but keeping this allows us to draw a background

    //Tiles system
    //Amount of tiles rendered on window
    //27x15 is cute 32x24 is just a bit too much informationdd
    public static final int TS_X = 27;
    public static final int TS_Y = 15;

    //Amount of tiles in world
    //TS tile system
    public static final int TS_WorldScale_X = 3;
    public static final int TS_World_Scale_Y = 5;
    public static final int TS_World_X = TS_X * TS_WorldScale_X;
    public static final int TS_World_Y = TS_Y * TS_World_Scale_Y;

    //Pixel
    //PX pixel system
    public static final int PX_SCREEN_WIDTH = TS_X * TILE_SIZE;
    public static final int PX_SCREEN_HEIGHT = TS_Y * TILE_SIZE;

    //Down right ui should be 6x and 4y tiles

    //For now, dig power will aim towards the middle. 3x from the right 2y
    public static final int PX_UI_DIGPOWER_X = (TS_X -3) * TILE_SIZE;
    public static final int PX_UI_DIGPOWER_Y = (TS_Y - 2) * (TILE_SIZE);
    public static final int PX_UI_NUMBER_OFFSET = 16;


    public static final int FPS = 60;
    public static final double INTERVAL = 1_000_000_000 / FPS;
}
