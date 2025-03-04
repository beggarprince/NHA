package main.java.level;


import main.java.graphics.ScreenSettings;

import java.util.ArrayList;
import java.util.Random;

//Purpose is to create a random array of tiles given instructions
//This is for when we don't load from a csv file
public class LevelGenerator {
    Random random;
    int nutrientDistribution;
    int manaDistribution;

    private  ArrayList<ArrayList<Integer>> initialLevel;
    //Nutrient
    public LevelGenerator(int nutrientDistribution, int manaDistribution){
        initialLevel = new ArrayList<>();
        this.nutrientDistribution = nutrientDistribution;
        this.manaDistribution = manaDistribution;
        random = new Random();
        generateLevel();
    }

    private void generateLevel() {
        for(int i = 0; i < ScreenSettings.TS_World_Y; i++){
            //TODO move the logic in setting tile nutrient/mana weight here so we don't have to go from int->Tile in the array, ATM it's in Level
            //for now this will NOT add tile distributions
            ArrayList<Integer> row = new ArrayList<>();

            for( int j = 0; j < ScreenSettings.TS_World_X; j++){
                row.add(setTile());
            }
            initialLevel.add(row);
        }

    }

    public ArrayList<ArrayList<Integer>> returnLevel(){
        return initialLevel;
    }

    //The logic is 0 < nutrient < mana
    //Value is mana unless lower than manaDistribution, then it's nutrient unless lower than value distribution, then defaults to dirt
    private Integer setTile() {
        //Set tile value to an int, then determine type based on the int.
        int tileValue = getRandomNumberInRange(0, 100);
       if(tileValue >= manaDistribution) return 4;
       if(tileValue >= nutrientDistribution) return 1;
       return 0;
    }

    private int getRandomNumberInRange(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("Max must be greater than min");
        }
        return random.nextInt((max - min) + 1) + min;
    }

}
