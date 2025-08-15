package entities.NPC.Heroes.skills;

import entities.Direction;
import entities.WorldObjects.Projectile;
import graphics.Sprite.ImgLoader;
import graphics.Sprite.SpriteConstants;

import java.awt.image.BufferedImage;


public class Fireball extends Projectile {
    public static BufferedImage sheet = ImgLoader.getImageResource(SpriteConstants.FIREBALL);
    private final int strength;

    public static BufferedImage set[] = {
            sheet.getSubimage(3, 16, 96, 96),
            sheet.getSubimage(133, 16, 96, 96),
            sheet.getSubimage(5, 144, 96, 96),
            sheet.getSubimage(135, 144, 96, 96)
    };

    final static int len = 4;
    int index = 0;

    public Fireball(int tilePositionX, int tilePositionY, Direction direction, int strength) {
        super(tilePositionX, tilePositionY, direction);
        this.strength = strength;
    }

    public BufferedImage getImage(){
        index++;
        if(index == len) index = 0;
        return set[index];
    }

    public int getStrength(){
        return strength;
    }

}

//          3, 16, 96, 96
//        133, 16, 96, 96
//        5, 144, 96, 96
//        135, 144, 96, 96
