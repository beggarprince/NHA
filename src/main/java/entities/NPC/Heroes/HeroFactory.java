package entities.NPC.Heroes;

import Game.LevelState;
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

    public Hero createHero(LevelState.HeroData heroData, int positionX, int positionY){
        Hero hero;
        var heroType = heroData.heroType;

        if(heroType == NPCType.Soldier){
            hero = new Soldier(heroData.health, positionX * ScreenSettings.TILE_SIZE,
                    positionY * ScreenSettings.TILE_SIZE, heroData.heroName);
        }
        else if(heroType == NPCType.Warrior){
            hero = new Warrior(heroData.health , positionX * ScreenSettings.TILE_SIZE,
                    positionY * ScreenSettings.TILE_SIZE, heroData.heroName);
        }
        else if(heroType == NPCType.Priest){
            hero = new Priest(heroData.health, positionX * ScreenSettings.TILE_SIZE,
                    positionY * ScreenSettings.TILE_SIZE, heroData.heroName);
        }
        else if(heroType == NPCType.Mage){
            hero = new Mage(heroData.health, positionX * ScreenSettings.TILE_SIZE,
                    positionY * ScreenSettings.TILE_SIZE, heroData.heroName);
        }
        else if(heroType == NPCType.Joker){
            hero = new Joker(heroData.health, positionX * ScreenSettings.TILE_SIZE,
                    positionY * ScreenSettings.TILE_SIZE, heroData.heroName);
        }
        else {
            //Soldier is the incomplete dummy class, we'll know we have one when we see it
            hero =    hero = new Soldier(1, positionX * ScreenSettings.TILE_SIZE,
                    positionY * ScreenSettings.TILE_SIZE, heroData.heroName);
        }
        return hero;

    }
}
