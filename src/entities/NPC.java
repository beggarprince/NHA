package entities;

import graphics.ScreenSettings;
import level.Level;
import java.awt.image.BufferedImage;
import java.util.*;

import static util.CollisionKt.detectNPCCollision;

public abstract class NPC {
    protected BufferedImage image;

    //Dumbass java, protected does not allow access to different subclasses just the superclass and itself
    public int health;
    protected Direction currDirection;
    public boolean inCombat = false;
    public boolean isDead = false;
    public NPCType npc;
    public int zone;
    protected int cooldown = 0;
    protected int basicAttackCooldown = 60; // Change this, default 60 i guess
    protected int basicAttackStrength;
    public abstract void behavior();

    public abstract void destroy();


    //Jank because i can't do abstract String
    public abstract String returnNpcType();

    //Logical position on array
    public int worldPositionX;
    public int worldPositionY;
    //Where the npc is drawn on the screen, out of bounds enemies from the camera are not rendered
    //World position
    public int screenPositionX;
    public int screenPositionY;

    protected int movementSpeed = 1; // How many pixels an enemy offsets per frame
    protected int movementCycle = 0; // How long it takes before we logically know we are at a new tile without math

    //Queue to holds all the combat targets so when we kill one we can go to the next
    protected Queue<NPC> combatTarget;

    public NPC(){
        combatTarget = new LinkedList<>();
    }

    public void addToCombatQueue(NPC npc){
        combatTarget.add(npc);
    }

    public void removeFromCombatQueue(NPC npc){
        combatTarget.poll();
    }


    Level level = Level.getInstance("res/levelTest.csv");

    private final static Random random = new Random();

    //Movement
    public List<Direction> getPossibleDirections(Boolean collisionCheck){
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


    public Direction getRandomDirection(int x, int y) {
        // List of possible directions
        List<Direction> possibleDirections = getPossibleDirections(true);

        for(Direction d : possibleDirections) if(d == currDirection) possibleDirections.remove(d);

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
    public void move() {

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
        if (screenPositionX % 16 == 0) {
            worldPositionX = screenPositionX / ScreenSettings.TILE_SIZE;
        }
        if (screenPositionY % 16 == 0) {
            worldPositionY = screenPositionY / ScreenSettings.TILE_SIZE;
        }
    }

    protected void signalNewTile(){
        if(movementCycle == ScreenSettings.TILE_SIZE){
            movementCycle = 0;
        }
    }

    //TODO RENAME
    protected boolean npcMoved(){

        boolean canMove = true;
        if(movementCycle == 0)  canMove = validateWalkableDirection(currDirection, worldPositionX, worldPositionY);

        if(canMove){
            move();
            updateWorldPosition();
            signalNewTile();
            //only at new tile do we signal that they moved
            return true;
        }

        else{
            //Get random dir but don't move, this will create it to be still for the entire time until there is a valid dir
            currDirection = getRandomDirection(worldPositionX, worldPositionY);

        }
        return false;
    }


    //TODO make sure there is no possibility of null calls to combatTarget
    public void basicAttack(){
        while(!combatTarget.isEmpty() && combatTarget.peek() == null) combatTarget.poll();
        //Check if on cooldown
        if(cooldown <=0 ) {
            //Hit
            combatTarget.peek().health -= basicAttackStrength;
            cooldown += basicAttackCooldown; //Assuming basic attack
            System.out.println(this.returnNpcType() + " attacked " + combatTarget.peek().returnNpcType() + " for " + basicAttackStrength +" damage");

            //Check if dead
            if (combatTarget.peek().health <= 0) {
                combatTarget.poll();
            }
        }
        //Cycle ot next or move, outside of cooldown just in case something else kills it
        if (combatTarget.isEmpty()){
            inCombat = false;
            //System.out.println(inCombat + " ");
        }


    }

    public void genericBehavior(){
        //combat
        if(this.inCombat){
            //basic attack handles cooldown
            System.out.println(returnNpcType() + " in combat with " + combatTarget.peek().returnNpcType());

            basicAttack();
        }
        //behavior - Movement, reproduction, etc
        else{
            //System.out.println("Moving " + returnNpcType());
            behavior();
        }

        //System.out.println(returnNpcType() + " health is " + health);
        //death
        if(health <= 0){
            isDead = true;
            destroy();
        }

        if(cooldown <= 0)cooldown--;

    }


}
