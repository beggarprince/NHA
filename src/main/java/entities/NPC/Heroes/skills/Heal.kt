package entities.NPC.Heroes.skills

import entities.NPC.NPC

class Heal {

    //Instead of passing ints i could use a base stat like mag or something
    fun healNPC(npc: NPC, healStrength: Int){
        npc.health += healStrength;
    }

}