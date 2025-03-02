package entities.NPC.Monsters;

import entities.NPC.Monsters.MonsterLogic.MonsterList;
import util.ImgLoader;
import graphics.ScreenSettings;

import java.awt.image.BufferedImage;

public class Slime_Flower extends Monster {
    MonsterList monsterListInstance;
    static BufferedImage image = ImgLoader.getImageResource("sprites/monster/slime_flower.png");

    public Slime_Flower( int x, int y) {
        super(18, x, y);
        monsterListInstance = MonsterList.getInstance();
        this.screenPositionX = x;
        this.screenPositionY = y;
        this.tilePositionX = x / ScreenSettings.TILE_SIZE;
        this.tilePositionY =y / ScreenSettings.TILE_SIZE;
        this.lifespan = 300;
        this.basicAttackStrength = 2;
    }

    @Override
    protected void setImage() {
        image = ImgLoader.getImageResource("sprites/monster/slime_flower.png");
    }

    @Override
    public BufferedImage getImage() {
        return image;
    }

    @Override
    public void behavior() {
        if(health <= 0) {
            isDead = true;
            return;
        }
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

        //this.image = null;
    }
    public String returnNpcType(){
        return "Slime Flower";
    }
}
