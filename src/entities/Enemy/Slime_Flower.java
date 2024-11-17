package entities.Enemy;

import graphics.ImgLoader;
import graphics.ScreenSettings;

import java.awt.image.BufferedImage;

public class Slime_Flower extends Enemy{
    EnemyList enemyListInstance;

    public Slime_Flower( int x, int y) {
        super(1, x, y);
        enemyListInstance = EnemyList.getInstance();
        this.enemyScreenPositionX = x;
        this.enemyScreenPositionY = y;
        this.enemyWorldPositionX = x / ScreenSettings.TILE_SIZE;
        this.enemyWorldPositionY =y / ScreenSettings.TILE_SIZE;
        this.enemyLifespan = 300;
        this.image = ImgLoader.getImageResource("slime_flower.png");

    }

    @Override
    protected void setImage() {
        image = ImgLoader.getImageResource("slime_flower.png");
    }

    @Override
    public BufferedImage getImage() {
        return image;
    }

    @Override
    public void behavior() {
        //Since it does move at all we simply never call move function
        agingCycle();
    }

    @Override
    protected void eat() {

    }

    @Override
    protected void agingCycle() {
        enemyLifespan--;
        if(enemyLifespan <= 0){
            this.enemyIsDead = true;
          //  System.out.println("mf died");
        }
    }

    //TODO The reproduction cycle doesn't have to be an abstract fn as Slime will never be able to reproduce but Flower will
    @Override
    protected void reproductionCycle() {
        if(enemyLifespan == 0){
            //Spawn 4 slimes

        }
    }
    public void destroy(){
        this.image = null;
    }
}
