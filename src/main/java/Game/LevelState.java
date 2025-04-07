package main.java.Game;

import main.java.entities.NPC.NPCType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LevelState {

    public class GameLevel {
         public int nutrientDistribution;
         public int manaDistribution;
         public int digPower;
        ArrayList<ArrayList<HeroData>> currentLevelHeroes; //List of heroes separated by rounds

        GameLevel(int nutrientDistribution, int manaDistribution, int digPower, int level ){
            this.nutrientDistribution = nutrientDistribution;
            this.manaDistribution = manaDistribution;
            this.digPower = digPower;

            this.currentLevelHeroes = levelHeroes.get(level);
        }

        public  ArrayList<HeroData> returnCurrentRoundHeroes(int currentRound){
            return currentLevelHeroes.get(currentRound);
        }

    }

    public GameLevel requestLevel(int requestedLevel){
        return GAME_LEVEL.get(requestedLevel);
    }

    //This is just the heroes before we build them as Hero objects
    //We have to choose the base class, a hero name, and we can change the health if we want
    //The engine will handle this outside, reading from the hero data and creating heroes with the factory
    public class HeroData {
        NPCType Hero;
        int health;
        String heroName;

        HeroData(NPCType Hero, int health, String heroName){
            this.Hero = Hero;
            this.health = health;
            this.heroName = heroName;
        }
    }


    //This is level initialization settings. It tells the engine
    // How much nutrient vs mana and by extension empty blocks there will be
    //Player starting dig power
    //And points to the correct hero list in the hero data
    //It's odd but you have to point to a game level in the list and initialize game level to game level in said list
    public final ArrayList<ArrayList<ArrayList<HeroData>>> levelHeroes = new ArrayList<>(Arrays.asList(
            // Level 0
            new ArrayList<>(Arrays.asList(
                    // Round 0
                    new ArrayList<>(Arrays.asList(
                            new HeroData(NPCType.Soldier, 24, "Max"),
                            new HeroData(NPCType.Soldier, 24, "Min")
                    )),
                    // Round 1
                    new ArrayList<>(Arrays.asList(
                            new HeroData(NPCType.Soldier, 68, "Max"),
                            new HeroData(NPCType.Soldier, 68, "Min"),
                            new HeroData(NPCType.Soldier, 68, "Fin")
                    ))
            )),
            // Level 1
            new ArrayList<>(Arrays.asList(
                    // Round 0
                    new ArrayList<>(Arrays.asList(
                            new HeroData(NPCType.Soldier, 124, "MaxL2"),
                            new HeroData(NPCType.Soldier, 124, "MinL2")
                    )),
                    // Round 1
                    new ArrayList<>(Arrays.asList(
                            new HeroData(NPCType.Soldier, 168, "MaxL2"),
                            new HeroData(NPCType.Soldier, 168, "MinL2"),
                            new HeroData(NPCType.Soldier, 168, "FinL2")
                    ))
            ))
    ));

    private final List<GameLevel> GAME_LEVEL = Arrays.asList(
            new GameLevel(85, 97, 450, 0),
            new GameLevel(75, 90, 450, 1)
    );
}
