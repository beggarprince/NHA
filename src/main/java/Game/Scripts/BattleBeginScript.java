package main.java.Game.Scripts;

import main.java.Game.GameState;
import main.java.Game.State;
import main.java.io.Audio.Sound;
import main.java.io.Audio.AudioConstants;

public class BattleBeginScript {
    //This file is small changes for when we change state where the hero is searching for the mvp

    public static void run(){
        Sound.setAndLoopMusic(AudioConstants.MUS_BATTLE_PHASE);
        GameState.gameState = State.GAMERUNNING;
    }

}
