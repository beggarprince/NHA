package entities.NPC.Monsters;

import entities.NPC.Monsters.MonsterLogic.DungeonIQ;
import   entities.NPC.Monsters.MonsterLogic.MonsterFactory;
import   entities.NPC.Monsters.MonsterLogic.MonsterList;
import entities.NPC.NPCType;
import   graphics.Sprite.ImgLoader;
import   graphics.ScreenSettings;
import   graphics.Sprite.SpriteConstants;

import java.awt.image.BufferedImage;

public class Slime_Flower extends Monster {
    MonsterList monsterListInstance;
    static BufferedImage image = ImgLoader.getImageResource(SpriteConstants.MONSTER_SLIME_FLOWER);
    static final NPCType type = NPCType.Slime_Flower;
    public Slime_Flower( int x, int y) {
        super(DungeonIQ.getMonsterState(type).getBaseHp(), x, y);
        this.basicAttackStrength = DungeonIQ.getMonsterState(type).getBaseAtt();

        monsterListInstance = MonsterList.getInstance();
        this.screenPositionX = x;
        this.screenPositionY = y;
        this.tilePositionX = x / ScreenSettings.TILE_SIZE;
        this.tilePositionY =y / ScreenSettings.TILE_SIZE;
        this.lifespan = ScreenSettings.FPS * 30;
        this.basicAttackStrength = 2;

        maxHunger = 12; // These should be doing photosynthesis or something
    }

    @Override
    protected void setImage() {
        image = ImgLoader.getImageResource(SpriteConstants.MONSTER_SLIME_FLOWER);
    }

    @Override
    public BufferedImage getImage() {
        return image;
    }

    @Override
    public void behavior() {
        assert this.basicAttackStrength != 0 : "Attack strength is 0";

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
