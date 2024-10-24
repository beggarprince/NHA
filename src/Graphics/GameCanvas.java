package Graphics;

import Entities.Player;
import io.KBInput;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

//Game Panel is the main game loop
//Updates Screen and handles input

public class GameCanvas extends JPanel {

    GameCanvas gameCanvas;
    Player player;
    ArrayList<ArrayList<Integer>> level;

    public GameCanvas(KBInput kb, Player p, ArrayList<ArrayList<Integer>> levelData){
        gameCanvas = this;
        gameCanvas.setPreferredSize((new Dimension(ScreenSettings.SCREEN_WIDTH, ScreenSettings.SCREEN_HEIGHT)));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(kb);
        this.setFocusable(true);
        player = p;
        level = levelData;
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        g2.setColor(Color.white);
        paintBackground(g2);
         paintPlayer(g2);
        //tiler.drawGrass(g2);
        g2.dispose();
    }

    private void paintBackground(Graphics2D g){
        for (int row = 0; row < level.size(); row++) {
            for (int col = 0; col < level.get(row).size(); col++) {
                // Get the tile value (0 for dirt, 1 for grass)
                int tileValue = level.get(row).get(col);

                // Calculate the position where the tile should be drawn
                int x = col * ScreenSettings.TILE_SIZE;
                int y = row * ScreenSettings.TILE_SIZE;

                // Fetch the correct image based on the tile value using the TileType enum
                BufferedImage tileImage = (tileValue == 1)
                        ? TileType.GRASS.getImage()
                        : TileType.DIRT.getImage();

                // Draw the image
                g.drawImage(tileImage, x, y, ScreenSettings.TILE_SIZE, ScreenSettings.TILE_SIZE, null);
            }
        }
    }

    private void paintPlayer(Graphics2D g2){
        //get player state

        //paint by image
        g2.drawImage(player.PlayerImage, player.cPosX, player.cPosY, ScreenSettings.TILE_SIZE, ScreenSettings.TILE_SIZE, null);
    }
}
