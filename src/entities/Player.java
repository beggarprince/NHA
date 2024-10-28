package entities;

import graphics.ScreenSettings;
import io.kbInput;
import level.LevelCreate;
import util.Coordinate;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player {
    public BufferedImage PlayerImage = null;
    static final public  int playerSpeed = ScreenSettings.TILE_SIZE;


    public Coordinate pos = new Coordinate(64,64);

    public Player(){
        setPlayerImage();
        //pos = coordinate;
    }

    public void playerPosUpdate(kbInput kb) {
        System.out.println("Moving player");
//TODO Player can go out of bounds, 1 to the right and 3 downwards. I'm not sure why
        //At least for now this keeps the player in the screen but camera does not pan
        if (kb.upPressed) {
            if(pos.y - Player.playerSpeed >= 0)pos.y -= Player.playerSpeed;
        } else if (kb.downPressed) {
            if(pos.y / ScreenSettings.TILE_SIZE + Player.playerSpeed < LevelCreate.levely) pos.y += Player.playerSpeed;
        } else if (kb.rightPressed) {
            if(pos.x + Player.playerSpeed < LevelCreate.levelx)pos.x += Player.playerSpeed;
        } else if (kb.leftPressed) {
            if(pos.x - Player.playerSpeed >= 0)pos.x -= Player.playerSpeed;
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
