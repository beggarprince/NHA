package world

import entities.NPC.Heroes.Hero
import entities.NPC.Monsters.Monster
import entities.NPC.NPC
import entities.WorldObjects.WorldObject
import java.util.LinkedList

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


    private var x: Int = 0
    private var y:Int = 0

    fun initialize(){

        x = level.Level.levelColumns
        y = level.Level.levelRows

        world = Array(y) { Array<TileEntities>(x) {
            TileEntities(
                mutableSetOf(),
                mutableSetOf(),
                mutableSetOf()
            )
        } }
    }

    fun addHeroToTile(hero: Hero){
        world[hero.tilePositionY][hero.tilePositionX].tileHeroes.add(hero);
    }

    fun removeHeroFromTile(hero: Hero){
        world[hero.tilePositionY][hero.tilePositionX].tileHeroes.remove(hero);
    }

    fun addMonsterToTile(monster: Monster){
        world[monster.tilePositionY][monster.tilePositionX].tileMonsters.add(monster)
    }

    fun removeMonsterFromTile(monster: Monster){
        world[monster.tilePositionY][monster.tilePositionX].tileMonsters.remove(monster)
    }

    fun addObjectToTile(obj: WorldObject){
        world[obj.tilePositionY][obj.tilePositionY].tileObjects.add(obj)
    }

    fun removeObjectFromTile(obj: WorldObject){
        world[obj.tilePositionY][obj.tilePositionY].tileObjects.remove(obj)
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
        if(npc is Hero){
            removeHeroFromTile(npc)
        }
        else if(npc is Monster){
            removeMonsterFromTile(npc)
        }
    }

    fun addNPC(npc: NPC){
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

}