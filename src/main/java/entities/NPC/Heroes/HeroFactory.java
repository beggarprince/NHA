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

    public Hero createHero(String heroType, int positionx, int positiony){
        //TODO atm only implements 1 type, so there is no switch/ifelse statement
        //System.out.println("The knight's position is    " + positionx + " : " +  positiony);
        Hero hero = new Soldier(1, positionx * ScreenSettings.TILE_SIZE, positiony * ScreenSettings.TILE_SIZE);
        return hero;
    }
}
