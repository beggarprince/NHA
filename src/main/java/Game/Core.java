package Game;

import   Game.Event.BattleBeginScript;
import   Game.Event.HeroEntryScript;
import   Game.Event.PlayerVictory;
import   PlayerActions.Spawn;
import   entities.NPC.Heroes.HeroList;
import   entities.NPC.Monsters.MonsterLogic.MonsterList;
import   entities.NPC.Mvp;
import   entities.Player;
import   graphics.Camera;
import   graphics.Cinema;
import   graphics.ScreenSettings;
import   io.keyboard.KBInputAccelerator;
import   io.keyboard.KbInputInGame;
import level.Level;
import   level.TileType;
import   util.TimerDebug;

import java.util.ArrayList;

import static   PlayerActions.Dig.dig;

public class Core {

    private long frameRatePrevTime;
    private final int timeUntilHeroSpawn = 600 * 1; // Ten seconds for now, 60 after *
    private int heroSpawnCountdown = 0;
    private static int currentRound = 0;
    private static int finalRound;
    private static ArrayList<ArrayList<LevelState.HeroData>> currentLevelHeroes; //List of heroes separated by rounds
    KBInputAccelerator keyboardInputAccelerator;
    private static Level level;

    private TimerDebug timerDebug;

    public Core(ArrayList<ArrayList<LevelState.HeroData>> levelHeroes){
        level = Level.getInstance();
        frameRatePrevTime = System.nanoTime();
         keyboardInputAccelerator = KBInputAccelerator.getInstance();
         currentLevelHeroes = levelHeroes;
         finalRound = levelHeroes.size()-1;
         int count = 1;

        printHeroes(levelHeroes, count);

    }

    private static void printHeroes(ArrayList<ArrayList<LevelState.HeroData>> levelHeroes, int count) {
        for (ArrayList<LevelState.HeroData> heroList : levelHeroes) {

            System.out.println("Round " + count +" heroes:");

            StringBuilder formattedHeroString = new StringBuilder();
            for (LevelState.HeroData hero : heroList) {
                formattedHeroString.append(hero.type).append(" ").append(hero.heroName).append(" | ");
           //     System.out.println( hero.type + " " + hero.heroName);
            }
            System.out.println(formattedHeroString);
            count++;
        }
    }

    private boolean timeForNewFrame(){
        //Check if we can move frame forward
        long frameRateCurrentTime = System.nanoTime();
        if( checkTimer(frameRateCurrentTime, frameRatePrevTime)){
            //Set new time
            frameRatePrevTime = frameRateCurrentTime;
            return true;
        }
        return false;
    }

    public void coreGameLoop(Object uiLock, Object npcLogicLock, KbInputInGame kb, Player player, Camera camera){

        ///Run one game cycle, 1 frame
        if (timeForNewFrame()) {
            runGameLoop(npcLogicLock, kb, player, camera);
            updateUI(uiLock); //We always update ui
        }
        //GUI won't need to update for a bit so we can stop checking gameLifecycle bc there is nothing to cycle
        else {
            try {
                Thread.sleep(ScreenSettings.FPS_DELAY); // Sleep for 3 milliseconds
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return; // Exit loop if thread is interrupted
            }
        }

    }


    //TODO separate the state functionality in separate methods
    public void runGameLoop( Object npcLogicLock, KbInputInGame kb, Player player, Camera camera){


        //System.out.println(GameState.gameState);

        //Cinematics atm just hero entry
        //Cinematic will run for X amount of frames being counted
        //Player input will not be taken into account until we finish the cinematic
        //Ui will still update and some logic tied to the cinematic will run
        if(GameState.gameState == State.CINEMATIC){
            Cinema.cinematicActive = true;
            Cinema.handler();
            return;
        }


        //Awaiting input press ENTER
        if(GameState.gameState == State.AWAITING_INPUT){
            //TODO put some type of message for the player to know they need to press enter
            if(kb.enterPressed){
                GameState.INPUT_STATE--;
                if(GameState.INPUT_STATE == 0){
                    GameState.gameState = State.GAMERUNNING;
                }
            }
            return;
        }


        //Check for hero spawn if the timer is set to equal the spawn timer
        //We still need to advance frames but i don't want the player to be able to do anything but pan
        //No npc logic either
        if(GameState.gameState == State.PAUSE){
            //run pause logic
            if(!kb.pausedGame){
                GameState.gameUnpaused();
            }
            return;
        }

        //Player camera movement
        playerMovement(keyboardInputAccelerator, kb, player, camera);


        if(GameState.stateHidingMvp()){
            //As soon as the player places the mvp the timer is set to 0 except it does not increment the spawnTimer until heroActive is false then we have concluded we won the round
            attemptToPlaceMVPAndCheckIfSuccessful(kb, player, timeUntilHeroSpawn, heroSpawnCountdown, camera);
            //this prevents the loop to run monster logic
            //gamePanel.paintFrame(monsterList.getMonsters(), heroList.getHeroes());
            return;
        }

        checkPlayerInputActiveStage(kb, player, 60);

        synchronized (npcLogicLock) {
            npcLogicLock.notify();
        }


        if(GameState.heroActive){
            Mvp.getInstance().runMVPLogic();

        }

        if(!GameState.heroActive){
            heroSpawnCountdown = decrementHeroTimer(timeUntilHeroSpawn, heroSpawnCountdown, camera);
        }

        else{
            if(HeroList.getInstance().getHeroes().isEmpty()){
                //We won this round
                PlayerVictory.run();
                currentRound++;
                if(currentRound > finalRound) {
                    System.out.println("you have won");
                }
                heroSpawnCountdown = 0;
            }
        }

    }

    public void updateUI(Object uiLock){
        synchronized (uiLock) {
            uiLock.notify();
        }
    }


    protected static boolean checkTimer(long currentTime, long previousTime){
        return ((currentTime - previousTime) >= ScreenSettings.INTERVAL);
    }

    protected static boolean attemptDig(Player player){
        if (dig(level.tileData,
                level.tileData.get(player.playerTilePositionY)
                        .get(player.playerTilePositionX),
                player.playerTilePositionX,
                player.playerTilePositionY
        )) {
            Spawn.spawnEnemyAtPlayer(level.tileData
                            .get(player.playerTilePositionY)
                            .get(player.playerTilePositionX),
                    MonsterList.getInstance());
            level.tileData.get(player.playerTilePositionY)
                    .get(player.playerTilePositionX).digDestruct();
            return true;
        }
        return false;
    }

    protected static void debugAddHero(int kbInputDebugJankTimer, Player player){
        if( kbInputDebugJankTimer != 60){
            System.out.println("Debug timer on cooldown");
            return;
        }

        kbInputDebugJankTimer = 0;
//        HeroList.getInstance().addHero(HeroFactory.getInstance().createHero("knight",
//                player.playerTilePositionX,
//                player.playerTilePositionY));

    }

    //We should separate the spawning and the decrement
    protected static int decrementHeroTimer(int heroSpawnTimer, int heroSpawnCountdown, Camera camera){
        heroSpawnCountdown++;
//        try {
//            System.out.println("Hero spawn countdown: " + heroSpawnCountdown);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
        if(heroSpawnTimer <= heroSpawnCountdown){
            //TODO add rounds here
            System.out.println("We are at round number " + currentRound);

            GameState.heroActive = true;

            //HeroEntryScript.run("Soldier", HeroList.getInstance(), camera);
            HeroEntryScript.run(currentLevelHeroes.get(currentRound), HeroList.getInstance(), camera);

            //currentRound++;
            //GameState.setMvpHiding();
        }
        return heroSpawnCountdown;
    }




    protected static void checkPlayerInputActiveStage(KbInputInGame kb,
                                                      Player player,
                                                      int kbInputDebugJankTimer){
        if (kb.dig) {
            if(player.getDigPower() >0){
                if(attemptDig(player))player.digPowerDecrement();
            }
        }
        else if (kb.debug ) {
            debugAddHero(kbInputDebugJankTimer, player);
        }
        else if(kb.spawnDebug){
            Spawn.spawnEnemyAtPlayerDebug(MonsterList.getInstance(), level.tileData
                    .get(player.playerTilePositionY)
                    .get(player.playerTilePositionX));
        }

        if (kbInputDebugJankTimer != 60) kbInputDebugJankTimer++;
    }

    protected static void playerMovement(KBInputAccelerator kba, KbInputInGame kb, Player player, Camera camera){
        if (kb.kbCheckIfPlayerMoving() ) {
            if(kb.maxSpeed){
                player.movePlayer(player, camera,  kb.returnMovementType());
            }
            else {
                kba.accelerateInput();
                if (kba.readyToMovePlayer()) {
                    player.movePlayer(player, camera, kb.returnMovementType() );
                }
            }
        }else{
            kba.resetAcceleration();
        }
    }

    protected static void attemptToPlaceMVPAndCheckIfSuccessful(KbInputInGame kb, Player player, int heroSpawnTimer, int heroSpawnCountdown, Camera camera){

        if(kb.dig && level.tileData.get(player.playerTilePositionY).get(player.playerTilePositionX).type == TileType.PATH){ //I'm just going to rename this to ACTION button or something
            GameState.heroActive = true;
            heroSpawnCountdown = 0;
            Mvp.getInstance().setXY(player.playerTilePositionX, player.playerTilePositionY);

            //TODO change this to battle state or something
            GameState.mvpSuccesfullyHidden();
            if(!GameState.stateHidingMvp()){
                BattleBeginScript.run();
            }
        }
    }

    public static void updateNPCLists(){
        MonsterList.getInstance().destroyEnemies();
        HeroList.getInstance().destroyHeroes();
    }

    protected static void setMvpHiding(){
        if(GameState.heroActive ){

        }
    }

}

