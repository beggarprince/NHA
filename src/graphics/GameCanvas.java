package graphics;

import entities.Monsters.Monster;
import entities.Monsters.MonsterList;
import entities.Player;
import io.KbInput;
import level.Tile;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

//Engine is the main game loop
//Updates Screen and handles input
//Solely paints what is passed to it, no logic other than dealing with offsets and deciding not to render out of bounds stuff
//Alt i could pass a subset of the array that illustrates the proper view, but it works as is
//Key input handler should not be in here

public class GameCanvas extends JPanel {

    GameCanvas gameCanvas;
    Camera camera;
    Player player;
    ArrayList<ArrayList<Tile>> level;
    MonsterList monsterList;

    private int startTileY = 0;
    private int startTileX = 0;
    private int endTileY = 0;
    private int endTileX = 0;


    public GameCanvas(KbInput kb, Player p, ArrayList<ArrayList<Tile>> levelData, Camera c, MonsterList e) {
        gameCanvas = this;
        gameCanvas.setPreferredSize((new Dimension(ScreenSettings.PX_SCREEN_WIDTH, ScreenSettings.PX_SCREEN_HEIGHT)));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(kb);
        this.setFocusable(true);
        player = p;
        level = levelData;
        camera = c;
        monsterList = e;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.white);
        paintTileBackground(g2);
        paintPlayer(g2);
        paintEnemies(g2);
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

    private int offsetTileX(int tileX) {
        return tileX * ScreenSettings.TILE_SIZE - (camera.offsetX * ScreenSettings.TILE_SIZE);
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


    private BufferedImage selectImage(int tileValue) {
        switch (tileValue) {
            case 1:
                return TileType.NUTRIENT.getImage();
            case 2:
                return TileType.BRICK.getImage();
            case 3:
                return TileType.PATH.getImage();
            case 4:
                return TileType.MANA.getImage();
            default:
                return TileType.DIRT.getImage();
        }
    }


    private void paintPlayer(Graphics2D g) {
        //get player state

        //paint by image
        g.drawImage(player.playerImage, player.playerScreenPosition.x, player.playerScreenPosition.y, ScreenSettings.TILE_SIZE, ScreenSettings.TILE_SIZE, null);
    }

    //Needs screen position
    private void paintEnemies(Graphics2D g) {
        ArrayList<Monster> list = monsterList.getMonsters();
        for (int i = 0; i < list.size(); i++) {
            Monster e = list.get(i);
            //If in camera view
            if ((e.worldPositionX >= startTileX && e.worldPositionX < endTileX) && (e.worldPositionY >= startTileY && e.worldPositionY < endTileY)) {
                //Draw according to offset
                g.drawImage(e.getImage(), offsetTileX(e.worldPositionX), offsetTileY(e.worldPositionY), ScreenSettings.TILE_SIZE, ScreenSettings.TILE_SIZE, null);

            }
        }
    }
}
