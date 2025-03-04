package main.java.entities.NPC.Monsters;

import main.java.entities.NPC.Monsters.MonsterLogic.MonsterList;
import main.java.entities.NPC.NPCType;
import main.java.graphics.ScreenSettings;
import main.java.util.ImgLoader;

import java.awt.image.BufferedImage;

import static main.java.Game.NPCLogicKTKt.checkCollisionsEAT;

public class LizardMan extends Monster{


    static final BufferedImage image =  ImgLoader.getImageResource("sprites/monster/lmanSheet.png");

    public LizardMan(int x, int y){
        super(64, x, y);
        setImage();
        this.lifespan = ScreenSettings.FPS * 65;
        this.basicAttackStrength = 16;
        this.type = NPCType.LizardMan;
        this.movementSpeed = 1;
        this.hasFullStomach = false;
        this.maxHunger = 32;
    }

    @Override
    protected void setImage() {
        //this.image = ImgLoader.getImageResource("lmanSheet.png");

    }

    @Override
    public BufferedImage getImage() {
        return  image.getSubimage(29, 69, 68, 68);
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

        //check if they can eat
        if(checkCollisionsEAT(this, MonsterList.getInstance().getMonsters(), NPCType.Bug)){
            eat();
            return;
        }
        else {
            if (moveNpcAndSignal());
        }
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
