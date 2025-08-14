package entities.NPC;

import   entities.Direction;
import entities.NPC.Heroes.HeroList;
import entities.NPC.Monsters.MonsterLogic.MonsterList;
import   graphics.ScreenSettings;
import   level.Level;
import util.CollisionKt;
import world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Movement {
    static Level levelInstance;

    public static void setLevelInstance(){
        levelInstance = Level.getInstance();
    }


    private final static Random random = new Random();

    //For now, i'll pass npc in every instance
    //Movement
    public static List<Direction> getPossibleDirections(Boolean moving, NPC n){
        // List of possible directions
        List<Direction> possibleDirections = new ArrayList<>();

        possibleDirections.add(Direction.UP);
        possibleDirections.add(Direction.DOWN);
        possibleDirections.add(Direction.LEFT);
        possibleDirections.add(Direction.RIGHT);


        //Will remove if the tiles are not walkable, this one is used to EAT as you can't eat PATH
        //Moving is a control variable, if we are not moving then we are eating in this function, thus we remove walkable tiles
        //This is because we eat nutrients in tiles, so we remove any path since there is nothing to eat
        if(!moving)  possibleDirections.removeIf(direction -> tileIsWalkable(direction, n.tilePositionX, n.tilePositionY));
        else {
            //If  walkable it is a candidate for movement, handles out of bounds as well
            //Here moving is true so we want to remove unwalkable tiles
            possibleDirections.removeIf(direction -> !tileIsWalkable(direction, n.tilePositionX, n.tilePositionY));
        }
        return possibleDirections;
    }


    public static Direction getRandomDirection(int x, int y, NPC npc) {
        // List of possible directions
        List<Direction> possibleDirections = getPossibleDirections(true, npc);
        List<Direction> subsetOfDir = new ArrayList<Direction>();

        for(Direction d : possibleDirections){
            if(d == npc.currDirection) possibleDirections.remove(d);
            else if(d != getOppositeDirection(npc.currDirection)){
                subsetOfDir.add(d);
            }
        }

        // If no valid directions, return NOT_MOVING
        if (possibleDirections.isEmpty()) {
            return Direction.NOT_MOVING;
        }

        if(!subsetOfDir.isEmpty()){
            return subsetOfDir.get(random.nextInt(subsetOfDir.size()));
        }

        // Randomly select a valid direction
        int index = random.nextInt(possibleDirections.size());


        return possibleDirections.get(index);
    }

    public static Direction getOppositeDirection(Direction dir) {
        if(dir == null){
            return Direction.NOT_MOVING;
        }
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
    public static boolean tileIsWalkable(Direction dir, int x, int y)
    {

        //Returns true if walkable, false if not
        if (dir == Direction.UP) {
            if (y > 0) {
                return CollisionKt.tileIsPath(levelInstance.tileData.get(y - 1).get(x));
            }
        } else if (dir == Direction.DOWN) {
            if (y < Level.levelRows - 1) {
                return CollisionKt.tileIsPath(levelInstance.tileData.get(y + 1).get(x));
            }
        } else if (dir == Direction.LEFT) {
            if (x > 0) {
                return CollisionKt.tileIsPath(levelInstance.tileData.get(y).get(x - 1));
            }
        } else if (dir == Direction.RIGHT) {
            if (x < Level.levelColumns - 1) {
                return CollisionKt.tileIsPath(levelInstance.tileData.get(y).get(x + 1));
            }
        }
        return false; // If all checks fail, the move is invalid.
    }



    //If we are here we are good to move in our direction of choice
    public static void moveOnScreen(NPC npc) {

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

        //int len = (MonsterList.getInstance().getMonsters().size() + HeroList.getInstance().getHeroes().size());

        if (npc.screenPositionX % 16
                //ScreenSettings.ORIGINAL_TILE_SIZE
                == 0 //&& npc.screenPositionX != 0
        ) {
            int pos = npc.screenPositionX / ScreenSettings.TILE_SIZE;

            if(pos != npc.tilePositionX) {
                World.INSTANCE.removeNPC(npc);
                npc.tilePositionX = pos;
                World.INSTANCE.addNPC(npc);
                npc.movementCycle = 0;
            }
        }
        if (npc.screenPositionY % 16
                //ScreenSettings.ORIGINAL_TILE_SIZE
                == 0
        ) {
            int pos = npc.screenPositionY / ScreenSettings.TILE_SIZE;

            if(pos != npc.screenPositionY){
                World.INSTANCE.removeNPC(npc);
                npc.tilePositionY = pos;
                World.INSTANCE.addNPC(npc);
                npc.movementCycle = 0;
            }
        }

       // assert len == (MonsterList.getInstance().getMonsters().size() + HeroList.getInstance().getHeroes().size()) : "Added/Removed from list";

    }

//    protected static void signalNewTile(NPC npc){
//        if(npc.movementCycle >= ScreenSettings.TILE_SIZE){
//            //World.INSTANCE.removeNPC(npc);
//            npc.movementCycle = 0; //This needs to be used to determine if they can attack so they don't get stuck in diagonal fights
//            //World.INSTANCE.addNPC(npc);
//            //World.INSTANCE.printWorld();
//        }
//    }

}
