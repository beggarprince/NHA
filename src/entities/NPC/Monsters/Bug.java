package entities.NPC.Monsters;

import entities.NPC.NPCType;
import entities.SpriteCoordinate;
import graphics.ScreenSettings;
import util.ImgLoader;

import java.awt.image.BufferedImage;
import java.util.Arrays;

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

    private static SpriteCoordinate[][] spriteCoordinateWalking = {
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



    public Bug( int x, int y) {
        super(32, x, y);

            setImage();
            this.hunger = 0;
            this.image = ImgLoader.getImageResource("bug.png"); //Default slime preloaded
            this.movementSpeed = 1;
            this.lifespan = ScreenSettings.FPS * 45;
            this.hasFullStomach = false;
            this.maxHunger = 1;
            this.basicAttackStrength = 4;
        System.out.println(image.getWidth() + " x " + image.getHeight());

    }

    @Override
    protected void setImage() {
        image = ImgLoader.getImageResource("bug.png");
    }

    @Override
    public void destroy() {
        this.image = null;
    }

    @Override
    public BufferedImage getImage() {
        return returnSprite();
    }

    @Override
    public void behavior() {
        spriteHandler();
        if(health <= 0) {
            isDead = true;
            return;
        }

        //check if they can eat
        if(checkCollisionsEAT(this, MonsterList.getInstance().getMonsters(), NPCType.Slime)){
            //if then it ate idk what i want to do here
            return;
        }
        else {
            if (moveNpcAndSignal()) eatingCycleReady = true; // we are at a new tile
        }
    }

    @Override
    protected void eat() {

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

    private BufferedImage returnSprite(){
        //int x = spriteCoordinateWalking[0][0].col;
        SpriteCoordinate temp =  spriteCoordinateWalking[0][spriteStage];

        if (temp.col + temp.row <= image.getWidth() && temp.row  + temp.col <= image.getHeight()) {
            BufferedImage subImage = image.getSubimage(temp.col, temp.row, temp.width, temp.height);
          //  System.out.println("Subimage extracted successfully.");
            return subImage;
        } else {
           // System.out.println("Subimage dimensions out of bounds!");
            return image;
        }
    }


    /*
    *
    *
    * 
    *
    * */
}
