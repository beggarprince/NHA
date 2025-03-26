package main.java;

import main.java.Game.Core;
import main.java.Game.Scripts.PlayerDefeat;
import main.java.entities.Combat;
import main.java.entities.NPC.Heroes.HeroFactory;
import main.java.entities.NPC.Heroes.HeroList;
import main.java.entities.NPC.Monsters.MonsterLogic.MonsterList;
import main.java.entities.NPC.Movement;
import main.java.entities.NPC.Mvp;
import main.java.entities.NPC.NPCLogicKTKt;
import main.java.entities.Player;
import main.java.graphics.Camera;
import main.java.io.Audio.Sound;
import main.java.level.Level;
import main.java.io.keyboard.KbInputInGame;
import main.java.graphics.GameCanvas;
import main.java.util.AudioConstants;
import main.java.util.Coordinate;

import static main.java.Game.CoreHelper.updateNPCLists;
import static main.java.io.Audio.Sound.getSoundInstance;


public class Engine implements Runnable {
    private final Camera camera;
    private final KbInputInGame kb;
    private Thread gameLifecycle;
    private Thread musicThread;
    private final Player player;
    private Level level; // Not set as final for now even though there is  only one main.java.level
    private final MonsterList monsterList;
    public GameCanvas gamePanel;
    private final HeroFactory heroFactory;
    private final HeroList heroList;
    private int xEntry;
    private Core core;
    //private final NPCLogic logic;
    //private final SpatialHash spatialHash;
    Sound sound;


    // Constructor
    public Engine() {
        core = new Core();
        //Audio
        sound = getSoundInstance();
        //World creation
        this.level = Level.getInstance();
        // this.spatialHash = SpatialHash.getInstance();

        Movement.setLevelInstance();
        Combat.setLevelInstance();

        //Player and player inputs
        this.kb = new KbInputInGame();
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

            player.movePlayer(player, camera, 2);
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

    public void startMainMusicThread() {
        Runnable musicRunnable = new Runnable() {
            @Override
            public void run() {
                sound.setAndLoopMusic(AudioConstants.MUS_DUNGEON_IDLE_2);

                while (musicThread != null) {

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


        Runnable npcLogicThread = () -> {
            NPCLogicKTKt.run(monsterList.getMonsters(), heroList.getHeroes());
            updateNPCLists();
        };

        //Update UI
        Runnable renderingThread = () -> {
            gamePanel.paintFrame(MonsterList.getInstance().getMonsters(), HeroList.getInstance().getHeroes());
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
                        if (Mvp.getInstance().mvpAtEntrance()) {
                            PlayerDefeat.run();

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
            core.coreGameLoop(uiLock ,npcLogicLock,  kb, player, camera);
        }//end of game loop

    }
}

