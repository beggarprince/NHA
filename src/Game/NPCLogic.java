package Game;

import entities.Heroes.Hero;
import entities.Heroes.HeroList;
import entities.Monsters.Monster;
import entities.Monsters.MonsterList;
import entities.NPC;

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

    private void runEnemyBehavior(List<Monster> enemies) {

            for (int i = 0; i < enemies.size(); i++) {
                Monster e = enemies.get(i);
                spatialHash.updateNPCZone(e);
                e.genericBehavior();
            }

    }

    private void runHeroBehavior(List<Hero> heroes, List<Monster> monsters) {

            for (int i = 0; i < heroes.size(); i++) {
                checkCollisionsMonsters(heroes.get(i), monsters);
                heroes.get(i).genericBehavior();
            }

    }


    private void checkSetMetamorphosis(){

        for(int i = 0; i < MonsterList.getInstance().getMonsters().size(); i++){
            Monster e = MonsterList.getInstance().getMonsters().get(i);
            if(e.metamorphosisReady){
                metamorphosis(i, e.metamorphosisValue, e.screenPositionX, e.screenPositionY);

            }
        }

    }

    private void checkCollisions(NPC npc){

    }

    private void checkCollisionsEAT(Monster monster){

    }

    //Heroes initiate combat
    private void checkCollisionsMonsters(Hero hero, List<Monster> monsters){

        for(int i = 0; i < monsters.size(); i++){
            if(checkAdjacent(monsters.get(i), hero)){

                monsters.get(i).inCombat = true;
                hero.inCombat = true;
                monsters.get(i).addToCombatQueue(hero);
                hero.addToCombatQueue(monsters.get(i));
            }

        }
    }

    private boolean checkAdjacent(NPC npc, NPC target){

        //If on the same axis
        if (npc.worldPositionX == target.worldPositionX) {
            if (npc.worldPositionY == target.worldPositionY + 1 || npc.worldPositionY == target.worldPositionY - 1) {
                return true;
            }
        }
        if (npc.worldPositionY == target.worldPositionY) {
            if (npc.worldPositionX == target.worldPositionX + 1 || npc.worldPositionX == target.worldPositionX - 1) {
                return true;
            }
        }
        return false;
    }

    public void run() {

        List<Monster> monsters = monsterList.getMonsters();
        List<Hero> heroes = heroList.getHeroes();
        //Here we should split up the task into 3 separate threads and check for collision

        runHeroBehavior(heroes, monsters);
        runEnemyBehavior(monsters);

        checkSetMetamorphosis();
    }
}
