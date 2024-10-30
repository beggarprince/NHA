package entities.Enemy;

import java.util.ArrayList;

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

    // Optional: Method to remove an enemy
    public void removeEnemy(Enemy enemy) {
        enemies.remove(enemy);
    }
}

