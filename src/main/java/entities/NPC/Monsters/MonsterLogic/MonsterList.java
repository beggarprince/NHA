package main.java.entities.NPC.Monsters.MonsterLogic;

import main.java.Game.SpatialHash;
import main.java.entities.NPC.Monsters.*;

import java.util.ArrayList;

/*
* This class simply holds an array of enemies
* Needs to be reimplemented as a singleton class
* */
public class MonsterList {
    private static MonsterList instance;     // Singleton instance


    //TODO change this into a 2d array with linkedlist so we can just check tile XY instead of iterating throughout the entire list
    private  ArrayList<Monster> monsters;      // Instance variable for the list

    //These are made so we can quickly access specified monsters
//    private static ArrayList<Slime> SlimeList;
//    private static ArrayList<Bug> BugList;
//    private static ArrayList<LizardMan> LizardManList;
//    private static ArrayList<Spirit> SpiritList;
//    private static ArrayList<Lilith> LilithList;
//    private static ArrayList<Dragon> DragonList;
//    private static ArrayList<Skeleton> SkeletonList;

    private SpatialHash init;
    // Private constructor for singleton

    private MonsterList() {
        monsters = new ArrayList<>();
        init = SpatialHash.getInstance();
    }

    // Singleton getInstance method
    public static MonsterList getInstance() {
        if (instance == null) {
            instance = new MonsterList();
        }
        return instance;
    }

    public void removeAll(){
        monsters = new ArrayList<>();
    }

    // Method to add an enemy
    public void addEnemy(Monster enemy) {
        monsters.add(enemy);
        init.hash(enemy);
    }

    // Method to get the list of enemies
    public synchronized ArrayList<Monster> getMonsters() {
        return monsters;
    }

    public void removeEnemy(Monster enemy) {
        monsters.remove(enemy);
    }


    public void updateList(){
        for(Monster e : monsters){
            if( e.isDead) removeEnemy(e);
        }
    }

    //TODO implement a metamorphosis list that just holds the indexes so we can iterate that instead of the array
    public void metamorphosizeEnemy(Monster e, int index){
        monsters.set(index, e);
        if(monsters.get(index) != e){
            System.out.println("Enemy was not properly set by metamorphosis function");
        }
    }

//I just changed it from MonsterList.getInstance().getMonsters.get to monsters bc it's a class member
    public void destroyEnemies(){
        for(int i = 0; i < monsters.size(); i++){
            if(monsters.get(i).isDead){
                Monster e = monsters.get(i);
                //System.out.println("Destroying " + e.returnNpcType());
                monsters.get(i).destroy();
                removeEnemy(e);

            }
        }
    }

}

