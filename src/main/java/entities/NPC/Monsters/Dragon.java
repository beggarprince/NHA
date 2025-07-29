package entities.NPC.Monsters;

import   entities.NPC.Monsters.MonsterLogic.MonsterList;
import   entities.NPC.NPCType;
import   entities.SpriteCoordinate;
import   graphics.ScreenSettings;
import   graphics.Sprite.ImgLoader;
import   graphics.Sprite.SpriteConstants;

import java.awt.image.BufferedImage;

import static   entities.NPC.NPCLogicKTKt.checkCollisionsEAT;

public class Dragon extends Monster{

    static BufferedImage image = ImgLoader.getImageResource(SpriteConstants.MONSTER_DRAGON);

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


    private final static int[] spriteArrayCoordinateCount = {
            28, // Walk hor
            0, // walk ver
            4, // attack hor
            0, // attack ver
            10// death
    };

    private static final SpriteCoordinate[][] spriteCoordinates = {
            //Walking horizontally - 0
            {
                    new SpriteCoordinate(50, 48, 96, 96),
                    new SpriteCoordinate(210, 48, 96, 96),
                    new SpriteCoordinate(370, 48, 96, 96),
                    new SpriteCoordinate(530, 48, 96, 96),
                    //
                    new SpriteCoordinate(50, 162, 96, 96),
                    new SpriteCoordinate(210, 162, 96, 96),
                    new SpriteCoordinate(370, 162, 96, 96),
                    new SpriteCoordinate(530, 162, 96, 96),
                    //From here i'm doing it mathematically assuming the spritesheet is consistent
                    new SpriteCoordinate(50, 276, 96, 96),
                    new SpriteCoordinate(210, 276, 96, 96),
                    new SpriteCoordinate(370, 276, 96, 96),
                    new SpriteCoordinate(530, 276, 96, 96),
                    //new row
                    new SpriteCoordinate(50, 390, 96, 96),
                    new SpriteCoordinate(210, 390, 96, 96),
                    new SpriteCoordinate(370, 390, 96, 96),
                    new SpriteCoordinate(530, 390, 96, 96),
                    //
                    new SpriteCoordinate(50, 504, 96, 96),
                    new SpriteCoordinate(210, 504, 96, 96),
                    new SpriteCoordinate(370, 504, 96, 96),
                    new SpriteCoordinate(530, 504, 96, 96),
                    //
                    new SpriteCoordinate(50, 618, 96, 96),
                    new SpriteCoordinate(210, 618, 96, 96),
                    new SpriteCoordinate(370, 618, 96, 96),
                    new SpriteCoordinate(530, 618, 96, 96),
                    //
                    new SpriteCoordinate(50, 732, 96, 96),
                    new SpriteCoordinate(210, 732, 96, 96),
                    new SpriteCoordinate(370, 732, 96, 96),
                    new SpriteCoordinate(530, 732, 96, 96)
            },

            //Walking vertically - 1
            {
                    //DRAGON CANNOT MOVE VERTICALLY

            },

            //Attack Horizontally - 2
            {
                    //This one is bigger than 96x96
                    new SpriteCoordinate(42, 883, 101, 96),
                    new SpriteCoordinate(201, 883, 101, 96),
                    new SpriteCoordinate(362, 883, 96, 96),
                    new SpriteCoordinate(518, 883, 104, 96)
            },

            //Attacking Vertically - 3
            {
                    //DRAGON CANNOT ATTACK VERTICALLY
            },

            //Death - 4
            {
                    new SpriteCoordinate(42, 1034, 102, 96),
                    new SpriteCoordinate(208, 1043, 102, 96),
                    new SpriteCoordinate(374, 1025, 102, 103),
                    new SpriteCoordinate(526, 1025, 102, 103),
                    new SpriteCoordinate(691, 1025, 102, 103),
                    new SpriteCoordinate(41, 1154, 107, 96),
                    new SpriteCoordinate(192, 1154, 113, 96),
                    new SpriteCoordinate(343, 1154, 122, 96),
                    new SpriteCoordinate(505, 1154, 122, 96),
                    new SpriteCoordinate(665, 1154, 122, 96)
            }

    };



    @Override
    protected void setImage() {
        image = ImgLoader.getImageResource(SpriteConstants.MONSTER_DRAGON);
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
        if(checkCollisionsEAT(this, MonsterList.getInstance().getMonsters(), NPCType.LizardMan)){
            eat();
        }
        moveNpcAndSignalTrueIfWeMove();
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
