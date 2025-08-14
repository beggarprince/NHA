package io.keyboard;

import util.HeroDebug;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class KBInputMenu implements KeyListener {
    public boolean movedUp = false;
    public boolean movedDown = false;
    public boolean pressedEnter = false;

    @Override
    public void keyTyped(KeyEvent e) {
        //We do not take in typed input

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        //Move menu cursor down
        if( code == KeyEvent.VK_S){
            movedDown = true;
        }
        //Move menu cursor up
        else if (code == KeyEvent.VK_W){
            movedUp = true;
        }
        //enter/accept
        else if (code == KeyEvent.VK_Q){
            pressedEnter = true;
        }
    }


    //These are probably really good for simple debugs to get a single instance
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        //Move menu cursor down
        if( code == KeyEvent.VK_S){
            movedDown = false;
        }
        //Move menu cursor up
        else if (code == KeyEvent.VK_W){
    movedUp= false;
        }
        //enter/accept
        else if (code == KeyEvent.VK_ENTER || code == KeyEvent.VK_F){
            pressedEnter = false;
        }

    }
}
