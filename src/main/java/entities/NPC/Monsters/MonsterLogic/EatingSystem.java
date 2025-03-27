package main.java.entities.NPC.Monsters.MonsterLogic;

import main.java.entities.Direction;
import main.java.entities.NPC.Monsters.Monster;
import main.java.entities.NPC.Movement;
import main.java.level.Level;
import main.java.level.TileType;
import java.util.List;

public class EatingSystem {


    //TODO getting possible directions should be it's own method since it's being used 3 times in different methods
    //TODO handle out of bounds in collision as well


    //TODO l1 monsters always eat this should be modified
    public static boolean L1checkIfHungryAndEat(Monster monster, TileType tileDiet){
        boolean eatStatus = false;

        //Eat
            if (monster.hunger < monster.maxHunger) {
                eatStatus = l1EatNutrient(monster, tileDiet);
            }
            else eatStatus = l1PoopNutrient(monster, tileDiet);

            if(eatStatus)monster.startAnimation(); // will cause the monster to begin animation and not run logic, except for updating frame on animation and returning when it's done since it's locked to the movement
            return eatStatus;

    }


    public static boolean l1EatNutrient(Monster npc, TileType tileDiet){
        List<Direction> list = Movement.getPossibleDirections(false, npc);

        if(eatSurroundingTile(tileDiet, list, npc.tilePositionX, npc.tilePositionY)) {
            npc.incrementHunger(npc.basicAttackStrength);
            npc.signalMonsterAte(); //This can only be set true by moving to a new tile
            npc.hunger++;
            return true;
        }
        return false;
    }

    public static boolean l1PoopNutrient(Monster monster, TileType tileType){

        List<Direction> list = Movement.getPossibleDirections(false, monster);

        if(depositSurroundingTile(tileType, list, monster.tilePositionX, monster.tilePositionY)) {
            monster.hunger--;
            monster.signalMonsterAte();
            return true;
        }
        return false;
    }


    //Is given surrounding tiles, then eats
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

    public static boolean eatSurroundingMonster(Monster m,
                                                List<Direction> possibleDirections,
                                                int worldPositionX,
                                                int worldPositionY){

        return false;
    }


    public static boolean depositSurroundingTile(TileType t, List<Direction> possibleDirections, int worldPositionX, int worldPositionY){
        Level level = Level.getInstance("");

        // Since we can only eat non-walkable directions we remove if validateWalkableDirection returns true
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
