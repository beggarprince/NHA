package io;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

//TODO make this a Singleton
public class KbInput implements KeyListener {
    public boolean upPressed, downPressed, leftPressed, rightPressed;
    public boolean debug = false;
    public boolean dig = false;
    public boolean spawnDebug = false;
    public boolean maxSpeed = false;

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

        if(code == KeyEvent.VK_F){
            dig = true;
        }

        if(code == KeyEvent.VK_I){
            spawnDebug = true;
        }
        if(code == KeyEvent.VK_C){
            maxSpeed = true;
        }


        //TODO while this does work there is a bit of delay on changing, i believe it was like this originally
        if(code == KeyEvent.VK_W){
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

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if(code == KeyEvent.VK_W){
            upPressed = false;
            resetMovementType();
        }
        if(code == KeyEvent.VK_A){
            leftPressed = false;
            resetMovementType();
        }
        if(code == KeyEvent.VK_S ){
            downPressed = false;

            resetMovementType();
        }
        if(code == KeyEvent.VK_D ){
            rightPressed = false;

            resetMovementType();

        }

        if(code == KeyEvent.VK_P){
            debug = false;
        }
        if(code == KeyEvent.VK_F){
            dig = false;
        }
        if(code == KeyEvent.VK_I){
            spawnDebug = false;
        }
        if(code == KeyEvent.VK_C){
            maxSpeed = false;
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
