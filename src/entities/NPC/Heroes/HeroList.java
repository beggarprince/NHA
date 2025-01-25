package entities.NPC.Heroes;

import java.util.ArrayList;

public class HeroList {
    private static HeroList instance;


    private ArrayList<Hero> heroes;

    private HeroList(){
        heroes = new ArrayList<>();
    }

    public static HeroList getInstance(){
        if(instance == null){
            instance = new HeroList();
        }
        return instance;
    }

    public void addHero(Hero hero){
        heroes.add(hero);
    }

    public ArrayList<Hero> getHeroes(){
        return heroes;
    }

    public void destroyHeroes(){
        for(int i = 0 ; i < heroes.size(); i++){
            if(heroes.get(i).isDead){
                Hero h = heroes.get(i);
                heroes.get(i).destroy();
                removeHero(h);
            }
        }
    }

    private void removeHero(Hero h) {
        heroes.remove(h);
    }
}
