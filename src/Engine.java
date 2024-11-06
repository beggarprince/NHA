import entities.Enemy.*;
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
    EnemyFactory enemyFactory = new EnemyFactory();
    EnemyList enemyList = EnemyFactory.enemyList;

    GameCanvas gamePanel = new GameCanvas(kb, player, level.levelData, camera, enemyList);


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

                movePlayer(player, camera, kb);

                if(kb.debug) {
                    enemyFactory.createEnemy("Slime", new Coordinate(0, 0 ));
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

    private void movePlayer(Player player, Camera camera, kbInput kb){
        boolean cameraMoved = false;
        if(player.getXOffset() == 0 && (kb.leftPressed || kb.rightPressed))cameraMoved = camera.updateCameraPosition(kb);
        else if (player.getYOffset() == 0 && (kb.upPressed || kb.downPressed)) cameraMoved = camera.updateCameraPosition(kb);
        player.playerPosUpdate(kb, cameraMoved);

    }



}
