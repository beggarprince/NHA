package graphics;

import entities.Player;
import io.KBInput;

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

    public GameCanvas(KBInput kb, Player p, ArrayList<ArrayList<Integer>> levelData, Camera c){
        gameCanvas = this;
        gameCanvas.setPreferredSize((new Dimension(ScreenSettings.SCREEN_WIDTH, ScreenSettings.SCREEN_HEIGHT)));
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
        g2.dispose();

    }

    private void paintTileBackground(Graphics2D g){
      //  System.out.println(camera.topLeft.x);
        for (int row = camera.topLeft.y; row < camera.topLeft.y + ScreenSettings.HEIGHT_OFFSET; row++) {

            for (int col = camera.topLeft.x; col < camera.topLeft.x + ScreenSettings.SCREEN_WIDTH; col++) {
                //Camera is out of bounds of array thus we can see outside of bounds but have nothing to draw over there besides the def background.
                // //I could just draw a diff tile since there are only issues if i try to reference the array for an address that doesn't exist

                if(row < 0 || row >= level.size() || col <0 || col >= level.get(row).size() ) continue;

                // Get the tile value (0 for dirt, 1 for grass)

                int tileValue = level.get(row).get(col);

//                // Calculate the position where the tile should be drawn
                int x = col * ScreenSettings.TILE_SIZE + (camera.offsetX * ScreenSettings.TILE_SIZE);
                int y = row * ScreenSettings.TILE_SIZE + (camera.offsetY * ScreenSettings.TILE_SIZE);


                // Fetch the correct image based on the tile value using the TileType enum
                BufferedImage tileImage;

                switch (tileValue) {
                    case 1:
                        tileImage = TileType.GRASS.getImage();
                        break;
                    case 2:
                        tileImage = TileType.BRICK.getImage();
                        break;
                    default:
                        tileImage = TileType.DIRT.getImage();
                        break;
                }
                // Draw the image
                g.drawImage(tileImage, x, y, ScreenSettings.TILE_SIZE, ScreenSettings.TILE_SIZE, null);
            }
        }
    }

    private void paintPlayer(Graphics2D g2){
        //get player state

        //paint by image
        g2.drawImage(player.PlayerImage, player.pos.x, player.pos.y, ScreenSettings.TILE_SIZE, ScreenSettings.TILE_SIZE, null);
    }
}
