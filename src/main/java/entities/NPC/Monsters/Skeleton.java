package entities.NPC.Monsters;

import   entities.NPC.NPCType;
import   graphics.ScreenSettings;
import   graphics.Sprite.ImgLoader;
import   graphics.Sprite.SpriteConstants;

import java.awt.image.BufferedImage;

public class Skeleton extends Monster{

    static BufferedImage image = ImgLoader.getImageResource(SpriteConstants.MONSTER_SKELETON);

    static BufferedImage[][] spriteSubsets = {
        //Walking left down right
            {
                image.getSubimage(5, 81, 53, 53),
                    image.getSubimage(55, 81, 53, 53),
                    image.getSubimage(106, 81, 53, 53),
                    image.getSubimage(154, 81, 53, 53),
                    image.getSubimage(250, 81, 53, 53)
            },

            //Walking up
            {
                    image.getSubimage(55, 214, 53, 53),
                    image.getSubimage(88, 214, 53, 53),
                    image.getSubimage(157, 214, 53, 53),
                    image.getSubimage(230, 214, 53, 53),
                    image.getSubimage(302, 214, 53, 53),
                    image.getSubimage(375, 214, 53, 53)
            },
            //DEATH
            {
                    image.getSubimage(21, 614, 53, 53),
                    image.getSubimage(89, 614, 53, 53),
                    image.getSubimage(156, 603, 64, 64),

                    image.getSubimage(233, 603, 64, 64),
                    image.getSubimage(305, 603, 64, 64),

                    image.getSubimage(15, 698, 64, 64),
                    image.getSubimage(303, 698, 64, 64),

                    image.getSubimage(15, 798, 64, 64),
                    image.getSubimage(87, 798, 64, 64),
                    image.getSubimage(159, 798, 64, 64),
                    image.getSubimage(231, 795, 64, 64),

            }

    };

    public Skeleton(int health, int x, int y) {
        super(health, x, y);

        image = ImgLoader.getImageResource("sprites/monster/skeletonSheet.png");
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
