package level;

import graphics.TileType;

public class Tile {
    public TileType type;
    public int x;
    public int y;
    public boolean walkable = false;
    private int nutrients = 0;
    private int mana = 0;

    public Tile(TileType t, int posx , int posy){
        if(t == TileType.PATH) walkable = true;
        this.type = t;
        this.x = posx;
        this.y = posy;
    }

    public void depositMana(){
        mana++;
    }
    public void depositNutrients(){
        nutrients++;
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
