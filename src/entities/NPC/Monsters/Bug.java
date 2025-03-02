package entities.NPC.Monsters;

import entities.NPC.Monsters.MonsterLogic.MonsterList;
import entities.NPC.NPCType;
import entities.SpriteCoordinate;
import graphics.ScreenSettings;
import util.ImgLoader;

import java.awt.image.BufferedImage;

import static Game.NPCLogicKTKt.checkCollisionsEAT;

public class Bug extends Monster {
int spriteCounter = 0;

//Direction, then sprite data
//SpriteCoordinate[][] walking;
//SpriteCoordinate[][] combat;
//SpriteCoordinate[] death;

//col row width height


    //105, 7 stages for now
    //48*48
    //Walking left
    //21, 56 - 91, 160, 232
    //16, 108 - 90, 165, 237

    private static final SpriteCoordinate[][] spriteCoordinateWalking = {
            {
                    new SpriteCoordinate(21,56,48,48),
                    new SpriteCoordinate(91,56,48,48),
                    new SpriteCoordinate(160,56,48,48),
                    new SpriteCoordinate(232,56,48,48),
                    new SpriteCoordinate(16,108,48,48),
                    new SpriteCoordinate(90,108,48,48),
                    new SpriteCoordinate(165,108,48,48),
                    new SpriteCoordinate(237,108,48,48)
            }
    };

    static final BufferedImage image =  ImgLoader.getImageResource("sprites/monster/bug.png");

    public Bug( int x, int y) {
        super(32, x, y);

           // setImage();
            this.hunger = 0;
            //this.image = ImgLoader.getImageResource("bug.png"); //Default slime preloaded
            this.movementSpeed = 1;
            this.lifespan = ScreenSettings.FPS * 45;
            this.hasFullStomach = false;
            this.maxHunger = 32;
            this.basicAttackStrength = 4;
            this.type = NPCType.Bug;
    }

    //To change sprite, atm not being used bc sprite sheets are now being used instead of individual sprites
    @Override
    protected void setImage() {
      //  image = ImgLoader.getImageResource("bug.png");
    }

    @Override
    public void destroy() {
      //  this.image = null;
    }

    @Override
    public BufferedImage getImage() {
        return returnSprite();
    }

    @Override
    public void behavior() {

        spriteHandler();

        //TODO figure out if this snippet is necessary of if the logic loop handles this already
        if(health <= 0) {
            isDead = true;
            return;
        }

        //check if they can eat
        if(checkCollisionsEAT(this, MonsterList.getInstance().getMonsters(), NPCType.Slime)){
            eat();
        }
        else {
            if (moveNpcAndSignal()) ;
        }

    }

    @Override
    public void eat() {
        this.hunger += basicAttackStrength;
        this.health += basicAttackStrength;
    }

    @Override
    protected void agingCycle() {
        lifespan--;
        if(lifespan == 0){
            //There needs to be code  here to determine whether the slime reproduces or just dies
            metamorphosisReady = false;
        }
    }

    @Override
    protected void reproductionCycle() {

    }

    public String returnNpcType(){
        return "Bug";
    }

    int spriteStage = 0;

    private void spriteHandler(){
        spriteCounter++;

        if(spriteCounter == 105){
            spriteCounter = 0;
            spriteStage = 0;
        }

        if(spriteCounter % 15 == 0){
            spriteStage++;
        }

    }


    // bug where this returns null
    //It has nothing to do with the image being out of bounds as a hardcoded 21,56,48,48 fails to paint
    //It also has nothing to do with spriteStage since we weren't using it when hardcoding, or the array for the sprite data points
    //It has nothing to do with the bug being dead since they're always at full health when i try to kill them
    //It has nothing to do with image being null or something because i initialized it in the constructor and it still failed to paint
    //Setting to final seems to have done the trick
    private BufferedImage returnSprite(){
        //int x = spriteCoordinateWalking[0][0].col;
        SpriteCoordinate temp =  spriteCoordinateWalking[0][spriteStage];


            BufferedImage subImage = image.getSubimage(temp.col, temp.row, temp.width, temp.height);
        //BufferedImage subImage = image.getSubimage(21,56,48,48);

            return subImage;

    }


}
