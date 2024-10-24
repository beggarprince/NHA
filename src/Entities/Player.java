package Entities;

import io.KBInput;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player {
    public BufferedImage PlayerImage = null;
    static final public  int playerSpeed = 4;
    public static int cPosX = 100;
    public static int cPosY = 100;

    public Player(){
        setPlayerImage();
    }

    public void playerPosUpdate(KBInput kb) {
        if (kb.upPressed) {
            Player.cPosY -= Player.playerSpeed;
        } else if (kb.downPressed) {
            Player.cPosY += Player.playerSpeed;
        } else if (kb.rightPressed) {
            Player.cPosX += Player.playerSpeed;
        } else if (kb.leftPressed) {
            Player.cPosX -= Player.playerSpeed;
        }
    }

    public void setPlayerImage(){
        try{
            PlayerImage = ImageIO.read(getClass().getClassLoader().getResourceAsStream("dumbaahknight.png"));
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
