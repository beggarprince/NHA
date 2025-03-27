package main.java.entities;

import main.java.graphics.Camera;
import main.java.graphics.ScreenSettings;
import main.java.graphics.Sprite.ImgLoader;
import main.java.level.Level;
import main.java.util.Coordinate;

import java.awt.image.BufferedImage;

public class Player {
    public BufferedImage playerImage = null;
    static final private int playerSpeed = ScreenSettings.TILE_SIZE ;

    //World position of player
    public int playerTilePositionX = 0;
    public int playerTilePositionY = 0;

    private int digPower;


    private int playerXOffset = 0; // Offset from center
    private int playerYOffset = 0;

    public int getDigPower() {
        return digPower;
    }

    public void setDigPower(int digPower) {
        this.digPower = digPower;
    }

    public void digPowerDecrement(){
        digPower--;
    }

    //Not really utilized
    private enum PlayerSprite {
        PLAYER(ImgLoader.getImageResource("sprites/player.png")),
        DAMAGEDPLAYER(ImgLoader.getImageResource("sprites/hero/hurtknight.png"));

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
    public void playerPosUpdate(int movementType, boolean cameraPanned) {
        boolean playerMoved = false;

        //The only time the player should move is if the camera is unable to
        if(!cameraPanned){

            //UP
        if (movementType == 1) {
            if(playerTilePositionY -1 >= 0){
                playerScreenPosition.y -= Player.playerSpeed;
                playerMoved = true;
                playerYOffset++;
            }

        }
        //DOWN
        else if (movementType == 3) {
            if(playerTilePositionY + 1 < Level.levelRows ) {
                playerMoved = true;
                playerScreenPosition.y += Player.playerSpeed;
                playerYOffset--;;
            }
        }
        //RIGHT
        else if (movementType == 2) {
            if(playerTilePositionX +1 < Level.levelColumns  ){
                playerMoved = true;
                playerScreenPosition.x += Player.playerSpeed;
                playerXOffset++;
            }

        }
        //LEFT
        else if (movementType == 4) {
            if(playerTilePositionX -1 >= 0){
                playerScreenPosition.x -= Player.playerSpeed;
            playerMoved = true;
            playerXOffset--;
            }
        }

        }
        if(playerMoved || cameraPanned){
            updatePlayerWorldPosition(movementType);
        }

    }

    //Player or camera moved, need to update player logical pos
    private void updatePlayerWorldPosition(int movementType){

        //UP
        if(movementType == 1){
            playerTilePositionY--;
        }
        //DOWN
        else if(movementType == 3){
            playerTilePositionY++;
        }
        //LEFT
        else if(movementType == 4){
            playerTilePositionX--;
        }
        //RIGHT
        else if(movementType == 2){
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

    public void movePlayer(Player player, Camera camera,  int movementType) {

        boolean cameraMoved = false;

        //Pressing up/down or left/right just halts the player
        //The logic handling this should be moved outside of the player, the player should not know about the input
        //If the offset is not 0, then the player is not centered. If the player is not centered, then the player must move before the camera pans on that axis
        if (player.getXOffset() == 0 &&
                (movementType == 4 || movementType == 2)) {
            cameraMoved = camera.attemptCameraPan(movementType);

        }

        else if (player.getYOffset() == 0
                && (movementType == 1 || movementType == 3)) {
            cameraMoved = camera.attemptCameraPan(movementType);
        }


        player.playerPosUpdate(movementType, cameraMoved);

    }



}
