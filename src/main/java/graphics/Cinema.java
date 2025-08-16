package graphics;

import   Game.GameState;
import   Game.Event.HideMvp;
import   Game.State;

public class Cinema {
    private static final int cinematicLength = ScreenSettings.FPS * 5;
    private static int currentFrame = 0;
    public static boolean cinematicActive = false; //tbh i don't think i need this

    public static void handler(){
        if(!cinematicActive){
            System.out.println("There was an error handler ran despite not being active");
        }

        if(currentFrame <= cinematicLength){
            currentFrame++;
            System.out.println("Cinema frame: " + currentFrame);
        }
        else{
            resetCinematic();
        }
    }

    private static void resetCinematic(){
        cinematicActive = false;
        GameState.gameState = State.AWAITING_INPUT;
        HideMvp.run();
        currentFrame = 0;
    }
}
