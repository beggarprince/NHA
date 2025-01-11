package entities;

import graphics.Camera;
import graphics.ScreenSettings;
import util.ImgLoader;
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

    //Not really utilized
    private enum PlayerSprite {
        PLAYER(ImgLoader.getImageResource("player.png")),
        DAMAGEDPLAYER(ImgLoader.getImageResource("hurtknight.png"));

        private final BufferedImage image;

        PlayerSprite(BufferedImage image) {
            this.image = image;
        }
        public BufferedImage getImage() {
            return image;
        }
    }

    public Coordinate playerScreenPosition = new Coordinate(ScreenSettings.TS_X /2
            * ScreenSettings.TILE_SIZE - ScreenSettings.TILE_SIZE,ScreenSettings.TS_Y /2
            * ScreenSettings.TILE_SIZE - ScreenSettings.TILE_SIZE);


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
            if(playerTilePositionY -1 >= 0){
                playerScreenPosition.y -= Player.playerSpeed;
                playerMoved = true;
                playerYOffset++;
            }

        } else if (kb.downPressed) {
            if(playerTilePositionY + 1 < Level.levelRows ) {
                playerMoved = true;
                playerScreenPosition.y += Player.playerSpeed;
                playerYOffset--;;
            }
        } else if (kb.rightPressed) {
            if(playerTilePositionX +1 < Level.levelColumns  ){
                playerMoved = true;
                playerScreenPosition.x += Player.playerSpeed;
                playerXOffset++;
            }

        } else if (kb.leftPressed) {
            if(playerTilePositionX -1 >= 0){
                playerScreenPosition.x -= Player.playerSpeed;
            playerMoved = true;
            playerXOffset--;
            }
        }

        }
        if(playerMoved || cameraPanned){
            updatePlayerWorldPosition(kb);
            //System.out.println(playerTilePositionX + " < " + Level.levelColumns);
            //System.out.println((playerTilePositionY+ " " + playerScreenPosition.y / ScreenSettings.TILE_SIZE)+ " < " + Level.levelRows);

        }

    }

    //Player or camera moved, need to update player logical pos
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

    public void movePlayer(Player player, Camera camera, KbInput kb) {
        boolean cameraMoved = false;

        if(kb.conflictingVerticalInput() || kb.conflictingHorizontalInput()) return;

        if (player.getXOffset() == 0 && (kb.leftPressed
                || kb.rightPressed))
            cameraMoved = camera.updateCameraPosition(kb);
        else if (player.getYOffset() == 0 && (kb.upPressed
                || kb.downPressed))
            cameraMoved = camera.updateCameraPosition(kb);
        player.playerPosUpdate(kb, cameraMoved);

    }

}
