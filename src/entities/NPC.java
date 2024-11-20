package entities;

import graphics.ScreenSettings;
import level.Level;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static util.CollisionKt.detectNPCCollision;

public abstract class NPC {
    protected BufferedImage image;
    protected int health;
    protected Direction currDirection;
    public boolean isDead = false;

    //Logical position on array
    public int worldPositionX;
    public int worldPositionY;
    //Where the npc is drawn on the screen, out of bounds enemies from the camera are not rendered
    //World position
    public int screenPositionX;
    public int screenPositionY;

    protected int movementSpeed; // How many pixels an enemy offsets per frame
    protected int movementCycle; // How long it takes before we logically know we are at a new tile without math

    Level level = Level.getInstance("res/levelTest.csv");

    private final static Random random = new Random();

    //Movement
    protected List<Direction> getPossibleDirections(Boolean collisionCheck){
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


    protected Direction getRandomDirection(int x, int y) {
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




}
