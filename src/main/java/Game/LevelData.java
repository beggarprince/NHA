package main.java.Game;

import main.java.entities.NPC.Heroes.Hero;
import main.java.entities.NPC.Heroes.Soldier;
import main.java.entities.NPC.NPCType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LevelData {

    public static class GameLevel {
        int nutrientDistribution;
        int manaDistribution;
        int digPower;
        GameLevel(int nutrientDistribution, int manaDistribution, int digPower){
            this.nutrientDistribution = nutrientDistribution;
            this.manaDistribution = manaDistribution;
            this.digPower = digPower;
        }
    }

    public static class LevelHeroData{
        NPCType Hero;
        int health;
        String heroName;
        LevelHeroData(NPCType Hero, int health, String heroName){
            this.Hero = Hero;
            this.health = health;
            this.heroName = heroName;
        }
    }


    public final static List<GameLevel> gameLevelList = Arrays.asList(
            new GameLevel(85, 97, 450),
            new GameLevel(75, 90, 450)
    );

    public final static ArrayList<ArrayList<LevelHeroData>> level1Heroes = new ArrayList<>(){
        {
                new LevelHeroData(NPCType.Soldier, 24, "Max");
                new LevelHeroData(NPCType.Soldier, 24, "Min");
                new LevelHeroData(NPCType.Soldier, 24, "Fj");
        }

    };
}
