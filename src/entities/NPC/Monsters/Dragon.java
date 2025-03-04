package entities.NPC.Monsters;

import entities.NPC.Monsters.MonsterLogic.MonsterList;
import entities.NPC.NPCType;
import graphics.ScreenSettings;
import util.ImgLoader;

import java.awt.image.BufferedImage;

import static Game.NPCLogicKTKt.checkCollisionsEAT;

public class Dragon extends Monster{

    static BufferedImage image = ImgLoader.getImageResource("sprites/monster/dragon.png");

    public Dragon( int x, int y) {
        super(300, x, y);
        this.basicAttackStrength = 115;
        setImage();
        this.hunger = 0;
        this.movementSpeed = 1;
        this.lifespan = ScreenSettings.FPS * 95;
        this.hasFullStomach = false;
        this.maxHunger = 32;
        this.type = NPCType.Dragon;
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
        this.startAnimation();
        this.hunger += basicAttackStrength;
        this.health += basicAttackStrength;
    }

    @Override
    protected void agingCycle() {

    }

    @Override
    protected void reproductionCycle() {

    }

    @Override
    public void behavior() {
        if(checkCollisionsEAT(this, MonsterList.getInstance().getMonsters(), NPCType.LizardMan)){
            eat();
        }
        moveNpcAndSignal();
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
