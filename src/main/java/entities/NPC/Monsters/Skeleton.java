package entities.NPC.Monsters;

import   entities.NPC.NPCType;
import   graphics.ScreenSettings;
import   graphics.Sprite.ImgLoader;
import   graphics.Sprite.SpriteConstants;

import java.awt.image.BufferedImage;

public class Skeleton extends Monster{

    static BufferedImage image = ImgLoader.getImageResource(SpriteConstants.MONSTER_SKELETON);

    public Skeleton(int health, int x, int y) {
        super(health, x, y);

        this.image = ImgLoader.getImageResource("sprites/monster/skeleton.png");
        this.basicAttackStrength = 24;
        this.hunger = 0;
        this.movementSpeed = 1;
        this.lifespan = ScreenSettings.FPS * 65;
        this.hasFullStomach = false;
        this.maxHunger = 32;
        this.type = NPCType.Skeleton;
    }

    @Override
    protected void setImage() {

    }

    @Override
    public BufferedImage getImage() {
        return image;
    }

    @Override
    public void eat() {

    }

    @Override
    protected void agingCycle() {

    }

    @Override
    protected void reproductionCycle() {

    }

    @Override
    public void behavior() {
        npcHasMoved();
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
