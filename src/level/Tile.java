package level;

import graphics.TileType;

import java.util.Random;

public class Tile {
    public TileType type;
    public int x;
    public int y;
    public boolean walkable = false;
    private int nutrients = 0;
    private int mana = 0;
    private static final Random random = new Random(); // Initialized once

    public final int nutrientL2Min = 4;
    public final int nutrientL3Min = 9;


    public Tile(TileType t, int posx , int posy){
        if(t == TileType.PATH) walkable = true;
        this.type = t;
        this.x = posx;
        this.y = posy;

        initializeTileResources();
    }

    private void initializeTileResources(){
        if(type == TileType.NUTRIENT){
            nutrients = getRandomNumberInRange(1, 100);
            //TODO the mins need to be taken out of TileType and put into Tile
            if(nutrients < Level.nutrientL2Distribution) nutrients = 1;
            else if(nutrients > Level.nutrientL2Distribution && nutrients < Level.nutrientL3Distribution) nutrients = 4;
            else nutrients = 9;
        }
        else if (type == TileType.MANA){
            mana = getRandomNumberInRange(1, Level.manaScale);
        }
        else return;
    }

    private static int getRandomNumberInRange(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("Max must be greater than min");
        }
        return random.nextInt((max - min) + 1) + min;
    }

    //When creatures die or poop on tile
    public void depositMana(){
        mana++;
    }
    public void depositNutrients(){
        nutrients++;
    }

    public void eatMana(){
        mana--;
        if(mana == 0) determineTileType();
    }

    private void determineTileType() {
        if (nutrients == 0 && mana == 0) {
            type = TileType.DIRT;
        } else {
            type = nutrients > mana ? TileType.NUTRIENT : TileType.MANA;
        }
    }


    public void eatNutrients(){
        nutrients--;
        if(nutrients == 0) determineTileType();
    }

    public int getNutrients(){
        return nutrients;
    }
    public int getMana(){
        return mana;
    }

    public void digDestruct(){
        type = TileType.PATH;
        walkable = true;
    }

}