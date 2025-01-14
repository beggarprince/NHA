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
            System.out.println("camera could not pan");

            System.out.println(kb.up);
        if (kb.upPressed) {
            if(playerTilePositionY -1 >= 0){
                playerScreenPosition.y -= Player.playerSpeed;
                playerMoved = true;
                playerYOffset++;

                System.out.println("Player going up");
            }

        } else if (kb.down) {
            if(playerTilePositionY + 1 < Level.levelRows ) {
                playerMoved = true;
                playerScreenPosition.y += Player.playerSpeed;
                playerYOffset--;;
            }
        }

        if (kb.right) {
            if(playerTilePositionX +1 < Level.levelColumns  ){
                playerMoved = true;
                playerScreenPosition.x += Player.playerSpeed;
                playerXOffset++;
            }

        } else if (kb.left) {
            if(playerTilePositionX -1 >= 0){
                System.out.println("kbleft activated offset");
                playerScreenPosition.x -= Player.playerSpeed;
            playerMoved = true;
            playerXOffset--;
            }
        }

        }
        if(playerMoved || cameraPanned){
            updatePlayerWorldPosition(kb);
//            System.out.println("PlayerPosition:" + playerTilePositionX + ":" + playerTilePositionY);
//            System.out.println("Player:" + playerXOffset + ":" + playerYOffset);
        }

    }

    //Player or camera moved, need to update player logical pos
    private void updatePlayerWorldPosition(KbInput kb){


        if(kb.up){
            playerTilePositionY--;
        }
        else if(kb.down){
            playerTilePositionY++;
        }


        if(kb.left){
            playerTilePositionX--;
        }
        else if(kb.right){
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



        //Pressing up/down or left/right just halts the player
        if(kb.conflictingVerticalInput() || kb.conflictingHorizontalInput()) return;

        player.playerPosUpdate(kb, camera.attemptCameraPan(kb));

    }

}
