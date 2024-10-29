package graphics;

public class ScreenSettings {
    // Screen settings
    public static final int ORIGINAL_TILE_SIZE = 16; // 16x16
    public static final int SCALE = 2;
    public static final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE;

    public static final int X = 32;
    public static final int Y = 24;
    public static final int SCREEN_WIDTH = X * TILE_SIZE;
    public static final int SCREEN_HEIGHT = Y * TILE_SIZE;


    public static final int FPS = 60;
    public static final double INTERVAL = 1_000_000_000 / FPS;
}
