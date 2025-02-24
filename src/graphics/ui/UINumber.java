package graphics.ui;

import util.ImgLoader;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class UINumber {
    public BufferedImage numberSprites[] = new BufferedImage[10];

    public UINumber(){
        setNumberArray();
    }


    private void setNumberArray(){
        numberSprites[0] = ImgLoader.getImageResource("sprites/ui/0.png");

        numberSprites[1] = ImgLoader.getImageResource("sprites/ui/1.png");

        numberSprites[2] = ImgLoader.getImageResource("sprites/ui/2.png");

        numberSprites[3] = ImgLoader.getImageResource("sprites/ui/3.png");

        numberSprites[4] = ImgLoader.getImageResource("sprites/ui/4.png");

        numberSprites[5] = ImgLoader.getImageResource("sprites/ui/5.png");

        numberSprites[6] = ImgLoader.getImageResource("sprites/ui/6.png");

        numberSprites[7] = ImgLoader.getImageResource("sprites/ui/7.png");

        numberSprites[8] = ImgLoader.getImageResource("sprites/ui/8.png");

        numberSprites[9] = ImgLoader.getImageResource("sprites/ui/9.png");

    }


    public List<BufferedImage> determineUINumberDisplay(int dp){

        List<BufferedImage> biArray = new ArrayList<BufferedImage>();

        if(dp >= 100){
            int hundred = dp / 100;
            biArray.add(numberSprites[hundred]);
        }
        if(dp >= 10){
            int ten = (dp / 10) % 10;
            biArray.add(numberSprites[ten]);
        }
        int single = dp % 10;
        biArray.add(numberSprites[single]);
        return biArray;

    }
}
