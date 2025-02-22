package entities.NPC.Monsters;

import entities.NPC.NPCType;
import graphics.ScreenSettings;
import util.ImgLoader;

import java.awt.image.BufferedImage;

import static Game.NPCLogicKTKt.checkCollisionsEAT;

public class LizardMan extends Monster{


    static final BufferedImage image =  ImgLoader.getImageResource("lmanSheet.png");

    public LizardMan(int x, int y){
        super(64, x, y);
        setImage();
        this.lifespan = ScreenSettings.FPS * 65;
        this.basicAttackStrength = 16;
        this.type = NPCType.LizardMan;
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
    protected void eat() {

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
            if (moveNpcAndSignal()) eatingCycleReady = true; // we are at a new tile
        }
    }

    @Override
    public void destroy() {
    }

    @Override
    public String returnNpcType() {
        return "";
    }
}
