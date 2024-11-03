package graphics;

public class ScreenSettings {
    // Screen settings
    public static final int ORIGINAL_TILE_SIZE = 16; // 16x16
    public static final int SCALE = 2;
    public static final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE;

    //Tiles system
    public static final int TS_X = 32;
    public static final int TS_Y = 24;

    //Pixel
    public static final int PX_SCREEN_WIDTH = TS_X * TILE_SIZE;
    public static final int PX_SCREEN_HEIGHT = TS_Y * TILE_SIZE;


    public static final int FPS = 60;
    public static final double INTERVAL = 1_000_000_000 / FPS;
}
