package entities;

import entities.NPC.NPC;
import level.Level;

public class Combat {

    public static Level level;
    public static void setLevelInstance(){
        level = Level.getInstance("res/levelTest.csv");
    }
    //TODO make sure there is no possibility of null calls to combatTarget
    public static void basicAttack(NPC npc){
        while(!npc.combatTarget.isEmpty() && npc.combatTarget.peek() == null) npc.combatTarget.poll(); //remove dead targets

        NPC target = npc.combatTarget.peek();

        //Check if on cooldown to initiate attack
        if(npc.cooldown <=0 ) {

            int l1 = target.health;
            //Hit
            target.health -= npc.basicAttackStrength;

            if(l1 == target.health) System.out.println("Error, enemy did not take damage");

            else {
                System.out.println(target.returnNpcType()+ " has " + target.health  + " hp");
            }

            npc.cooldown += npc.basicAttackCooldown; //Assuming basic attack
            //System.out.println(this.returnNpcType() + " attacked " + combatTarget.peek().returnNpcType() + " for " + basicAttackStrength +" damage");

            //Check if dead
            if (target.health <= 0) {
                l1 = npc.combatTarget.size();
                npc.combatTarget.poll();
                if(npc.combatTarget.size() == l1){
                    System.out.println("Error, did not pol");
                }
            }
        }
        else {
            System.out.println(npc.returnNpcType() + " on cooldown by " + npc.cooldown);
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
