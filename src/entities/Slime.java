package entities;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Slime {
    BufferedImage SlimeImage;

    public Slime(){
        setSlimeImage();
    }




    private void setSlimeImage(){
        try{
            SlimeImage = ImageIO.read(getClass().getClassLoader().getResourceAsStream("slime.png"));
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
