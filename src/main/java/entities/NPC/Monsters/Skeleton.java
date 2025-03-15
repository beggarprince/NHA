package main.java.entities.NPC.Monsters;

import main.java.entities.NPC.NPCType;
import main.java.graphics.ScreenSettings;

import java.awt.image.BufferedImage;

public class Skeleton extends Monster{


    public Skeleton(int health, int x, int y) {
        super(health, x, y);
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
        return null;
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
        moveNpcAndSignalTrueIfWeMove();
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
