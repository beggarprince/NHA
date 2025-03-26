package main.java.entities.NPC.Monsters;

import main.java.entities.NPC.Monsters.MonsterLogic.MonsterList;
import main.java.entities.NPC.NPCType;
import main.java.graphics.ScreenSettings;
import main.java.util.ImgLoader;
import main.java.util.SpriteConstants;

import java.awt.image.BufferedImage;

import static main.java.entities.NPC.NPCLogicKTKt.checkCollisionsEAT;

public class Lilith extends Monster{

    static BufferedImage image = ImgLoader.getImageResource(SpriteConstants.MONSTER_LILITH);

    public Lilith( int x, int y) {
        super(48, x, y);
        this.basicAttackStrength = 24;
        setImage();
        this.hunger = 0;
        this.movementSpeed = 1;
        this.lifespan = ScreenSettings.FPS * 65;
        this.hasFullStomach = false;
        this.maxHunger = 32;
        this.type = NPCType.Lilith;
    }

    @Override
    protected void setImage() {
        image = ImgLoader.getImageResource(SpriteConstants.MONSTER_LILITH);
    }

    @Override
    public BufferedImage getImage() {
        return image;
    }

    @Override
    public void eat() {
        this.startAnimation();
        this.hunger += basicAttackStrength;
        this.health += basicAttackStrength;
    }

    @Override
    protected void agingCycle() {

    }

    @Override
    protected void reproductionCycle() {

    }

    @Override
    public void behavior() {

        if(checkCollisionsEAT(this, MonsterList.getInstance().getMonsters(), NPCType.Spirit)){
            eat();
        }
        moveNpcAndSignalTrueIfWeMove();
    }

    @Override
    public void destroy() {

    }

    @Override
    public String returnNpcType() {
        return "";
    }

    @Override
    protected void spriteHandler() {

    }
}
