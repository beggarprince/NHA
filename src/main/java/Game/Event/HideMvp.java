package Game.Event;

import   Game.GameState;
import   io.Audio.Sound;
import   io.Audio.AudioConstants;

public class HideMvp {
    public static void run(){
        //We need to return because we don't want constant calls to set the music
        if(GameState.stateHidingMvp()){
            return;
        }
        GameState.setMvpHiding();
        //Sound.setAndLoopMusic(AudioConstants.MUS_HIDE_MVP);
    }
}
