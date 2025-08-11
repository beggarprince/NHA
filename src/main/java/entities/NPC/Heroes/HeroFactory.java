package entities.NPC.Heroes;

import   graphics.ScreenSettings;

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

    public Hero createHero(String heroType, int positionX, int positionY, String name){
        Hero hero;

        if(heroType == "Soldier"){
            hero = new Soldier(1, positionX * ScreenSettings.TILE_SIZE,
                    positionY * ScreenSettings.TILE_SIZE, name);
            return hero;
        }
        else if(heroType == "Warrior"){
             hero = new Warrior(1, positionX * ScreenSettings.TILE_SIZE,
                    positionY * ScreenSettings.TILE_SIZE, name);
            return hero;
        }
        else if(heroType == "Archer"){
            //This is where we'd create diff heroes if we had any
        }
        return  new Warrior(1, positionX * ScreenSettings.TILE_SIZE,
                positionY * ScreenSettings.TILE_SIZE, name);
    }
}
