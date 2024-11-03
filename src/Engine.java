import entities.Enemy.EnemyFactory;
import entities.Player;
import graphics.Camera;
import graphics.ScreenSettings;
import level.LevelCreate;
import io.kbInput;
import graphics.GameCanvas;
import util.Coordinate;

import javax.swing.*;
import java.util.logging.Level;

public class Engine  implements Runnable{
    //Setup
    Coordinate leftTop = new Coordinate(0,0);
    Camera camera = new Camera(leftTop);

    kbInput kb = new kbInput();
    Thread gameLifecycle;
    Player player = new Player();
    LevelCreate level = new LevelCreate("res/levelTest.csv");
    GameCanvas gamePanel = new GameCanvas(kb, player, level.levelData, camera);
    EnemyFactory enemyFactory = new EnemyFactory();

    public void startGameThread(){

        gameLifecycle = new Thread(this);

        gameLifecycle.start();

    }




    @Override
    public void run() {
        double frameRateDelta = 0 ;
        long frameRatePrevTime = System.nanoTime();
        long frameRateCurrentTime;

        while(gameLifecycle != null){

            frameRateCurrentTime = System.nanoTime();
            frameRateDelta += (frameRateCurrentTime - frameRatePrevTime) / ScreenSettings.INTERVAL;
            frameRatePrevTime = frameRateCurrentTime;

            //Update GUI information
            if(frameRateDelta >=1) {
                boolean cameraPanned;
                cameraPanned = camera.updateCameraPosition(kb);
                if(!cameraPanned)player.playerPosUpdate(kb);

                if(kb.debug) {
                    player.playerHurt();
                    enemyFactory.createEnemy("Slime", new Coordinate(64, 64 ));
                }
                frameRateDelta--;

                //Update UI
                gamePanel.repaint();
            }


        }

    }


}
