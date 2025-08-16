package   graphics.ui;

import   graphics.ScreenSettings;

import javax.swing.*;
import java.awt.*;

public class AwaitingInputTextBox extends JFrame {
    final static int centerScreenX = (ScreenSettings.PX_SCREEN_WIDTH /2);

    //The size of the menu
    //80% width and 25% height
    static final int textBoxWidth = ScreenSettings.PX_SCREEN_WIDTH * 8 / 10;
    static final int textBoxHeight = ScreenSettings.PX_SCREEN_HEIGHT * 25 /100;

    static final int textBoxOriginX = centerScreenX - (textBoxWidth / 2);
    static final int textBoxOriginY = ScreenSettings.PX_SCREEN_HEIGHT * 7 /10 ;
    static final int textBoxMarginX = 16;
    static final int textBoxMarginY = 16;
    static final int textLineSpace = ScreenSettings.PX_SCREEN_HEIGHT * 1/10; //This really needs to be tested
    static int getTextBoxWidth = 0;

    private static final Font TEXT_FONT = new Font("Arial", Font.PLAIN, 24);
    private static final Font BOLD_FONT = new Font("Arial", Font.BOLD, 24);

    public enum FontStyle {
        DEFAULT,  // Maps to TEXT_FONT
        BOLD      // Maps to BOLD_FONT
    }



    //TODO make some character count to split the text into two lines
    //Upon calling this will not decide whether or not to split the text, rather it will do that in a diff fun
    public static void textBox(Graphics2D g, Font font, String text){

        //Box
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        UIConstants.setTransparent(g);

        g.setColor(UIConstants.boxColor);
        g.fillRect(textBoxOriginX, textBoxOriginY, textBoxWidth, textBoxHeight);



        UIConstants.setOpaque(g);

        //Outline
        g.setColor(UIConstants.boxOutline);
        g.drawRect(textBoxOriginX, textBoxOriginY, textBoxWidth, textBoxHeight);

        //Set font and color
        g.setFont(font);
        g.setColor(UIConstants.boxTextColor);

        FontMetrics metrics = g.getFontMetrics(font);
        int textWidth = metrics.stringWidth(text) /2;
        g.drawString(text, (centerScreenX  -textWidth), (textBoxOriginY + textLineSpace));

    }

}
