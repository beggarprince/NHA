package entities.Enemy;

import java.util.ArrayList;


public class EnemyList {
    private static EnemyList instance;     // Singleton instance
    private ArrayList<Enemy> enemies;      // Instance variable for the list

    // Private constructor for singleton
    public EnemyList() {
        enemies = new ArrayList<>();
    }

    // Singleton getInstance method
    public static EnemyList getInstance() {
        if (instance == null) {
            instance = new EnemyList();
        }
        return instance;
    }

    // Method to add an enemy
    public void addEnemy(Enemy enemy) {
        enemies.add(enemy);
    }

    // Method to get the list of enemies
    public ArrayList<Enemy> getEnemies() {
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
}

