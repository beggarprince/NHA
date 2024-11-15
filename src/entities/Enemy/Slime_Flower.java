package entities.Enemy;

import graphics.ImgLoader;

import java.awt.image.BufferedImage;

public class Slime_Flower extends Enemy{
    EnemyList enemyListInstance;

    public Slime_Flower( int x, int y) {
        super(1, x, y);
        enemyListInstance = EnemyList.getInstance();
    }

    @Override
    protected void setImage() {
        image = ImgLoader.getImageResource("slime_flower");
    }

    @Override
    public BufferedImage getImage() {
        return image;
    }

    @Override
    public void behavior() {
        //Since it does move at all we simply never call move function
    }

    @Override
    protected void eat() {

    }

    @Override
    protected void agingCycle() {

    }

    //TODO The reproduction cycle doesn't have to be an abstract fn as Slime will never be able to reproduce but Flower will
    @Override
    protected void reproductionCycle() {
        if(enemyLifespan == 0){
            //Spawn 4 slimes

        }
    }
}
