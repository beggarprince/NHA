package main.java.graphics;

import main.java.Game.GameState;
import main.java.Game.Scripts.HideMvp;
import main.java.Game.State;

public class Cinema {
    private static final int cinematicLength = ScreenSettings.FPS * 5;
    private static int currentFrame = 0;
    public static boolean cinematicActive = false; //tbh i don't think i need this

    public static void handler(){
        if(cinematicActive == false){
            System.out.println("There was an error handler ran despite not being active");
        }

        if(currentFrame <= cinematicLength){
            currentFrame++;
        }
        else{
            resetCinematic();
        }
    }

    private static void resetCinematic(){
        cinematicActive = false;
       // System.out.println("Cinematic is finisheddddddddddddddddddddddddddd dddddddddd");
        GameState.gameState = State.AWAITING_INPUT;
        HideMvp.run();
        currentFrame = 0;
    }
}
