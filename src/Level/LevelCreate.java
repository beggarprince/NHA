package Level;

import java.util.ArrayList;

//LevelCreate will take in the information gathered from level loader and translate it to
// a usable array or list that GameCanvas can translate into a level

//Maybe some logic in determining what to call, other than that this seems redundant
public class LevelCreate {
    public ArrayList<ArrayList<Integer>> levelData;

    public LevelCreate(){
        levelData = LevelLoader.getLevelData( "res/levelTest.csv");
    }


}
