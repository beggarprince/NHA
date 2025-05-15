package main.java.entities.NPC.Heroes;

import main.java.graphics.ScreenSettings;

public class HeroFactory {
    private static HeroFactory instance;

    public static HeroFactory getInstance(){
        if(instance == null){
            instance = new HeroFactory();
        }
        return instance;
    }

    private HeroFactory(){

    }

    public Hero createHero(String heroType, int positionx, int positiony, String name){
        Hero hero = new Soldier(1, positionx * ScreenSettings.TILE_SIZE,
                positiony * ScreenSettings.TILE_SIZE, name);
        if(heroType == "Soldier"){
            return hero;
        }
        else if(heroType == "Archer"){
            //This is where we'd create diff heroes if we had any
        }
        return hero;
    }
}
