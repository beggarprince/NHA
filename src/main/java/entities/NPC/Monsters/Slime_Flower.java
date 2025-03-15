package main.java.entities.NPC.Monsters;

import main.java.entities.NPC.Monsters.MonsterLogic.MonsterFactory;
import main.java.entities.NPC.Monsters.MonsterLogic.MonsterList;
import main.java.util.ImgLoader;
import main.java.graphics.ScreenSettings;
import main.java.util.StringRes;

import java.awt.image.BufferedImage;

public class Slime_Flower extends Monster {
    MonsterList monsterListInstance;
    static BufferedImage image = ImgLoader.getImageResource(StringRes.MONSTER_SLIME_FLOWER);

    public Slime_Flower( int x, int y) {
        super(18, x, y);
        monsterListInstance = MonsterList.getInstance();
        this.screenPositionX = x;
        this.screenPositionY = y;
        this.tilePositionX = x / ScreenSettings.TILE_SIZE;
        this.tilePositionY =y / ScreenSettings.TILE_SIZE;
        this.lifespan = ScreenSettings.FPS * 30;
        this.basicAttackStrength = 2;
        this.hunger = hunger;
        maxHunger = 12;
    }

    @Override
    protected void setImage() {
        image = ImgLoader.getImageResource(StringRes.MONSTER_SLIME_FLOWER);
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
    public void eat() {

    }

    @Override
    protected void agingCycle() {
        lifespan--;
        if(lifespan <= 0){
            endOfLife();
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

    @Override
    protected void spriteHandler() {

    }

    private void endOfLife(){
        if(maxHunger <= hunger) {
            MonsterList.getInstance().addEnemy(MonsterFactory.createMonster("Slime", this.tilePositionX * ScreenSettings.TILE_SIZE, this.tilePositionY * ScreenSettings.TILE_SIZE));
        }
        if(maxHunger /2 <= hunger) {
            MonsterList.getInstance().addEnemy(MonsterFactory.createMonster("Slime", this.tilePositionX * ScreenSettings.TILE_SIZE, this.tilePositionY * ScreenSettings.TILE_SIZE));
        }
    }
}
