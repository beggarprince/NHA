package   graphics.ui;

import   graphics.ScreenSettings;

import javax.swing.*;
import java.awt.*;

public class EnemyUITextBox extends JFrame {

    private static final Font TEXT_FONT = new Font("Arial", Font.PLAIN, 24);
    private static final Font BOLD_FONT = new Font("Arial", Font.BOLD, 24);

    //The size of the enemyUI
    //Max of 4, 20% of the width with 5 spacings in between them of 5% of the width
    //Height undetermined, we'll 20% as well
    //The height is 4 spacings and 3 text lines in height, 1 spacing margin of the textbox

    //yes i know i can do omit *1, the finals are not final and i like it this way
    static final int uiDimensionsWidth = ScreenSettings.PX_SCREEN_WIDTH * 1 / 5;
    static final int uiDimensionsHeight = ScreenSettings.PX_SCREEN_HEIGHT * 1 /6;

    static final int enemyUIBoxXSpacing = ScreenSettings.PX_SCREEN_WIDTH * 4 /100; // This is the length of the area between the ui boxes

    static final int uiMarginX = enemyUIBoxXSpacing;//The point between
    static final int uiMarginY = 16; // This is the point between of the top of the box

    static final int uiTextSpacing = 24; // This is the buffer between lines of text

    static final int textBoxOriginX = uiMarginX;
    static final int textBoxOriginY = uiMarginY;


    public enum FontStyle {
        DEFAULT,  // Maps to TEXT_FONT
        BOLD      // Maps to BOLD_FONT
    }



    //TODO make some character count to split the text into two lines
    //Upon calling this will not decide whether or not to split the text, rather it will do that in a diff fun
    public static void enemyUITextBox(Graphics2D g, String text, int nHeroCount, String hp){

        int xOrigin;
        if(nHeroCount == 0){
            xOrigin = textBoxOriginX;
        }
        else {
            xOrigin = textBoxOriginX + (nHeroCount * (uiMarginX + uiDimensionsWidth));
        }

        UIConstants.setTransparent(g);
        //Box
        g.setColor(UIConstants.boxColor);
        g.fillRect(xOrigin, textBoxOriginY, uiDimensionsWidth, uiDimensionsHeight);


        UIConstants.setOpaque(g);

        //Outline
        g.setColor(UIConstants.boxOutline);
        g.drawRect(xOrigin, textBoxOriginY, uiDimensionsWidth, uiDimensionsHeight);

        //Set font and color
        g.setFont(TEXT_FONT);
        g.setColor(UIConstants.boxTextColor);

        //I'm going to use this to det the height l8r
        //FontMetrics metrics = g.getFontMetrics(TEXT_FONT);
        //int textWidth = metrics.stringHeight(text) /2;
        //TODO get the height of the font so i can more easily work with the spacing
        //Name
        g.drawString(text, xOrigin + uiTextSpacing  , (textBoxOriginY + uiTextSpacing) );
        //HP
        g.drawString( hp , xOrigin + uiTextSpacing  , (textBoxOriginY + uiTextSpacing + 32));
        //Mana
        g.drawString("STATUS", xOrigin + uiTextSpacing  , (textBoxOriginY + uiTextSpacing) + 64);
    }

}
