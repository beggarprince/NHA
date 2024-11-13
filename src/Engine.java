import PlayerActions.Spawn;
import entities.Enemy.*;
import entities.Player;
import graphics.Camera;
import graphics.ScreenSettings;
import level.Level;
import io.KbInput;
import graphics.GameCanvas;
import util.Coordinate;

import static PlayerActions.Dig.dig;

public class Engine implements Runnable {
    //Instantiation
    //TODO Move the initialization in the engine constructor
    // Declare instance variables
    private Coordinate leftTop;
    private Camera camera;
    private KbInput kb;
    private Thread gameLifecycle;
    private Player player;
    private Level level;
    private EnemyFactory enemyFactory;
    private EnemyList enemyList;
    public GameCanvas gamePanel;

    // Constructor
    public Engine() {
        // Setup and initialize all instance variables
        this.leftTop = new Coordinate(0, 0);
        this.camera = new Camera(leftTop);
        this.kb = new KbInput();
        this.player = new Player();
        this.level = Level.getInstance("res/levelTest.csv");
        this.enemyFactory = new EnemyFactory();
        this.enemyList = new EnemyList();
        this.gamePanel = new GameCanvas(kb, player, level.tileData, camera, enemyList);
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



            //Update GUI information
            if (elapsedTime >= ScreenSettings.INTERVAL) {
               // System.out.println("New frame");
                frameRatePrevTime = frameRateCurrentTime;
                movePlayer(player, camera, kb);
                runEnemyBehavior();
                if (kb.dig) {
                    Spawn.spawnEnemyAtPlayer(enemyFactory,  level.tileData.get(player.playerTilePositionY).get(player.playerTilePositionX), enemyList);

                    dig(level.tileData.get(player.playerTilePositionY).get(player.playerTilePositionX));
                }
                //Update UI
                gamePanel.repaint();
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

        if (player.getXOffset() == 0 && (kb.leftPressed || kb.rightPressed))
            cameraMoved = camera.updateCameraPosition(kb);
        else if (player.getYOffset() == 0 && (kb.upPressed || kb.downPressed))
            cameraMoved = camera.updateCameraPosition(kb);
        player.playerPosUpdate(kb, cameraMoved);

    }

    private void runEnemyBehavior() {
        synchronized (enemyList) {
            for (Enemy e : enemyList.getEnemies()) {
                e.behavior();
            }
        }
    }


}
