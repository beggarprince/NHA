package main.java.Game.Scripts;

import main.java.Game.GameState;
import main.java.io.Audio.Sound;
import main.java.util.AudioConstants;

public class PlayerVictory {

    public static void run(){
        GameState.heroActive = false;
        setMusic();
        cycleVictoryDialogue();
        showUpgradeMenu();
    }

    private static void setMusic(){
        //set the appropriate music
        Sound.setMusic(AudioConstants.MUS_ROUND_VICTORY);
    }

    private static void cycleVictoryDialogue(){
        //mvp talking saying good job or something whilst the dungeon is paused
    }

    private static void showUpgradeMenu(){
        //Where player sees the upgrade menu after the round is over
    }
}
