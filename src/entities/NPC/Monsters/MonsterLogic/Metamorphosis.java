package entities.NPC.Monsters.MonsterLogic;

import entities.NPC.Monsters.Monster;
import entities.NPC.Monsters.MonsterFactory;
import entities.NPC.Monsters.MonsterList;

public class Metamorphosis {

    public static void metamorphosis(int index, String metamorphosisType, int x, int y){
        Monster l1 = MonsterFactory.createMonster(metamorphosisType, x, y);

        MonsterList.getInstance().metamorphosizeEnemy(l1, index);
    }
}
