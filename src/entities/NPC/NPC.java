package entities.NPC;

import entities.Direction;
import graphics.ScreenSettings;
import level.Level;
import java.awt.image.BufferedImage;
import java.util.*;

import static util.CollisionKt.detectNPCCollision;

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
    protected Queue<NPC> combatTarget;

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


    Level level = Level.getInstance("res/levelTest.csv");

    private final static Random random = new Random();


    //TODO RENAME
    protected boolean npcMoved(){

        boolean canMove = true;
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


    //TODO make sure there is no possibility of null calls to combatTarget
    public void basicAttack(){
        while(!combatTarget.isEmpty() && combatTarget.peek() == null) combatTarget.poll(); //remove dead targets

        NPC target = combatTarget.peek();

        //Check if on cooldown to initiate attack
        if(cooldown <=0 ) {

            int l1 = target.health;
            //Hit
            target.health -= this.basicAttackStrength;

            if(l1 == target.health) System.out.println("Error, enemy did not take damage");

            else {
                System.out.println(target.returnNpcType()+ " has " + target.health  + " hp");
            }

            cooldown += basicAttackCooldown; //Assuming basic attack
            //System.out.println(this.returnNpcType() + " attacked " + combatTarget.peek().returnNpcType() + " for " + basicAttackStrength +" damage");

            //Check if dead
            if (target.health <= 0) {
                l1 = combatTarget.size();
                combatTarget.poll();
                if(combatTarget.size() == l1){
                    System.out.println("Error, did not pol");
                }
            }
        }
        else {
            System.out.println(returnNpcType() + " on cooldown by " + cooldown);
        }

        //Cycle ot next or move, outside of cooldown just in case something else kills it
        if (combatTarget.isEmpty()){
            inCombat = false;
            //System.out.println(inCombat + " ");
        }
    }

    public void targetedAttack(NPC target){
        target.health -= basicAttackStrength;
        if(target.health <= 0 ) {
            target.isDead = true; //this might not belong here, i might have set it to dead twice idk
        }
    }

    public void genericBehavior(){
        if(cooldown > 0)cooldown--;

        //combat
        if(this.inCombat){
            //basic attack handles cooldown
//            System.out.println(returnNpcType() + " in combat with " + combatTarget.peek().returnNpcType());
//            System.out.println(returnNpcType() + " has "+ this.health + " current health");
  //          System.out.println(returnNpcType() + " has " + combatTarget.size() + " targets");
            basicAttack();
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
