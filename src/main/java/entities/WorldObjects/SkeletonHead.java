package   entities.WorldObjects;

import entities.NPC.Monsters.MonsterLogic.MonsterFactory;
import   graphics.Sprite.ImgLoader;
import   graphics.Sprite.SpriteConstants;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SkeletonHead extends WorldObject{

    public static BufferedImage spriteSheet = ImgLoader.getImageResource(SpriteConstants.MONSTER_SKELETON);
    public static BufferedImage image = spriteSheet.getSubimage(231, 795, 64, 64);
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

    @Override
    public Image getImage() {
        return image;
    }

    public void absorbMana(){
        this.manaLevel += 1;
    }

    public void spawnSkeleton(){
        //Spawn here, delete object
        MonsterFactory.createMonster("Skeleton", this.tilePositionX, this.tilePositionY);

    }
}
