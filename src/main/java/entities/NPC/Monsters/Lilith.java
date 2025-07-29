package entities.NPC.Monsters;

import   entities.NPC.NPCType;
import   graphics.ScreenSettings;
import   graphics.Sprite.ImgLoader;
import   graphics.Sprite.SpriteConstants;

import java.awt.image.BufferedImage;

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
        basicPredatorEat(this);
    }

    @Override
    protected void agingCycle() {

    }

    @Override
    protected void reproductionCycle() {

    }

    @Override
    public void behavior() {

        if(!hasFullStomach) basicPredation(NPCType.Spirit);
        else {
            moveNpcAndSignalTrueIfWeMove();
        }
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
