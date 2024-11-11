package entities.Enemy;


public class EnemyFactory {
    public static EnemyList enemyList;

    public EnemyFactory(){
        enemyList = new EnemyList();
    }


    public void createEnemy(String type, int posx, int posy) {

        if (type.equals("Slime")) {
            Slime slime = new Slime(posx, posy);
            enemyList.addEnemy(slime);
        }

        else if(type.equals("Spirit")){
            Spirit spirit = new Spirit(posx, posy);
            enemyList.addEnemy(spirit);
        }
    }
}
