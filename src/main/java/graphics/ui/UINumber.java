package main.java.graphics.ui;

import main.java.graphics.Sprite.ImgLoader;
import main.java.graphics.Sprite.SpriteConstants;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class UINumber {
    public static BufferedImage numberSprites[] = new BufferedImage[10];

    public UINumber(){
        setNumberArray();
    }


    private void setNumberArray(){
        numberSprites[0] = ImgLoader.getImageResource(SpriteConstants.NUM0);

        numberSprites[1] = ImgLoader.getImageResource(SpriteConstants.NUM1);

        numberSprites[2] = ImgLoader.getImageResource(SpriteConstants.NUM2);

        numberSprites[3] = ImgLoader.getImageResource(SpriteConstants.NUM3);

        numberSprites[4] = ImgLoader.getImageResource(SpriteConstants.NUM4);

        numberSprites[5] = ImgLoader.getImageResource(SpriteConstants.NUM5);

        numberSprites[6] = ImgLoader.getImageResource(SpriteConstants.NUM6);

        numberSprites[7] = ImgLoader.getImageResource(SpriteConstants.NUM7);

        numberSprites[8] = ImgLoader.getImageResource(SpriteConstants.NUM8);

        numberSprites[9] = ImgLoader.getImageResource(SpriteConstants.NUM9);

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
