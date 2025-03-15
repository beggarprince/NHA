package main.java.entities.NPC.Heroes;

import main.java.entities.Direction;
import main.java.entities.NPC.Movement;
import main.java.entities.NPC.NPC;
import main.java.graphics.ScreenSettings;
import main.java.level.Level;
import main.java.level.Tile;
import main.java.level.TileType;
import main.java.util.Coordinate;
import java.util.Random;
import java.util.Stack;


public class HeroPathfinder extends Movement {
    //NOT static, each hero atm will have their own pathfinding.
    private static final int heroSightline = 2;
    private Hero currHero;
    public static Random random = new Random();
    public boolean currentlyBacktracking = false;

    //This list will only hold areas where the hero has decided to make a turn and left a path unexplored
    //This also has to add unexplored sightlines to the junction
    private Stack<Coordinate> dfs = new Stack<>();
    private final int[][] visitedMap = new int[Level.levelRows][Level.levelColumns];

    public HeroPathfinder(Hero hero) {
        currHero = hero;

    }

    public Direction determinePath(){
        //check to see that we don't hae any adjacent unvisited tiles
        Direction potentialDirection = selectUnvisitedTile();
        if(potentialDirection != Direction.NOT_MOVING) return potentialDirection;

        potentialDirection = determineNextMoveBacktracking();
        if(potentialDirection == Direction.NOT_MOVING){
            System.out.println("we fucked up");
        }
        return potentialDirection;
    }

    //Call this on every move not on backtrack
    public void logPath(){
        if(currentlyBacktracking) return;

        if(dfs.size() >0 ) {
            if(currHero.tilePositionY == dfs.peek().y && currHero.tilePositionX == dfs.peek().x)return;
        }
        System.out.println("LOGGING "+currHero.tilePositionX+ " " +  currHero.tilePositionY);
        dfs.add(new Coordinate(currHero.tilePositionX, currHero.tilePositionY));
        //Log as visited
        visitedMap[currHero.tilePositionY][currHero.tilePositionX] =1 ;
    }

    private Direction selectUnvisitedTile(){
        Direction direction = Direction.NOT_MOVING;

        //0 means unvisited btw
        if(visitedMap[currHero.tilePositionY-1][currHero.tilePositionX] == 0 &&
        Level.getInstance().tileData.get(currHero.tilePositionY-1).get(currHero.tilePositionX).walkable){
            direction = Direction.UP;
        }
        else if(visitedMap[currHero.tilePositionY+1][currHero.tilePositionX] == 0 &&
                Level.getInstance().tileData.get(currHero.tilePositionY+1).get(currHero.tilePositionX).walkable){
            direction = Direction.DOWN;
        }
        else if(visitedMap[currHero.tilePositionY][currHero.tilePositionX-1] == 0 &&
                Level.getInstance().tileData.get(currHero.tilePositionY).get(currHero.tilePositionX-1).walkable){
            direction = Direction.LEFT;
        }
        else if(visitedMap[currHero.tilePositionY][currHero.tilePositionX+1] == 0 &&
                Level.getInstance().tileData.get(currHero.tilePositionY).get(currHero.tilePositionX+1).walkable){
            direction = Direction.RIGHT;
        }
        if(direction != Direction.NOT_MOVING){
            System.out.println("Found appropriate unexplored direction to move into thus currentlyBacktracking is false");
            currentlyBacktracking = false;
        }
        else{
            System.out.println("Could not find appropriate direction to move into");
        }
        return direction;
    }

    private Direction determineNextMoveBacktracking(){
        currentlyBacktracking = true;
        Direction returnDirection = Direction.NOT_MOVING;
        dfs.pop();

        int diffX = (currHero.tilePositionX - dfs.peek().x);
        int diffY = (currHero.tilePositionY - dfs.peek().y);

        System.out.println(diffX + " " + diffY);
        if(diffX != 0 || diffY !=0){
            System.out.println("diff in diffx or diffy");
        }
        else{
        System.out.println("No diff in x and y in the last pop");
        }

        if(diffY == 0){
            if(diffX == -1){
                returnDirection = Direction.RIGHT;
            }
            else if(diffX == 1){
                returnDirection = Direction.LEFT;
            }
        }
        else if(diffX == 0){
            if(diffY == -1){
                returnDirection= Direction.UP;
            }
            else if (diffY == 1){
                returnDirection = Direction.DOWN;
            }
        }
        else {
            System.out.println("Both diffX and diffY == 0");
        }

        return returnDirection;
    }





}
