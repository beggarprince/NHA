package entities.NPC.Monsters;
import graphics.ScreenSettings;
import util.ImgLoader;
import java.awt.image.BufferedImage;

public class Spirit extends Monster {

    public Spirit(int x, int y) {
        super(16, x, y); // Slime has a default health of 1
        this.image = ImgLoader.getImageResource("spirit.png");
        this.worldPositionX = x / ScreenSettings.TILE_SIZE;
        this.worldPositionY =y / ScreenSettings.TILE_SIZE;
        this.screenPositionX = x;
        this.screenPositionY = y;
        this.lifespan = 3;
        this.movementSpeed = 1;
        this.movementCycle = 0;
        this.basicAttackStrength = 1;
    }

    @Override
    protected void setImage() {
        //Will be used to change sprite
        image = ImgLoader.getImageResource("spirit.png");
    }

    @Override
    public void destroy() {
        this.image = null;
    }

    // Getter for the image if needed
    public BufferedImage getImage() {
        return image;
    }

    public void behavior(){
        if(health <= 0) {
            isDead = true;
            return;
        }
        if(moveNpcAndSignal()) eatingCycleReady = true; // we are at a new tile

        agingCycle();
    }

    protected void eat(){

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
