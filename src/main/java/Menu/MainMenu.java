package main.java.Menu;

import main.java.Game.NPCLogicKTKt;
import main.java.Main;
import main.java.entities.NPC.Heroes.HeroList;
import main.java.entities.NPC.Monsters.MonsterLogic.MonsterList;
import main.java.entities.NPC.Mvp;
import main.java.graphics.ScreenSettings;
import main.java.io.KBInputAccelerator;
import main.java.util.ImgLoader;
import main.java.util.StringRes;

import java.awt.image.BufferedImage;

public class MainMenu implements Runnable{
    //Assuming menu is 60% title 40% options in a tiny square where the user can really only cycle through 2-5 options via kb
    //Position for title
    final BufferedImage titleImage = ImgLoader.getImageResource(StringRes.MESSAGE_GAMEOVER); // Just a temp
    //  Divided by 2 is center, good for width but not height. .3 might work
    final int centerScreenX = (ScreenSettings.PX_SCREEN_WIDTH / 2) ;
    final int tileImagePosX = centerScreenX - (titleImage.getWidth() / 2);
    final int titleImagePosY = (ScreenSettings.PX_SCREEN_HEIGHT * 3 / 10) - (titleImage.getHeight() / 2);

    //Positions for buttons
    //Center of 40 then separated by an offset of, say, 5% screen space
    final BufferedImage option1 = ImgLoader.getImageResource(StringRes.MESSAGE_GAMEOVER);
    final BufferedImage option2 = ImgLoader.getImageResource(StringRes.MESSAGE_GAMEOVER);
    final BufferedImage option3 = ImgLoader.getImageResource(StringRes.MESSAGE_GAMEOVER);
    final int buttonHeightSpacing = ScreenSettings.PX_SCREEN_HEIGHT * 5 / 100;

    //Start it at 70 and make our way down
    final int menuOptionSelectPosY = ScreenSettings.PX_SCREEN_HEIGHT * 7 /10;
    final int option1Y = menuOptionSelectPosY;
    final int option2Y = option1Y + buttonHeightSpacing;
    final int option3Y = option2Y + buttonHeightSpacing;
    private Thread gameLifecycle;

    public MainMenu(){

    }

    public void startMenuLifecycle() {
        gameLifecycle = new Thread(this);
        gameLifecycle.start();
    }

    //KB handling

        @Override
        public void run() {

            long frameRatePrevTime = System.nanoTime();
            KBInputAccelerator kba = KBInputAccelerator.getInstance();

            while (gameLifecycle != null) {

                //Check if we can move frame forward
                long frameRateCurrentTime = System.nanoTime();

                ///Run one game cycle, 1 frame
                if (checkTimer(frameRateCurrentTime, frameRatePrevTime)) {
                     //Set new time
                    frameRatePrevTime = frameRateCurrentTime;
                    //Take player input


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

    private boolean checkTimer(long currentTime, long previousTime){
        return ((currentTime - previousTime) >= ScreenSettings.INTERVAL);
    }


}
