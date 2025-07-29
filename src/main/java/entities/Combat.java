package entities;

import   entities.NPC.NPC;
import   io.Audio.Sound;
import   level.Level;
import   io.Audio.AudioConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class Combat {

    private static final Logger logger = LoggerFactory.getLogger(Combat.class);

    public static Level level;
    public static void setLevelInstance(){
        level = Level.getInstance();
    }
    //TODO make sure there is no possibility of null calls to combatTarget
    //This function does not take in a target. The npc has a list of it's combat targets and will choose from there.
    public static void basicAttack(NPC npc){

        while(!npc.combatTarget.isEmpty() &&
                npc.combatTarget.peek() == null){
            npc.combatTarget.poll(); //remove dead targets
        }

        NPC target = npc.combatTarget.peek();
        if(target == null){
            npc.inCombat = false;
            return;
        }

        //Check if attack is on cooldown
        if(npc.combatCooldown > 0){
            npc.combatCooldown--;
            return;
        }

           // int l1 = target.health;
        if(npc.basicAttackStrength <= 0) {
            System.out.println("NPC attack strength is zero");
        }
            int l1 = target.health;
            target.health -= npc.basicAttackStrength;

            if(l1 == target.health) {
                logger.error("Enemy {} did not take damage - current: {}, expected {}", target.returnNpcType(),l1, (l1- npc.basicAttackStrength));
                logger.error("The attacker is a {} and has {} attack", npc.returnNpcType(), npc.basicAttackStrength);
            }

            npc.combatCooldown += npc.basicAttackCooldown; //Assuming basic attack
            //System.out.println(this.returnNpcType() + " attacked " + combatTarget.peek().returnNpcType() + " for " + basicAttackStrength +" damage");
        //TODO this is temp for now but in the future we'll always have fxIndex pointing at sounds. It's just not everything has a sound so it'll crash, or i wont be able to properly differentiate them if they use the same noises
            if(npc.fxIndex != -1){
                Sound.getSoundInstance().playFXClip(AudioConstants.FX_SWORD_SLASH);
            }

        // Check if dead
        if (target.health <= 0) {
            int sizeBeforePoll = npc.combatTarget.size();
            NPC removed = npc.combatTarget.poll();

            if (removed == null) {
                logger.error("Failed to remove dead target {} from combat queue - queue was empty",
                        target.returnNpcType());
            } else if (npc.combatTarget.size() == sizeBeforePoll) {
                logger.error("Combat target queue size unchanged after poll - expected removal of {}",
                        target.returnNpcType());
            } else {
                logger.debug("Removed dead target {} from combat queue", target.returnNpcType());
            }
        }


        //Cycle ot next or move, outside of cooldown just in case something else kills it
        if (npc.combatTarget.isEmpty()){
            npc.inCombat = false;
            //System.out.println(inCombat + " ");
        }
    }

    public static void targetedAttack(NPC target, NPC npc){
        target.health -= npc.basicAttackStrength;

        if(target.health <= 0 ) {
            target.isDead = true; //this might not belong here, i might have set it to dead twice idk
        }
    }

}
