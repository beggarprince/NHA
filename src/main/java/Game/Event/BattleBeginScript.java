package Game.Event;

import   Game.GameState;
import   Game.State;
import   io.Audio.Sound;
import   io.Audio.AudioConstants;

public class BattleBeginScript {
    //This file is small changes for when we change state where the hero is searching for the mvp

    public static void run(){
        Sound.setAndLoopMusic(AudioConstants.MUS_BATTLE_PHASE);
        GameState.gameState = State.GAMERUNNING;
    }

}
