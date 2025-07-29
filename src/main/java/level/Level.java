package level;

import   graphics.ScreenSettings;
import   util.Coordinate;

import java.util.ArrayList;

// Level will take in the information gathered from   level loader and translate it to
// a usable array or list that GameCanvas can translate into a   level

public class Level {
    // Private static instance variable to hold the single instance
    private static Level instance;

    public ArrayList<ArrayList<Integer>> levelData;

    public ArrayList<ArrayList<Tile>> tileData;

    //These are part of the tile init for initial resources as the max
    //Higher scale will mean that the map will have less of the nutrient per block
    //Say l3 distribution for mana is 98, then out of we get a number 0-100 and only 98/99/100 assign it that   level
    //Goes to the highest   level, so if not 97 then l2 mana block. If below l1 requirement it's just a block
    public static int manaL2Distribution = 95;
    public static int manaL3Distribution = 100;
    public static int nutrientL2Distribution = 99;
    public static int nutrientL3Distribution = 101;

    public static Coordinate initialMVPCoordinate = new Coordinate(0,0);
    public static Coordinate entryPoint = new Coordinate(0,0);

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

    private Level(int nutrientDistribution, int manaDistribution) {

        WorldGenerator worldGenerator = new WorldGenerator(nutrientDistribution, manaDistribution);

        levelData = worldGenerator.returnLevel();


        levelRows = levelData.size(); // Size of column
        levelColumns = levelData.get(0).size(); // Size of row
        createInitialPath();


        if(levelColumns != ScreenSettings.TS_World_X) System.out.println("Not enough columns made");
        if(levelRows != ScreenSettings.TS_World_Y) System.out.println("Not enough rows made");
        createTileData();
    }


    // Public static method to provide access to the single instance

//    public static Level getInstance(String levelFilePath) {
//        if (instance == null) {
//            instance = new Level(levelFilePath);
//        }
//        return instance;
//    }


    public static Level getInstance(int nutrientDistribution, int manaDistribution) {
        if (instance == null) {
            instance = new Level(nutrientDistribution, manaDistribution);
        }
        return instance;
    }

    public static Level getInstance() {
        if (instance == null) {
            instance = new Level(0, 0);
        }
        return instance;
    }


    private void createInitialPath(){
        int y = 0;
        int x = Level.levelColumns /2;
        entryPoint.x = x;
        entryPoint.y = y;

        levelData.get(y).set(x,3); // leveldata goes from int to enum to tile smh
        levelData.get(y++).set(x,3);
        levelData.get(y++).set(x,3);
        levelData.get(y++).set(x,3);
        levelData.get(y++).set(x,3);
        levelData.get(y).set(x,3);
        levelData.get(y).set(x+1,3);

        initialMVPCoordinate.x = x+1;
        initialMVPCoordinate.y = y;
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
