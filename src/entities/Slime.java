package entities;

import graphics.imgLoader;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class Slime {
    BufferedImage slimeImage;

    public Slime(){
        setSlimeImage();
    }



    private void setSlimeImage() {
        slimeImage = imgLoader.getImage("slime.png");
    }
}
