import Game.NPCLogic;
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
    private Thread gameLifecycle;
    private Player player;
    private Level level;
    private MonsterFactory monsterFactory;
    private final MonsterList monsterList;
    public GameCanvas gamePanel;
    private HeroFactory heroFactory;
    private final HeroList heroList;
    private final NPCLogic logic;
    Sound sound;

    // Constructor
    public Engine() {
        // Camera and screen setup
        this.leftTop = new Coordinate(0, 0);
        this.camera = new Camera(leftTop);

        //Player and player inputs
        this.kb = new KbInput();
        this.player = new Player();

        //Audio
        sound = new Sound();
        sound.setMusic(0);
        sound.loop();

        //World creation
        this.level = Level.getInstance("res/levelTest.csv");

        //Helper Classes
        logic = new NPCLogic();

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

            long frameRateCurrentTime = System.nanoTime();
            long elapsedTime = frameRateCurrentTime - frameRatePrevTime;

            ///Run one game cycle
            if (elapsedTime >= ScreenSettings.INTERVAL) {
                // System.out.println("New frame");

                frameRatePrevTime = frameRateCurrentTime;
                if (kb.playerMoving()) player.movePlayer(player, camera, kb);

                logic.run();

                if (kb.dig) {
                    Spawn.spawnEnemyAtPlayer(level.tileData
                                    .get(player.playerTilePositionY)
                                    .get(player.playerTilePositionX),
                            monsterList);

                    dig(level.tileData.get(player.playerTilePositionY)
                            .get(player.playerTilePositionX));
                } else if (kb.debug) {
                    heroList.addHero(heroFactory.createHero("knight",
                            player.playerTilePositionX,
                            player.playerTilePositionY));
                }

                //Update UI
                gamePanel.repaint();

                //Remove references to res so the garbage collector can remove
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

}
