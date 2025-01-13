package io;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

//TODO make this a Singleton
public class KbInput implements KeyListener {
    public boolean upPressed, downPressed, leftPressed, rightPressed;
    public boolean debug = false;
    public boolean dig = false;
    @Override
    public void keyTyped(KeyEvent e) {

    }

    private boolean singleWASD(){
        return !upPressed && !downPressed && !leftPressed && !rightPressed;
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


        //TODO while this does work there is a bit of delay on changing, i believe it was like this originally
        if(code == KeyEvent.VK_W && singleWASD()){
                upPressed = true;
        }
        if(code == KeyEvent.VK_A && singleWASD()){
                leftPressed = true;
        }
        if(code == KeyEvent.VK_S && singleWASD()) {
              downPressed = true;
        }
        if(code == KeyEvent.VK_D && singleWASD()){
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
