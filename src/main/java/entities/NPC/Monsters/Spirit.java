package main.java.entities.NPC.Monsters;
import main.java.entities.NPC.Monsters.MonsterLogic.EatingSystem;
import main.java.graphics.ScreenSettings;
import main.java.level.TileType;
import main.java.util.ImgLoader;
import java.awt.image.BufferedImage;

public class Spirit extends Monster {

    static BufferedImage image = ImgLoader.getImageResource("sprites/monster/spirit.png");

    public Spirit(int x, int y) {
        super(16, x, y); // Slime has a default health of 1
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
        image = ImgLoader.getImageResource("sprites/monster/spirit.png");
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
            if (EatingSystem.l1EatOrPoopNutrient(this, TileType.MANA))
                return; // we have acted and thus we need a slight cooldown before we act again
        }

        //We see if we can move this direction
        if (moveNpcAndSignal()) ;
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


}
