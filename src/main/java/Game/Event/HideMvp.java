package main.java.Game.Event;

import main.java.Game.GameState;
import main.java.io.Audio.Sound;
import main.java.io.Audio.AudioConstants;

public class HideMvp {
    public static void run(){
        //We need to return because we don't want constant calls to set the music
        if(GameState.stateHidingMvp()){
            return;
        }
        GameState.setMvpHiding();
        Sound.setAndLoopMusic(AudioConstants.MUS_HIDE_MVP);
    }
}
