package Game.Event;

import   level.Level;

public class InitializeLevel {
    private Level level;

    public InitializeLevel(){
    level = Level.getInstance();
    }

    public void initLevel(){
        //create initial path

        // set sound
    }

    private  void createInitialPath(){
//        int y = 0;
//        int x = Level.levelColumns /2;
//        Level.entryPoint.x = x;
//        Level.entryPoint.y = y;
//
//        Level.levelData.get(y).set(x,3); // leveldata goes from int to enum to tile smh
//        Level.levelData.get(y++).set(x,3);
//        Level.levelData.get(y++).set(x,3);
//        Level.levelData.get(y++).set(x,3);
//        Level.levelData.get(y++).set(x,3);
//        levelData.get(y).set(x,3);
//        levelData.get(y).set(x+1,3);
//
//        initialMVPCoordinate.x = x+1;
//        initialMVPCoordinate.y = y;
    }

}
