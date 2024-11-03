package entities;

import graphics.ScreenSettings;
import graphics.imgLoader;
import io.kbInput;
import level.Level;
import util.Coordinate;

import java.awt.image.BufferedImage;

public class Player {
    public BufferedImage playerImage = null;
    static final private int playerSpeed = ScreenSettings.TILE_SIZE ;

    private enum PlayerSprite {
        PLAYER(imgLoader.getImage("knight.png")),
        ENEMY(imgLoader.getImage("hurtknight.png"));

        private final BufferedImage image;

        PlayerSprite(BufferedImage image) {
            this.image = image;
        }
        public BufferedImage getImage() {
            return image;
        }
    }


    public Coordinate pos = new Coordinate(ScreenSettings.TS_X /2 * ScreenSettings.TILE_SIZE - ScreenSettings.TILE_SIZE,ScreenSettings.TS_Y /2 * ScreenSettings.TILE_SIZE - ScreenSettings.TILE_SIZE);

    public Player(){
        setPlayerImage();
        System.out.println(pos.x + " " + pos.y);
    }

    //TODO
    //JANK, there is an off by one error fixed by +1 on y axis and -1 on x axis, otherwise y axis doesn't go to corner and x axis goes off by 1
    //Player speed introduces jank if i use it to check bounds and it does not equal a tile
    public void playerPosUpdate(kbInput kb) {

        //At least for now this keeps the player in the screen but camera does not pan
        if (kb.upPressed) {
            if(pos.y - Player.playerSpeed >= 0)pos.y -= Player.playerSpeed;
        } else if (kb.downPressed) {
            if(pos.y / ScreenSettings.TILE_SIZE + Player.playerSpeed < Level.levelRows +1) pos.y += Player.playerSpeed;
        } else if (kb.rightPressed) {
            if((pos.x / ScreenSettings.TILE_SIZE + Player.playerSpeed)  < Level.levelColumns -1 )pos.x += Player.playerSpeed;

        } else if (kb.leftPressed) {
            if(pos.x - Player.playerSpeed >= 0)pos.x -= Player.playerSpeed;
        }

    }

    //BC the player is just a cursor in this game it needs to pan to whatever direction the player is headed towards

    private void setPlayerImage() {
        playerImage = PlayerSprite.PLAYER.getImage();
    }


    public void playerHurt() {
        playerImage = PlayerSprite.ENEMY.getImage();
    }
}
