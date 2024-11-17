package io;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KbInput implements KeyListener {
    public boolean upPressed, downPressed, leftPressed, rightPressed;
    public boolean debug = false;
    public boolean dig = false;
    @Override
    public void keyTyped(KeyEvent e) {

    }

    //TODO more than one keypressed can be true which causes conflict and since keyPressed happens only once it causes cases where when two opposites are held and one is released the other still needs to be re pressed
    //Could handle at engine level as player_Moving being set to true causing things not to be checked, but gameCanvas has the key listener
    //To prevent this atm we just used a lock of sorts. It prevents crashing but adds a delay when two are held and one is released, 1-2 seconds before the other one moves
    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();


        if(code == KeyEvent.VK_P){
            debug = true;
        }
        if(code == KeyEvent.VK_F){
            dig = true;
        }


        if(code == KeyEvent.VK_W){
                upPressed = true;
        }
        if(code == KeyEvent.VK_A){
                leftPressed = true;
        }
        if(code == KeyEvent.VK_S){
              downPressed = true;
        }
        if(code == KeyEvent.VK_D){
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
