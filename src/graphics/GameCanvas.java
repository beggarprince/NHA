package graphics;

import entities.Direction;
import entities.NPC.Heroes.Hero;
import entities.NPC.Heroes.HeroList;
import entities.NPC.Monsters.Monster;
import entities.NPC.Monsters.MonsterList;
import entities.NPC.NPCType;
import entities.Player;
import io.KbInput;
import level.Tile;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

//Engine is the main game loop
//Updates Screen and handles input

public class GameCanvas extends JPanel {

    GameCanvas gameCanvas;
    Camera camera;
    Player player;
    ArrayList<ArrayList<Tile>> level;
    MonsterList monsterList;
    HeroList heroList;

    private int startTileY = 0;
    private int startTileX = 0;
    private int endTileY = 0;
    private int endTileX = 0;
    private SpriteSheetInterpreter  s;

    public GameCanvas(KbInput kb,
                      Player p,
                      ArrayList<ArrayList<Tile>> levelData,
                      Camera c,
                      MonsterList e,
                      HeroList h) {
        gameCanvas = this;
        gameCanvas.setPreferredSize((new Dimension(ScreenSettings.PX_SCREEN_WIDTH,
                ScreenSettings.PX_SCREEN_HEIGHT)));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(kb);
        this.setFocusable(true);
        s = new SpriteSheetInterpreter();
        player = p;
        level = levelData;
        camera = c;
        monsterList = e;
        heroList = h;


    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.white);
        paintTileBackground(g2);
        paintPlayer(g2);
        paintEnemies(g2);
        paintHeroes(g2);
        paintBadman(g2);
        g2.dispose();

    }

    //Takes in a logical tile location and moves it to fit camera
    private void setCornerTiles() {

        // Calculate starting tile indices based on the camera's top-left corner
        startTileY = camera.topLeftCrn.y / ScreenSettings.TILE_SIZE;
        startTileX = camera.topLeftCrn.x / ScreenSettings.TILE_SIZE;
        // Calculate the ending tile indices to cover the visible screen area
        endTileY = startTileY + ScreenSettings.TS_Y;
        endTileX = startTileX + ScreenSettings.TS_X;
    }

    //Changes the world position to a screen position consistent with the camera location
    private int offsetTileX(int tileX) {
        return tileX * ScreenSettings.TILE_SIZE - (camera.offsetX * ScreenSettings.TILE_SIZE)  ;
    }

    private int offsetTileY(int tileY) {
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
                if(tileX <0) continue;
                g.drawImage(level.get(tileY).get(tileX).type.getImage(level.get(tileY).get(tileX)),
                        offsetTileX(tileX), offsetTileY(tileY),
                        ScreenSettings.TILE_SIZE, ScreenSettings.TILE_SIZE,
                        null);
            }
        }

    }

    private void paintPlayer(Graphics2D g) {
        //get player state

        //paint by image
        g.drawImage(player.playerImage, player.playerScreenPosition.x, player.playerScreenPosition.y, ScreenSettings.TILE_SIZE, ScreenSettings.TILE_SIZE, null);
    }

    private void paintBadman(Graphics2D g){

        g.drawImage(s.getSpriteFromSheet(), player.playerScreenPosition.x, player.playerScreenPosition.y, ScreenSettings.TILE_SIZE , ScreenSettings.TILE_SIZE , null  );
    }

    private void paintEnemies(Graphics2D g) {
        ArrayList<Monster> list = monsterList.getMonsters();
        for (int i = 0; i < list.size(); i++) {
            Monster e = list.get(i);
            //If in camera view
            if ((e.worldPositionX >= startTileX && e.worldPositionX < endTileX)
                    && (e.worldPositionY >= startTileY && e.worldPositionY < endTileY)) {
                //Draw according to offset
                //if(e.currDirection != Direction.RIGHT && e.currDirection != Direction.DOWN) {
                try {
                    g.drawImage(e.getImage(),
                            e.screenPositionX - camera.topLeftCrn.x,
                            e.screenPositionY - camera.topLeftCrn.y,
                            ScreenSettings.TILE_SIZE,
                            ScreenSettings.TILE_SIZE,
                            null);
                }catch (Exception l1){
                    System.out.println("L1: Failed to paint enemy");
                  //  System.out.println(e.worldPositionX + ":" + e.worldPositionY + "\n" + e.health + " and is " + e.inCombat + " combat");
                    System.out.println();
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
        ArrayList<Hero> list = heroList.getHeroes();
        for (int i = 0; i < list.size(); i++) {
            Hero h = list.get(i);
            //If in camera view
            if ((h.worldPositionX >= startTileX && h.worldPositionX < endTileX) && (h.worldPositionY >= startTileY && h.worldPositionY < endTileY)) {
                //Draw according to offset
                g.drawImage(h.getImage(), h.screenPositionX - camera.topLeftCrn.x , h.screenPositionY - camera.topLeftCrn.y, ScreenSettings.TILE_SIZE, ScreenSettings.TILE_SIZE, null);

            }
        }
    }
}
