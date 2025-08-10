package entities.WorldObjects;

import entities.Direction;
import graphics.ScreenSettings;
import level.Level;
import util.CollisionKt;


public class Projectile extends WorldObject{

    private int movementSpeed = 2;
    private Direction direction;
    private boolean destroyed = false;
    private int collisionHealth = 1;
    private int movementCycle; //Increments to avoid % and division check for new tiles


    public Projectile(int tilePositionX,
                      int tilePositionY,
                      Direction direction) {
        super(tilePositionX, tilePositionY);
        this.direction = direction;
        WorldObjectManager.INSTANCE.determineListEntry(this);
    }

    public int getMovementSpeed() {
        return movementSpeed;
    }

    public void setMovementSpeed(int movementSpeed) {
        this.movementSpeed = movementSpeed;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    //Movement
    //This would need to check npc locations.
        //This would make it so i'd have to have an array of linkedlists representing the world of npc for O(1)
        //Just destroy itself on the first collision, or not and keep checking the linkedlist for whatever is next
        //Or that i check every single monster O(n)
    //Instead, i'm likely to have 1-5 projectiles max
    //Npc will instead check the list of projectiles at n * O(5) = n?
    // p * O(n) where n and p are combined sum ~5 is significantly better than the above
    //Who knows how much constant swapping per frame would take up vs an increment/decrement

    public void projectileMovement(){
        //Check if npc flagged this as having been collided
        if(destroyed) return;

        //Displace, check if new position is acceptable
        displaceProjectile();
        if(atNewTile()){
            if(!newTileIsPath()) {
                destroyed = true;
                return;
            }
            updateWorldPosition();
        }
        //We are on prev/new tile which is a path. We already displaced. We're done
    }

    private void displaceProjectile(){
        if(direction == Direction.UP){
            screenPositionY -= movementSpeed;
        }
        else if(direction == Direction.DOWN){
            screenPositionY += movementSpeed;
        }
        else if (direction == Direction.LEFT){
            screenPositionX -= movementSpeed;
        }
        else if(direction == Direction.RIGHT){
            screenPositionX += movementSpeed;
        }
    }

    private boolean atNewTile(){
        movementCycle += movementSpeed;
        if(movementCycle >= ScreenSettings.TILE_SIZE){
            movementCycle = 0;
            return true;
        }
        return false;
    }

    private boolean newTileIsPath(){
        //Returns true if walkable, false if not
        if (direction== Direction.UP) {
            if (tilePositionY> 0) {
                return CollisionKt.tileIsPath(Level.getInstance()
                        .tileData.get(tilePositionY- 1).get(tilePositionX));
            }
        }
        else if (direction== Direction.DOWN) {
            if (tilePositionY < Level.levelRows - 1) {
                return CollisionKt.tileIsPath(Level.getInstance()
                        .tileData.get(tilePositionY+ 1).get(tilePositionX));
            }
        }
        else if (direction== Direction.LEFT) {
            if (tilePositionX > 0) {
                return CollisionKt.tileIsPath(Level.getInstance()
                        .tileData.get(tilePositionY).get(tilePositionX - 1));
            }
        }
        else if (direction== Direction.RIGHT) {
            if (tilePositionX < Level.levelColumns - 1) {
                return CollisionKt.tileIsPath(Level.getInstance()
                        .tileData.get(tilePositionY).get(tilePositionX + 1));
            }
        }
        return false; //we should never be here
    }

    public void collideWithNpc() {
        collisionHealth--;
        if(collisionHealth <= 0) this.destroyed = true;
    }

    public int getCollisionHealth() {
        return collisionHealth;
    }

    //For multiple collision projectiles
    public void overrideCollisionHealth(int collisionHealth) {
        this.collisionHealth = collisionHealth;
    }

    private void updateWorldPosition(){
        if (direction== Direction.UP) {
            tilePositionY--;
        }
        else if (direction== Direction.DOWN) {
            tilePositionY++;
        }
        else if (direction== Direction.LEFT) {
            tilePositionX--;
        }
        else if (direction== Direction.RIGHT) {
            tilePositionX++;
        }
    }
}
