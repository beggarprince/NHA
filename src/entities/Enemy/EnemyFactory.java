package entities.Enemy;

import util.Coordinate;

public class EnemyFactory {
    public static EnemyList enemyList;

    public EnemyFactory(){
        enemyList = new EnemyList();
    }


    public void createEnemy(String type, Coordinate position) {

        if (type.equals("Slime")) {
            Slime slime = new Slime(position);
            enemyList.addEnemy(slime);
            System.out.println(enemyList.getEnemies().size());
        }
    }
}
