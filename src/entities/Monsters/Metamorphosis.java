package entities.Monsters;

public class Metamorphosis {

    public static void metamorphosis(int index, String metamorphosisType, int x, int y){
        Monster l1 = MonsterFactory.createMonster(metamorphosisType, x, y);

        MonsterList.getInstance().metamorphosizeEnemy(l1, index);
    }
}