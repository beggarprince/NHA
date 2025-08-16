package Game;

//This is a static class, there can only ever be one gamestate so this information is used across
public class GameState {
    //Logically, what state is the game in rn
    public static State gameState = State.GAME_RUNNING;

    //These are just to signal throughout the system what is going on to avoid passing around a ton of booleans or having tons of canvas/audio calls everywhere
    private static boolean hidingMvp = false;
    public static boolean heroActive = false;
    public static int INPUT_STATE = 0;

    //TODO i should really fix some of this public stuff, but they are separate functions as to add more functionality later on when state changes

    public static void gameLoss(){
        gameState = State.GAME_OVER;
    }
    public static void gamePaused(){
        gameState = State.PAUSE;
    }

    public static void gameUnpaused(){
        gameState = State.GAME_RUNNING;
    }

    public static void resumeGame(){
        gameState = State.GAME_RUNNING;
    }

    public static void startMenu(){
        gameState = State.MENU;
    }

    public static void setMvpHiding(){
        hidingMvp = true;
    }

    public static void mvpSuccesfullyHidden(){
        hidingMvp = false;
    }

    public static boolean stateHidingMvp(){
        return hidingMvp;
    }
}
