package entities.NPC.Monsters;

import entities.Direction;
import entities.NPC.Monsters.MonsterLogic.DungeonIQ;
import   entities.NPC.NPCType;
import   graphics.ScreenSettings;
import   graphics.Sprite.ImgLoader;
import   graphics.Sprite.SpriteConstants;
import graphics.SpriteSettings;

import java.awt.image.BufferedImage;

public class Skeleton extends Monster{

    static BufferedImage image = ImgLoader.getImageResource(SpriteConstants.MONSTER_SKELETON);

    //TODO replace this with something uniform, this is due to lack of assets
    private int index = 0;
    private int skeletonTempHandler(){
        if(inCombat){
            if(currDirection == Direction.UP){
                return 3;
            }
            return 2;
        }
        if(currDirection == Direction.UP){
            return 1;
        }
        return 0;
    }



    public static final NPCType type = NPCType.Skeleton;

    public Skeleton(int health, int x, int y) {
        super(DungeonIQ.getMonsterState(type).getBaseHp(), x, y);
        this.basicAttackStrength = DungeonIQ.getMonsterState(type).getBaseAtt();
        image = ImgLoader.getImageResource("sprites/monster/skeletonSheet.png");
        this.hunger = 0;
        this.movementSpeed = 1;
        this.lifespan = ScreenSettings.FPS * 65;
        this.hasFullStomach = false;
        this.maxHunger = 32;
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
        spriteHandler();
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

        var t = skeletonTempHandler();
        if(t != index){
            index = t;
            spriteFrame = 0;
        }
        image = spriteSubsets[index][spriteFrame];
        spriteFrameTimeCounter++;
        if(spriteFrameTimeCounter == SpriteSettings.ANIMATION_LENGTH) {
            spriteFrame++;
            spriteFrameTimeCounter = 0;
            if (spriteFrame >= spriteSubsets[index].length) spriteFrame = 0;
        }
    }

    //index, frame
    static final BufferedImage[][] spriteSubsets = {
            //0 Walking left down right
            {
                    image.getSubimage(5, 81, 53, 53),
                    image.getSubimage(55, 81, 53, 53),
                    image.getSubimage(106, 81, 53, 53),
                    image.getSubimage(154, 81, 53, 53),
                    image.getSubimage(250, 81, 53, 53)
            },

            //1 Walking up
            {
                    image.getSubimage(55, 214, 53, 53),
                    image.getSubimage(88, 214, 53, 53),
                    image.getSubimage(157, 214, 53, 53),
                    image.getSubimage(230, 214, 53, 53),
                    image.getSubimage(302, 214, 53, 53),
                    image.getSubimage(375, 214, 53, 53)
            },
            // 2 Attack asd
            {
                    image.getSubimage(28, 347, 53,53),
                    image.getSubimage(126, 347, 53,53),
                    image.getSubimage(216, 347, 53,53),
                    image.getSubimage(308, 347, 53,53),
                    image.getSubimage(395, 344, 59,59),
                    image.getSubimage(502, 347, 53,53),
                    image.getSubimage(502, 347, 53,53),

            },

            // 3 Attack up
            {
                    image.getSubimage(19, 479, 53, 53),
                    image.getSubimage(92, 479, 53, 53),
                    image.getSubimage(165, 479, 53, 53),
                    image.getSubimage(239, 479, 53, 53),
                    image.getSubimage(310, 472, 53, 59),
                    image.getSubimage(380, 479, 53, 53),
                    image.getSubimage(452, 479, 53, 53),

            },

            // 4 DEATH
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
}
