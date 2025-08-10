package entities.NPC.Heroes;

import graphics.Sprite.ImgLoader;
import graphics.ScreenSettings;

public class Mage extends Hero {

    public Mage(int health,
                int x,
                int y,
                String name) {

        super(health, x, y, name);
        this.name = name;
        this.health = 96;
        this.tilePositionX = x / ScreenSettings.TILE_SIZE;
        this.tilePositionY = y / ScreenSettings.TILE_SIZE;
        this.screenPositionX = x;
        this.screenPositionY = y;
        this.movementSpeed = 1;
        this.movementCycle = 0;
        this.basicAttackStrength = 4;
        this.image = ImgLoader.getImageResource("sprites/hero/knight_mvRight.png");
        this.combatCooldown = 60;
        this.fxIndex = 1;
        this.pathfinding = new HeroPathfinder(this);
        pathfinding.logPath(false);
        lastTilePosX = tilePositionX;
        lastTilePosY = tilePositionY;
        this.hasMVP = false;
    }

    int lastTilePosX;
    int lastTilePosY;

    @Override
    public void behavior() {

        if(this.health <= 0){
            isDead = true;
            return;
        }

        if(npcHasMoved()){

            if(detectNewTile()){
                pathfinding.logPath(hasMVP);

                if(pathfinding.currentlyBacktracking || hasMVP) {
                    currDirection = pathfinding.determinePath(hasMVP);
                }
            }
        }

        else if(movementBlocked){
            this.currDirection = pathfinding.determinePath(hasMVP);
            movementBlocked = false;
        }

    }

    @Override
    public void destroy() {
        image = null;
    }

    public String returnNpcType(){
        return "Mage";
    }

    @Override
    protected void spriteHandler() {

    }
}