package main.java;

public class GameState {
    public static State gameState = State.GAMERUNNING;

    //These are just to signal throughout the system what is going on to avoid passing around a ton of booleans or having tons of canvas/audio calls everywhere
    public static boolean mvpCaptured = false;
    public static boolean hidingMvp = false;
    public static boolean heroActive = false;

    //TODO i should really fix some of this public stuff, but they are separate functions as to add more functionality later on when state changes

    public static void gameLoss(){
        gameState = State.GAMEOVER;
    }
    public static void gamePaused(){
        gameState = State.PAUSE;
    }

    public static void gameUnpaused(){
        gameState = State.GAMERUNNING;
    }

    public static void resumeGame(){
        gameState = State.GAMERUNNING;
    }

    public static void startMenu(){
        gameState = State.MENU;
    }

    public static void currentlyHidingMvp(){
        hidingMvp = true;
    }
    public static void mvpSuccesfullyHidden(){
        hidingMvp = false;
    }
}
