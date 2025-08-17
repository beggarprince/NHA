package Game.Event;

import   Game.GameState;
import   Game.State;
import   io.Audio.Sound;
import   io.Audio.AudioConstants;

public class PlayerDefeat {

    public static void run(){
        setDefeatMusic();
        playDefeatAnimation();
    }

    //Animation of the player getting dragged out of the dungeon + stats or something

    private static void setDefeatMusic(){
        if(GameState.getGameState() == State.GAME_OVER) return; // i only want this to run once

        GameState.setGameState( State.GAME_OVER);
        //change music
    }

    private static void playDefeatAnimation(){
        //where the player is dragged out of the dungeon or sm
    }
}
