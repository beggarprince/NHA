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

    public int playerXPos = 0;
    public int playerYPos = 0;
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

        System.out.println(pos.x  / ScreenSettings.TILE_SIZE+ " " + pos.y / ScreenSettings.TILE_SIZE);
        playerXPos = pos.x / ScreenSettings.TILE_SIZE;
        playerYPos = pos.y / ScreenSettings.TILE_SIZE;
    }

    //TODO
    //JANK, there is an off by one error fixed by +1 on y axis and -1 on x axis, otherwise y axis doesn't go to corner and x axis goes off by 1
    //Player speed introduces jank if i use it to check bounds and it does not equal a tile
    public void playerPosUpdate(kbInput kb, boolean cameraPanned) {
        boolean playerMoved = false;

        //The only time the player should move is if the camera is unable to
        if(!cameraPanned){

        if (kb.upPressed) {
            if(pos.y - Player.playerSpeed >= 0){
                pos.y -= Player.playerSpeed;
                playerMoved = true;
            }

        } else if (kb.downPressed) {
            if(pos.y / ScreenSettings.TILE_SIZE + Player.playerSpeed < Level.levelRows +1) {
                playerMoved = true;
                pos.y += Player.playerSpeed;
            }
        } else if (kb.rightPressed) {
            if((pos.x / ScreenSettings.TILE_SIZE + Player.playerSpeed)  < Level.levelColumns -1 ){
                playerMoved = true;
                pos.x += Player.playerSpeed;
            }

        } else if (kb.leftPressed) {

            if(pos.x - Player.playerSpeed >= 0){pos.x -= Player.playerSpeed;
            playerMoved = true;
            }
        }

        }
        if(playerMoved || cameraPanned)updatePlayerWorldPosition(kb);
    }

    private void updatePlayerWorldPosition(kbInput kb){

        if(kb.upPressed){
            playerYPos--;
        }
        else if(kb.downPressed){
            playerYPos++;
        }

        else if(kb.leftPressed){
            playerXPos--;
        }
        else if(kb.rightPressed){
            playerXPos++;
        }

    }

    private void setPlayerImage() {
        playerImage = PlayerSprite.PLAYER.getImage();
    }


    public void playerHurt() {
        playerImage = PlayerSprite.ENEMY.getImage();
    }
}
