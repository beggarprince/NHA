package entities.NPC.Monsters.MonsterLogic;

import entities.Direction;
import entities.NPC.Monsters.Monster;
import level.Level;
import level.TileType;
import java.util.List;

public class EatingSystem {


    //TODO getting possible directions should be it's own method since it's being used 3 times in different methods
    //TODO handle out of bounds in collision as well
    //Is given surrouding tiles, then eats
    public static boolean eatSurroundingTile(TileType t, List<Direction> possibleDirections, int worldPositionX, int worldPositionY){
        Level level = Level.getInstance("");

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

    public static boolean eatSurroundingMonster(Monster m, List<Direction> possibleDirections, int worldPositionX, int worldPositionY){
        Level level = Level.getInstance("");

        return false;
    }


    public static boolean depositSurroundingTile(TileType t, List<Direction> possibleDirections, int worldPositionX, int worldPositionY){
        Level level = Level.getInstance("");

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

}
