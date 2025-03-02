package entities.NPC.Monsters;

import entities.NPC.NPC;
import util.ImgLoader;

import java.awt.image.BufferedImage;

public class Lilith extends Monster{

    static BufferedImage image = ImgLoader.getImageResource("sprites/monster/lilith.png");

    public Lilith( int x, int y) {
        super(48, x, y);
        setImage();
    }

    @Override
    protected void setImage() {
        image = ImgLoader.getImageResource("sprites/monster/lilith.png");
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
