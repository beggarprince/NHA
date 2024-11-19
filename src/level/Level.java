package level;

import graphics.ScreenSettings;

import java.util.ArrayList;

// Level will take in the information gathered from level loader and translate it to
// a usable array or list that GameCanvas can translate into a level

public class Level {
    // Private static instance variable to hold the single instance
    private static Level instance;

    public ArrayList<ArrayList<Integer>> levelData;

    public ArrayList<ArrayList<Tile>> tileData;

    //These are part of the tile init for initial resources as the max
    //Higher scale will mean that the map will have more of the nutrient per block
    public static int manaScale = 3;
    public static int nutrientL2Distribution = 70;
    public static int nutrientL3Distribution = 95;

    public static int levelColumns;
    public static int levelRows;
    private LevelGenerator levelGenerator;

    // Private constructor prevents instantiation from other classes
    private Level(String levelFilePath) {
        //levelData = LevelLoader.getLevelData(levelFilePath);

        levelGenerator = new LevelGenerator(60, 90);
        levelData = levelGenerator.returnLevel();

        levelRows = levelData.size(); // Size of column
        levelColumns = levelData.get(0).size(); // Size of row
        System.out.println(levelColumns + " " + levelRows);
        if(levelColumns != ScreenSettings.TS_World_X) System.out.println("Not enough columns made");
        if(levelRows != ScreenSettings.TS_World_Y) System.out.println("Not enough rows made");
        createTileData();
    }

    // Public static method to provide access to the single instance
    //TODO override it so we don't always need a string
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
        Tile tile = new Tile(intToType(val), x, y);

        if ((tile.type == TileType.NUTRIENT)) {
            tile.depositNutrients();
        } else if(tile.type == TileType.MANA){
            tile.depositMana();
        }
        return tile;
    }

    //TODO reorganize this
    private TileType intToType(int val) {
        switch (val) {
            case 1:
                return TileType.NUTRIENT;
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
