package main.java.level;

import main.java.graphics.ScreenSettings;

import java.util.ArrayList;

// Level will take in the information gathered from main.java.level loader and translate it to
// a usable array or list that GameCanvas can translate into a main.java.level

public class Level {
    // Private static instance variable to hold the single instance
    private static Level instance;

    public ArrayList<ArrayList<Integer>> levelData;

    public ArrayList<ArrayList<Tile>> tileData;

    //These are part of the tile init for initial resources as the max
    //Higher scale will mean that the map will have less of the nutrient per block
    //Say l3 distribution for mana is 98, then out of we get a number 0-100 and only 98/99/100 assign it that main.java.level
    //Goes to the highest main.java.level, so if not 97 then l2 mana block. If below l1 requirement it's just a block
    public static int manaL2Distribution = 90;
    public static int manaL3Distribution = 95;
    public static int nutrientL2Distribution = 70;
    public static int nutrientL3Distribution = 95;


    //Distribution is the balance out of 100.
    //Nutrient has to be less than mana
    //mana priority, if above mana distribution value it's a mana, if above nutrient then nutrient, otherwise empty tile

    public static int levelColumns;
    public static int levelRows;

    // Private constructor prevents instantiation from other classes
    private Level(String levelFilePath) {
        levelData = LevelLoader.getLevelData(levelFilePath);

        levelRows = levelData.size(); // Size of column
        levelColumns = levelData.get(0).size(); // Size of row

        if(levelColumns != ScreenSettings.TS_World_X) System.out.println("Not enough columns made");
        if(levelRows != ScreenSettings.TS_World_Y) System.out.println("Not enough rows made");
        createTileData();
    }

    private Level() {

        LevelGenerator levelGenerator = new LevelGenerator(99, 100);
        levelData = levelGenerator.returnLevel();


        levelRows = levelData.size(); // Size of column
        levelColumns = levelData.get(0).size(); // Size of row
        createInitialPath();


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

    public static Level getInstance() {
        if (instance == null) {
            instance = new Level();
        }
        return instance;
    }

    private void createInitialPath(){
        int y = 0;
        int x = Level.levelColumns /2;
        levelData.get(y).set(x,3); // leveldata goes from int to enum to tile smh
        levelData.get(y++).set(x,3);
        levelData.get(y++).set(x,3);
        levelData.get(y++).set(x,3);
        levelData.get(y++).set(x,3);
        levelData.get(y).set(x,3);
        levelData.get(y).set(x+1,3);

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
