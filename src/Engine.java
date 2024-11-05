import entities.Enemy.EnemyFactory;
import entities.Player;
import graphics.Camera;
import graphics.ScreenSettings;
import level.Level;
import io.kbInput;
import graphics.GameCanvas;
import util.Coordinate;

public class Engine  implements Runnable{
    //Setup
    Coordinate leftTop = new Coordinate(0,0);
    Camera camera = new Camera(leftTop);

    kbInput kb = new kbInput();
    Thread gameLifecycle;
    Player player = new Player();
    Level level = new Level("res/levelTest.csv");

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
                boolean cameraPanned = false;
                cameraPanned = camera.updateCameraPosition(kb);
                player.playerPosUpdate(kb, cameraPanned);

                if(kb.debug) {
                    player.playerHurt();
                    enemyFactory.createEnemy("Slime", new Coordinate(64, 64 ));
                }
                if(kb.dig){
                    //get cursor pos then change that type to gravel aka type 3
                    level.dig(player.playerXPos, player.playerYPos);
                }

                frameRateDelta--;

                //Update UI
                gamePanel.repaint();
            }


        }

    }


}
