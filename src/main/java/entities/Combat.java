package main.java.entities;

import main.java.entities.NPC.NPC;
import main.java.io.Audio.Sound;
import main.java.level.Level;
import main.java.io.Audio.AudioConstants;

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
            //Hit
            target.health -= npc.basicAttackStrength;

//            if(l1 == target.health) System.out.println("Error, enemy did not take damage");
//
//            else {
//                System.out.println(target.returnNpcType()+ " has " + target.health  + " hp");
//            }

            npc.combatCooldown += npc.basicAttackCooldown; //Assuming basic attack
            //System.out.println(this.returnNpcType() + " attacked " + combatTarget.peek().returnNpcType() + " for " + basicAttackStrength +" damage");
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
