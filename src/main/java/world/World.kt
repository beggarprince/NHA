package world

import entities.Direction
import entities.NPC.Heroes.Hero
import entities.NPC.Monsters.Monster
import entities.NPC.Monsters.MonsterLogic.MonsterFactory
import entities.NPC.Monsters.MonsterLogic.MonsterList
import entities.NPC.NPC
import entities.WorldObjects.Projectile
import entities.WorldObjects.SkeletonHead
import entities.WorldObjects.WorldObject
import graphics.ScreenSettings
import java.lang.Integer.max
import kotlin.math.min

object World {
    //What do i need?
    /*
    * A 2d array holding a list of entities as follows
    *  Monster
    *  NPC
    *  WorldObjects
    *  State of tile
    *
    *  Since level already has the tile information, all this needs to hold is
    *  whether a tile is walkable
    *
    * Nvm the level already holds that and it's a simple check
    *
    *  This will represent the physical world in game
    * */

    lateinit var world: Array<Array<TileEntities>>

    data class TileEntities(
        val tileMonsters: MutableSet<Monster>,
        val tileHeroes: MutableSet<Hero>,
        val tileObjects: MutableSet<WorldObject> //Not projectiles, like idk a barrel
    )

    fun getProjectiles(x: Int, y: Int): List<Projectile>{
        val list = mutableListOf<Projectile>()

        for(obj in world[y][x].tileObjects){
            if(obj is Projectile && obj.projectileActive()){
                list.add(obj);
            }
        }
        return list;
    }

    private var columns: Int = 0
    private var rows:Int = 0

    fun initialize(){

        columns = level.Level.levelColumns
        rows = level.Level.levelRows

        world = Array(rows) { Array<TileEntities>(columns) {
            TileEntities(
                mutableSetOf(),
                mutableSetOf(),
                mutableSetOf()
            )
        } }
    }

    fun addHeroToTile(hero: Hero){
        return
        world[hero.tilePositionY][hero.tilePositionX].tileHeroes.add(hero);
    }

    fun removeHeroFromTile(hero: Hero){
        return
        world[hero.tilePositionY][hero.tilePositionX].tileHeroes.remove(hero);
    }

    fun addMonsterToTile(monster: Monster){
        world[monster.tilePositionY][monster.tilePositionX].tileMonsters.add(monster)
    }

    fun removeMonsterFromTile(monster: Monster){
        world[monster.tilePositionY][monster.tilePositionX].tileMonsters.remove(monster)
    }

    fun addObjectToTile(obj: WorldObject){
        world[obj.tilePositionY][obj.tilePositionX].tileObjects.add(obj)

    }

    fun removeObjectFromTile(obj: WorldObject){
        world[obj.tilePositionY][obj.tilePositionX].tileObjects.remove(obj)
    }

    fun getAllMonstersFromTile(x: Int, y: Int): List<Monster>{
        val a = world[y][x].tileMonsters.toList()
        return a;
    }

    fun getAllHeroesFromTile(x: Int, y: Int): List<Hero>{
        val h = world[y][x].tileHeroes.toList()
        return h
    }

    fun getAllWorldObjectsFromTile(x: Int, y:Int): List<WorldObject>{
        val o = world[y][x].tileObjects.toList()
        return o;
    }

    fun getAllNpcFromTile(x:Int, y: Int): List<NPC>{
        val monster = world[y][x].tileMonsters.toList()
        val hero = world[y][x].tileHeroes.toList()
        return (monster +hero)
    }

    fun removeNPC(npc : NPC){
        //println("Delete npc");
        if(npc is Hero){
            removeHeroFromTile(npc)
        }
        else if(npc is Monster){
            removeMonsterFromTile(npc)
        }
    }

    fun addNPC(npc: NPC){
       // println("Write npc")
        if(npc is Hero){
            addHeroToTile(npc)
        }
        else if (npc is Monster){
            addMonsterToTile(npc)
        }
    }

    fun printWorld(){
        println("------------New frame-----------------")
        for (y in world.indices) {

            for (x in world[y].indices) {
                print("[${getTileAmountOfEntities(x, y)}] ")
            }
            println()
        }
    }

    fun getTileAmountOfEntities( x: Int, y: Int): Int{
        val tile = world[y][x]
        return (tile.tileHeroes.size + tile.tileMonsters.size + tile.tileObjects.size)
    }

    fun swap(npc: NPC, tPosX: Int, tPosY : Int): Boolean{
        if(npc.tilePositionX != tPosX){
            println("Change in x")
            removeNPC(npc);
            npc.tilePositionX = tPosX;
            addNPC(npc)

            return true
        }
        else if(npc.tilePositionY != tPosY){
            println("Change in Y")
            removeNPC(npc);
            npc.tilePositionY = tPosY
            addNPC(npc)
            return true
        }

        println("Not true")
        return false;
    }

    fun checkIfMonsterAdjacent(hero : Hero): Boolean{
        val x = hero.tilePositionX;
        val y = hero.tilePositionY;
        //Get tiles Up, down, left, right
        //I think atp it's best to add multiples into the combat queue

        //Does not add monsters in the same space the hero is standing on into the queue

        val monsterCombatList: MutableList<Monster> =  mutableListOf();

        if( x > 0){
            monsterCombatList +=  world[y][x-1].tileMonsters;
        }
        if(x < level.Level.levelColumns -1){
            monsterCombatList += world[y][x+1].tileMonsters;
        }
        if(y >0){
            monsterCombatList += world[y-1][x].tileMonsters;
        }
        if(y < level.Level.levelRows - 1){
            monsterCombatList += world[y+1][x].tileMonsters;
        }

        if(monsterCombatList.isNotEmpty()) {
            hero.inCombat = true;
            for (m in monsterCombatList) {
                if(hero.returnCombatQueue().contains(m)) continue

                hero.addToCombatQueue(m);
                m.addToCombatQueue(hero);
                m.inCombat = true;
            }
            //Forgot what i meant to do with the boolean
            return true;
        }
        else return false;
    }


    //Oops i didn't need a list of the monsters
    fun checkIfMonsterInRange(hero: Hero, range: Int): Boolean{
        var dis: Int;
        val targetList: MutableList<Monster> =  mutableListOf();

        when(hero.currDirection){
            Direction.LEFT -> {
                dis = max((hero.tilePositionX - range), 0)
                //get all in range
                while(dis != hero.tilePositionX){
                    targetList += world[hero.tilePositionY][dis].tileMonsters;
                    if(targetList.isNotEmpty()) return true;
                    dis++;
                }
            }
            Direction.RIGHT -> {
                dis = min((hero.tilePositionX + range), level.Level.levelColumns -1)
                while(dis != hero.tilePositionX){
                    targetList += world[hero.tilePositionY][dis].tileMonsters;
                    if(targetList.isNotEmpty()) return true;

                    dis--;
                }
            }
            Direction.UP -> {
                dis = max(0, (hero.tilePositionY - range))
                while(dis != hero.tilePositionY){
                    targetList += world[dis][hero.tilePositionX].tileMonsters;
                    if(targetList.isNotEmpty()) return true;
                    dis++;
                }
            }
            Direction.DOWN -> {
                dis = min(level.Level.levelRows -1, (hero.tilePositionY + range))
                while(dis != hero.tilePositionY){
                    targetList += world[dis][hero.tilePositionX].tileMonsters
                    if(targetList.isNotEmpty()) return true;
                    dis--;
                }
            }
            Direction.NOT_MOVING -> return false
        }
       // println("Nothing in range of ${hero.name}")
        return false;
    }

    //This could use an instance of and i could add ENEMY or HERO tag to projectiles
    //Or use a target so slime flowers can kill bugs
    //This actually runs all the time, since projectiles move even if the npc was still it can get hit
    fun checkIfProjectiles(npc : NPC){

        if(npc is Monster){
            //println("Checking for projectiles ");
            val projectiles = getProjectiles(npc.tilePositionX, npc.tilePositionY)

            for(projectile in projectiles){
                val prevHealth = npc.health
                npc.health -= projectile.strength;
                println("Collision: " +prevHealth + "->" +npc.health)

                projectile.destroyProjectile()
                if(npc.health <= 0) break;
            }
        }

    }

    fun spawnSkeletonHeadsOnTile(tilePosX: Int, tilePosY : Int){

        val tileObjects = getAllWorldObjectsFromTile(tilePosX, tilePosY);

        for(obj in tileObjects){
            if(obj is SkeletonHead){
                //TODO that's not very separation of concerns of me. I should move this elsewhere
                //There is never an instance when i want to create a monster and not add it to the list, i should start by updating that instead
                obj.spawnSkeleton(obj.tilePositionX, obj.tilePositionY);

            };
        }


    }
}
