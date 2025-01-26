package graphics;

import util.ImgLoader;

import java.awt.image.BufferedImage;

public class SpriteSheetInterpreter {
    //This is meant to handle custom sprite sheets or json sprite sheets
    private  BufferedImage image = ImgLoader.getImageResource("mvp.png");

    public  SpriteSheetInterpreter(){
        this.image = ImgLoader.getImageResource("mvp.png");
    }
    int counter = 0;
    int x = 32;
    //This is going to need some metadata
    public  BufferedImage getSpriteFromSheet(int col, int row, int width, int height){
        counter++;
        if(counter == 30){
            x += 128;
        }
        if(counter == 60){
            x += 128;
        }
        if(counter == 90){
            x += 128;
        }
        if(counter == 120){
            x += 128;
        }
        if(counter == 150) {
            x = 32;
            counter = 0;
        }

        BufferedImage img = image.getSubimage(x  , 69, 68,68);
                return img;
    }
}
