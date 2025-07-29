package entities.NPC.Monsters;

import   entities.NPC.NPCType;
import   entities.SpriteCoordinate;
import   graphics.ScreenSettings;
import   graphics.SpriteSettings;
import   graphics.Sprite.ImgLoader;
import   graphics.Sprite.SpriteConstants;

import java.awt.image.BufferedImage;

public class Bug extends Monster {
    int spriteFrameTimeCounter = 0;
    int spriteFrame = 0;
    private int previousSpriteArray = 0;
    static final BufferedImage image =  ImgLoader.getImageResource(SpriteConstants.MONSTER_BUG);
    static final int bugLifespan = ScreenSettings.FPS * 45;
    static final String metamorphosisValue = "";
    static final int hungerDecCounter = 5;

//col row width height

    //105, 7 stages for now
    //48*48
    //Walking left
    //21, 56 - 91, 160, 232
    //16, 108 - 90, 165, 237

    private final static int[] spriteArrayCoordinateCount = {
            8, // Walk hor
            8, // walk ver
            12, // attack hor
            12, // attack ver
            9 // death
    };

    private static final SpriteCoordinate[][] spriteCoordinates = {
            //Walking horizontally - 0
            {
                    new SpriteCoordinate(21,56,48,48),
                    new SpriteCoordinate(91,56,48,48),
                    new SpriteCoordinate(160,56,48,48),
                    new SpriteCoordinate(232,56,48,48),
                    new SpriteCoordinate(16,108,48,48),
                    new SpriteCoordinate(90,108,48,48),
                    new SpriteCoordinate(165,108,48,48),
                    new SpriteCoordinate(237,108,48,48)
            },
            //Walking vertically - 1
            {
                    new SpriteCoordinate(10, 182, 24, 42),
                    new SpriteCoordinate(34, 182, 24, 42),
                    new SpriteCoordinate(58, 182, 24, 42),
                    new SpriteCoordinate(82, 182, 24, 42),
                    new SpriteCoordinate(106, 182, 24, 42),
                    new SpriteCoordinate(130, 182, 24, 42),
                    new SpriteCoordinate(154, 182, 24, 42),
                    new SpriteCoordinate(178, 182, 24, 42)
            },
            //Attack Horizontally - 2
            {
                    new SpriteCoordinate(27, 300, 39, 24),
                    new SpriteCoordinate(97, 300, 39, 24),
                    new SpriteCoordinate(171, 297, 39, 27),
                    new SpriteCoordinate(247, 291, 33, 29),
                    new SpriteCoordinate(313, 279, 39, 39),
                    new SpriteCoordinate(376, 291, 48, 33),
                    new SpriteCoordinate(27, 366, 37, 20),
                    new SpriteCoordinate(103, 351, 33, 31),
                    new SpriteCoordinate(169, 339, 39, 39),
                    new SpriteCoordinate(232, 351, 48, 33),
                    new SpriteCoordinate(315, 365, 37, 21),
                    new SpriteCoordinate(385, 360, 40, 24)
            },
            //Attacking Vertically - 3
            {
                    new SpriteCoordinate(18, 454, 33, 42),
                    new SpriteCoordinate(67, 454, 33, 42),
                    new SpriteCoordinate(112, 454, 33, 42),
                    new SpriteCoordinate(166, 451, 33, 42),
                    new SpriteCoordinate(214, 430, 36, 63),
                    new SpriteCoordinate(256, 427, 36, 66),
                    new SpriteCoordinate(14, 553, 32, 51),
                    new SpriteCoordinate(68, 553, 32, 51),
                    new SpriteCoordinate(118, 541, 36, 60),
                    new SpriteCoordinate(159, 535, 36, 66),
                    new SpriteCoordinate(203, 553, 36, 51),
                    new SpriteCoordinate(262, 568, 24, 36)
            },
            //Death - 4
            {
                    new SpriteCoordinate(33, 730, 48, 32),
                    new SpriteCoordinate(130, 723, 48, 32),
                    new SpriteCoordinate(225, 729, 48, 32),
                    new SpriteCoordinate(316, 710, 54, 45),
                    new SpriteCoordinate(409, 680, 60, 60),
                    new SpriteCoordinate(22, 788, 66, 30),
                    new SpriteCoordinate(118, 785, 63, 51),
                    new SpriteCoordinate(208, 800, 72, 63),
                    new SpriteCoordinate(316, 812, 60, 56)
            }
    };

    public Bug( int x, int y) {
        super(32, x, y);
        // setImage();
        this.hunger = 0;
        this.movementSpeed = 1;
        this.lifespan = bugLifespan;
        this.hasFullStomach = false;
        this.maxHunger = 60;
        this.basicAttackStrength = 4;
        this.type = NPCType.Bug;
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
            moveNpcAndSignalTrueIfWeMove();
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

        if(previousSpriteArray != determineSpriteArray()){
            spriteFrameTimeCounter = 0;
            spriteFrame = 0;
            previousSpriteArray = determineSpriteArray();
        }
        spriteFrameTimeCounter++;

        if(spriteFrameTimeCounter == (spriteArrayCoordinateCount[determineSpriteArray()]-1 ) * SpriteSettings.ANIMATION_LENGTH){
            spriteFrameTimeCounter = 0;
            spriteFrame = 0;
        }

        if(spriteFrameTimeCounter % SpriteSettings.ANIMATION_LENGTH == 0){
            spriteFrame++;
        }
        //System.out.println(spriteFrame);

    }


    // bug where this returns null
    //It has nothing to do with the image being out of bounds as a hardcoded 21,56,48,48 fails to paint
    //It also has nothing to do with spriteStage since we weren't using it when hardcoding, or the array for the sprite data points
    //It has nothing to do with the bug being dead since they're always at full health when i try to kill them
    //It has nothing to do with image being null or something because i initialized it in the constructor and it still failed to paint
    //Setting to final seems to have done the trick
    private BufferedImage returnSprite(){
        //int x = spriteCoordinateWalking[0][0].col;
        SpriteCoordinate temp =  spriteCoordinates[determineSpriteArray()][spriteFrame];

        BufferedImage subImage = image.getSubimage(temp.col, temp.row, temp.width, temp.height);
        //BufferedImage subImage = image.getSubimage(21,56,48,48);

        return subImage;

    }

}
