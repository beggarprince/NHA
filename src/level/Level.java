package level;

import graphics.ScreenSettings;
import graphics.TileType;
import util.Coordinate;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

// Level will take in the information gathered from level loader and translate it to
// a usable array or list that GameCanvas can translate into a level

public class Level {
    // Private static instance variable to hold the single instance
    private static Level instance;

    public ArrayList<ArrayList<Integer>> levelData;

    public ArrayList<ArrayList<Tile>> tileData;

    public static int levelColumns;
    public static int levelRows;

    // Private constructor prevents instantiation from other classes
    private Level(String levelFilePath) {
        levelData = LevelLoader.getLevelData(levelFilePath);

        levelRows = levelData.size(); // Size of column
        levelColumns = levelData.get(0).size(); // Size of row
        createTileData();
    }

    // Public static method to provide access to the single instance
    public static Level getInstance(String levelFilePath) {
        if (instance == null) {
            instance = new Level(levelFilePath);
        }
        return instance;
    }





    private void createTileData() {
        // Initialize tileData
        tileData = new ArrayList<>();
        for (int i = 0; i < levelRows; i++) {
            ArrayList<Tile> temp = new ArrayList<>();
            for (int j = 0; j < levelColumns; j++) {
                temp.add(createTile(j, i, levelData.get(i).get(j)));
            }
            tileData.add(temp);
        }
    }

    private Tile createTile(int x, int y, int val) {
        return new Tile(intToType(val), x, y);
    }

    private TileType intToType(int val) {
        switch (val) {
            case 1:
                return TileType.GRASS;
            case 2:
                return TileType.BRICK;
            case 3:
                return TileType.PATH;
            case 4:
                return TileType.MANA;
            default:
                return TileType.DIRT;
        }
    }
}
