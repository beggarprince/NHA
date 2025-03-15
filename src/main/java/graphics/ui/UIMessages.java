package main.java.graphics.ui;

import main.java.graphics.ScreenSettings;
import main.java.util.ImgLoader;
import main.java.util.StringRes;

import java.awt.image.BufferedImage;

public class UIMessages {
    public final BufferedImage uiHideMVPMessage = ImgLoader.getImageResource(StringRes.MESSAGE_HIDE_MVP);
    private final int uiHideMVPMessageWidth = uiHideMVPMessage.getWidth();
    private final int UiHideMVPMessageHeight = uiHideMVPMessage.getHeight();

    public final int uiHideMvpMessageStylizedOffsetX = ScreenSettings.PX_SCREEN_WIDTH /2 - uiHideMVPMessageWidth /2;
    public final int uiHideMvpMessageStylizedOffsetY = ScreenSettings.PX_SCREEN_HEIGHT /2 - UiHideMVPMessageHeight /2;
}
