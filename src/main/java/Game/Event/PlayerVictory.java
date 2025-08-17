package Game.Event;

import   Game.GameState;
import Game.State;
import entities.Player;
import   io.Audio.Sound;
import   io.Audio.AudioConstants;

public class PlayerVictory {

    public static void run(){
        GameState.heroActive = false;
        setMusic();
        cycleVictoryDialogue();
        //showUpgradeMenu();
        GameState.setGameState(State.POST_VICTORY_SPEECH);
    }

    private static void setMusic(){
        //set the appropriate music
        Sound.setMusic(AudioConstants.MUS_ROUND_VICTORY);
        Player.increaseDigPower(125);
    }

    private static void cycleVictoryDialogue(){
        //mvp talking saying good job or something whilst the dungeon is paused
    }

    private static void showUpgradeMenu(){
        //Where player sees the upgrade menu after the round is over
    }
}
