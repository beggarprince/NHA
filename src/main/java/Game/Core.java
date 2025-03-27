package main.java.Game;

import main.java.Game.Scripts.MVPCaptured;
import main.java.Game.Scripts.PlayerVictory;
import main.java.entities.NPC.Heroes.HeroList;
import main.java.entities.NPC.Mvp;
import main.java.entities.Player;
import main.java.graphics.Camera;
import main.java.graphics.Cinema;
import main.java.io.keyboard.KBInputAccelerator;
import main.java.io.keyboard.KbInputInGame;
import main.java.util.TimerDebug;

public class Core extends CoreHelper{

    private long frameRatePrevTime;
    private int kbDebugInputDelayTimer = 60;
    private final int timeUntilHeroSpawn = 600 * 1; // Ten seconds for now, 60 after *
    private int heroSpawnCountdown = 0;
    long getFrameRatePrevTime;
    KBInputAccelerator keyboardInputAccelerator;

    private TimerDebug timerDebug;

    public Core(){
        frameRatePrevTime = System.nanoTime();
         keyboardInputAccelerator = KBInputAccelerator.getInstance();
    }

    public void coreGameLoop(Object uiLock, Object npcLogicLock, KbInputInGame kb, Player player, Camera camera){

        //Check if we can move frame forward
        long frameRateCurrentTime = System.nanoTime();

        ///Run one game cycle, 1 frame
        if (CoreHelper.checkTimer(frameRateCurrentTime, frameRatePrevTime)) {

            //Set new time
            frameRatePrevTime = frameRateCurrentTime;

            //timerDebug.start();

            if(!GameState.heroActive)heroSpawnCountdown = decrementHeroTimer(timeUntilHeroSpawn, heroSpawnCountdown, camera);

            else{
                if(HeroList.getInstance().getHeroes().isEmpty()){
                    //We won this round
                    PlayerVictory.run();
                    heroSpawnCountdown = 0;
                }
            }
            //System.out.println(GameState.gameState);

            //Cinematics atm just hero entry
            //Cinematic will run for X amount of frames being counted
            //Player input will not be taken into account until we finish the cinematic
            //Ui will still update and some logic tied to the cinematic will run
            if(GameState.gameState == State.CINEMATIC){
                Cinema.cinematicActive = true;
                Cinema.handler();

                synchronized (uiLock){
                    uiLock.notify();
                }
                return;
            }

            //Awaiting input press ENTER
            if(GameState.gameState == State.AWAITING_INPUT){
                if(kb.enterPressed){
                    GameState.INPUT_STATE--;
                    if(GameState.INPUT_STATE == 0){
                        GameState.gameState = State.GAMERUNNING;
                    }
                }
                synchronized (uiLock){
                    uiLock.notify();
                }
                return;
            }

            //Player camera movement
            playerMovement(keyboardInputAccelerator, kb, player, camera);

            //Check for hero spawn if the timer is set to equal the spawn timer
            //We still need to advance frames but i don't want the player to be able to do anything but pan
            //No npc logic either
            if(GameState.gameState == State.PAUSE){
                //run pause logic
                if(kb.pausedGame == false){
                    GameState.gameUnpaused();
                }
                synchronized (uiLock){
                    uiLock.notify();
                }
                return;
            }


            if(GameState.hidingMvp){
                //As soon as the player places the mvp the timer is set to 0 except it does not increment the spawnTimer until heroActive is false then we have concluded we won the round
                attemptToPlaceMVPAndCheckIfSuccessful(kb, player, timeUntilHeroSpawn, heroSpawnCountdown, camera);
                //this prevents the loop to run monster logic
                //gamePanel.paintFrame(monsterList.getMonsters(), heroList.getHeroes());
                synchronized (uiLock){
                    uiLock.notify();
                }
                return;
            }

            checkPlayerInputActiveStage(kb, player, kbDebugInputDelayTimer);
            synchronized (uiLock){
                uiLock.notify();
            }
            synchronized (npcLogicLock) {
                npcLogicLock.notify();
            }


            if(GameState.heroActive){
                Mvp.getInstance().runMVPLogic();

            }

            //  timerDebug.stopMicros();

        }
        //GUI won't need to update for a bit so we can stop checking gameLifecycle bc there is nothing to cycle
        else {
            try {
                Thread.sleep(2); // Sleep for 2 milliseconds
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return; // Exit loop if thread is interrupted
            }
        }

    }

}

