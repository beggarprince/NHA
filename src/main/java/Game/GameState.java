package Game;

//This is a static class, there can only ever be one gamestate so this information is used across
public class GameState {
    //Logically, what state is the game in rn
    //TODO make this private and handle conflicting state changes
    public static State gameState = State.GAME_RUNNING;

    //These are just to signal throughout the system what is going on to avoid passing around a ton of booleans or having tons of canvas/audio calls everywhere
    private static boolean hidingMvp = false;
    public static boolean heroActive = false;
    private static int INPUT_STATE = 0;
    private static String CURRENT_MESSAGE;


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

    public static void setGameInputState(int INPUTS_EXPECTED){
        INPUT_STATE = INPUTS_EXPECTED;
    }

    public static int getInputState() {
        return INPUT_STATE;
    }

    public static void decrementInputState(){
        --INPUT_STATE;
    }

    public static String getCurrentMessage() {
        return CURRENT_MESSAGE;
    }

    public static void setCurrentMessage(String currentMessage) {
        CURRENT_MESSAGE = currentMessage;
        //TODO I might want to move logic here to keep GameState changes in gamestate
    }
}
