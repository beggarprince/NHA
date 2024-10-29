package level;

import java.util.ArrayList;

//LevelCreate will take in the information gathered from level loader and translate it to
// a usable array or list that GameCanvas can translate into a level

//Maybe some logic in determining what to call, other than that this seems redundant
public class LevelCreate {
    public ArrayList<ArrayList<Integer>> levelData;
    public static int levelx;
    public static int levely;
    public LevelCreate(){
        //y level.size()
        //x level.get(0).size()
        levelData = LevelLoader.getLevelData( "res/levelTest.csv");
        levely = levelData.size(); //Size of column
        levelx = levelData.get(0).size(); // Size of row
        System.out.println(levelx + " " + levely);
    }


}
