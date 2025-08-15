package entities.NPC;
import   entities.Combat
import   entities.NPC.Heroes.Hero
import entities.NPC.Monsters.MonsterLogic.Metamorphosis
import entities.NPC.Monsters.Monster
import   entities.NPC.Monsters.MonsterLogic.MonsterList
import entities.WorldObjects.WorldObjectManager
import world.World


private fun runEnemyBehavior(enemies: List<Monster>) {
        for (i in enemies.indices) {
            val e = enemies[i]
            //spatialHash.updateNPCZone(e)
            e.genericNPCBehavior()
        }
    }

    private fun runHeroBehavior(heroes: List<Hero>, monsters: List<Monster>) {
        for (i in heroes.indices) {
            checkCollisionsMonsters(heroes[i], monsters)
            heroes[i].genericNPCBehavior()
        }
    }


    private fun checkSetMetamorphosis() {
        for (i in MonsterList.getInstance().monsters.indices) {
            val e = MonsterList.getInstance().monsters[i]
            if (e.metamorphosisReady) {
                Metamorphosis.metamorphosis(i, e.metamorphosisValue, e.screenPositionX, e.screenPositionY)
            }
        }
    }

    private fun checkCollisions(npc: NPC) {
    }


//These need to be edited so we use the 2d array of linkedlists instead of iterating over the entire list
    public fun checkCollisionsEAT(monster: Monster, monsters: List<Monster>, prey: NPCType) : Boolean{
    for (i in monsters.indices) {

        if (checkAdjacent(monsters[i], monster)) {
            ///Instead of combat they will attack once and that's it
            if(monsters[i].type == prey){
                Combat.targetedAttack(monsters[i], monster)
                return true
            }
        }

    }
    return false
    }

    //Heroes initiate combat
    private fun checkCollisionsMonsters(hero: Hero, monsters: List<Monster>) {

//        for (i in monsters.indices) {
//
//            //If there is an adjacent monster
//            if (checkAdjacent(monsters[i], hero)) {
//
//                //Check if monster is already in our queue, should probably have checked this first
//                if(!hero.returnCombatQueue().contains(monsters[i])) {
//                    //If not change both states to battle, add each other to each other's list
//                    monsters[i].inCombat = true
//                    hero.inCombat = true
//                    monsters[i].addToCombatQueue(hero)
//                    hero.addToCombatQueue(monsters[i])
//                }
//            }
//        }

        //Going from checking every monster for every hero to just adjacent ones

        //This handles state, no need for the return boolean
        World.checkIfMonsterAdjacent(hero);

    }

    private fun checkAdjacent(npc: NPC, target: NPC): Boolean {
        //If on the same axis

        if (npc.tilePositionX == target.tilePositionX) {
            if (npc.tilePositionY == target.tilePositionY + 1
                || npc.tilePositionY == target.tilePositionY - 1) {
                return true
            }
        }
        if (npc.tilePositionY == target.tilePositionY) {
            if (npc.tilePositionX == target.tilePositionX + 1
                || npc.tilePositionX == target.tilePositionX - 1) {
                return true
            }
        }
        return false

    }


    fun run(mList: List<Monster>,  hList : List<Hero>) {
        val monsters: List<Monster> = mList
        val heroes: List<Hero> = hList

        //Here we should split up the task into 3 separate threads and check for collision
        runHeroBehavior(heroes, monsters)
        runEnemyBehavior(monsters)
        WorldObjectManager.INSTANCE.WorldObjectLogic();

        checkSetMetamorphosis()
    }

