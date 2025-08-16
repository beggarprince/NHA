package entities.NPC.Monsters;

import entities.NPC.Monsters.MonsterLogic.DungeonIQ;
import   entities.NPC.Monsters.MonsterLogic.EatingSystem;
import entities.NPC.NPCType;
import   graphics.ScreenSettings;
import   level.TileType;
import   graphics.Sprite.ImgLoader;
import   graphics.Sprite.SpriteConstants;

import java.awt.image.BufferedImage;

public class Spirit extends Monster {

    static BufferedImage image = ImgLoader.getImageResource(SpriteConstants.MONSTER_SPIRIT);
    static final NPCType type = NPCType.Spirit;

    public Spirit(int x, int y) {
        super(DungeonIQ.getNPCState(type).getBaseHp(), x, y);
        this.basicAttackStrength = DungeonIQ.getNPCState(type).getBaseAtt();
        this.tilePositionX = x / ScreenSettings.TILE_SIZE;
        this.tilePositionY =y / ScreenSettings.TILE_SIZE;
        this.screenPositionX = x;
        this.screenPositionY = y;
        this.lifespan = ScreenSettings.FPS * 45;
        this.movementSpeed = 1;
        this.movementCycle = 0;
        this.basicAttackStrength = 1;
    }

    @Override
    protected void setImage() {
        //Will be used to change sprite
        image = ImgLoader.getImageResource(SpriteConstants.MONSTER_SPIRIT);
    }

    @Override
    public void destroy() {

        //this.image = null;
    }

    // Getter for the image if needed
    public BufferedImage getImage() {
        return image;
    }

    public void behavior(){
        if(health == 0){
            isDead = true;
            return;
        }

        if(eatingCycleReady) {
            if (EatingSystem.L1checkIfHungryAndEat(this, TileType.MANA))
                return; // we have acted and thus we need a slight cooldown before we act again
        }

        //We see if we can move this direction
        if (npcHasMoved()) ;
        if (detectNewTile()) eatingCycleReady = true;
        agingCycle();

    }

    public void eat(){
        EatingSystem.l1EatNutrient(this, TileType.MANA);
    }

    @Override
    protected void agingCycle() {

    }

    protected void reproductionCycle(){

    }

    public String returnNpcType(){
        return "Spirit";
    }

    @Override
    protected void spriteHandler() {

    }


}
