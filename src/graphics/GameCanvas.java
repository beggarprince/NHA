package graphics;

import entities.Enemy.Enemy;
import entities.Enemy.EnemyList;
import entities.Player;
import io.kbInput;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

//Engine is the main game loop
//Updates Screen and handles input

public class GameCanvas extends JPanel {

    GameCanvas gameCanvas;
    Camera camera;
    Player player;
    ArrayList<ArrayList<Integer>> level;
    EnemyList enemyList;


    public GameCanvas(kbInput kb, Player p, ArrayList<ArrayList<Integer>> levelData, Camera c, EnemyList e){
        gameCanvas = this;
        gameCanvas.setPreferredSize((new Dimension(ScreenSettings.PX_SCREEN_WIDTH, ScreenSettings.PX_SCREEN_HEIGHT)));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(kb);
        this.setFocusable(true);
        player = p;
        level = levelData;
        camera = c;
        enemyList = e;
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

    private void paintTileBackground(Graphics2D g) {

        //int count = 0;

        // Calculate starting tile indices based on the camera's top-left corner
        int startTileY = camera.topLeftCrn.y / ScreenSettings.TILE_SIZE;
        int startTileX = camera.topLeftCrn.x / ScreenSettings.TILE_SIZE;

        // Calculate the ending tile indices to cover the visible screen area
        int endTileY = startTileY + ScreenSettings.TS_Y;
        int endTileX = startTileX + ScreenSettings.TS_X;

        // Loop over the vertical tiles
        for (int tileY = startTileY; tileY < endTileY && tileY < level.size(); tileY++) {

            // Skip out-of-bounds rows
            if (tileY < 0 ) {
                continue;
            }

            // Loop over the horizontal tiles
            for (int tileX = startTileX; tileX < endTileX && tileX < level.get(tileY).size(); tileX++) {

                // Skip out-of-bounds columns
                if (tileX < 0 || tileX >= level.get(tileY).size()) {
                    continue;
                }

                //count++;

                // Get the tile value at the current position
                int tileValue = level.get(tileY).get(tileX);

                // Calculate the drawing position
                int x = tileX * ScreenSettings.TILE_SIZE - (camera.offsetX * ScreenSettings.TILE_SIZE);
                int y = tileY * ScreenSettings.TILE_SIZE - (camera.offsetY * ScreenSettings.TILE_SIZE);

                // Draw the tile image
                g.drawImage(selectImage(tileValue), x, y, ScreenSettings.TILE_SIZE, ScreenSettings.TILE_SIZE, null);
            }
        }

       //  System.out.println(count); // Uncomment to see the count of painted tiles
    }


    private BufferedImage selectImage(int tileValue){
        switch (tileValue) {
            case 1:
                return TileType.GRASS.getImage();
            case 2:
                return TileType.BRICK.getImage();
            case 3:
                return  TileType.PATH.getImage();
            case 4:
                return TileType.MANA.getImage();
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
        ArrayList<Enemy> list = enemyList.getEnemies();
        for(Enemy e : list){
            g.drawImage(e.getImage(), e.getPosition().x, e.getPosition().y, ScreenSettings.TILE_SIZE, ScreenSettings.TILE_SIZE, null);
        }
    }
}
