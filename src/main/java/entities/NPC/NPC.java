package entities.NPC;

import   PlayerActions.Spawn;
import   entities.Combat;
import   entities.Direction;
import   entities.NPC.Monsters.MonsterLogic.MonsterList;
import   graphics.ScreenSettings;

import java.awt.image.BufferedImage;
import java.util.*;

public abstract class NPC extends Stats {
    protected BufferedImage image;

    public abstract void behavior();

    public abstract void destroy();


    //Jank because i can't do abstract String
    public abstract String returnNpcType();


    public int fxIndex = -1;

    public boolean movementBlocked = false;

    //Logical position on tile array
    public int tilePositionX;
    public int tilePositionY;

    public final int defaultAnimationTime = 12;
    public int animationFrameCounter = 12;

    //Where the npc is drawn on the screen, out of bounds enemies from the camera are not rendered
    //World position
    public int screenPositionX;
    public int screenPositionY;

    protected int movementSpeed = 1; // How many pixels an enemy offsets per frame
    protected int movementCycle = 0; // How long it takes before we logically know we are at a new tile without math

    public final int eatingCooldown = 16;
    public int eatingCooldownStage = eatingCooldown;

    //Queue to holds all the combat targets so when we kill one we can go to the next
    public Queue<NPC> combatTarget;

    public NPC() {
        combatTarget = new LinkedList<>();
    }

    public void addToCombatQueue(NPC npc) {
        combatTarget.add(npc);
    }

    public Queue<NPC> returnCombatQueue() {
        return combatTarget;
    }

    public void removeFromCombatQueue(NPC npc) {
        combatTarget.poll();
    }

    public void startAnimation() {
        animationFrameCounter = 0;
    }

    public void spawnSkeletonAtDeath(){
        int prev = MonsterList.getInstance().getMonsters().size();
        Spawn.spawnEnemyAtPosition("Skeleton",this.tilePositionX, this.tilePositionY);
        if(prev == MonsterList.getInstance().getMonsters().size()) System.out.println("Could not add skeleton to the list");
    }

    //TODO RENAME holy shit
    protected boolean npcHasMoved() {

        if (currDirection == Direction.NOT_MOVING) {
            return false;
        }

        boolean pathIsNotBlocked = true;

        //If the movement cycle is not 0 we are not determining new direction, skip check
        // If 0 we just need to check the next tile
        if (movementCycle == 0) {
            pathIsNotBlocked = Movement.tileIsWalkable(currDirection, tilePositionX, tilePositionY);
        }

        if (pathIsNotBlocked) {
            Movement.move(this);
            Movement.updateWorldPosition(this);
            Movement.signalNewTile(this);
            movementBlocked = false;
            //only at new tile do we signal that they moved
            return true;
        } else {
            //Get random dir but don't move, this will create it to be still for the entire time until there is a valid dir
            movementBlocked = true; // This is solely for hero backtracking
            currDirection = Movement.getRandomDirection(tilePositionX, tilePositionY, this);
        }

        return false;
    }

    protected abstract void spriteHandler();

    public void genericNPCBehavior() {
        spriteHandler();

        if (animationFrameCounter < defaultAnimationTime) {
            animationFrameCounter++;
            //This is combat cooldown
            if (combatCooldown > 0) combatCooldown--;
            if (animationFrameCounter < defaultAnimationTime) {
                // We are in an animation and thus can't change action
                // System.out.println("Animation locked "+ animationFrameCounter);
                return;
            }
            //  else System.out.println("Unlocked");
        }


        //combat
        if (this.inCombat && movementCycle == 0) {
            //basic attack handles cooldown
            Combat.basicAttack(this);
            this.startAnimation();
        }

        //behavior - Movement, reproduction, etc
        //TODO at the moment it either attacks or runs logic, i should probably separate logic into two categories so i can run some logic 100% of the time regardless if in combat or not
        else {
            behavior();
        }

        ///System.out.println(returnNpcType() + " health is " + health);
        if (health <= 0) {
            //i could probably add deathBehavior or something
            //spawnSkeletonAtDeath();
            isDead = true;
        }
    }

    public boolean detectNewTile() {
        return ((tilePositionX * ScreenSettings.TILE_SIZE == screenPositionX) && (tilePositionY * ScreenSettings.TILE_SIZE == screenPositionY));
    }

    public boolean spriteNeedsToBeFlipped() {
        if (this.currDirection == Direction.DOWN || this.currDirection == Direction.RIGHT) {
            return true;
        }
        return false;
    }

    //Determine appropriate sprite array
    //int[] spriteSheetCount
    // Walk hor 0
    // walk ver 1
    // attack hor 2
    // attack ver 3
    // death 4
    protected int determineSpriteArray() {

        if (this.health <= 0) {
            return 4;
        }
        //ATM this just means we are in combat
        else if (inCombat) {
            if (currDirection == Direction.LEFT || currDirection == Direction.RIGHT) {
                return 2;
            } else if (currDirection == Direction.UP || currDirection == Direction.DOWN) {
                return 3;
            }
        }

        else if (currDirection == Direction.LEFT || currDirection == Direction.RIGHT) {
            return 0;
        } else if (currDirection == Direction.UP || currDirection == Direction.DOWN) {
            return 1;
        }

        return 0; //default to walking
    }


}
