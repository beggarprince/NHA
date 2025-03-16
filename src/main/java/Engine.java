package main.java;

import main.java.Game.NPCLogicKTKt;
import main.java.Game.SpatialHash;
import main.java.PlayerActions.Spawn;
import main.java.entities.Combat;
import main.java.entities.NPC.Heroes.HeroFactory;
import main.java.entities.NPC.Heroes.HeroList;
import main.java.entities.NPC.Monsters.MonsterLogic.MonsterList;
import main.java.entities.NPC.Movement;
import main.java.entities.NPC.Mvp;
import main.java.entities.Player;
import main.java.graphics.Camera;
import main.java.graphics.ScreenSettings;
import main.java.io.Audio.Sound;
import main.java.io.KBInputAccelerator;
import main.java.level.Level;
import main.java.io.KbInput;
import main.java.graphics.GameCanvas;
import main.java.level.TileType;
import main.java.util.Coordinate;
import main.java.util.TimerDebug;

import static main.java.PlayerActions.Dig.dig;
import static main.java.io.Audio.Sound.getSoundInstance;

public class Engine implements Runnable {
    private final Camera camera;
    private final KbInput kb;
    private int kbInputDebugJankTimer = 60;
    private Thread gameLifecycle;
    private Thread musicThread;
    private final Player player;
    private Level level; // Not set as final for now even though there is  only one main.java.level
    private final MonsterList monsterList;
    public GameCanvas gamePanel;
    private final HeroFactory heroFactory;
    private final HeroList heroList;
    //private final NPCLogic logic;
    private final SpatialHash spatialHash;
    Sound sound;
    private boolean heroActive = false;
    private final int heroSpawnTimer = 600 * 1; // Ten seconds for now, 60 after *
    private int heroFrameCount = 0;
    private boolean mvpPlaced = false;
    private int xEntry;
    private TimerDebug timerDebug;
    private boolean gameLoss = false;

    // Constructor
    public Engine() {

        //Audio
        sound =  getSoundInstance();

        //World creation
        this.level = Level.getInstance();
        this.spatialHash = SpatialHash.getInstance();

        Movement.setLevelInstance();
        Combat.setLevelInstance();

        //Player and player inputs
        this.kb = new KbInput();
        this.player = new Player();
        player.setDigPower(250);
        xEntry = player.playerTilePositionX;
        // Camera and screen setup
        //Level.levelColumns /2 - (ScreenSettings.TS_X /2)
        //Instantiation
        Coordinate leftTop = new Coordinate(0, 0);
        this.camera = new Camera(leftTop);

        //TODO jank until i fix the center function, there was 1 instance where the camera did not move up and the player went out of bounds but i can't replicate it
        //We can replace main.java.level.levelColumns with preferred X position or something that we can pass in and then move
        while (player.playerTilePositionX < Level.levelColumns / 2) {
            kb.rightPressed = true;

            player.movePlayer(player, camera,  2);
            kb.rightPressed = false;
            xEntry++;
        }

        //Monster creation and spawning
        this.monsterList = MonsterList.getInstance();

        //Hero Creation and spawning

        this.heroFactory = HeroFactory.getInstance();
        this.heroList = HeroList.getInstance();

        //UI
        this.gamePanel = new GameCanvas(kb, player,
                level.tileData, camera, heroList.getHeroes(), monsterList.getMonsters());
    }

    public void startGameThread() {
        gameLifecycle = new Thread(this);
        gameLifecycle.start();
    }
    public void startMainMusicThread(){
        Runnable musicRunnable = new Runnable() {
            @Override
            public void run() {
                sound.setMusic(0);
                //sound.loop();
                while(musicThread != null){

                }
            }
        };
        musicThread = new Thread(musicRunnable);
        musicThread.start();

    }

    @Override
    public void run() {

         Object uiLock = new Object();
         Object npcLogicLock = new Object();

        long frameRatePrevTime = System.nanoTime();
        KBInputAccelerator kba = KBInputAccelerator.getInstance();

        Runnable npcLogicThread = ()->{
                    NPCLogicKTKt.run(monsterList.getMonsters(), heroList.getHeroes());
                    updateNPCLists();
             };

        //Update UI
        Runnable renderingThread = () -> {
            gamePanel.paintFrame(MonsterList.getInstance().getMonsters(), HeroList.getInstance().getHeroes(), (!mvpPlaced && heroSpawnTimer()), gameLoss );
            // else System.out.println("Can't paint UI, awaiting new frame");
        };

        Thread uiWorker = new Thread(() -> {
            while (gameLifecycle != null) {
                try {
                    synchronized (uiLock) {
                        uiLock.wait(); // Wait for main thread to signal

                        renderingThread.run(); // Execute task when signaled
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });

        Thread npcLogicWorker = new Thread(() -> {
            while (gameLifecycle != null) {
                try {
                    synchronized (npcLogicLock) {
                        npcLogicLock.wait(); // Wait for main thread to signal
                        npcLogicThread.run(); // Execute task when signaled
                        if(Mvp.getInstance().mvpAtEntrance()){
                            gameLoss = true;
                        }
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });

        npcLogicWorker.start();
        uiWorker.start();

        while (gameLifecycle != null) {

            //Check if we can move frame forward
            long frameRateCurrentTime = System.nanoTime();

            ///Run one game cycle, 1 frame
            if (checkTimer(frameRateCurrentTime, frameRatePrevTime)) {

                //Set new time
                frameRatePrevTime = frameRateCurrentTime;

                //timerDebug.start();

                //Player camera movement
                playerMovement(kba);

                //Check for hero spawn if the timer is set to equal the spawn timer
                    //We still need to advance frames but i don't want the player to be able to do anything but pan
                    //No npc logic either
                if(!mvpPlaced && heroSpawnTimer()){
                    //As soon as the player places the mvp the timer is set to 0 except it does not increment the spawnTimer until heroActive is false then we have concluded we won the round
                    mvpPlaced = placeMVP();
                    //this prevents the loop to run monster logic
                    //gamePanel.paintFrame(monsterList.getMonsters(), heroList.getHeroes());
                    synchronized (uiLock){
                        uiLock.notify();
                    }
                    continue;
                }

                checkPlayerInputActiveStage();
                synchronized (uiLock){
                    uiLock.notify();
                }
                synchronized (npcLogicLock) {
                    npcLogicLock.notify();
                }

                //Remove references to res so the garbage collector can remove, must be done after UI update
                //Deferred so we can have access the same list without worrying about null in multiple threads
                //It does have minor effects, things going into negative hp zones and acting or not being able to move to a newly made PATH for 1 frame


                if(!heroActive)heroFrameCount++;
                if(heroActive){
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
                    break; // Exit loop if thread is interrupted
                }
            }

//            try{
//                System.out.println("Joining threads");
//                renderingThread.join();
//                npcLogicThread.join();
//            }catch (InterruptedException e){
//                Thread.currentThread().interrupt();
//            }

        }

    }


    private boolean checkTimer(long currentTime, long previousTime){
        return ((currentTime - previousTime) >= ScreenSettings.INTERVAL);
    }

    private boolean attemptDig(){
        if (dig(level.tileData,
                level.tileData.get(player.playerTilePositionY)
                        .get(player.playerTilePositionX),
                player.playerTilePositionX,
                player.playerTilePositionY
        )) {
            Spawn.spawnEnemyAtPlayer(level.tileData
                            .get(player.playerTilePositionY)
                            .get(player.playerTilePositionX),
                    monsterList);
            level.tileData.get(player.playerTilePositionY)
                    .get(player.playerTilePositionX).digDestruct();
            return true;
        }
        return false;
    }

    private void debugAddHero(){
        if( kbInputDebugJankTimer != 60){
            System.out.println("Debug timer on cooldown");
            return;
        }

        kbInputDebugJankTimer = 0;
        heroList.addHero(heroFactory.createHero("knight",
                player.playerTilePositionX,
                player.playerTilePositionY));

    }

    private boolean heroSpawnTimer(){
        if(heroSpawnTimer <= heroFrameCount){
            heroActive = true;
            return true;
        }
        return false;
    }

    private void spawnHeroAtEntry(){
        try {
            heroList.addHero(heroFactory.createHero("knight",
                    xEntry,
                    0));
        }catch (Exception e){
            System.out.println("Could not spawn hero");
        }
    }

    private void checkPlayerInputActiveStage(){
        if (kb.dig) {
            if(player.getDigPower() >0){
                if(attemptDig())player.digPowerDecrement();
            }
        }
        else if (kb.debug ) {
            //Sound.getSoundInstance().playFXClip(1);
            debugAddHero();
        }
        else if(kb.spawnDebug){
            Spawn.spawnEnemyAtPlayerDebug(monsterList, level.tileData
                    .get(player.playerTilePositionY)
                    .get(player.playerTilePositionX));
        }

        if (kbInputDebugJankTimer != 60) kbInputDebugJankTimer++;
    }

    private void playerMovement(KBInputAccelerator kba){
        if (kb.kbCheckIfPlayerMoving() ) {
            if(kb.maxSpeed){
                player.movePlayer(player, camera,  kb.returnMovementType());
            }
            else {
                kba.accelerateInput();
                // System.out.println(kba.getState());
                if (kba.readyToMovePlayer()) {
                    player.movePlayer(player, camera, kb.returnMovementType() );
                }
            }
        }else{
            kba.resetAcceleration();
        }
    }

    private boolean placeMVP(){

        if(kb.dig && level.tileData.get(player.playerTilePositionY).get(player.playerTilePositionX).type == TileType.PATH){ //I'm just going to rename this to ACTION button or something
            mvpPlaced = true;
            heroActive = true;
            heroFrameCount = 0;
            Mvp.getInstance().setXY(player.playerTilePositionX, player.playerTilePositionY);
            spawnHeroAtEntry();
            return true;
        }
        return false;
    }

    private void updateNPCLists(){
        monsterList.destroyEnemies();
        heroList.destroyHeroes();
    }
}

