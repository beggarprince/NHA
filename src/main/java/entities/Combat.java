package entities;

import   entities.NPC.NPC;
import   io.Audio.Sound;
import   level.Level;
import   io.Audio.AudioConstants;

public class Combat {

    public static Level level;
    public static void setLevelInstance(){
        level = Level.getInstance();
    }
    //TODO make sure there is no possibility of null calls to combatTarget
    public static void basicAttack(NPC npc){
        while(!npc.combatTarget.isEmpty() && npc.combatTarget.peek() == null) npc.combatTarget.poll(); //remove dead targets

        NPC target = npc.combatTarget.peek();

        //Check if on cooldown
        if(npc.combatCooldown > 0){
            npc.combatCooldown--;
            return;
        }

           // int l1 = target.health;
        if(npc.basicAttackStrength <= 0) {
            System.out.println("NPC attack strength is zero");
        }
            //Hit
            target.health -= npc.basicAttackStrength;

//            if(l1 == target.health) System.out.println("Error, enemy did not take damage");
//
//            else {
//                System.out.println(target.returnNpcType()+ " has " + target.health  + " hp");
//            }

            npc.combatCooldown += npc.basicAttackCooldown; //Assuming basic attack
            //System.out.println(this.returnNpcType() + " attacked " + combatTarget.peek().returnNpcType() + " for " + basicAttackStrength +" damage");
        //TODO this is temp for now but in the future we'll always have fxIndex pointing at sounds. It's just not everything has a sound so it'll crash, or i wont be able to properly differentiate them if they use the same noises
            if(npc.fxIndex != -1){
                Sound.getSoundInstance().playFXClip(AudioConstants.FX_SWORD_SLASH);
            }

            //Check if dead
            if (target.health <= 0) {
               int l1 = npc.combatTarget.size();
                npc.combatTarget.poll();
                if(npc.combatTarget.size() == l1){
                    System.out.println("Error, did not pol");
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
