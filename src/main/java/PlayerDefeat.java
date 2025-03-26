package main.java;

import main.java.io.Audio.Sound;
import main.java.util.AudioConstants;

public class PlayerDefeat {

    public static void run(){
        setDefeatMusic();
        playDefeatAnimation();
    }

    //Animation of the player getting dragged out of the dungeon + stats or something

    private static void setDefeatMusic(){
        //change music
        Sound.setMusic(AudioConstants.MUS_GAME_OVER);
    }

    private static void playDefeatAnimation(){
        //where the player is dragged out of the dungeon or sm
    }
}
