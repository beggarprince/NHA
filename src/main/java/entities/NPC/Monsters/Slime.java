package entities.NPC.Monsters;

import entities.NPC.Monsters.MonsterLogic.DungeonIQ;
import   entities.NPC.Monsters.MonsterLogic.EatingSystem;
import   entities.NPC.NPCType;
import   graphics.ScreenSettings;
import   level.TileType;
import   graphics.Sprite.ImgLoader;
import   graphics.Sprite.SpriteConstants;

import java.awt.image.BufferedImage;

public class Slime extends Monster {

    static BufferedImage image = ImgLoader.getImageResource(SpriteConstants.MONSTER_SLIME);

    static final String metamorphosisValue = "Slime_Flower";
    static final NPCType type = NPCType.Slime;
    public static final int maxHunger = 12;
   // public static final NPCType type = NPCType.Slime;
    public static final int slimeLifespan = ScreenSettings.FPS * 30;

    public Slime(int x, int y) {
        super(DungeonIQ.getNPCState(type).getBaseHp(), x, y);
        this.basicAttackStrength = DungeonIQ.getNPCState(type).getBaseAtt();

        this.hunger = 0;
        this.movementSpeed = 1;
        //this.image = ImgLoader.getImageResource("slime.png"); //Default slime preloaded
        this.lifespan = slimeLifespan;
        //this.maxHunger = 12;
        this.movementCycle = 0;
    }

    @Override
    protected void setImage() {
        //Will be used to change sprite
        image = ImgLoader.getImageResource("sprites/monster/slime.png");
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

        //TODO change it so it selects a function based on current circumstance, behavior should not be abstract and call abstract function MOVE/REPRODUCE/FIGHT
        if(health == 0){
            isDead = true;
            return;
        }

        if(eatingCycleReady) {
            if (EatingSystem.L1checkIfHungryAndEat(this, TileType.NUTRIENT))
                return; // we have acted and thus we need a slight cooldown before we act again
        }

        //We see if we can move this direction
        if (npcHasMoved()) ;

        if (detectNewTile()) eatingCycleReady = true;
        agingCycle();

    }




    //Extract all the logic, it should only know if it ate and increment itself
    public void eat(){
        EatingSystem.l1EatNutrient(this, TileType.NUTRIENT);

    }

    @Override
    protected void agingCycle() {

        lifespan--;

        if(lifespan == 0 && hunger+4 >= maxHunger){
         //There needs to be code  here to determine whether the slime reproduces or just dies
            metamorphosisReady = true;
        }
    }


    //To be implemented
    protected void reproductionCycle(){

    }

    public String returnNpcType(){
        return "Slime";
    }

    @Override
    protected void spriteHandler() {

    }


}
