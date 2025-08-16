package Game;

import   entities.NPC.NPCType;
import level.LevelText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


//This predates the game,is made to create the level itself.
// //GameState has information on
public class LevelState {

    public class GameLevel {
         private int nutrientDistribution;
         private int manaDistribution;
         public int digPower;
         public int round =0;
         private ArrayList<ArrayList<String>> levelText;

        private ArrayList<ArrayList<HeroData>> currentLevelHeroes; //List of heroes separated by rounds

        //TODO i probably should just return this data in the call to create the level i am no long er sure what having an entire object for this data does except condense stuff
        GameLevel(int nutrientDistribution, int manaDistribution, int digPower, int level ){
            this.nutrientDistribution = nutrientDistribution;
            this.manaDistribution = manaDistribution;
            this.digPower = digPower;
            this.currentLevelHeroes = levelHeroes.get(level);
            this.levelText = LevelText.levelTextData.get(level);
        }

        //tbh this shouldn't be here
        public ArrayList<HeroData> returnCurrentRoundHeroes(int currentRound){
            return currentLevelHeroes.get(currentRound);
        }

        //The Core logic now has the data, and is responsible for using it. This get is a one time per level get
        public ArrayList<ArrayList<HeroData>> getCurrentLevelHeroes(){
            return currentLevelHeroes;
        }

        //Same for the level text
        public ArrayList<ArrayList<String>> getLevelText(){
            return levelText;
        }

        public int getNutrientDistribution() {
            System.out.println("The nutrient distribution is " + nutrientDistribution);
            return nutrientDistribution;
        }

        public int getManaDistribution() {
            System.out.println("The mana distribution is " + manaDistribution);
            return manaDistribution;
        }
    }

    public GameLevel requestLevel(int requestedLevel){
        return GAME_LEVEL.get(requestedLevel);
    }

    //This is just the heroes before we build them as Hero objects
    //We have to choose the base class, a hero name, and we can change the health if we want
    //The engine will handle this outside, reading from the hero data and creating heroes with the factory
    public class HeroData {
        public NPCType heroType;
        public int health;
        public String heroName;

        HeroData(NPCType Hero,
                 int health,
                 String heroName){
            this.heroType = Hero;
            this.health = health;
            this.heroName = heroName;

        }

    }

    //TODO find a more appropriate spot for this
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
                            new HeroData(NPCType.Warrior, 24, "Jakob"),
                            new HeroData(NPCType.Priest, 24, "Felicia")
                    )),
                    // Round 1
                    new ArrayList<>(Arrays.asList(
                            new HeroData(NPCType.Priest, 68, "Elise"),
                            new HeroData(NPCType.Warrior, 68, "Effie"),
                            new HeroData(NPCType.Joker, 68, "Arthur")
                    )),
                    //Round 2
                    new ArrayList<>(Arrays.asList(
                            //TODO add females? Tint Camilla?
                            new HeroData(NPCType.Warrior, 174, "Camilla"),
                            new HeroData(NPCType.Warrior, 34, "Selena"),
                            new HeroData(NPCType.Warrior, 68, "Beruka")
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

    //This is how we retrieve levels, these numbers are all that is needed to generate the level.
    // the arguments are nutrientDistribution, manaDistribution, digPower, and level int that will map to the appropriate element in the enemy array
    //such that we can programmatically retrieve it only knowing the hardcoded value and all the enemies will be created as such
    private final List<GameLevel> GAME_LEVEL = Arrays.asList(
            new GameLevel(85, 97, 450, 0),
            new GameLevel(75, 90, 450, 1)
    );
}
