package entities;

import io.KBInput;
import util.Coordinate;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player {
    public BufferedImage PlayerImage = null;
    static final public  int playerSpeed = 4;


    public Coordinate pos = new Coordinate(100,100);

    public Player(){
        setPlayerImage();
        //pos = coordinate;
    }

    public void playerPosUpdate(KBInput kb) {
        if (kb.upPressed) {
            pos.y -= Player.playerSpeed;
        } else if (kb.downPressed) {
            pos.y += Player.playerSpeed;
        } else if (kb.rightPressed) {
            pos.x += Player.playerSpeed;
        } else if (kb.leftPressed) {
            pos.x -= Player.playerSpeed;
        }
    }

    //BC the player is just a cursor in this game it needs to pan to whatever direction the player is headed towards

    private void setPlayerImage(){
        try{
            PlayerImage = ImageIO.read(getClass().getClassLoader().getResourceAsStream("dumbaahknight.png"));
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
