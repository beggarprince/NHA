package main.java.entities.NPC.Heroes;

import main.java.entities.Direction;
import main.java.entities.NPC.Movement;
import main.java.entities.NPC.NPC;
import main.java.graphics.ScreenSettings;
import main.java.util.Coordinate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class HeroPathfinder extends Movement {
    //NOT static, each hero atm will have their own pathfinding.
    private static final int heroSightline = 6;
    public static Random random = new Random();

    //This list will only hold areas where the hero has decided to make a turn and left a path unexplored
    //This also has to add unexplored sightlines to the junction
    private Stack<Coordinate> junctionList = new Stack<>();

    public HeroPathfinder(){

    }

    public Stack<Coordinate> returnStack(){
        return junctionList;
    }

    public void add(int x, int y){
        Coordinate c = new Coordinate(x, y);
        junctionList.add(c);
    }

    public void peek(){
        junctionList.peek();
    }

    public void pop(){
        junctionList.pop();
    }

    public void heroMove(Hero hero){

    }

    private Direction heroCollision(Hero hero){
        List<Direction> possiblePath = getPossibleDirections(true, hero);

        //This means we back track
        if(possiblePath.size() == 1){

        }
        //Otherwise we don't
        return Direction.UP;
    }

    protected boolean heroMoveAndSignal(Hero hero){

        boolean canMove = true;

        //If the movement cycle is not 0 then we know we are still moving in a valid direction so we don't have to check if said direction is valid
        //If 0 we just need to check the next tile
        if(hero.movementCycle == 0)  canMove = Movement.tileIsNotWalkable(hero.currDirection, hero.tilePositionX, hero.tilePositionY);

        if(canMove){
            Movement.move(hero);
            Movement.updateWorldPosition(hero);
            Movement.signalNewTile(hero);
            //only at new tile do we signal that they moved
            return true;
        }

        else{
            //Get random dir but don't move, this will create it to be still for the entire time until there is a valid dir
            hero.currDirection = heroCollision( hero);
        }

        return false;
    }

    private static void signalNewTileHero(Hero npc){
        if(npc.movementCycle >= ScreenSettings.TILE_SIZE){
            npc.movementCycle = 0; //This needs to be used to determine if they can attack so they don't get stuck in diagonal fights
        }
    }
    public static Direction getRandomDirectionHero(int x, int y, Hero npc) {
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

}
