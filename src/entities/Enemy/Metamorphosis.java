package entities.Enemy;

import java.util.ArrayList;

public class Metamorphosis {

    public static void metamorphosis(int index, String metamorphosisType, int x, int y){
        Enemy l1 = EnemyFactory.createEnemy(metamorphosisType, x, y);
        EnemyList.getInstance().metamorphosizeEnemy(l1, index);
    }
}
