package main.java;

import main.java.entities.NPC.Heroes.Hero;
import main.java.entities.NPC.Heroes.HeroList;

import java.util.List;
//Todo need a list of heroes and their stats to then simply add here with a script
public class HeroEntryScript {
    //Coordinate for camera to make follow animation

    //take in heroes and spawn them

    public HeroEntryScript(){

    }

    public void spawnHero(){

    }

    private void addHeroesToList(List<Hero> heroList) {
        for (Hero l1 : heroList) {
            HeroList.getInstance().addHero(l1);
        }
    }

}
