package entities.NPC;

//TODO might need to make separate files and pass arguments, it's getting messy

import   PlayerActions.Spawn;
import   entities.Combat;
import   entities.Direction;
import entities.NPC.Heroes.Hero;
import   entities.NPC.Monsters.MonsterLogic.MonsterList;
import entities.SpriteCoordinate;
import entities.WorldObjects.SkeletonHead;
import   graphics.ScreenSettings;
import graphics.Sprite.SpriteType;
import graphics.SpriteSettings;
import world.World;

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

    protected final int skillCycle = 300; // This can be overriden in constructor
    protected int skillCooldown = skillCycle; // 5 seconds

    //Where the npc is drawn on the screen, out of bounds enemies from the camera are not rendered
    //World position
    public int screenPositionX;
    public int screenPositionY;

    protected int movementSpeed = 1; // How many pixels an enemy offsets per frame
    protected int movementCycle = 0; // How long it takes before we logically know we are at a new tile without math

    public final int eatingCooldown = 16;
    public int eatingCooldownStage = eatingCooldown;

    //Sprite cycling
    protected SpriteType spriteType = SpriteType.WALK_HOR;
    protected int spriteArrayIndex = 0;
    protected int spriteFrameTimeCounter = 0;
    protected int spriteFrame = 0;

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
            Movement.moveOnScreen(this);

            Movement.updateWorldPosition(this);
            //Movement.signalNewTile(this);
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

    //Setting to
    private void skillCooldownHandler(){
        if(skillCooldown == skillCycle) return;
        skillCooldown++;
    }

    //Setting sprites, checking if it can attack, checking if it needs to be destroyed
    //This is shared behavior that always applies to each NPC
    public void genericNPCBehavior() {
        spriteHandler();
        World.INSTANCE.checkIfProjectiles(this);
        skillCooldownHandler();

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
            //Individual unique entity behavior
            behavior();
        }

        ///System.out.println(returnNpcType() + " health is " + health);

        //ATM, there might be some death behavior i want so this is a last resort handle but it's handled in behavior()
        //Like die->spawn skull or explode, or notify listener

        if (health <= 0) {
            //i could probably add deathBehavior or something
            //spawnSkeletonAtDeath();
            //This is generic behavior shared with monsters, skeleton death would not go here
            isDead = true;
            if(this instanceof Hero) new SkeletonHead(this.tilePositionX, this.tilePositionY);
            World.INSTANCE.removeNPC(this);
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
    protected SpriteType determineSpriteArray() {

        if (this.health <= 0) {
            return SpriteType.DEATH;
        }
        //ATM this just means we are in combat
        else if (inCombat) {
            if (currDirection == Direction.LEFT || currDirection == Direction.RIGHT) {
                return SpriteType.ATTACK_HOR;
            } else if (currDirection == Direction.UP || currDirection == Direction.DOWN) {
                return SpriteType.ATTACK_VER;
            }
        }

        else if (currDirection == Direction.LEFT || currDirection == Direction.RIGHT) {
            return SpriteType.WALK_HOR;
        } else if (currDirection == Direction.UP || currDirection == Direction.DOWN) {
            return SpriteType.WALK_VER;
        }

        return SpriteType.WALK_DOWN; //default to walking
    }

    protected int simpleSpriteToArray(SpriteType type){
        switch(type){
            case DEATH: return 4;
            case WALK_HOR: return 0;
            case WALK_VER: return 1;
            case ATTACK_HOR: return 2;
            case ATTACK_VER: return 3;
            default: return 1;
        }
    }

    protected SpriteType determineSpriteArrayFull(){
        if(currDirection == Direction.LEFT){
            if(inCombat){
                return SpriteType.ATTACK_LEFT;
            }
            return SpriteType.WALK_LEFT;
        }
        else if(currDirection == Direction.RIGHT){
            if(inCombat){
                return SpriteType.ATTACK_RIGHT;
            }
            return SpriteType.WALK_RIGHT;
        }
        else if(currDirection == Direction.DOWN){
            if(inCombat){
                return SpriteType.ATTACK_DOWN;
            }
            return SpriteType.WALK_DOWN;
        }
        else if(currDirection == Direction.UP){
            if(inCombat){
                return SpriteType.ATTACK_UP;
            }
            return SpriteType.WALK_UP;
        }

        return SpriteType.WALK_DOWN; //default to walking
    }

    //Alternative to simple check
    //Can be used in conjunction with full, if the sprite [] size is 0 we could just cast.
    //Alt i could literally just set the rows to walk horizontally as a placeholder and stomach
    protected SpriteType castType(){
            if( spriteType == SpriteType.WALK_DOWN || spriteType == SpriteType.WALK_UP) return SpriteType.WALK_HOR;
            if( spriteType == SpriteType.WALK_LEFT || spriteType == SpriteType.WALK_RIGHT) return SpriteType.WALK_VER;
            if( spriteType == SpriteType.ATTACK_LEFT || spriteType == SpriteType.ATTACK_RIGHT) return SpriteType.ATTACK_HOR;
            else return SpriteType.ATTACK_VER;
    }


    //This doesn't work because not every npc has a spriteArrayCoordinateCount, and those are not yet uniform anyway
//    protected void defaultSpriteHandler(){
//        SpriteType currentSpriteArray = determineSpriteArray();
//        spriteArrayIndex = simpleSpriteToArray(currentSpriteArray);
//
//        if(spriteType != currentSpriteArray){
//            System.out.println("Changing to " + currentSpriteArray + ":" + spriteArrayIndex);
//            //reset the frame counters
//            spriteFrameTimeCounter = 0;
//            spriteFrame = 0;
//            spriteType =  currentSpriteArray;
//        }
//        spriteFrameTimeCounter++;
//
//        if(spriteFrameTimeCounter == (spriteArrayCoordinateCount[spriteArrayIndex]-1 )
//                * SpriteSettings.ANIMATION_LENGTH){
//            spriteFrameTimeCounter = 0;
//            spriteFrame = 0;
//        }
//
//        if(spriteFrameTimeCounter % SpriteSettings.ANIMATION_LENGTH == 0){
//            spriteFrame++;
//        }
//
//    };



    //Handle sprite animations for full
    protected BufferedImage getSpriteFromCoordinate(SpriteCoordinate temp){
        BufferedImage subImage = image.getSubimage(temp.col, temp.row, temp.width, temp.height);
        return subImage;
    }

    protected void spriteHandlerFull(int length){
        SpriteType currentSpriteArray = determineSpriteArrayFull();

        // We don't need this since we have the advanced sprite array
        //spriteArrayIndex = simpleSpriteToArray(currentSpriteArray);


        //Change sprite state like Walk_Right -> Attack_Right
        if(spriteType != currentSpriteArray){
            spriteFrameTimeCounter = 0;
            spriteFrame = 0;
            spriteType = currentSpriteArray;
        }

        if(spriteFrameTimeCounter % SpriteSettings.ANIMATION_LENGTH == 0){
            spriteFrame++;
        }

        //Reset frame cycle
        if(spriteFrame == (length)){
            spriteFrameTimeCounter = 1;
            spriteFrame = 0;
        }

        spriteFrameTimeCounter++;

    }

}
