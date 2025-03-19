package main.java.io;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KBInputMenu implements KeyListener {
    @Override
    public void keyTyped(KeyEvent e) {
        //We do not take in typed input

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        //Move menu cursor down
        if( code == KeyEvent.VK_S){

        }
        //Move menu cursor up
        else if (code == KeyEvent.VK_W){

        }
        //enter/accept
        else if (code == KeyEvent.VK_ENTER || code == KeyEvent.VK_F){

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        //Move menu cursor down
        if( code == KeyEvent.VK_S){

        }
        //Move menu cursor up
        else if (code == KeyEvent.VK_W){

        }
        //enter/accept
        else if (code == KeyEvent.VK_ENTER || code == KeyEvent.VK_F){

        }
    }
}
