package entities.Enemy;

import graphics.ScreenSettings;
import graphics.TileType;
import level.Level;

import java.util.Random;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import static util.CollisionKt.detectNPCCollision;
/*
* A clusterfck of functions every enemy will inherit
*
* ATM handles
*   movement
*   eating
*   eatingCycles
*   knowledge check when the creature is in a new tile
*   pooping back into tiles
* */

public abstract class Enemy {
    protected BufferedImage image;
    protected int enemyHealth;
    protected int enemyLifespan;
    protected int enemyHunger;
    protected boolean enemyHasFullStomach = false;
    protected boolean enemyEatingCycleReady = true;
    protected int enemyMaxHunger;
    protected Direction enemyCurrentDirection;
    public boolean enemyIsDead = false;

    //Logical position on array
    public int enemyWorldPositionX;
    public int enemyWorldPositionY;
    //Where the enemy is drawn on the screen, out of bounds enemies from the camera are not rendered
    //World position
    public int enemyScreenPositionX;
    public int enemyScreenPositionY;

    protected int enemyMovementSpeed; // How many pixels an enemy offsets per frame
    protected int enemyMovementCycle; // How long it takes before we logically know we are at a new tile without math
    public String enemyMetamorphosis;
    public boolean enemyMetamorphosisIsReady = false;

    Level level = Level.getInstance("res/levelTest.csv");

    private final Random random = new Random();


    public Enemy(int health, int x, int y) {
        this.enemyHealth = health;

    }

    // Getter and setter methods
    public int getEnemyHealth() {
        return enemyHealth;
    }

    public void setEnemyHealth(int enemyHealth) {
        this.enemyHealth = enemyHealth;
    }



    public int getEnemyWorldPositionX() {
        return enemyWorldPositionX;
    }

    public int getEnemyWorldPositionY() {
        return enemyWorldPositionY;
    }

    protected void decreaseHunger() {
        enemyHunger--;
    }

    protected void damage(int damage) {
        enemyHealth -= damage;
    }

    protected void age() {
        enemyLifespan--;
    }

    protected abstract void setImage();

    public abstract BufferedImage getImage();

    public abstract void behavior();

    protected abstract void eat();

    protected  abstract void agingCycle();

    protected abstract void reproductionCycle();

    protected void death(){
        enemyIsDead = true;
        //Add nutrients back to the ecosystem
    }

    //Movement
    private List<Direction> getPossibleDirections(Boolean collisionCheck){
        // List of possible directions
        List<Direction> possibleDirections = new ArrayList<>();

        possibleDirections.add(Direction.UP);
        possibleDirections.add(Direction.DOWN);
        possibleDirections.add(Direction.LEFT);
        possibleDirections.add(Direction.RIGHT);

        //Will remove if the tiles are walkable, this one is used to EAT as you can't eat PATH
        if(!collisionCheck)  possibleDirections.removeIf(direction -> validateWalkableDirection(direction, enemyWorldPositionX, enemyWorldPositionY));
        else {
            //If not walkable it is a candidate for movement, handles out of bounds as well
            possibleDirections.removeIf(direction -> !validateWalkableDirection(direction, enemyWorldPositionX, enemyWorldPositionY));
        }
        return possibleDirections;
    }


    protected Direction enemyGetRandomDirection(int x, int y) {
        // List of possible directions
        List<Direction> possibleDirections = getPossibleDirections(true);

        for(Direction d : possibleDirections) if(d == enemyCurrentDirection) possibleDirections.remove(d);

        // If no valid directions, return NOT_MOVING
        if (possibleDirections.isEmpty()) {
            return Direction.NOT_MOVING;
        }

        // Randomly select a valid direction
        int index = random.nextInt(possibleDirections.size());

        if(possibleDirections.isEmpty()) return getOppositeDirection(enemyCurrentDirection);
        return possibleDirections.get(index);
    }

    public static Direction getOppositeDirection(Direction dir) {
        switch (dir) {
            case UP:
                return Direction.DOWN;
            case DOWN:
                return Direction.UP;
            case LEFT:
                return Direction.RIGHT;
            case RIGHT:
                return Direction.LEFT;
            default:
                throw new IllegalArgumentException("Unknown direction: " + dir);
        }
    }


    //When checking if false it can be used to check bounds
    public boolean validateWalkableDirection(Direction dir, int x, int y) {
        //Returns true if walkable, false if not
        if (dir == Direction.UP) {
            if (y > 0) {
                return detectNPCCollision(level.tileData.get(y - 1).get(x));
            }
        } else if (dir == Direction.DOWN) {
            if (y < Level.levelRows - 1) {
                return detectNPCCollision(level.tileData.get(y + 1).get(x));
            }
        } else if (dir == Direction.LEFT) {
            if (x > 0) {
                return detectNPCCollision(level.tileData.get(y).get(x - 1));
            }
        } else if (dir == Direction.RIGHT) {
            if (x < Level.levelColumns - 1) {
                return detectNPCCollision(level.tileData.get(y).get(x + 1));
            }
        }
        return false; // If all checks fail, the move is invalid.
    }



    //If we are here we are good to move in our direction of choice
    protected void move(int movementSpeed) {

        if (enemyCurrentDirection == Direction.UP) {
            enemyScreenPositionY -= movementSpeed;
        } else if (enemyCurrentDirection == Direction.DOWN) {
            enemyScreenPositionY += movementSpeed;
        } else if (enemyCurrentDirection == Direction.LEFT) {
            enemyScreenPositionX -= movementSpeed;
        } else if (enemyCurrentDirection == Direction.RIGHT) {
            enemyScreenPositionX += movementSpeed;
        }
        enemyMovementCycle += movementSpeed;

    }


    protected void updateWorldPosition() {
        enemyWorldPositionX = enemyScreenPositionX / ScreenSettings.TILE_SIZE;
        enemyWorldPositionY = enemyScreenPositionY / ScreenSettings.TILE_SIZE;
    }


    //TODO getting possible directions should be it's own method since it's being used 3 times in different methods
    //TODO there should be a method to removeIf and then do out of bounds, atm we can do remove unwalkable and remove walkable
    protected boolean eatSurroundingTile(TileType t){
        //t is target food

        List<Direction> possibleDirections = getPossibleDirections( false);

        for(Direction d : possibleDirections){
            if (d == Direction.UP && enemyWorldPositionY > 0) {
                if(level.tileData.get(enemyWorldPositionY - 1).get(enemyWorldPositionX).type == t) {
                    level.tileData.get(enemyWorldPositionY - 1).get(enemyWorldPositionX).eatNutrients();

                    return true;
                }
            }
            else if (d == Direction.DOWN && enemyWorldPositionY < Level.levelRows -1) {
                if(level.tileData.get(enemyWorldPositionY + 1).get(enemyWorldPositionX).type == t) {
                    level.tileData.get(enemyWorldPositionY + 1).get(enemyWorldPositionX).eatNutrients();

                    return true;
                }
            }
            else if (d == Direction.LEFT && enemyWorldPositionX > 0) {
                if (level.tileData.get(enemyWorldPositionY).get(enemyWorldPositionX - 1).type == t) {

                    level.tileData.get(enemyWorldPositionY).get(enemyWorldPositionX - 1).eatNutrients();
                    return true;
                }
            }
            else if (d == Direction.RIGHT && enemyWorldPositionX < Level.levelColumns -1 ) {
                if(level.tileData.get(enemyWorldPositionY).get(enemyWorldPositionX + 1).type == t) {
                    level.tileData.get(enemyWorldPositionY).get(enemyWorldPositionX + 1).eatNutrients();
                    return true;
                }
            }
        }
        return false;
    }

    protected boolean depositSurroundingTile(TileType t){
        //t is target food

        // List of possible directions
        List<Direction> possibleDirections = getPossibleDirections(false);
        // Since we can only eat non walkable directions we remove if validateWalkableDirection returns true
       // possibleDirections.removeIf(direction -> validateWalkableDirection(direction, worldPosX, worldPosY));
        // whatever is not valid won't be used in the random function

        for(Direction d : possibleDirections){
            if (d == Direction.UP && enemyWorldPositionY > 0) {
                if(level.tileData.get(enemyWorldPositionY - 1).get(enemyWorldPositionX).type == t) {
                    level.tileData.get(enemyWorldPositionY - 1).get(enemyWorldPositionX).depositNutrients();

                    return true;
                }
            }
            else if (d == Direction.DOWN && enemyWorldPositionY < Level.levelRows -1) {
                if(level.tileData.get(enemyWorldPositionY + 1).get(enemyWorldPositionX).type == t) {
                    level.tileData.get(enemyWorldPositionY + 1).get(enemyWorldPositionX).depositNutrients();

                    return true;
                }
            }
            else if (d == Direction.LEFT && enemyWorldPositionX > 0) {
                if (level.tileData.get(enemyWorldPositionY).get(enemyWorldPositionX - 1).type == t) {

                    level.tileData.get(enemyWorldPositionY).get(enemyWorldPositionX - 1).depositNutrients();
                    return true;
                }
            }
            else if (d == Direction.RIGHT && enemyWorldPositionX < Level.levelColumns -1 ) {
                if(level.tileData.get(enemyWorldPositionY).get(enemyWorldPositionX + 1).type == t) {
                    level.tileData.get(enemyWorldPositionY).get(enemyWorldPositionX + 1).depositNutrients();
                    return true;
                }
            }
        }
        return false;
    }

    protected void resetMovementCycle(){
        if(enemyMovementCycle == ScreenSettings.TILE_SIZE){
            enemyEatingCycleReady = true; // we are at a new tile
            enemyMovementCycle = 0;
        }
    }



}
