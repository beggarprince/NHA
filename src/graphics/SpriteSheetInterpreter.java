package graphics;

import util.ImgLoader;

import java.awt.image.BufferedImage;

public class SpriteSheetInterpreter {
    //This is meant to handle custom sprite sheets or json sprite sheets
    private  BufferedImage image = ImgLoader.getImageResource("sprites/monster/mvp.png");

    public  SpriteSheetInterpreter(){
    }

}
