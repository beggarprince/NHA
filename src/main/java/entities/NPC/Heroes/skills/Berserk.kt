package entities.NPC.Heroes.skills

import entities.NPC.Heroes.Hero
import entities.NPC.NPC

//Not a static class, berserk entities will need their own handler (?)
class Berserk {

    var berserkTimer = (60) * 10
    var threshold = 0
    var berserk: Boolean = false // never flip this back to false

    //at 25% hp or less
    fun Berserk(npc : NPC){
        threshold = npc.health / 4
    }

    private fun berserk(npc: NPC){
        npc.basicAttackStrength *= 2
    }

    private fun berserkComedown(npc: NPC){
        if(npc is Hero) println(npc.name +" is no longer berserk")
        npc.basicAttackStrength /= 2
    }

    private fun berserkAction(npc: NPC){
        if(berserkTimer == 0){
            berserkComedown(npc)
            return;
        }
        npc.genericNPCBehavior()
        berserkTimer--;
    }

    //Just call this one
    //Check if berserking
    //If so, skip check and act. Otherwise we check, flip if true, or just return
    fun berserkHandler(npc: NPC){
        if(berserk) {
            berserkAction(npc)
            return
        }

        if(npc.health <= threshold){
            if(npc is Hero) println(npc.name +" is now berserk")
            berserk = true;
        }

    }
}