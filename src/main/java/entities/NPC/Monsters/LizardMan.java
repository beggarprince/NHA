package entities.NPC.Monsters;

import entities.NPC.Monsters.MonsterLogic.DungeonIQ;
import   entities.NPC.NPCType;
import   graphics.ScreenSettings;
import   graphics.Sprite.ImgLoader;
import   graphics.Sprite.SpriteConstants;

import java.awt.image.BufferedImage;

public class LizardMan extends Monster{

    static final BufferedImage image =  ImgLoader.getImageResource(SpriteConstants.MONSTER_LIZARD);
    static final NPCType type = NPCType.LizardMan;

    public LizardMan(int x, int y){
        super(DungeonIQ.getMonsterState(type).getBaseHp(), x, y);
        this.basicAttackStrength = DungeonIQ.getMonsterState(type).getBaseAtt();
        setImage();
        this.lifespan = ScreenSettings.FPS * 65;
        this.movementSpeed = 1;
        this.hasFullStomach = false;
        this.maxHunger = 32;
    }

    @Override
    protected void setImage() {
        //this.image = ImgLoader.getImageResource("lmanSheet.png");
    }

    @Override
    public BufferedImage getImage()
    {
        return  image.getSubimage(29, 69, 68, 68);
    }

    @Override
    public void eat() {
        basicPredatorEat(this);
    }

    @Override
    protected void agingCycle() {

    }

    @Override
    protected void reproductionCycle() {

    }

    @Override
    public void behavior() {

        if(!hasFullStomach)basicPredation(NPCType.Bug);

        else {
            npcHasMoved();
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
