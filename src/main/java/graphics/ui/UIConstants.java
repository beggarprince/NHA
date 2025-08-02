package graphics.ui;

import java.awt.*;

public class UIConstants {

    public static Color boxColor = Color.BLACK;
    public static Color boxColorAlt = Color.GRAY;
    public static Color boxTextColor = Color.WHITE;
    public static Color uiBoxTextColorAlt = Color.BLACK;
    public static Color boxOutline = Color.WHITE;
    public static Color uiBoxOutlineAlt = Color.BLACK;



    public static void setTransparent(Graphics2D g){
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
    }

    public static void setOpaque(Graphics2D g){
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
    }
}
