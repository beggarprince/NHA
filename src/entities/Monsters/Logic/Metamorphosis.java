package entities.Monsters.Logic;

import entities.Monsters.Monster;
import entities.Monsters.MonsterFactory;
import entities.Monsters.MonsterList;

public class Metamorphosis {

    public static void metamorphosis(int index, String metamorphosisType, int x, int y){
        Monster l1 = MonsterFactory.createMonster(metamorphosisType, x, y);

        MonsterList.getInstance().metamorphosizeEnemy(l1, index);
    }
}
