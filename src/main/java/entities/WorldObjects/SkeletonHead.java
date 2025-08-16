package   entities.WorldObjects;

import entities.NPC.Monsters.MonsterLogic.MonsterFactory;
import entities.NPC.Monsters.MonsterLogic.MonsterList;
import graphics.ScreenSettings;
import   graphics.Sprite.ImgLoader;
import   graphics.Sprite.SpriteConstants;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SkeletonHead extends WorldObject{

    public static BufferedImage spriteSheet = ImgLoader.getImageResource(SpriteConstants.MONSTER_SKELETON);
    public static BufferedImage image = spriteSheet.getSubimage(231, 795, 64, 64);
    private int manaLevel = 0;
    private boolean hasBeenSpawned = false;

    public SkeletonHead(int tilePositionX, int tilePositionY) {

        super(tilePositionX, tilePositionY);
    }

    @Override
    public void behavior() {
        if(hasBeenSpawned){
            //this.image = null;
            this.sprite = null;
        }
    }

    @Override
    public Image getImage() {
        return image;
    }

    public void absorbMana(){
        this.manaLevel += 1;
    }

    public void spawnSkeleton(int x, int y){
        //Spawn here, delete object
       // MonsterFactory.createMonster("Skeleton", this.tilePositionX, this.tilePositionY);
        var skeleton = MonsterFactory.createMonster("Skeleton", x * ScreenSettings.TILE_SIZE ,
                y * ScreenSettings.TILE_SIZE);
        MonsterList.getInstance().addEnemy(skeleton);
        WorldObjectManager.INSTANCE.removeObject(this);

    }
}
