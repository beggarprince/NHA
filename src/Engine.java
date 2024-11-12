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
    Coordinate leftTop = new Coordinate(0, 0);
    Camera camera = new Camera(leftTop);
    KbInput kb = new KbInput();
    Thread gameLifecycle;
    Player player = new Player();
    Level level = Level.getInstance("res/levelTest.csv");
    EnemyFactory enemyFactory = new EnemyFactory();
    EnemyList enemyList = EnemyFactory.enemyList;
    GameCanvas gamePanel = new GameCanvas(kb, player, level.tileData, camera, enemyList);


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
                frameRatePrevTime = frameRateCurrentTime;
                movePlayer(player, camera, kb);
                runEnemyBehavior();
                if (kb.debug) {
                    enemyFactory.createEnemy("Slime", 0,0);
                }
                if (kb.dig) {
                    Spawn.spawnEnemyAtPlayer(enemyFactory,  level.tileData.get(player.playerTilePositionY).get(player.playerTilePositionX));
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
