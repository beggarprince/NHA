import entities.Player;
import graphics.Camera;
import graphics.ScreenSettings;
import level.LevelCreate;
import io.KBInput;
import graphics.GameCanvas;
import util.Coordinate;

public class Engine implements Runnable{
    //Setup
    Coordinate leftTop = new Coordinate(0,0);
    Camera camera = new Camera(leftTop);

    KBInput kb = new KBInput();
    Thread gameLifecycle;
    Player player = new Player();
    LevelCreate level = new LevelCreate();
    GameCanvas gamePanel = new GameCanvas(kb, player, level.levelData, camera);

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
                camera.posUpdate(player.pos);
                player.playerPosUpdate(kb);
                if(kb.debug) camera.printPlayerAndCameraInfo(player.pos);

                delta--;
                //Update UI
                gamePanel.repaint();
            }


        }

    }


}
