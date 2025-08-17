package graphics;

import Game.Core;
import   Game.GameState;
import   Game.Event.HideMvp;
import   Game.State;

//This class is meant to change the camera and prevent player input
//Cinema ends on AWAITING_INPUT STATE to make chains of text the player must click through
//but does not handle setting up the input state, text boxes, or other logic
public class Cinema {
    private static final int cinematicLength = ScreenSettings.FPS * 2;
    private static int currentFrame = 0;
    public static boolean cinematicActive = false; //tbh i don't think i need this

    public static void startCinemaState(){
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
        //The cinema transition requires i get the text
        //Core.setRoundText();
        cinematicActive = false;
        GameState.setGameState(State.AWAITING_INPUT);
        HideMvp.run();
        currentFrame = 0;
    }
}
