package entities.Enemy;

import java.util.ArrayList;

/*
* This class simply holds an array of enemies
* Needs to be reimplemented as a singleton class
* */
public class EnemyList {
    private static EnemyList instance;     // Singleton instance
    private  ArrayList<Enemy> enemies;      // Instance variable for the list

    // Private constructor for singleton
    private EnemyList() {
        enemies = new ArrayList<>();
    }

    // Singleton getInstance method
    public static EnemyList getInstance() {
        if (instance == null) {
            instance = new EnemyList();
        }
        return instance;
    }

    public void removeAll(){
        enemies = new ArrayList<>();
    }

    // Method to add an enemy
    public void addEnemy(Enemy enemy) {
        enemies.add(enemy);
    }

    // Method to get the list of enemies
    public synchronized ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public void removeEnemy(Enemy enemy) {
        enemies.remove(enemy);
    }


    public void updateList(){
        for(Enemy e : enemies){
            if( e.enemyIsDead) removeEnemy(e);
        }
    }

    public void metamorphosizeEnemy(Enemy e, int index){
        enemies.set(index, e);
        if(enemies.get(index) != e){
            System.out.println("Enemy was not properly set by metamorphosis function");
        }
    }

}

