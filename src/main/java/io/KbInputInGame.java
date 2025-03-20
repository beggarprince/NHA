package main.java.io;

import main.java.GameState;
import main.java.io.Audio.Sound;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

//TODO make this a Singleton
public class KbInputInGame implements KeyListener {
    public boolean upPressed, downPressed, leftPressed, rightPressed;
    public boolean debug = false;
    public boolean dig = false;
    public boolean spawnDebug = false;
    public boolean maxSpeed = false;
    private boolean volumeLock;
    public boolean pausedGame = false;
    public int currentDirection = 0;


    @Override
    public void keyTyped(KeyEvent e) {

    }

    private boolean singleWASD(){
        return !upPressed && !downPressed && !leftPressed && !rightPressed;
    }

    private void resetWASD(int movementType){
        currentDirection = movementType;
    }
    private void resetMovementType(){
        if(upPressed){
            currentDirection = 1;
        }
        else if(rightPressed){
            currentDirection = 2;
        }
        else if (downPressed){
            currentDirection = 3;
        }
        else if(leftPressed){
            currentDirection = 4;
        }

        else currentDirection = 0;
    }


    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();


        if(code == KeyEvent.VK_P){
            debug = true;
        }

        else if(code == KeyEvent.VK_F){
            dig = true;
        }

        else if(code == KeyEvent.VK_I){
            spawnDebug = true;
        }
        else if(code == KeyEvent.VK_C){
            maxSpeed = true;
        }

        else if(code == KeyEvent.VK_W){
            resetWASD(1);
                upPressed = true;
        }
        else if(code == KeyEvent.VK_A){
            resetWASD(4);
                leftPressed = true;
        }
        else if(code == KeyEvent.VK_S ) {
            resetWASD(3);
              downPressed = true;
        }

        else if(code == KeyEvent.VK_D ){
            resetWASD(2);
            rightPressed = true;
        }

        // i don't want this to SPIKE in volume because the user held
        else if (code == KeyEvent.VK_UP && !volumeLock){
            Sound.adjustMusicVolume(+5.0f);
            volumeLock = true;
        }
        else if (code == KeyEvent.VK_DOWN && !volumeLock){
            Sound.adjustMusicVolume(-5.0f);
            volumeLock = true;
        }
        else if (code == KeyEvent.VK_M){
            Sound.muteMusicToggle();
        }
        else if (code == KeyEvent.VK_Q){
            if(pausedGame == false){
                GameState.gamePaused();
            }
            else{
                GameState.gameUnpaused();
            }
            pausedGame = !pausedGame;
        }


    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

         if(code == KeyEvent.VK_W){
            upPressed = false;
            resetMovementType();
        }
        else if(code == KeyEvent.VK_A){
            leftPressed = false;
            resetMovementType();
        }
        else if(code == KeyEvent.VK_S ){
            downPressed = false;

            resetMovementType();
        }
        else if(code == KeyEvent.VK_D ){
            rightPressed = false;

            resetMovementType();

        }

        else if(code == KeyEvent.VK_P){
            debug = false;
        }
        else if(code == KeyEvent.VK_F){
            dig = false;
        }
        else if(code == KeyEvent.VK_I){
            spawnDebug = false;
        }
        else if(code == KeyEvent.VK_C){
            maxSpeed = false;
        }
        else if(code == KeyEvent.VK_DOWN){
            volumeLock = false;
        }
        else if(code == KeyEvent.VK_UP){
            volumeLock = false;
        }

    }

    public boolean conflictingHorizontalInput(){
        return leftPressed && rightPressed ;
    }
    public boolean conflictingVerticalInput(){
        return downPressed && upPressed;
    }

    public int returnMovementType(){
        return currentDirection;
    }

    public boolean kbCheckIfPlayerMoving(){
        return (currentDirection != 0);
    }
}
