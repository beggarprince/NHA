package main.java.graphics.ui;

import main.java.graphics.ScreenSettings;
import main.java.graphics.Sprite.ImgLoader;
import main.java.graphics.Sprite.SpriteConstants;

import java.awt.image.BufferedImage;

public class UIMessages {
    public final BufferedImage uiHideMVPMessage = ImgLoader.getImageResource(SpriteConstants.MESSAGE_HIDE_MVP);
    public final BufferedImage uiGameOver = ImgLoader.getImageResource(SpriteConstants.MESSAGE_GAMEOVER);

    private final int centerScreenWidth = ScreenSettings.PX_SCREEN_WIDTH /2;
    private final int centerScreenHeight = ScreenSettings.PX_SCREEN_HEIGHT /2;

    //tbh i don't know why we'd need the widths and heights
    private final int uiHideMVPMessageWidth = uiHideMVPMessage.getWidth();
    private final int UiHideMVPMessageHeight = uiHideMVPMessage.getHeight();

    private final int uiGameOverWidth = uiGameOver.getWidth();
    private final int uiGameOverHeight = uiGameOver.getHeight();

    //No function that does the math since i'll access this a lot and it never changes so i want final
    public final int uiHideMvpMessageStylizedOffsetX = centerScreenWidth - uiHideMVPMessageWidth /2;
    public final int uiHideMvpMessageStylizedOffsetY = centerScreenHeight - UiHideMVPMessageHeight /2;

    public final int uiGameOverMessageStylizedOffsetX = centerScreenWidth - uiGameOverWidth /2;
    public final int uiGameOverMessageStylizedOffsetY = centerScreenHeight - uiGameOverHeight/2;


}
