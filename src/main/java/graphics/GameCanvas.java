package main.java.graphics;

import main.java.Game.GameState;
import main.java.Game.LevelState;
import main.java.Menu.PauseMenu;
import main.java.Game.State;
import main.java.entities.NPC.Heroes.Hero;
import main.java.entities.NPC.Monsters.Monster;
import main.java.entities.NPC.Mvp;
import main.java.entities.Player;
import main.java.graphics.ui.UIMessages;
import main.java.graphics.ui.UINumber;
import main.java.io.keyboard.KbInputInGame;
import main.java.level.Tile;
import main.java.util.Coordinate;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static main.java.graphics.ui.EnemyUITextBox.enemyUITextBox;

//main.java.Engine is the main game loop
//Updates Screen and handles input

public class GameCanvas extends JPanel {

    //main.java.Game canvas should not have access to the original lists, since it will be decoupled so logic and ui can run in separate threads
    GameCanvas gameCanvas;
    Camera camera;
    Player player;
    ArrayList<ArrayList<Tile>> level;
    ArrayList<Monster> monsterList;
    ArrayList<Hero> heroList;

    private int startTileY = 0;
    private int startTileX = 0;
    private int endTileY = 0;
    private int endTileX = 0;
    private final UINumber uiNumber;
    private final UIMessages uiMessages;
    private Coordinate MasterCoordinate;
    Graphics2D g2Master;

    public GameCanvas(KbInputInGame kb,
                      Player p,
                      ArrayList<ArrayList<Tile>> levelData,
                      Camera c, ArrayList<Hero> h, ArrayList<Monster> m) {
        gameCanvas = this;
        gameCanvas.setPreferredSize((new Dimension(ScreenSettings.PX_SCREEN_WIDTH,
                ScreenSettings.PX_SCREEN_HEIGHT)));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(kb);
        this.setFocusable(true);
        player = p;
        level = levelData;
        camera = c;
        uiNumber = new UINumber();
        uiMessages = new UIMessages();
        //gameCanvas.setDoubleBuffered(true);
        //gameCanvas.setPreferredSize(new Dimension(ScreenSettings.PX_SCREEN_WIDTH, ScreenSettings.PX_SCREEN_HEIGHT));
        MasterCoordinate = camera.topLeftCrn;
    }

    public void repaintCanvas(){
        this.repaint();
    }

    public void setMonsterListInCanvas(ArrayList<Monster> frameMonsterList, ArrayList<Hero> frameHeroList){
        monsterList =frameMonsterList;
        heroList = frameHeroList;

        //this.repaint();
    }


    public void paintComponent(Graphics g) {
        updateMasterCoordinate();
       // System.out.println(MasterCoordinate.x + ":"+ MasterCoordinate.y);
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2Master = g2;
        //g2.setColor(Color.white);
        paintTileBackground(g2);
        paintPlayer(g2);
        paintEnemies(g2);
        paintHeroes(g2);
        paintBadman(g2);
        paintUIDigPower(g2);
       ////TODO same as below
        if(GameState.gameState == State.PAUSE)PauseMenu.inGamePause(g2);

//        //TODO i need to move this out so the paint component function is not deciding whether or not to draw the heroes
        if(GameState.heroActive == true){
            for(int i = 0; i < heroList.size(); i++){
                enemyUITextBox(g2, heroList.get(i).name , i, String.valueOf(heroList.get(i).health));
            }
        }
        //TODO replace this with some state code instead of booleans
        if(GameState.stateHidingMvp() && GameState.gameState != State.CINEMATIC) paintHideMVPMessage(g2);
        if(GameState.gameState == State.GAMEOVER) paintGameOverMessage(g2);
        g2.dispose();

    }


    //Takes in a logical tile location and moves it to fit camera
    private void setCornerTiles() {

        // Calculate starting tile indices based on the camera's top-left corner
        startTileY = MasterCoordinate.y / ScreenSettings.TILE_SIZE;
        startTileX = MasterCoordinate.x / ScreenSettings.TILE_SIZE;
        // Calculate the ending tile indices to cover the visible screen area
        endTileY = startTileY + ScreenSettings.TS_Y;
        endTileX = startTileX + ScreenSettings.TS_X;
    }

    //Changes the world position to a screen position consistent with the camera location
    private int offsetTileX(int tileX) {
        if(GameState.gameState == State.CINEMATIC){
          //  System.out.println("Offset for x is " + camera.cinematicCameraOffsetX);
            return tileX * ScreenSettings.TILE_SIZE - (camera.cinematicCameraOffsetX * ScreenSettings.TILE_SIZE);
        }
      //  System.out.println("Offset for x is " + camera.offsetX);
        return tileX * ScreenSettings.TILE_SIZE - (camera.offsetX * ScreenSettings.TILE_SIZE)  ;
    }

    private int offsetTileY(int tileY) {
        if(GameState.gameState == State.CINEMATIC){
          //  System.out.println("Offset for y is " + camera.cinematicCameraOffsetY);
            return tileY * ScreenSettings.TILE_SIZE - (camera.cinematicCameraOffsetY * ScreenSettings.TILE_SIZE);
        }

       // System.out.println("Offset for y is " + camera.offsetY);
        return tileY * ScreenSettings.TILE_SIZE - (camera.offsetY * ScreenSettings.TILE_SIZE);
    }

    private void paintTileBackground(Graphics2D g) {
        setCornerTiles();

        for (int tileY = startTileY; tileY < endTileY && tileY < level.size(); tileY++) {

            // Skip out-of-bounds rows
            if (tileY < 0) {
                continue;
            }

            // Loop over the horizontal tiles
            for (int tileX = startTileX; tileX < endTileX && tileX < level.get(tileY).size(); tileX++) {
                if(tileX <0){
                    continue;
                }
                g.drawImage(level.get(tileY).get(tileX).type.getImage(level.get(tileY).get(tileX)),
                        offsetTileX(tileX), offsetTileY(tileY),
                        ScreenSettings.TILE_SIZE, ScreenSettings.TILE_SIZE,
                        null);
            }
        }

    }

    private void paintPlayer(Graphics2D g) {
        //paint by image
        g.drawImage(player.playerImage, player.playerScreenPosition.x, player.playerScreenPosition.y, ScreenSettings.TILE_SIZE, ScreenSettings.TILE_SIZE, null);
    }

    private void paintBadman(Graphics2D g){
        g.drawImage(Mvp.getInstance().getSpriteFromSheet(),
                Mvp.getInstance().returnScreenPositionX() - MasterCoordinate.x,
                Mvp.getInstance().returnScreenPositionY() - MasterCoordinate.y,
                ScreenSettings.TILE_SIZE ,
                ScreenSettings.TILE_SIZE ,
                null  );
    }

    private void paintEnemies(Graphics2D g) {
        if(monsterList == null) return;
        for (int i = 0; i < monsterList.size(); i++) {
            Monster e = monsterList.get(i);
            //If in camera view
            if ((e.tilePositionX >= startTileX && e.tilePositionX < endTileX)
                    && (e.tilePositionY >= startTileY && e.tilePositionY < endTileY)) {
                //Draw according to offset
                //if(e.currDirection != Direction.RIGHT && e.currDirection != Direction.DOWN) {
                try {
                    g.drawImage(e.getImage(),
                            e.screenPositionX - MasterCoordinate.x,
                            e.screenPositionY - MasterCoordinate.y,
                            ScreenSettings.TILE_SIZE,
                            ScreenSettings.TILE_SIZE,
                            null);
                }catch (Exception l1){
                    System.out.println("L1: Failed to paint enemy");
                  //  System.out.println(e.worldPositionX + ":" + e.worldPositionY + "\n" + e.health + " and is " + e.inCombat + " combat");
                  //  System.out.println();
                }

//                }
//                else {
//                    System.out.println("Mirroring");
//                    // Flip sprite horizontally (mirror effect)
//                    AffineTransform transform = new AffineTransform();
//                    transform.scale(-1, 1); // Mirror horizontally
//                    transform.translate(e.getImage().getWidth(), 0); // Move into place
//                    g.drawImage(e.getImage(), transform, null);
//                }
            }
        }
    }

    private void paintHeroes(Graphics2D g) {
        if(heroList == null) return;

        for (int i = 0; i < heroList.size(); i++) {
            Hero h = heroList.get(i);
            //If in camera view
            if ((h.tilePositionX >= startTileX && h.tilePositionX < endTileX) && (h.tilePositionY >= startTileY && h.tilePositionY < endTileY)) {
                //Draw according to offset
                g.drawImage(h.getImage(), h.screenPositionX - MasterCoordinate.x , h.screenPositionY - MasterCoordinate.y, ScreenSettings.TILE_SIZE, ScreenSettings.TILE_SIZE, null);

            }
        }
    }
    private void paintUIDigPower(Graphics2D g){
        var list = uiNumber.determineUINumberDisplay(player.getDigPower());

        for(int i = 0; i < list.size(); i++){

            g.drawImage(list.get(i), ScreenSettings.PX_UI_DIGPOWER_X + (ScreenSettings.PX_UI_NUMBER_OFFSET * i), ScreenSettings.PX_UI_DIGPOWER_Y, null);

        }
       // g.drawImage(ImgLoader.getImageResource("sprites/ui/0.png"), ScreenSettings.PX_UI_DIGPOWER_X, ScreenSettings.PX_UI_DIGPOWER_Y, null);
    }

    private void paintHideMVPMessage(Graphics2D g){
        g.drawImage(uiMessages.uiHideMVPMessage,
                uiMessages.uiHideMvpMessageStylizedOffsetX,
                uiMessages.uiHideMvpMessageStylizedOffsetY, null);
    }

    private void paintGameOverMessage(Graphics2D g){
        g.drawImage(uiMessages.uiGameOver,
                uiMessages.uiGameOverMessageStylizedOffsetX,
                uiMessages.uiGameOverMessageStylizedOffsetY,
                null);
    }

    private void updateMasterCoordinate(){

        //TODO atm it is cinematic when awaiting input, cinematic camera and awaiting input state should be able to be differentiated
        if(GameState.gameState == State.GAMEOVER ||
                GameState.gameState == State.CINEMATIC ){
          //  System.out.println("Cinematic camera");
            MasterCoordinate = camera.getCinematicCamera();
        }
        else {
          //  System.out.println("Normal camera");
            MasterCoordinate = camera.topLeftCrn;
        }
    }


}
