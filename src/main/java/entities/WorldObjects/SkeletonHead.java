package   entities.WorldObjects;

import   graphics.Sprite.ImgLoader;
import   graphics.Sprite.SpriteConstants;

import java.awt.image.BufferedImage;

public class SkeletonHead extends WorldObject{

    public static BufferedImage sprite = ImgLoader.getImageResource(SpriteConstants.MONSTER_SKELETON);

    private int manaLevel = 0;

    public SkeletonHead(int tilePositionX, int tilePositionY) {

        super(tilePositionX, tilePositionY);
    }

    @Override
    public BufferedImage getSprite() {
        return sprite;
    }

    @Override
    public void behavior() {

    }

    public void absorbMana(){
        this.manaLevel += 1;
    }

    public void spawnSkeleton(){
        //Spawn skeleton based on absorbed mana or something, we'll make them stronger if they absorb more idek
    }
}
