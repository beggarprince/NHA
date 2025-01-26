package entities.NPC;

import entities.Direction;
import graphics.ScreenSettings;
import level.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static util.CollisionKt.detectNPCCollision;

public class Movement {
    static Level levelInstance; //The static might create issues

    public static void setLevelInstance(){
        levelInstance = Level.getInstance("res/levelTest.csv");
    }


    private final static Random random = new Random();

    //For now, i'll pass npc in every instance
    //Movement
    public static List<Direction> getPossibleDirections(Boolean collisionCheck, NPC n){
        // List of possible directions
        List<Direction> possibleDirections = new ArrayList<>();

        possibleDirections.add(Direction.UP);
        possibleDirections.add(Direction.DOWN);
        possibleDirections.add(Direction.LEFT);
        possibleDirections.add(Direction.RIGHT);


        //Will remove if the tiles are walkable, this one is used to EAT as you can't eat PATH
        if(!collisionCheck)  possibleDirections.removeIf(direction -> validateWalkableDirection(direction, n.worldPositionX, n.worldPositionY));
        else {
            //If not walkable it is a candidate for movement, handles out of bounds as well
            possibleDirections.removeIf(direction -> !validateWalkableDirection(direction, n.worldPositionX, n.worldPositionY));
        }
        return possibleDirections;
    }


    public static Direction getRandomDirection(int x, int y, NPC npc) {
        // List of possible directions
        List<Direction> possibleDirections = getPossibleDirections(true, npc);

        for(Direction d : possibleDirections) if(d == npc.currDirection) possibleDirections.remove(d);

        // If no valid directions, return NOT_MOVING
        if (possibleDirections.isEmpty()) {
            return Direction.NOT_MOVING;
        }

        // Randomly select a valid direction
        int index = random.nextInt(possibleDirections.size());


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
    public static boolean validateWalkableDirection(Direction dir, int x, int y) {

        //Returns true if walkable, false if not
        if (dir == Direction.UP) {
            if (y > 0) {
                return detectNPCCollision(levelInstance.tileData.get(y - 1).get(x));
            }
        } else if (dir == Direction.DOWN) {
            if (y < Level.levelRows - 1) {
                return detectNPCCollision(levelInstance.tileData.get(y + 1).get(x));
            }
        } else if (dir == Direction.LEFT) {
            if (x > 0) {
                return detectNPCCollision(levelInstance.tileData.get(y).get(x - 1));
            }
        } else if (dir == Direction.RIGHT) {
            if (x < Level.levelColumns - 1) {
                return detectNPCCollision(levelInstance.tileData.get(y).get(x + 1));
            }
        }
        return false; // If all checks fail, the move is invalid.
    }



    //If we are here we are good to move in our direction of choice
    public static void move(NPC npc) {

        if (npc.currDirection == Direction.UP) {
            npc.screenPositionY -= npc.movementSpeed;
        } else if (npc.currDirection == Direction.DOWN) {
            npc.screenPositionY += npc.movementSpeed;
        } else if (npc.currDirection == Direction.LEFT) {
            npc.screenPositionX -= npc.movementSpeed;
        } else if (npc.currDirection == Direction.RIGHT) {
            npc.screenPositionX += npc.movementSpeed;
        }

        npc.movementCycle += npc.movementSpeed;


    }


    protected static void updateWorldPosition(NPC npc) {
        if (npc.screenPositionX % 16 == 0) {
            npc.worldPositionX = npc.screenPositionX / ScreenSettings.TILE_SIZE;
        }
        if (npc.screenPositionY % 16 == 0) {
            npc.worldPositionY = npc.screenPositionY / ScreenSettings.TILE_SIZE;
        }
    }

    protected static void signalNewTile(NPC npc){
        if(npc.movementCycle == ScreenSettings.TILE_SIZE){
            npc.movementCycle = 0;
        }
    }

}
