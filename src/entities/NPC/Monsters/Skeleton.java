package entities.NPC.Monsters;

import java.awt.image.BufferedImage;

public class Skeleton extends Monster{


    public Skeleton(int health, int x, int y) {
        super(health, x, y);
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
        moveNpcAndSignal();
    }

    @Override
    public void destroy() {

    }

    @Override
    public String returnNpcType() {
        return "";
    }
}
