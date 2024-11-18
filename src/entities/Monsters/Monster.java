package entities.Monsters;

import entities.Direction;
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
* ATM handles
*   movement
*   eating
*   eatingCycles
*   knowledge check when the creature is in a new tile
*   pooping back into tiles
* */

//TODO REFACTOR THIS, SPLIT INTO DIFFERENT FILES
public abstract class Monster {
    protected BufferedImage image;
    protected int health;
    protected int lifespan;
    protected int hunger;
    protected boolean hasFullStomach = false;
    protected boolean eatingCycleReady = true;
    protected int maxHunger;
    protected Direction currDirection;
    public boolean isDead = false;

    //Logical position on array
    public int worldPositionX;
    public int worldPositionY;
    //Where the enemy is drawn on the screen, out of bounds enemies from the camera are not rendered
    //World position
    public int screenPositionX;
    public int screenPositionY;

    protected int movementSpeed; // How many pixels an enemy offsets per frame
    protected int movementCycle; // How long it takes before we logically know we are at a new tile without math
    public String metamorphosisValue;
    public boolean metamorphosisReady = false;

    Level level = Level.getInstance("res/levelTest.csv");

    private final static Random random = new Random();


    public Monster(int health, int x, int y) {
        this.health = health;
    }

    // Getter and setter methods
    public int getMonsterHealth() {
        return health;
    }

    public void setMonsterHealth(int monsterHealth) {
        this.health = monsterHealth;
    }



    public int getWorldPositionX() {
        return worldPositionX;
    }

    public int getWorldPositionY() {
        return worldPositionY;
    }

    protected void decreaseHunger() {
        hunger--;
    }

    protected void damage(int damage) {
        health -= damage;
    }

    protected void age() {
        lifespan--;
    }

    protected abstract void setImage();


    //Removes final references of the object so it can be removed, allows unique deaths based on type bc it's abstract
    protected abstract void destroy();

    public abstract BufferedImage getImage();

    public abstract void behavior();

    protected abstract void eat();

    protected  abstract void agingCycle();

    protected abstract void reproductionCycle();

    protected void death(){
        isDead = true;
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
        if(!collisionCheck)  possibleDirections.removeIf(direction -> validateWalkableDirection(direction, worldPositionX, worldPositionY));
        else {
            //If not walkable it is a candidate for movement, handles out of bounds as well
            possibleDirections.removeIf(direction -> !validateWalkableDirection(direction, worldPositionX, worldPositionY));
        }
        return possibleDirections;
    }


    protected Direction enemyGetRandomDirection(int x, int y) {
        // List of possible directions
        List<Direction> possibleDirections = getPossibleDirections(true);

        for(Direction d : possibleDirections) if(d == currDirection) possibleDirections.remove(d);

        // If no valid directions, return NOT_MOVING
        if (possibleDirections.isEmpty()) {
            return Direction.NOT_MOVING;
        }

        // Randomly select a valid direction
        int index = random.nextInt(possibleDirections.size());

        if(possibleDirections.isEmpty()) return getOppositeDirection(currDirection);
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

        if (currDirection == Direction.UP) {
            screenPositionY -= movementSpeed;
        } else if (currDirection == Direction.DOWN) {
            screenPositionY += movementSpeed;
        } else if (currDirection == Direction.LEFT) {
            screenPositionX -= movementSpeed;
        } else if (currDirection == Direction.RIGHT) {
            screenPositionX += movementSpeed;
        }
        movementCycle += movementSpeed;

    }


    protected void updateWorldPosition() {
        worldPositionX = screenPositionX / ScreenSettings.TILE_SIZE;
        worldPositionY = screenPositionY / ScreenSettings.TILE_SIZE;
    }


    //TODO getting possible directions should be it's own method since it's being used 3 times in different methods
    //TODO there should be a method to removeIf and then do out of bounds, atm we can do remove unwalkable and remove walkable
    protected boolean eatSurroundingTile(TileType t){
        //t is target food

        List<Direction> possibleDirections = getPossibleDirections( false);

        for(Direction d : possibleDirections){
            if (d == Direction.UP && worldPositionY > 0) {
                if(level.tileData.get(worldPositionY - 1).get(worldPositionX).type == t) {
                    level.tileData.get(worldPositionY - 1).get(worldPositionX).eatNutrients();

                    return true;
                }
            }
            else if (d == Direction.DOWN && worldPositionY < Level.levelRows -1) {
                if(level.tileData.get(worldPositionY + 1).get(worldPositionX).type == t) {
                    level.tileData.get(worldPositionY + 1).get(worldPositionX).eatNutrients();

                    return true;
                }
            }
            else if (d == Direction.LEFT && worldPositionX > 0) {
                if (level.tileData.get(worldPositionY).get(worldPositionX - 1).type == t) {

                    level.tileData.get(worldPositionY).get(worldPositionX - 1).eatNutrients();
                    return true;
                }
            }
            else if (d == Direction.RIGHT && worldPositionX < Level.levelColumns -1 ) {
                if(level.tileData.get(worldPositionY).get(worldPositionX + 1).type == t) {
                    level.tileData.get(worldPositionY).get(worldPositionX + 1).eatNutrients();
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
            if (d == Direction.UP && worldPositionY > 0) {
                if(level.tileData.get(worldPositionY - 1).get(worldPositionX).type == t) {
                    level.tileData.get(worldPositionY - 1).get(worldPositionX).depositNutrients();

                    return true;
                }
            }
            else if (d == Direction.DOWN && worldPositionY < Level.levelRows -1) {
                if(level.tileData.get(worldPositionY + 1).get(worldPositionX).type == t) {
                    level.tileData.get(worldPositionY + 1).get(worldPositionX).depositNutrients();

                    return true;
                }
            }
            else if (d == Direction.LEFT && worldPositionX > 0) {
                if (level.tileData.get(worldPositionY).get(worldPositionX - 1).type == t) {

                    level.tileData.get(worldPositionY).get(worldPositionX - 1).depositNutrients();
                    return true;
                }
            }
            else if (d == Direction.RIGHT && worldPositionX < Level.levelColumns -1 ) {
                if(level.tileData.get(worldPositionY).get(worldPositionX + 1).type == t) {
                    level.tileData.get(worldPositionY).get(worldPositionX + 1).depositNutrients();
                    return true;
                }
            }
        }
        return false;
    }

    protected void resetMovementCycle(){
        if(movementCycle == ScreenSettings.TILE_SIZE){
            eatingCycleReady = true; // we are at a new tile
            movementCycle = 0;
        }
    }


}
