package level;

import graphics.ScreenSettings;
import util.Coordinate;

import java.util.ArrayList;

//LevelCreate will take in the information gathered from level loader and translate it to
// a usable array or list that GameCanvas can translate into a level

//Maybe some logic in determining what to call, other than that this seems redundant
public class Level {
    public ArrayList<ArrayList<Integer>> levelData;
    public static int levelColumns;
    public static int levelRows;
    public Level(String levelFilePath){
        levelData = LevelLoader.getLevelData( levelFilePath);

        levelRows = levelData.size(); //Size of column
        levelColumns = levelData.get(0).size(); // Size of rowa
    }

    public void dig(int x, int y) {
        if(levelData.get(y).get(x) == 3) return;
        try {
            levelData.get(y).set(x, 3);  // Set to 3

        } catch (IndexOutOfBoundsException e) {
            System.err.println("Error: Position (" + x + ", " + y + ") is out of bounds.");
        } catch (NullPointerException e) {
            System.err.println("Error: levelData or a nested ArrayList is null.");
        }
        if(levelData.get(y).get(x) != 3){
            System.out.println("Dig did not change value");
        }
        else{
            System.out.println(x + " " +  y + " changed to " + levelData.get(y).get(x));
        }
    }

    private void print() {
        for (ArrayList<Integer> row : levelData) {
            System.out.println(row);  // Prints each row on a new line
        }
    }





}
