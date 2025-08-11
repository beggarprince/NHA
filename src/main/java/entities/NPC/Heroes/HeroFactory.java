package entities.NPC.Heroes;

import entities.NPC.NPCType;
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

    public Hero createHero(NPCType heroType, int positionX, int positionY, String name){
        Hero hero;

        if(heroType == NPCType.Soldier){
            hero = new Soldier(1, positionX * ScreenSettings.TILE_SIZE,
                    positionY * ScreenSettings.TILE_SIZE, name);
        }
        else if(heroType == NPCType.Warrior){
            hero = new Warrior(1, positionX * ScreenSettings.TILE_SIZE,
                    positionY * ScreenSettings.TILE_SIZE, name);
        }
        else if(heroType == NPCType.Priest){
            hero = new Priest(1, positionX * ScreenSettings.TILE_SIZE,
                    positionY * ScreenSettings.TILE_SIZE, name);
        }
        else if(heroType == NPCType.Mage){
            hero = new Mage(1, positionX * ScreenSettings.TILE_SIZE,
                    positionY * ScreenSettings.TILE_SIZE, name);
        }
        else if(heroType == NPCType.Joker){
            hero = new Joker(1, positionX * ScreenSettings.TILE_SIZE,
                    positionY * ScreenSettings.TILE_SIZE, name);
        }
        else {
            //Soldier is the incomplete dummy class, we'll know we have one when we see it
            hero =    hero = new Soldier(1, positionX * ScreenSettings.TILE_SIZE,
                    positionY * ScreenSettings.TILE_SIZE, name);
        }
        return hero;

    }
}
