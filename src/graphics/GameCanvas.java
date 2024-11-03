package graphics;

import entities.Player;
import io.kbInput;
import util.Coordinate;

import javax.script.ScriptEngine;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

//Game Panel is the main game loop
//Updates Screen and handles input

public class GameCanvas extends JPanel {

    GameCanvas gameCanvas;
    Camera camera;
    Player player;
    ArrayList<ArrayList<Integer>> level;


    public GameCanvas(kbInput kb, Player p, ArrayList<ArrayList<Integer>> levelData, Camera c){
        gameCanvas = this;
        gameCanvas.setPreferredSize((new Dimension(ScreenSettings.PX_SCREEN_WIDTH, ScreenSettings.PX_SCREEN_HEIGHT)));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(kb);
        this.setFocusable(true);
        player = p;
        level = levelData;
        camera = c;
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        g2.setColor(Color.white);
        paintTileBackground(g2);
        paintPlayer(g2);
        paintEnemies(g2);
        g2.dispose();

    }

    private void paintTileBackground(Graphics2D g){
       Coordinate tl = camera.topLeftCrn;
       int count = 0;
     //  System.out.println((tl.y / ScreenSettings.TILE_SIZE));
        //Det subset in array
        int ly = tl.y / ScreenSettings.TILE_SIZE;
        int l1 = ly; // Placeholders for camera.topLefrCrn.y to avoid the constant division

        //Now that the bounds are in the tile system

        //While within bounds of the camera AND screen
        while(ly < (ScreenSettings.TS_Y + l1) && ly < level.size()){

            int lx = tl.x / ScreenSettings.TILE_SIZE;
            int l2 = lx;

            //Ditto but for the x bounds
            while (lx < (ScreenSettings.TS_X + l2) && lx < level.get(ly).size()) {
                count++;
                //ATP we paint the row
                if (ly < 0 || ly >= level.size() || lx < 0 || lx >= level.get(ly).size()) {
                    lx++; // Increment lx to avoid infinite loop
                    continue;
                }
                int tileValue = level.get(ly).get(lx);

                // Calculate the position where the tile should be drawn
                int x = lx * ScreenSettings.TILE_SIZE - (camera.offsetX * ScreenSettings.TILE_SIZE);
                int y = ly * ScreenSettings.TILE_SIZE - (camera.offsetY * ScreenSettings.TILE_SIZE);


                // Draw the image
                g.drawImage(selectImage(tileValue), x, y, ScreenSettings.TILE_SIZE, ScreenSettings.TILE_SIZE, null);

                //increment x pos
                lx++;
            }

            //increment y pos
            ly++;
        }
        System.out.println(count);

    }

    private BufferedImage selectImage(int tileValue){
        switch (tileValue) {
            case 1:
                return TileType.GRASS.getImage();
            case 2:
                return TileType.BRICK.getImage();
            default:
                return TileType.DIRT.getImage();
        }
    }

    private void paintPlayer(Graphics2D g){
        //get player state

        //paint by image
        g.drawImage(player.playerImage, player.pos.x, player.pos.y, ScreenSettings.TILE_SIZE, ScreenSettings.TILE_SIZE, null);
    }

    private void paintEnemies(Graphics2D g){

    }
}
