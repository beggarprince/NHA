import Game.NPCLogicKTKt;
import Game.SpatialHash;
import PlayerActions.Spawn;
import entities.Heroes.HeroFactory;
import entities.Heroes.HeroList;
import entities.Monsters.*;
import entities.Player;
import graphics.Camera;
import graphics.ScreenSettings;
import io.Audio.Sound;
import level.Level;
import io.KbInput;
import graphics.GameCanvas;
import util.Coordinate;

import static PlayerActions.Dig.dig;

public class Engine implements Runnable {
    //Instantiation
    private final Coordinate leftTop;
    private final Camera camera;
    private final KbInput kb;
    private int kbInputDebugJankTimer = 60;
    private Thread gameLifecycle;
    private Player player;
    private Level level;
    private MonsterFactory monsterFactory;
    private final MonsterList monsterList;
    public GameCanvas gamePanel;
    private HeroFactory heroFactory;
    private final HeroList heroList;
    //private final NPCLogic logic;
    private final SpatialHash spatialHash;
    Sound sound;

    // Constructor
    public Engine() {

        //Audio
        sound = new Sound();
        sound.setMusic(0);

        //Annoying as fuck
        //sound.loop();

        //World creation
        this.level = Level.getInstance();
        this.spatialHash = SpatialHash.getInstance();


        //Player and player inputs
        this.kb = new KbInput();
        this.player = new Player();
        // Camera and screen setup
        //Level.levelColumns /2 - (ScreenSettings.TS_X /2)
        this.leftTop = new Coordinate(0, 0);
        this.camera = new Camera(leftTop);

        //TODO jank until i fix the center function, there was 1 instance where the camera did not move up and the player went out of bounds but i can't replicate it
        //We can replace level.levelColumns with prefered X position or something that we can pass in and then move
        while (player.playerTilePositionX < Level.levelColumns / 2) {
            kb.rightPressed = true;
            player.movePlayer(player, camera, kb);
            kb.rightPressed = false;
        }

        //Monster creation and spawning
        this.monsterFactory = new MonsterFactory();
        this.monsterList = MonsterList.getInstance();

        //Hero Creation and spawning

        this.heroFactory = HeroFactory.getInstance();
        this.heroList = HeroList.getInstance();

        //UI
        this.gamePanel = new GameCanvas(kb, player,
                level.tileData, camera, monsterList, heroList);
    }

    public void startGameThread() {
        gameLifecycle = new Thread(this);
        gameLifecycle.start();
    }


    @Override
    public void run() {

        long frameRatePrevTime = System.nanoTime();

        while (gameLifecycle != null) {

            //Check if we can move frame forward
            long frameRateCurrentTime = System.nanoTime();

            ///Run one game cycle
            if (checkTimer(frameRateCurrentTime, frameRatePrevTime)) {
                //Set new time
                frameRatePrevTime = frameRateCurrentTime;

                if (kb.playerMoving()) player.movePlayer(player, camera, kb);
                NPCLogicKTKt.run(monsterList.getMonsters(), heroList.getHeroes());

                if (kb.dig) {
                  attemptDig();
                }
                else if (kb.debug ) {
                  debugAddHero();
                }

                if (kbInputDebugJankTimer != 60) kbInputDebugJankTimer++;

                //Update UI
                gamePanel.repaint();

                //Remove references to res so the garbage collector can remove, must be done after UI update
                monsterList.destroyEnemies();
                heroList.destroyHeroes();

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

        }

    }


    private boolean checkTimer(long currentTime, long previousTime){
        return ((currentTime - previousTime) >= ScreenSettings.INTERVAL);
    }

    private void attemptDig(){
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
        }

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
}
