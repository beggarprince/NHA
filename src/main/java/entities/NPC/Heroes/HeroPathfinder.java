package entities.NPC.Heroes;

import   entities.Direction;
import   entities.NPC.Movement;
import   entities.NPC.NPC;
import   graphics.ScreenSettings;
import   level.Level;
import   level.Tile;
import   level.TileType;
import   util.Coordinate;

import java.util.ArrayList;
import java.util.List;
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

    //This responsibility is solely to get a new direction for the hero to move into
    public Direction determinePath(boolean hasMvp){
        //check to see that we don't hae any adjacent unvisited tiles
        Direction potentialDirection;

        //Initially we check if there are any new paths available and return the direction towards the new path
        if(!hasMvp) {
             potentialDirection = selectUnvisitedTile();
            if (potentialDirection != Direction.NOT_MOVING) return potentialDirection;
        }

        //If not then we have to backtrack
        potentialDirection = determineNextMoveBacktracking();

        if(potentialDirection == Direction.NOT_MOVING){
            System.out.println(currHero.name +": BACKTRACKING COULD NOT FIND DIR AT " + currHero.tilePositionX +":" +currHero.tilePositionY);
        }
        return potentialDirection;
    }


    //Call this on every move not on backtrack
    public void logPath(boolean hasMvp){
        //We do not want to log backtracking or when we have the mvp in tow
        if(currentlyBacktracking ) {
            return;
        }
        addToLinkedList(new Coordinate(currHero.tilePositionX, currHero.tilePositionY));
        //Log as visited
        visitedMap[currHero.tilePositionY][currHero.tilePositionX] =1 ;
    }

    private void addToLinkedList(Coordinate cor){
        //System.out.println("Adding " + cor.x + ":"+ cor.y +" to list");
        dfs.add(cor);
    }

    private Direction selectUnvisitedTile(){
        List<Direction> directions = new ArrayList<>();

        Direction direction = Direction.NOT_MOVING;

        //0 means unvisited btw
        if(currHero.tilePositionY >0 &&
                visitedMap[currHero.tilePositionY-1][currHero.tilePositionX] == 0 &&
        Level.getInstance().tileData.get(currHero.tilePositionY-1).get(currHero.tilePositionX).walkable){
            direction = Direction.UP;
            directions.add(Direction.UP);
        }
         if(
                currHero.tilePositionY < Level.levelRows-1 &&
                visitedMap[currHero.tilePositionY+1][currHero.tilePositionX] == 0 &&
                Level.getInstance().tileData.get(currHero.tilePositionY+1).get(currHero.tilePositionX).walkable){
            direction = Direction.DOWN;
            directions.add(Direction.DOWN);
        }
         if(currHero.tilePositionX >0 &&
                visitedMap[currHero.tilePositionY][currHero.tilePositionX-1] == 0 &&
                Level.getInstance().tileData.get(currHero.tilePositionY).get(currHero.tilePositionX-1).walkable){
            direction = Direction.LEFT;
            directions.add(Direction.LEFT);
        }
         if(
                currHero.tilePositionX < Level.levelColumns-1&&
                visitedMap[currHero.tilePositionY][currHero.tilePositionX+1] == 0 &&
                Level.getInstance().tileData.get(currHero.tilePositionY).get(currHero.tilePositionX+1).walkable){
            direction = Direction.RIGHT;
            directions.add(Direction.RIGHT);
        }

        //There are no new paths available
        if(direction != Direction.NOT_MOVING){
           // System.out.println("Found appropriate unexplored direction to move into thus currentlyBacktracking is false");
            currentlyBacktracking = false;
        }
        else{
            return direction;
        }

        if(directions.size() ==1) return direction;

        else{
            if(directions.isEmpty()){
                System.out.println(direction);
                System.out.println("what the fuck");
            }

            int index = random.nextInt(directions.size());

            System.out.println(index);
            return directions.get(index);
        }
    }

    public Direction determineNextMoveBacktracking(){
        currentlyBacktracking = true;

        Direction direction = Direction.NOT_MOVING;

        if(dfs.isEmpty()) return direction; //Empty stack exception ffs

        dfs.pop();
        if(dfs.isEmpty()) return direction;

        int diffX = (currHero.tilePositionX - dfs.peek().x);

        int diffY = (currHero.tilePositionY - dfs.peek().y);

       // System.out.println("Difference x:y = "+ diffX + " " + diffY);

        if((diffX == 0 && Math.abs(diffY) == 1)|| (diffY ==0 && Math.abs(diffX) ==1)){
          //  System.out.println("Backtracking");
        }

        else{
        System.out.println("X:Y " + diffX +":"+ diffY);
        }

        if(diffY == 0){
            if(diffX == -1){
                direction = Direction.RIGHT;
            }
            else if(diffX == 1){
                direction = Direction.LEFT;
            }
        }

        else if(diffX == 0){
            if(diffY == 1){
                direction= Direction.UP;
            }
            else if (diffY == -1){
                direction = Direction.DOWN;
            }
        }

        return direction;
    }





}
