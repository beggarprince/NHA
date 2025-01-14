package Game

import entities.Heroes.Hero
import entities.Heroes.HeroList
import entities.Monsters.Logic.Metamorphosis
import entities.Monsters.Monster
import entities.Monsters.MonsterList
import entities.NPC
import entities.NPCType

//    var monsterList: MonsterList = MonsterList.getInstance()
//    var heroList: HeroList = HeroList.getInstance()
//    var spatialHash: SpatialHash = SpatialHash.getInstance()

    private fun runEnemyBehavior(enemies: List<Monster>) {
        for (i in enemies.indices) {
            val e = enemies[i]
            //spatialHash.updateNPCZone(e)
            e.genericBehavior()
        }
    }

    private fun runHeroBehavior(heroes: List<Hero>, monsters: List<Monster>) {
        for (i in heroes.indices) {
            checkCollisionsMonsters(heroes[i], monsters)
            heroes[i].genericBehavior()
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
                monster.targetedAttack(monsters[i])
                //TODO should probably heal them
                return true
            }
        }

    }
    return false
    }

    //Heroes initiate combat
    private fun checkCollisionsMonsters(hero: Hero, monsters: List<Monster>) {
        for (i in monsters.indices) {
            if (checkAdjacent(monsters[i], hero)) {
                monsters[i].inCombat = true
                hero.inCombat = true
                monsters[i].addToCombatQueue(hero)
                hero.addToCombatQueue(monsters[i])
            }
        }
    }

    private fun checkAdjacent(npc: NPC, target: NPC): Boolean {
        //If on the same axis

        if (npc.worldPositionX == target.worldPositionX) {
            if (npc.worldPositionY == target.worldPositionY + 1 || npc.worldPositionY == target.worldPositionY - 1) {
                return true
            }
        }
        if (npc.worldPositionY == target.worldPositionY) {
            if (npc.worldPositionX == target.worldPositionX + 1 || npc.worldPositionX == target.worldPositionX - 1) {
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

        checkSetMetamorphosis()
    }
