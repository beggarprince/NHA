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

public class Engine implements Runnable{
    //Setup
    Coordinate leftTop = new Coordinate(0,0);
    Camera camera = new Camera(leftTop);

    kbInput kb = new kbInput();
    Thread gameLifecycle;
    Player player = new Player();
    LevelCreate level = new LevelCreate("res/levelTest.csv");
    GameCanvas gamePanel = new GameCanvas(kb, player, level.levelData, camera);
    EnemyFactory enemyFactory = new EnemyFactory();

    //Ideally this should be fed into the engine when creating so i can alternate starting pos, but fuck it

    public void startGameThread(){

        gameLifecycle = new Thread(this);
        gameLifecycle.start();

    }




    @Override
    public void run() {
        double delta = 0 ; //The change in time must be at least 1 second for it to update
        long prevTime = System.nanoTime();
        long currTime;

        while(gameLifecycle != null){

            currTime = System.nanoTime();
            delta += (currTime - prevTime) / ScreenSettings.INTERVAL;
            prevTime = currTime;

            //Update game information
            if(delta >=1) {
                boolean cameraPanned;
                cameraPanned = camera.updateCameraPosition(kb);
                if(!cameraPanned)player.playerPosUpdate(kb);
                if(kb.debug) {
                    player.playerHurt();
                    enemyFactory.createEnemy("Slime", new Coordinate(64, 64 ));
                }
                delta--;
                //Update UI
                gamePanel.repaint();
            }


        }

    }


}
