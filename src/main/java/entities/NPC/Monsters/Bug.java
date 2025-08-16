package entities.NPC.Monsters;

import entities.NPC.Monsters.MonsterLogic.DungeonIQ;
import   entities.NPC.NPCType;
import   graphics.ScreenSettings;
import graphics.Sprite.SpriteType;
import   graphics.SpriteSettings;
import   graphics.Sprite.ImgLoader;
import   graphics.Sprite.SpriteConstants;

import java.awt.image.BufferedImage;

public class Bug extends Monster {


    static final BufferedImage image =  ImgLoader.getImageResource(SpriteConstants.MONSTER_BUG);
    static final int bugLifespan = ScreenSettings.FPS * 45;
    static final String metamorphosisValue = "";
    static final int hungerDecCounter = 5;
    static final NPCType type = NPCType.Bug;


//col row width height

    //105, 7 stages for now
    //48*48
    //Walking le
    //21, 56 - 91, 160, 232
    //16, 108 - 90, 165, 237

    private final static int[] spriteArrayCoordinateCount = {
            8, // Walk hor
            8, // walk ver
            12, // attack hor
            12, // attack ver
            9 // death
    };


    private static final BufferedImage[][] spriteSubsets = {
            //Walking horizontally - 0
            {
                    image.getSubimage(21,56,48,48),
                    image.getSubimage(91,56,48,48),
                    image.getSubimage(160,56,48,48),
                    image.getSubimage(232,56,48,48),
                    image.getSubimage(16,108,48,48),
                    image.getSubimage(90,108,48,48),
                    image.getSubimage(165,108,48,48),
                    image.getSubimage(237,108,48,48)
            },
            //Walking vertically - 1
            {
                    image.getSubimage(10, 182, 24, 42),
                    image.getSubimage(34, 182, 24, 42),
                    image.getSubimage(58, 182, 24, 42),
                    image.getSubimage(82, 182, 24, 42),
                    image.getSubimage(106, 182, 24, 42),
                    image.getSubimage(130, 182, 24, 42),
                    image.getSubimage(154, 182, 24, 42),
                    image.getSubimage(178, 182, 24, 42)
            },
            //Attack Horizontally - 2
            {
                    image.getSubimage(27, 300, 39, 24),
                    image.getSubimage(97, 300, 39, 24),
                    image.getSubimage(171, 297, 39, 27),
                    image.getSubimage(247, 291, 33, 29),
                    image.getSubimage(313, 279, 39, 39),
                    image.getSubimage(376, 291, 48, 33),
                    image.getSubimage(27, 366, 37, 20),
                    image.getSubimage(103, 351, 33, 31),
                    image.getSubimage(169, 339, 39, 39),
                    image.getSubimage(232, 351, 48, 33),
                    image.getSubimage(315, 365, 37, 21),
                    image.getSubimage(385, 360, 40, 24)
            },
            //Attacking Vertically - 3
            {
                    image.getSubimage(18, 454, 33, 42),
                    image.getSubimage(67, 454, 33, 42),
                    image.getSubimage(112, 454, 33, 42),
                    image.getSubimage(166, 451, 33, 42),
                    image.getSubimage(214, 430, 36, 63),
                    image.getSubimage(256, 427, 36, 66),
                    image.getSubimage(14, 553, 32, 51),
                    image.getSubimage(68, 553, 32, 51),
                    image.getSubimage(118, 541, 36, 60),
                    image.getSubimage(159, 535, 36, 66),
                    image.getSubimage(203, 553, 36, 51),
                    image.getSubimage(262, 568, 24, 36)
            },
            //Death - 4
            {
                    image.getSubimage(33, 730, 48, 32),
                    image.getSubimage(130, 723, 48, 32),
                    image.getSubimage(225, 729, 48, 32),
                    image.getSubimage(316, 710, 54, 45),
                    image.getSubimage(409, 680, 60, 60),
                    image.getSubimage(22, 788, 66, 30),
                    image.getSubimage(118, 785, 63, 51),
                    image.getSubimage(208, 800, 72, 63),
                    image.getSubimage(316, 812, 60, 56)
            }
    };



    public Bug( int x, int y) {
        super(DungeonIQ.getMonsterState(type).getBaseHp(), x, y);
        this.basicAttackStrength = DungeonIQ.getMonsterState(type).getBaseAtt();
        this.hunger = 0;
        this.movementSpeed = 1;
        this.lifespan = bugLifespan;
        this.hasFullStomach = false;
        this.maxHunger = 60;
        this.basicAttackStrength = 4;
    }

    //To change sprite, atm not being used bc sprite sheets are now being used instead of individual sprites
    //Maybe for like upgrading or evoultion where we change color palette i'll just change the buffered image
    @Override
    protected void setImage() {
        //  image = ImgLoader.getImageResource("bug.png");
    }

    //Also useless atm UNLESS we want npc specific death logic like blowing up
    @Override
    public void destroy() {
        //  this.image = null;
    }

    //Returns for canvas
    @Override
    public BufferedImage getImage() {
        return returnSprite();
    }

    @Override
    public void behavior() {

        //TODO figure out if this snippet is necessary of if the logic loop handles this already
        if(health <= 0) {
            isDead = true;
            return;
        }

        //decrease hunger every 5 frames
        if(this.hunger % 5 == 0){
            hunger--;
            if(hunger < maxHunger / 2){
                hasFullStomach = false;
            }
        }

        if(!hasFullStomach)basicPredation(NPCType.Slime);
        //Wonky code. If the bug is not hungry i'm not checking lists, that doesn't mean we don't move
        //We also don't want to move if eat() runs, like at all.
        else {
            //System.out.println("The bug is not hungry");
            npcHasMoved();
        }


    }

    @Override
    public void eat() {
       basicPredatorEat(this);
    }

    @Override
    protected void agingCycle() {
        lifespan--;
        if(lifespan == 0){
            //There needs to be code  here to determine whether the slime reproduces or just dies
            if(this.hunger >= this.maxHunger/2 )metamorphosisReady = true;
        }
    }


    //Not yet implemented
    @Override
    protected void reproductionCycle() {

    }

    public String returnNpcType(){
        return "Bug";
    }

    @Override
    protected void spriteHandler(){

        SpriteType currentSpriteArray = determineSpriteArray();
        spriteArrayIndex = simpleSpriteToArray(currentSpriteArray);

        if(spriteType != currentSpriteArray){
            //System.out.println("Changing to " + currentSpriteArray + ":" + spriteArrayIndex);
            //reset the frame counters
            spriteFrameTimeCounter = 0;
            spriteFrame = 0;
            spriteType =  currentSpriteArray;
        }
        spriteFrameTimeCounter++;

        if(spriteFrameTimeCounter == (spriteArrayCoordinateCount[spriteArrayIndex]-1 )
                * SpriteSettings.ANIMATION_LENGTH){
            spriteFrameTimeCounter = 0;
            spriteFrame = 0;
        }

        if(spriteFrameTimeCounter % SpriteSettings.ANIMATION_LENGTH == 0){
            spriteFrame++;
        }

    }



    private BufferedImage returnSprite(){
        //int x = spriteCoordinateWalking[0][0].col;
        //SpriteCoordinate temp =  spriteCoordinates[spriteArrayIndex][spriteFrame];
        //BufferedImage subImage = image.getSubimage(temp.col, temp.row, temp.width, temp.height);

        return spriteSubsets[spriteArrayIndex][spriteFrame];

    }

}
