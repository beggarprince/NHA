package graphics;

import util.ImgLoader;

import java.awt.image.BufferedImage;

public class SpriteSheetInterpreter {
    //This is meant to handle custom sprite sheets or json sprite sheets
    private  BufferedImage image = ImgLoader.getImageResource("sprites/monster/mvp.png");

    public  SpriteSheetInterpreter(){
        this.image = ImgLoader.getImageResource("sprites/monster/mvp.png");
    }
    int counter = 0;
    int x = 32;
    int y = 68;
    int yd = 108;
    //This is going to need some metadata
    public  BufferedImage getSpriteFromSheet(){

        counter+=2;

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
            y += yd;
        }
        if(counter == 180){
            x += 128;
        }
        if(counter == 210){
            x += 128;
        }
        if(counter == 240){
            x += 128;
        }
        if(counter == 270){
            x += 128;
        }
        if(counter == 300) {
            x = 32;
            y -= yd;
            counter = 0;
        }


        BufferedImage img = image.getSubimage(x  , y, 68,68);
                return img;
    }
}
