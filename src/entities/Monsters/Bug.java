package entities.Monsters;

import graphics.ScreenSettings;
import util.ImgLoader;

import java.awt.image.BufferedImage;

public class Bug extends Monster {

    public Bug( int x, int y) {
        super(12, x, y);

            setImage();
            this.hunger = 0;

            this.image = ImgLoader.getImageResource("bug.png"); //Default slime preloaded
            this.movementSpeed = 1;
            this.lifespan = ScreenSettings.FPS * 45;
            this.hasFullStomach = false;
            this.maxHunger = 1;


    }

    @Override
    protected void setImage() {
        image = ImgLoader.getImageResource("bug.png");
    }

    @Override
    protected void destroy() {
        this.image = null;
    }

    @Override
    public BufferedImage getImage() {
        return image;
    }

    @Override
    public void behavior() {

        if(npcMoved()) eatingCycleReady = true; // we are at a new tile

    }

    @Override
    protected void eat() {

    }

    @Override
    protected void agingCycle() {

    }

    @Override
    protected void reproductionCycle() {

    }
}
