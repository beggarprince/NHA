package io;

import entities.Direction;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

//TODO make this a Singleton
public class KbInput implements KeyListener {
    public boolean upPressed, downPressed, leftPressed, rightPressed;
    public boolean debug = false;
    public boolean dig = false;
    public boolean spawnDebug = false;


    @Override
    public void keyTyped(KeyEvent e) {

    }

    private boolean singleWASD(){
        return !upPressed && !downPressed && !leftPressed && !rightPressed;
    }

    private void resetWASD(){
        upPressed = false;
        downPressed = false;
        leftPressed = false;
        rightPressed = false;
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


        //TODO while this does work there is a bit of delay on changing, i believe it was like this originally
        if(code == KeyEvent.VK_W){
                resetWASD();
                upPressed = true;
        }
        else if(code == KeyEvent.VK_A){
            resetWASD();
                leftPressed = true;
        }
        else if(code == KeyEvent.VK_S ) {
            resetWASD();
              downPressed = true;
        }

        else if(code == KeyEvent.VK_D ){
            resetWASD();
            rightPressed = true;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if(code == KeyEvent.VK_W){
            upPressed = false;
        }
        if(code == KeyEvent.VK_A){
            leftPressed = false;

        }
        if(code == KeyEvent.VK_S ){
            downPressed = false;

        }
        if(code == KeyEvent.VK_D ){
            rightPressed = false;

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

    }

    public boolean conflictingHorizontalInput(){
        return leftPressed && rightPressed ;
    }
    public boolean conflictingVerticalInput(){
        return downPressed && upPressed;
    }

    public boolean playerMoving(){
        return (leftPressed || rightPressed || upPressed || downPressed);
    }
}
