package main.java.graphics.ui;

import main.java.util.ImgLoader;
import main.java.util.StringRes;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class UINumber {
    public static BufferedImage numberSprites[] = new BufferedImage[10];

    public UINumber(){
        setNumberArray();
    }


    private void setNumberArray(){
        numberSprites[0] = ImgLoader.getImageResource(StringRes.NUM0);

        numberSprites[1] = ImgLoader.getImageResource(StringRes.NUM1);

        numberSprites[2] = ImgLoader.getImageResource(StringRes.NUM2);

        numberSprites[3] = ImgLoader.getImageResource(StringRes.NUM3);

        numberSprites[4] = ImgLoader.getImageResource(StringRes.NUM4);

        numberSprites[5] = ImgLoader.getImageResource(StringRes.NUM5);

        numberSprites[6] = ImgLoader.getImageResource(StringRes.NUM6);

        numberSprites[7] = ImgLoader.getImageResource(StringRes.NUM7);

        numberSprites[8] = ImgLoader.getImageResource(StringRes.NUM8);

        numberSprites[9] = ImgLoader.getImageResource(StringRes.NUM9);

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
