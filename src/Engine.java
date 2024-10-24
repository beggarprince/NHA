import Entities.Player;
import Graphics.ScreenSettings;
import Level.LevelCreate;
import io.KBInput;
import Graphics.GameCanvas;

public class Engine implements Runnable{
    //Setup

    KBInput kb = new KBInput();
    Thread gameLifecycle;
    Player player = new Player();
    LevelCreate level = new LevelCreate();
    GameCanvas gamePanel = new GameCanvas(kb, player, level.levelData);


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
                player.playerPosUpdate(kb);
                delta--;
                //Update UI
                gamePanel.repaint();
            }


        }

    }


}
