package Game;

import entities.Heroes.Hero;
import entities.Heroes.HeroList;
import entities.Monsters.Monster;
import entities.Monsters.MonsterList;

import java.util.List;

import static entities.Monsters.Logic.Metamorphosis.metamorphosis;

public class NPCLogic {

    MonsterList monsterList;
    HeroList heroList;
    SpatialHash spatialHash;

    public NPCLogic(){
        heroList = HeroList.getInstance();
        monsterList =  MonsterList.getInstance();
        spatialHash = SpatialHash.getInstance();
    }

    public void runEnemyBehavior() {

            List<Monster> enemies = monsterList.getMonsters();
            for (int i = 0; i < enemies.size(); i++) {
                Monster e = enemies.get(i);
                spatialHash.updateNPCZone(e);
                e.behavior();
            }

    }

    public void runHeroBehavior() {

            List<Hero> heroes = heroList.getHeroes();
            for (int i = 0; i < heroes.size(); i++) {
                heroes.get(i).behavior();
            }

    }


    public void checkSetMetamorphosis(){

        for(int i = 0; i < MonsterList.getInstance().getMonsters().size(); i++){
            Monster e = MonsterList.getInstance().getMonsters().get(i);
            if(e.metamorphosisReady){
                metamorphosis(i, e.metamorphosisValue, e.screenPositionX, e.screenPositionY);

            }
        }

    }

    public void run() {
        runEnemyBehavior();
        runHeroBehavior();
        checkSetMetamorphosis();
    }
}
