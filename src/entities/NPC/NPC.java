package entities.NPC;

import entities.Combat;
import level.Level;
import java.awt.image.BufferedImage;
import java.util.*;

public abstract class NPC extends Stats {
    protected BufferedImage image;

    //Dumbass java, protected does not allow access to different subclasses just the superclass and itself


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
    public Queue<NPC> combatTarget;

    public NPC(){
        combatTarget = new LinkedList<>();
    }

    public void addToCombatQueue(NPC npc){
        combatTarget.add(npc);
    }

    public Queue<NPC> returnCombatQueue(){
        return combatTarget;
    }

    public void removeFromCombatQueue(NPC npc){
        combatTarget.poll();
    }


    //Level level = Level.getInstance("res/levelTest.csv");

    private final static Random random = new Random();


    //TODO RENAME
    protected boolean moveNpcAndSignal(){

        boolean canMove = true;

        //If the movement cycle is not 0 then we know we are still moving in a valid direction so we don't have to check if said direction is valid
        //If 0 we just need to check the next tile
        if(movementCycle == 0)  canMove = Movement.validateWalkableDirection(currDirection, worldPositionX, worldPositionY);

        if(canMove){
            Movement.move(this);
            Movement.updateWorldPosition(this);
            Movement.signalNewTile(this);
            //only at new tile do we signal that they moved
            return true;
        }

        else{
            //Get random dir but don't move, this will create it to be still for the entire time until there is a valid dir
            currDirection = Movement.getRandomDirection(worldPositionX, worldPositionY, this);

        }
        return false;
    }

    public void genericBehavior(){

        if(cooldown > 0)cooldown--;

        //combat
        if(this.inCombat && movementCycle == 0){
            //basic attack handles cooldown
            Combat.basicAttack(this);
        }

        //behavior - Movement, reproduction, etc
        else{
            ///System.out.println("Moving " + returnNpcType());
            behavior();
        }

        ///System.out.println(returnNpcType() + " health is " + health);
   if(health <= 0){
    isDead = true;
   }

    }


}
