import PlayerActions.Spawn;
import entities.Heroes.Hero;
import entities.Heroes.HeroFactory;
import entities.Heroes.HeroList;
import entities.Monsters.*;
import entities.Player;
import graphics.Camera;
import graphics.ScreenSettings;
import level.Level;
import io.KbInput;
import graphics.GameCanvas;
import util.Coordinate;

import java.util.List;

import static PlayerActions.Dig.dig;
import static entities.Monsters.Metamorphosis.metamorphosis;

public class Engine implements Runnable {
    //Instantiation
    //TODO Move the initialization in the engine constructor
    // Declare instance variables
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
    private final HeroList  heroList;
    Thread enemyThread;

    // Constructor
    public Engine() {
        // Camera and screen setup
        this.leftTop = new Coordinate(0, 0);
        this.camera = new Camera(leftTop);

        //Player and player inputs
        this.kb = new KbInput();
        this.player = new Player();

        //World creation
        this.level = Level.getInstance("res/levelTest.csv");

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
                    if(kb.playerMoving())movePlayer(player, camera, kb);

                    runEnemyBehavior();
                    runHeroBehavior();
                    checkSetMetamorphosis();

                    if (kb.dig) {
                        Spawn.spawnEnemyAtPlayer( level.tileData
                                .get(player.playerTilePositionY)
                                .get(player.playerTilePositionX),
                                monsterList);

                        dig(level.tileData.get(player.playerTilePositionY)
                                .get(player.playerTilePositionX));
                    }

                    else if(kb.debug){
                        heroList.addHero(heroFactory.createHero("knight",
                                player.playerTilePositionX,
                                player.playerTilePositionY));
                    }

                    //Update UI
                    gamePanel.repaint();

                    monsterList.destroyEnemies();

            }
            //GUI won't need to update for a bit so we can stop checking gameLifecycle bc there is nothing to cycle
            else{
                try {
                    Thread.sleep(2); // Sleep for 2 milliseconds
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break; // Exit loop if thread is interrupted
                }
            }

        }

    }

    private void movePlayer(Player player, Camera camera, KbInput kb) {
        boolean cameraMoved = false;

        if(kb.conflictingVerticalInput() || kb.conflictingHorizontalInput()) return;

        if (player.getXOffset() == 0 && (kb.leftPressed
                || kb.rightPressed))
            cameraMoved = camera.updateCameraPosition(kb);
        else if (player.getYOffset() == 0 && (kb.upPressed
                || kb.downPressed))
            cameraMoved = camera.updateCameraPosition(kb);
        player.playerPosUpdate(kb, cameraMoved);

    }

    private void runEnemyBehavior() {
        synchronized (monsterList) {
            List<Monster> enemies = monsterList.getMonsters();
            for (int i = 0; i < enemies.size(); i++) {
                Monster e = enemies.get(i);
                e.behavior();
            }
        }
    }

    private void runHeroBehavior() {
        synchronized (heroList) {
            List<Hero> heroes = heroList.getHeroes();
            for (int i = 0; i < heroes.size(); i++) {
                heroes.get(i).behavior();
            }
        }
    }


    public void checkSetMetamorphosis(){

        for(int i = 0; i < MonsterList.getInstance().getMonsters().size(); i++){
            Monster e = MonsterList.getInstance().getMonsters().get(i);
            if(e.metamorphosisReady){
               metamorphosis(i, e.metamorphosisValue, e.screenPositionX, e.screenPositionY);

            }
        }

    }

}
