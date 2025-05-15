package main.java.Menu;

import main.java.graphics.ScreenSettings;

import javax.swing.*;
import java.awt.*;

public class PauseMenu extends JFrame {
    //This is more of a function rather than a runnable like the main menu. tbh the package is arbitrary
    //I'm going to shoot for the menu to be from 30% to 60% of the screen height
    //We can offset by 5%, allowing us 5 menu options
    final static int centerScreenX = (ScreenSettings.PX_SCREEN_WIDTH / 2);



    //The size of the menu, we'll just do 45-65% of the width aka 20%
    static final int pauseMenuWidth = ScreenSettings.PX_SCREEN_WIDTH * 2 / 10;
    //Height will be 30% since we already decided this previously
    static final int pauseMenuHeight = ScreenSettings.PX_SCREEN_HEIGHT * 3 / 10;

    static final int pauseMenuTopY = ScreenSettings.PX_SCREEN_HEIGHT * 3 /10;
    final static int pauseMenuTopX = centerScreenX - (pauseMenuWidth / 2);
    final static int menuSpacing = ScreenSettings.PX_SCREEN_HEIGHT * 5 /100;
    final static Font l1 = new Font("Arial", Font.PLAIN, 24);
    //We now just add spacing to create the menu
    final static String paused = "Game Paused type shit" ;
    static int textWidth = 0;

    public static void inGamePause(Graphics2D g){

        //Rectangle
        g.setColor(Color.GRAY);
        //x y width height
        g.fillRect(pauseMenuTopX, pauseMenuTopY, pauseMenuWidth, pauseMenuHeight);

        //Outline
        g.setColor(Color.BLACK);
        g.drawRect(pauseMenuTopX, pauseMenuTopY,pauseMenuWidth,pauseMenuHeight);

        // Set the font and color for the text
        g.setFont(l1);
        g.setColor(Color.BLACK);
        if(textWidth == 0){
            initTextWidth(g);
        }

        // Draw the text at a specific position (e.g., x=50, y=50)
        g.drawString(paused, (centerScreenX - textWidth), (pauseMenuTopY + menuSpacing));
    }

    //This allows us to center the text by starting at the center and then subtracting half the width
    private static  void initTextWidth(Graphics2D g){
        FontMetrics m = g.getFontMetrics(l1);
        //With the current setup paused width is 151
        //At 24 font
        //W_ = 31
        //W = 24
        //l = 5
        //50 W is greater than 1296 and too much because the screen width is only 1296 but shows length as 1296
        //Holy shit i just accidentally guessed the max screen width, 51 is 1320 and too many Ws, 50 W fits
        System.out.println(m.stringWidth(paused));
        textWidth = m.stringWidth(paused) / 2;
    }
}
