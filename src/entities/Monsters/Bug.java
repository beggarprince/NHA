package entities.Monsters;

import entities.NPCType;
import graphics.ScreenSettings;
import util.ImgLoader;

import java.awt.image.BufferedImage;

import static Game.NPCLogicKTKt.checkCollisionsEAT;

public class Bug extends Monster {

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
        return image;
    }

    @Override
    public void behavior() {

        //check if they can eat
        if(checkCollisionsEAT(this, MonsterList.getInstance().getMonsters(), NPCType.Slime)){
            //if then it ate idk what i want to do here
            return;
        }
        else {
            if (npcMoved()) eatingCycleReady = true; // we are at a new tile
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
}
