package entities.NPC.Monsters;

import entities.NPC.Monsters.MonsterLogic.DungeonIQ;
import   entities.NPC.NPCType;
import   graphics.ScreenSettings;
import   graphics.Sprite.ImgLoader;
import   graphics.Sprite.SpriteConstants;

import java.awt.image.BufferedImage;

public class Lilith extends Monster{

    static BufferedImage image = ImgLoader.getImageResource(SpriteConstants.MONSTER_LILITH);

    static final NPCType type = NPCType.Lilith;
    public Lilith( int x, int y) {
        super(DungeonIQ.getNPCState(type).getBaseHp(), x, y);
        this.basicAttackStrength = DungeonIQ.getNPCState(type).getBaseAtt();
        setImage();
        this.hunger = 0; // Lilith no eat?
        this.movementSpeed = 1;
        this.lifespan = ScreenSettings.FPS * 65;
        this.hasFullStomach = false;
        this.maxHunger = 32;
    }

    @Override
    protected void setImage() {
        image = ImgLoader.getImageResource(SpriteConstants.MONSTER_LILITH);
    }

    @Override
    public BufferedImage getImage() {
        return image;
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

        if(!hasFullStomach) basicPredation(NPCType.Spirit);
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
