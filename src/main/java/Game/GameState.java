package Game;

import io.Audio.AudioConstants;
import io.Audio.Sound;

//This is a static class, there can only ever be one gamestate so this information is used across
public class GameState {
    //Logically, what state is the game in rn
    //TODO make this private and handle conflicting state changes
    private static State gameState = State.GAME_RUNNING;
    public static boolean upgradeShopReady = false;
    private static State previousState;

    //These are just to signal throughout the system what is going on to avoid passing around a ton of booleans or having tons of canvas/audio calls everywhere
    private static boolean hidingMvp = false;
    public static boolean heroActive = false;
    private static int INPUT_STATE = 0;
    private static boolean BRIEFING_EVENT = true;
    private static String CURRENT_MESSAGE;


    //TODO i should really fix some of this public stuff, but they are separate functions as to add more functionality later on when state changes

    public static void gameLoss(){
        gameState = State.GAME_OVER;
    }

    public static State getGameState(){
        return gameState;
    }

    //TODO bring out some of the state change logic into the setGameState
    public static void setGameState(State state){
        System.out.println("State change "+ state);
        gameState = state;
        //change music

        switch (state) {
            case GAME_RUNNING:
                //Hero active means we are fighting
                if(hidingMvp){
                    Sound.setAndLoopMusic(AudioConstants.MUS_HIDE_MVP);
                }
                else if(heroActive){
                    Sound.setAndLoopMusic(AudioConstants.MUS_BATTLE_PHASE);
                }

                //We could get a random here from the idle music
                else Sound.setAndLoopMusic(AudioConstants.MUS_DUNGEON_IDLE_2);
                break;

            case MENU:

                break;

            case PAUSE:
                //Handled in core
                break;

            case GAME_OVER:
                Sound.resumeMusic();
                Sound.setMusic(AudioConstants.MUS_GAME_OVER);
                break;

            case AWAITING_INPUT:
                // Mus
                if(BRIEFING_EVENT) Sound.setMusic(AudioConstants.MUS_HERO_PREVIEW);
                break;

            case CINEMATIC:
                // This one is more generic and the music has to be handled in the script
                break;

            case POST_VICTORY_SPEECH:
                Sound.setMusic(AudioConstants.MUS_ROUND_VICTORY);
                break;
            case LEVEL_VICTORY:
                Sound.setMusic(AudioConstants.MUS_LEVEL_VICTORY);
                break;

            default:
                // Handle unexpected state
                break;
        }
    }

    public static void gamePaused(){
        previousState = gameState;
        gameState = State.PAUSE;
    }

    public static void gameUnpaused(){
        gameState = previousState;
    }


    public static void startMenu(){
        gameState = State.MENU;
    }

    public static void setMvpHiding(){
        hidingMvp = true;
    }

    public static void mvpSuccessfullyHidden(){
        hidingMvp = false;
        setGameState(State.GAME_RUNNING);
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
        if(INPUT_STATE == 0){
            System.out.println("Setting to run state");
            setGameState(State.GAME_RUNNING);
        }
        System.out.println(INPUT_STATE);
    }

    public static String getCurrentMessage() {
        return CURRENT_MESSAGE;
    }

    public static void setCurrentMessage(String currentMessage) {
        CURRENT_MESSAGE = currentMessage;
        //TODO I might want to move logic here to keep GameState changes in gamestate
    }

    public static boolean isBriefingEvent() {
        return BRIEFING_EVENT;
    }

    public static void setBriefingEvent(boolean briefingEvent) {
        BRIEFING_EVENT = briefingEvent;
    }
}
