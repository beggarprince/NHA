package entities;

import graphics.ScreenSettings;
import graphics.imgLoader;
import io.KbInput;
import level.Level;
import util.Coordinate;

import java.awt.image.BufferedImage;

public class Player {
    public BufferedImage playerImage = null;
    static final private int playerSpeed = ScreenSettings.TILE_SIZE ;

    //World position of player
    public int playerTilePositionX = 0;
    public int playerTilePositionY = 0;

    private int playerXOffset = 0; // Offset from center
    private int playerYOffset = 0;

    private enum PlayerSprite {
        PLAYER(imgLoader.getImageResource("knight.png")),
        DAMAGEDPLAYER(imgLoader.getImageResource("hurtknight.png"));

        private final BufferedImage image;

        PlayerSprite(BufferedImage image) {
            this.image = image;
        }
        public BufferedImage getImage() {
            return image;
        }
    }

    public Coordinate playerScreenPosition = new Coordinate(ScreenSettings.TS_X /2 * ScreenSettings.TILE_SIZE - ScreenSettings.TILE_SIZE,ScreenSettings.TS_Y /2 * ScreenSettings.TILE_SIZE - ScreenSettings.TILE_SIZE);


    public Player(){
        setPlayerImage();
        playerTilePositionX = playerScreenPosition.x / ScreenSettings.TILE_SIZE;
        playerTilePositionY = playerScreenPosition.y / ScreenSettings.TILE_SIZE;
    }

    //TODO
    //JANK, there is an off by one error fixed by +1 on y axis and -1 on x axis, otherwise y axis doesn't go to corner and x axis goes off by 1
    //Player speed introduces jank if i use it to check bounds and it does not equal a tile
    public void playerPosUpdate(KbInput kb, boolean cameraPanned) {
        boolean playerMoved = false;

        //The only time the player should move is if the camera is unable to
        if(!cameraPanned){

        if (kb.upPressed) {
            if(playerScreenPosition.y - Player.playerSpeed >= 0){
                playerScreenPosition.y -= Player.playerSpeed;
                playerMoved = true;
                playerYOffset++;
            }

        } else if (kb.downPressed) {
            if(playerScreenPosition.y / ScreenSettings.TILE_SIZE + Player.playerSpeed < Level.levelRows +1) {
                playerMoved = true;
                playerScreenPosition.y += Player.playerSpeed;
                playerYOffset--;
            }
        } else if (kb.rightPressed) {
            if((playerScreenPosition.x / ScreenSettings.TILE_SIZE + Player.playerSpeed)  < Level.levelColumns -1 ){
                playerMoved = true;
                playerScreenPosition.x += Player.playerSpeed;
                playerXOffset++;
            }

        } else if (kb.leftPressed) {

            if(playerScreenPosition.x - Player.playerSpeed >= 0){
                playerScreenPosition.x -= Player.playerSpeed;
            playerMoved = true;
            playerXOffset--;
            }
        }

        }
        if(playerMoved || cameraPanned)updatePlayerWorldPosition(kb);

    }

    private void updatePlayerWorldPosition(KbInput kb){

        if(kb.upPressed){
            playerTilePositionY--;
        }
        else if(kb.downPressed){
            playerTilePositionY++;
        }

        else if(kb.leftPressed){
            playerTilePositionX--;
        }
        else if(kb.rightPressed){
            playerTilePositionX++;
        }

    }

    private void setPlayerImage() {
        playerImage = PlayerSprite.PLAYER.getImage();
    }


    public void playerHurt() {
        playerImage = PlayerSprite.DAMAGEDPLAYER.getImage();
    }

    public int getXOffset(){
        return playerXOffset;
    }
    public int getYOffset(){
        return playerYOffset;
    }


}
