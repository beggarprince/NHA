package entities.NPC.Monsters;

import util.ImgLoader;

import java.awt.image.BufferedImage;

public class Dragon extends Monster{

    static BufferedImage image = ImgLoader.getImageResource("sprites/monster/dragon.png");

    public Dragon( int x, int y) {
        super(300, x, y);
        setImage();
    }

    @Override
    protected void setImage() {
        image = ImgLoader.getImageResource("sprites/monster/dragon.png");
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
