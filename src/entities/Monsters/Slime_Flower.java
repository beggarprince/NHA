package entities.Monsters;

import util.ImgLoader;
import graphics.ScreenSettings;

import java.awt.image.BufferedImage;

public class Slime_Flower extends Monster {
    MonsterList monsterListInstance;

    public Slime_Flower( int x, int y) {
        super(1, x, y);
        monsterListInstance = MonsterList.getInstance();
        this.screenPositionX = x;
        this.screenPositionY = y;
        this.worldPositionX = x / ScreenSettings.TILE_SIZE;
        this.worldPositionY =y / ScreenSettings.TILE_SIZE;
        this.lifespan = 300;
        this.image = ImgLoader.getImageResource("slime_flower.png");
        this.basicAttackStrength = 2;
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
        lifespan--;
        if(lifespan <= 0){
            this.isDead = true;
        }
    }

    //TODO The reproduction cycle doesn't have to be an abstract fn as Slime will never be able to reproduce but Flower will
    @Override
    protected void reproductionCycle() {
        if(lifespan == 0){
            //Spawn 4 slimes

        }
    }
    public void destroy(){
        this.image = null;
    }
    public String returnNpcType(){
        return "Slime Flower";
    }
}
